package com.auctionapp;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class TCPServer {
    private static ConcurrentHashMap<String, Double> userBids = new ConcurrentHashMap<>();
    private static List<Double> bidList = new ArrayList<>();
    private static double highestBid = 0;

    public static void main(String[] args) {
        try {
            // Creează un server socket care ascultă pe portul 6789
            ServerSocket welcomeSocket = new ServerSocket(6789);
            System.out.println("The bidding session has started");

            while (true) {
                // Așteaptă pentru conexiuni de la clienți
                Socket connectionSocket = welcomeSocket.accept();
                new ClientHandler(connectionSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String clientName;
        private Double clientAmount;
        private BufferedReader inFromClient;  
        private DataInputStream dataInFromClient;
        private DataOutputStream outToClient;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Creează fluxuri de intrare și ieșire pentru comunicare
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                dataInFromClient = new DataInputStream(socket.getInputStream());
                outToClient = new DataOutputStream(socket.getOutputStream());

                // Citește mesajul de la client
                clientName = inFromClient.readLine();
                clientAmount = dataInFromClient.readDouble();
                System.out.println("Received: " + clientName + " amount " + clientAmount);

                outToClient.writeBytes("You are logged in. Starting countdown...\n");

                // Salvare user și suma inițială
                userBids.put(clientName, clientAmount);

                // Începem licitația
                startAuction();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void startAuction() {
            try {
                Product product = new Product();
                Scanner scan = new Scanner(System.in);
                product.listItem(scan);
                outToClient.writeBytes(product.messageToTheUsers());

                boolean continueBidding = true;

                while (continueBidding) {
                    final int countdown = 20;

                    Thread countdownThread = new Thread(new Runnable() {
                        public void run() {
                            try {
                                for (int i = countdown; i >= 0; i--) {
                                    outToClient.writeBytes("Countdown: " + i + " seconds remaining\n");
                                    Thread.sleep(1000);
                                }
                            } catch (InterruptedException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    countdownThread.start();

                    outToClient.writeBytes("Enter your bid : ");
                    int bid = dataInFromClient.readInt();

                    if (bid == -1) {
                        continueBidding = false;
                        break;
                    }

                    synchronized (bidList) {
                        bidList.add((double) bid);
                        if (bid > highestBid) {
                            highestBid = bid;
                            outToClient.writeBytes("New highest bid: " + highestBid + "\n");
                        }
                    }

                    try {
                        countdownThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    outToClient.writeBytes("The highest bid so far is: " + highestBid + "\n");
                    outToClient.writeBytes("Do you want to place a higher bid? (y/n): ");
                    String response = inFromClient.readLine();

                    if (response.equalsIgnoreCase("n")) {
                        continueBidding = false;
                    }
                }

                outToClient.writeBytes("Auction ended. The highest bid was: " + highestBid + "\n");
                outToClient.writeBytes("Thank you for participating!\n");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

