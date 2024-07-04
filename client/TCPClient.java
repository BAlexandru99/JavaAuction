import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Creează un socket client și se conectează la server pe portul 6789
            Socket clientSocket = new Socket("localhost", 6789);
            
            // Creează fluxuri de intrare și ieșire pentru comunicare
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Citește mesajul de la utilizator
            System.out.print("Enter the credetials: ");
            String fullName = inFromUser.readLine();
            System.out.print("The amount of money with which you want to participate in the auctions: ");
            Double sum = Double.parseDouble(inFromUser.readLine());

            // Trimite mesajul la server
            outToServer.writeBytes(fullName + '\n');
            outToServer.writeDouble(sum);

            // Primește răspunsul de la server
            String modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);

            // Închide socket-ul client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
