package email.ui.form;

import email.services.email.Client;
import email.ui.Home;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RecipientForm extends VBox {

    // dependencies
    private Home home;

    // components
    private Label recipientLabel = new Label("Recipient : ");
    private TextField recipientNameInput = new TextField();
    private TextField recipientFirstNameInput = new TextField();
    private TextField recipientEmailInput = new TextField();
    private Button addRecipientButton = new Button("add a recipient");

    public RecipientForm(Home home) {
        super(10);

        // init this component style
        this.setAlignment(Pos.CENTER);

        // init dependencies
        this.home = home;

        // init this component style
        this.recipientNameInput.setPromptText("Name");
        this.recipientFirstNameInput.setPromptText("FirstName");
        this.recipientEmailInput.setPromptText("Email");

        // init components and events
        this.init();
        this.initEvents();
    }

    public void init() {
        this.getChildren().addAll(this.recipientLabel, this.recipientNameInput,
                this.recipientFirstNameInput, this.recipientEmailInput, this.addRecipientButton);
    }

    public void initEvents() {
        this.addRecipientButton.setOnAction(event -> addRecipient());
    }

    private void addRecipient() {
        String name = this.recipientNameInput.getText().trim();
        String firstName = this.recipientFirstNameInput.getText().trim();
        String email = this.recipientEmailInput.getText().trim();

        if (areInputsValid(name, firstName, email)) {
            try {
                Client client = new Client(name, firstName, email);
                this.home.getClientManager().addClient(client);
                this.home.getRecipientList().addClient(client);
                clearInputs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean areInputsValid(String name, String firstName, String email) {
        return !name.isEmpty() && !firstName.isEmpty() && !email.isEmpty();
    }

    private void clearInputs() {
        this.recipientNameInput.setText("");
        this.recipientFirstNameInput.setText("");
        this.recipientEmailInput.setText("");
    }

}
