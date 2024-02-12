package email.services.email;

import email.services.email.interfaces.IClient;

public class Client implements IClient {
    private String name;
    private String firstName;
    private String email;
    // Assuming these fields are not set in the provided snippet, so including
    // setters for these.
    private String publicKey;
    private String privateKey;

    // Constructor to initialize client with name, firstName, and email
    public Client(String name, String firstName, String email) {
        this.name = name;
        this.firstName = firstName;
        this.email = email;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    // Setter methods for keys, assuming these might be set after instantiation
    public void setPublicKey(String newPublicKey) {
        this.publicKey = newPublicKey;
    }

    public void setPrivateKey(String newPrivateKey) {
        this.privateKey = newPrivateKey;
    }

    // Returns a semicolon-separated representation of the client
    public String getParsedClientString() {
        return String.format("%s;%s;%s;", name, firstName, email);
    }

    // Static method to parse a string into a Client object
    public static Client getClientParsed(String clientString) {
        String[] parts = clientString.split(";");
        if (parts.length != 3)
            return null;
        return new Client(parts[0], parts[1], parts[2]);
    }

    // Returns the display name of the client
    public String getDisplayName() {
        return String.format("%s %s", firstName, name);
    }
}
