import java.net.*;
import java.io.*;

// Does not work, because the Message.java in this directory is different
// than the one from ChatClient.
// To fix, implement packaging in ChatClient
//import ChatClient.Message;

public class LocalTest {
    public static void main(String[] args) throws IOException { System.out.println("Awaiting client...");

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
                // http://stackoverflow.com/a/7586021/805556
                out.flush();
                System.out.println("Connected.");
                Message received; // from the client
    
                // Test Messages to send
                int currentMsg = 0;
                Message[] testMessages = new Message[6];
                testMessages[0] = 
                    new Message("How is it going?", 2, 1, 1000L);
                testMessages[1] = 
                    new Message("How is the weather over there?", 2, 1, 1000L);
                testMessages[2] = 
                    new Message("I'm feeling slightly ill this evening, thank you for asking.", 2, 1, 1000L);
                testMessages[3] = 
                    new Message("Ah, I will just take a nice hot bath and be fine again.", 2, 1, 1000L);
                testMessages[4] = 
                    new Message("I will also probably read the Times. Do you read any newspapers?", 2, 1, 1000L);
                testMessages[5] = 
                    new Message("I'm sorry, someone was at the door. Where were we?", 2, 1, 1000L);

                // Infinite loop, interrupt by Ctrl-C
                while (true) {
                    // Get the Object in the input stream, and
                    // typecast it into a Message
                    received = (Message) in.readObject();
                    System.out.println("Client: " + received);

                    // Pause for 2 seconds
                    Thread.sleep(1000);

                    Message testMsg = testMessages[currentMsg];
                    currentMsg++;
                    if (currentMsg >= testMessages.length)
                        currentMsg = 0;

                    // Send test message to client
                    out.writeObject(testMsg);

                
                }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
