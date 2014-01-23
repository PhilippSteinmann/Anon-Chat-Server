/*
 * class LOCALTEST
 * Runs local server that periodically sends messages
*/

import java.net.*;
import java.io.*;

public class LocalTest {
    public static void main(String[] args) throws IOException {

        System.out.println("connecting to client...");

        // Called a 'try-with-resources expression.
        try (
            // Create the socket of the server, listening to port 9090
            ServerSocket serverSocket = 
                new ServerSocket(9090, 0, InetAddress.getByName(null)); // localhost

            // Through the serverSocket, accept the socket to the client
            Socket clientSocket = serverSocket.accept();

            // Used to send mssages
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

            // Used to receive messages
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        )  {
                System.out.println("connected.");
                String inputLine;
                while (true) { // infinite, interrupt by Ctrl-C
                    inputLine = in.readLine(); // read messages in socket
                    if (inputLine != null) // if not empty
                        System.out.println("Received: " + inputLine);
                    out.println("what's up?"); // send message to client
                    Thread.sleep(2000); // wait 2 seconds
                
                }
        } catch (Exception e) {
            System.out.println("There was an Exception.");
        }

    }
}
