import java.util.ArrayList;

public class ChatHistory {
    private ArrayList<String> chatHistory;

    public ChatHistory() {
        chatHistory = new ArrayList<>();
    }

    public void addMessage(String message) {
        chatHistory.add(message);
    }

    public void viewChatHistory() {
        if (chatHistory.isEmpty()) {
            System.out.println("No chat history available.");
        } else {
            System.out.println("Chat History:");
            for (String message : chatHistory) {
                System.out.println(message);
            }
        }
    }
}
