package email.ui.serviceComponentsUI.email;

import email.services.email.Client;
import email.services.email.interfaces.IClient;
import javafx.scene.control.Label;

public class ClientLabel extends Label implements IClient {
    private Client client;

    public ClientLabel(Client client) {
        this.client = client;
        this.setText(client.getDisplayName());
    }

    @Override
    public String getName() {
        return client.getName();
    }

    @Override
    public String getFirstName() {
        return client.getFirstName();
    }

    @Override
    public String getEmail() {
        return client.getEmail();
    }

    @Override
    public String getPublicKey() {
        return client.getPublicKey();
    }

    @Override
    public String getPrivateKey() {
        return client.getPrivateKey();
    }
}
