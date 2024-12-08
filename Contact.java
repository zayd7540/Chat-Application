import java.util.ArrayList;

public class Contact {
    private ArrayList<String> contacts;

    public Contact() {
        contacts = new ArrayList<>();
    }

    public void addContact(String name) {
        if (!contacts.contains(name)) {
            contacts.add(name);
            System.out.println("Contact added: " + name);
        } else {
            System.out.println("Contact already exists.");
        }
    }

    public void removeContact(String name) {
        if (contacts.contains(name)) {
            contacts.remove(name);
            System.out.println("Contact removed: " + name);
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("Contacts:");
            for (String contact : contacts) {
                System.out.println("- " + contact);
            }
        }
    }
}
