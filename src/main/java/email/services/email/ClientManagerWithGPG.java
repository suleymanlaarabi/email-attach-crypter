package email.services.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import email.services.email.interfaces.IClient;
import email.services.email.interfaces.IClientManager;
import javafx.scene.control.Alert;

public class ClientManagerWithGPG implements IClientManager {

  private ArrayList<Client> clients = new ArrayList<>();

  Runtime runtime = Runtime.getRuntime();
  String gpgPath = "\"C:\\Program Files (x86)\\GnuPG\\bin\\gpg\"";

  public ClientManagerWithGPG() throws IOException {
    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
      gpgPath = "gpg";
    }
    this.updateClientsFromGPG();
  }

  public void addClient(Client client) throws IOException {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Add client is unavailable in client manager with GPG");
    alert.setContentText("Please use GPG to add a client");
    alert.showAndWait();
    throw new IOException("Add client is unavailable in client manager with GPG");
  }

  public void removeClient(IClient client) throws IOException {
    String[] args = new String[] { gpgPath, "--delete-key", client.getEmail() };
    Process process = runtime.exec(args);
    process.destroy();
    this.clients.remove(client);
  }

  public void printClients() {
    for (Client client : clients) {
      System.out.println(client);
    }
  }

  public ArrayList<Client> getClients() {
    return clients;
  }

  public String getClientEmailByFirstName(String firstName) {
    for (Client client : clients) {
      if (client.getFirstName().equals(firstName)) {
        return client.getEmail();
      }
    }
    return null;
  }

  private void updateClientsFromGPG() {
    clients.clear();
    String[] args = new String[] { gpgPath, "--list-keys" };
    try {
      Process process = runtime.exec(args);
      process.waitFor();

      InputStream inputStream = process.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains("uid")) {
          String email = line.split("<")[1].split(">")[0];
          String displayName = line.split("]")[1].split("<")[0].trim();
          String firstName = displayName.split(" ")[0];
          String lastName = "";
          if (displayName.split(" ").length > 1) {
            lastName = displayName.split(" ")[1];
          } else {
            lastName = "Undefined";
          }
          clients.add(new Client(lastName, firstName, email));
        }
      }

      process.destroy();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

}
