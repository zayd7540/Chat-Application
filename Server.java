import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Contact contactList;
    private ChatHistory chatHistory;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        contactList = new Contact();
        chatHistory = new ChatHistory();
    }

    public void start() throws IOException {
        clientSocket = serverSocket.accept();
        System.out.println("Client connected.");

        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());

        new Thread(this::receiveMessages).start();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> sendMessage(scanner);
                    case 2 -> addContact(scanner);
                    case 3 -> removeContact(scanner);
                    case 4 -> viewChatHistory();
                    case 5 -> exit();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private void showMenu() {
        System.out.println("\nServer Menu:");
        System.out.println("1. Send Message");
        System.out.println("2. Add Contact");
        System.out.println("3. Remove Contact");
        System.out.println("4. View Chat History");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private void sendMessage(Scanner scanner) {
        System.out.print("Enter message to send: ");
        String message = scanner.nextLine();

        try {
            out.writeObject("Server: " + message);
            out.flush();
            chatHistory.addMessage("You: " + message);
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    private void addContact(Scanner scanner) {
        System.out.print("Enter contact name to add: ");
        String name = scanner.nextLine();
        contactList.addContact(name);
    }

    private void removeContact(Scanner scanner) {
        System.out.print("Enter contact name to remove: ");
        String name = scanner.nextLine();
        contactList.removeContact(name);
    }

    private void viewChatHistory() {
        chatHistory.viewChatHistory();
    }

    private void receiveMessages() {
        try {
            while (true) {
                String message = (String) in.readObject();
                System.out.println(message);
                chatHistory.addMessage(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection closed.");
        }
    }

    private void exit() {
        try {
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server disconnected.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(12345);
            server.start();
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
