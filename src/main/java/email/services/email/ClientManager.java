package email.services.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import email.services.email.interfaces.IClient;
import email.services.email.interfaces.IClientManager;

public class ClientManager implements IClientManager {
    private ArrayList<Client> clients = new ArrayList<>();
    private File file;

    public ClientManager(File file) throws IOException {
        this.file = file;
        if (!file.exists()) {
            file.createNewFile();
        }
        loadClientsFromFile();
    }

    private void loadClientsFromFile() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            clients.clear();
            while ((line = fileReader.readLine()) != null) {
                Client client = Client.getClientParsed(line);
                if (client != null) {
                    clients.add(client);
                }
            }
        }
    }

    public void addClient(Client client) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true))) {
            fileWriter.write(client.getParsedClientString());
            fileWriter.newLine();
        }
        clients.add(client);
    }

    public void removeClient(IClient client) throws IOException {
        this.clients.remove(client);
        rewriteClientsToFile();
    }

    private void rewriteClientsToFile() throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, false))) {
            for (Client client : clients) {
                fileWriter.write(client.getParsedClientString());
                fileWriter.newLine();
            }
        }
    }

    public void printClients() {
        clients.forEach(client -> System.out.println(client.getParsedClientString()));
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public String getClientEmailByFirstName(String firstName) {
        return clients.stream()
                .filter(client -> client.getFirstName().equals(firstName))
                .map(Client::getEmail)
                .findFirst()
                .orElse(null);
    }

    public void updateCurrentFile(File newFile) throws IOException {
        this.file = newFile;
        if (!newFile.exists()) {
            newFile.createNewFile();
        }
        loadClientsFromFile();
    }
}
