import java.net.*;
import java.io.*;

// Import Message.java from 'clients' submodule.
// You have to add `package client` to the top of Message.java

import ChatClient.Message;

public class LocalTest {
    public static void main(String[] args) throws IOException {

        System.out.println("Connecting to client...");

        // Called a 'try-with-resources' expression.
        // If it throws an error, make sure you have Java 7.
        try (
        
            // Create a new ServerSocket, which is used to create the connection
            // to the client. We're listening to port 9090 on localhost.
            ServerSocket serverSocket = 
                new ServerSocket(9090, 0, InetAddress.getByName(null));

            // Create a connection to the client
            Socket clientSocket = serverSocket.accept();

            // Used to send objects
            ObjectOutputStream out =
                new ObjectOutputStream(clientSocket.getOutputStream());                   

            // Used to receive objects
            ObjectInputStream in = new ObjectInputStream(
                clientSocket.getInputStream());
        )  {
                System.out.println("Connected.");
                Message received; // from the client
    
                // Test Message to send
                Message ping = new Message("What's up?", 2, 1, 1000L);

                // Infinite loop, interrupt by Ctrl-C
                while (true) {
                    // Get the Object in the input stream, and
                    // typecast it into a Message
                    received = (Message) in.readObject();
                    if (received != null)
                        System.out.println("Received: " + received);

                    // Send test message to client
                    out.writeObject(ping);

                    // Pause for 2 seconds
                    Thread.sleep(2000);
                
                }
        } catch (Exception e) {
            System.out.println("There was an Exception.");
        }

    }
}
