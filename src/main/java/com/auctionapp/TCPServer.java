package com.auctionapp;
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {


        try {
            // Creează un server socket care ascultă pe portul 6789
            ServerSocket welcomeSocket = new ServerSocket(6789);
            System.out.println("The bidding session has started");

            while (true) {
                // Așteaptă pentru conexiuni de la clienți
                Socket connectionSocket = welcomeSocket.accept();
                
                // Creează fluxuri de intrare și ieșire pentru comunicare
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataInputStream dataInFromClient = new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                // Citește mesajul de la client
                String clientName = inFromClient.readLine();
                Double clientAmount = dataInFromClient.readDouble();
                System.out.println("Received: " + clientName + " amount " + clientAmount);

                outToClient.writeBytes("You are logged in. Starting countdown...\n");

                ThreadAction thread = new ThreadAction(100);
                new Thread(thread).start();

                ThreadSave threadSave = new ThreadSave(clientName , clientAmount);
                new Thread(threadSave).start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

