package email.ui.list;

import email.services.email.Client;
import email.services.email.ClientManagerWithGPG;
import email.ui.Home;
import email.ui.serviceComponentsUI.email.ClientLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecipientList extends VBox {

    // dependencies
    private Home home;

    // components
    private TextField searchField = new TextField();
    private ListView<ClientLabel> clientListView = new ListView<ClientLabel>();
    private HBox buttonBox = new HBox(10);
    private Button removeRecipientButton = new Button("Remove");
    private Button toggleClientManagerButton = new Button("Toggle Client Manager");
    private Text clientManagerUsedText = new Text("Client manager with GPG is used");

    public RecipientList(Home home) {
        // init attributes
        super(10);
        this.home = home;

        // init this component style
        this.setAlignment(Pos.CENTER);
        this.setMaxHeight(240);
        this.setMaxWidth(330);
        this.setMinWidth(330);

        // init components
        this.searchField.setPromptText("Search");
        this.buttonBox.setTranslateY(-23);
        this.buttonBox.setAlignment(Pos.CENTER);

        // init events and render components
        this.init();
        this.initEvents();
    }

    private void init() {
        this.updateClientListView();
        this.buttonBox.getChildren().addAll(this.toggleClientManagerButton, this.removeRecipientButton);
        this.getChildren().addAll(this.clientManagerUsedText, this.searchField, this.clientListView, this.buttonBox);
    }

    private void initEvents() {
        // on select client from list update the sender input
        clientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                home.getMailForm().getRecipientInput().setText(newValue.getEmail());
            }
        });

        // on click re update sender input
        clientListView.setOnMouseClicked(e -> {
            ClientLabel selectedClient = clientListView.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                home.getMailForm().getRecipientInput().setText(selectedClient.getEmail());
            }
        });

        // on click remove client from list
        removeRecipientButton.setOnAction(e -> {
            ClientLabel selectedClient = clientListView.getSelectionModel().getSelectedItem();
            if (selectedClient != null) {
                clientListView.getItems().remove(selectedClient);
                try {
                    home.getClientManager().removeClient(selectedClient);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        // on click toggle client manager
        toggleClientManagerButton.setOnAction(e -> {
            home.toggleClientManager();
            home.getRecipientList().updateClientListView();
            if (home.getClientManager() instanceof ClientManagerWithGPG) {
                clientManagerUsedText.setText("Client manager with GPG is used");
            } else {
                clientManagerUsedText.setText("Client manager with file is used");
            }
        });

        // on search input update list view
        searchField.setOnKeyTyped(evt -> updateClientListView());
    }

    public void updateClientListView() {
        String inputText = searchField.getText().toLowerCase();
        this.clientListView.getItems().clear();

        if (inputText.isEmpty()) {
            for (Client client : home.getClientManager().getClients()) {
                this.clientListView.getItems().add(new ClientLabel(client));
            }
            return;
        }
        for (Client client : home.getClientManager().getClients()) {
            if (client.getDisplayName().toLowerCase().contains(inputText)) {
                this.clientListView.getItems().add(new ClientLabel(client));
            }
        }
    }

    // clear list view
    public void clear() {
        this.clientListView.getItems().clear();
    }

    public void addClient(Client client) {
        this.clientListView.getItems().add(new ClientLabel(client));
    }

}
