package email.services.email.interfaces;

import java.io.IOException;
import java.util.ArrayList;

import email.services.email.Client;

public interface IClientManager {
  void addClient(Client client) throws IOException;

  void removeClient(IClient client) throws IOException;

  void printClients();

  ArrayList<Client> getClients();

  String getClientEmailByFirstName(String firstName);

}
