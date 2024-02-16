package email.ui.views;

import java.io.File;

import email.services.email.ClientManager;
import email.services.email.ClientManagerWithGPG;
import email.services.email.EmailClient;
import email.services.email.interfaces.IClientManager;
import email.ui.PageManager;
import email.ui.form.MailForm;
import email.ui.form.RecipientForm;
import email.ui.list.RecipientList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomePage extends VBox {

    private static final String CLIENTS_FILE_PATH = "./resources/Clients.txt";

    private IClientManager clientManager;
    private PageManager pageManager;
    // components
    private Label title = new Label("Secure your email sending");
    private Label errorMessage = new Label("");

    private HBox recipientBox = new HBox(10);
    private EmailClient emailClient = new EmailClient("ciel.mail@lacigogne.shop", "Lolo0913477!");
    private MailForm mailForm = new MailForm(this, emailClient);
    private RecipientList recipientList;
    private RecipientForm recipientForm;

    public HomePage(PageManager pageManager) {
        super(10);
        this.setAlignment(Pos.TOP_CENTER);
        this.pageManager = pageManager;
        this.errorMessage.setStyle("-fx-text-fill: red;");

        try {
            this.clientManager = new ClientManagerWithGPG();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.recipientForm = new RecipientForm(this);
        this.recipientList = new RecipientList(this);
        this.recipientBox.setAlignment(Pos.CENTER);

        this.init();
    }

    private void init() {
        recipientBox.getChildren().addAll(this.recipientForm, this.recipientList);
        this.getChildren().addAll(this.title, this.mailForm, this.recipientBox);
    }

    // GETTER
    public MailForm getMailForm() {
        return mailForm;
    }

    public RecipientList getRecipientList() {
        return recipientList;
    }

    public void setErrorMessage(String message) {
        this.errorMessage.setText(message);
    }

    public void clearErrorMessage() {
        this.errorMessage.setText("");
    }

    public IClientManager getClientManager() {
        return this.clientManager;
    }

    public PageManager getPageManager() {
        return pageManager;
    }

    public void toggleClientManager() {
        System.out.println("Toggle client manager");
        try {
            if (this.clientManager instanceof ClientManagerWithGPG) {
                System.out.println("Switching to file client manager");
                this.clientManager = new ClientManager(new File(CLIENTS_FILE_PATH));
            } else {
                System.out.println("Switching to gpg client manager");
                this.clientManager = new ClientManagerWithGPG();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
