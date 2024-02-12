package email.ui.form;

import email.services.email.EmailClient;
import email.ui.Home;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MailForm extends VBox {

    private VBox mailHeaderFormBox = new VBox(10);

    private HBox senderForm = new HBox(10);
    private Label senderLabel = new Label("Sender : ");
    private TextField senderInput = new TextField();

    private HBox recipientForm = new HBox(10);
    private Label recipientLabel = new Label("Recipient : ");
    private TextField recipientInput = new TextField();

    private HBox objectNameForm = new HBox(10);
    private Label objectNameLabel = new Label("Objet : ");
    private TextField objectNameInput = new TextField();

    private HBox textMailForm = new HBox(10);
    private TextArea textMail = new TextArea("");
    private Button sendButton = new Button("Send");

    private FileEncryptForm fileEncryptForm;

    private EmailClient emailClient;

    // constants style
    final double mailFormWidth = 500;

    public MailForm(
            Home home, EmailClient emailClient) {
        super(10);
        this.emailClient = emailClient;

        this.fileEncryptForm = new FileEncryptForm(this, home);

        // init this component style
        this.setAlignment(Pos.CENTER);

        // init attributes component style
        this.mailHeaderFormBox.maxWidth(mailFormWidth);
        this.mailHeaderFormBox.minWidth(mailFormWidth);
        this.mailHeaderFormBox.setAlignment(Pos.CENTER);

        this.objectNameForm.setMaxWidth(mailHeaderFormBox.getWidth());
        this.senderForm.setMaxWidth(mailHeaderFormBox.getWidth());
        this.recipientForm.setMaxWidth(mailHeaderFormBox.getWidth());

        this.objectNameForm.setMinWidth(mailFormWidth);
        this.senderForm.setMinWidth(mailFormWidth);
        this.recipientForm.setMinWidth(mailFormWidth);

        this.objectNameForm.setAlignment(Pos.CENTER_LEFT);
        this.senderForm.setAlignment(Pos.CENTER_LEFT);
        this.recipientForm.setAlignment(Pos.CENTER_LEFT);

        this.senderInput.setPromptText("example@example.fr");
        this.recipientInput.setPromptText("example@exemple.fr");
        this.objectNameInput.setPromptText("Your email object");
        this.textMail.setPromptText("Message");

        this.objectNameInput.setMaxWidth(mailFormWidth / 3 + 50);
        this.senderInput.setMaxWidth(mailFormWidth / 3 + 50);
        this.recipientInput.setMaxWidth(mailFormWidth / 3 + 50);

        this.objectNameInput.setMinWidth(mailFormWidth / 3 + 50);
        this.senderInput.setMinWidth(mailFormWidth / 3 + 50);
        this.recipientInput.setMinWidth(mailFormWidth / 3 + 50);

        this.objectNameLabel.setMinWidth(mailFormWidth / 3 - 50);
        this.senderLabel.setMinWidth(mailFormWidth / 3 - 50);
        this.recipientLabel.setMinWidth(mailFormWidth / 3 - 50);

        this.sendButton.setMinWidth(mailFormWidth / 3 - 20);

        this.textMail.setMaxWidth(300);
        this.textMail.setPrefWidth(300);

        this.textMail.setMaxHeight(125);
        this.textMail.setPrefHeight(125);

        this.textMail.setWrapText(true);

        this.textMailForm.setMaxHeight(mailFormWidth);
        this.textMailForm.setMaxWidth(mailFormWidth);
        this.textMailForm.setAlignment(Pos.CENTER);

        this.init();
        this.initEvents();
    }

    private void init() {
        this.senderForm.getChildren().addAll(this.senderLabel, this.senderInput);
        this.recipientForm.getChildren().addAll(this.recipientLabel, this.recipientInput);
        this.objectNameForm.getChildren().addAll(this.objectNameLabel, this.objectNameInput, this.sendButton);
        this.mailHeaderFormBox.getChildren().addAll(senderForm, this.recipientForm, objectNameForm,
                this.textMail);
        this.textMailForm.getChildren().addAll(this.textMail, this.fileEncryptForm);
        this.getChildren().addAll(mailHeaderFormBox, textMailForm);
    }

    private void initEvents() {

        this.sendButton.setOnAction(
                evt -> {
                    if (!this.fileEncryptForm.checkIfFormIsFilled()) {
                        this.fileEncryptForm.autoFillAlert();
                        this.fileEncryptForm.getAlert().showAndWait();
                        return;
                    }
                    this.onMailSend();
                });
    }

    private void onMailSend() {
        if (fileEncryptForm.getFileManager().getFile() == null) {
            this.emailClient.sendEmail(senderInput.getText(), objectNameInput.getText(), this.textMail.getText());
            return;
        }
        emailClient.sendAttachmentEmail(senderInput.getText(), objectNameInput.getText(),
                this.textMail.getText(), fileEncryptForm.getFileManager().getFile());

    }

    // GETTER
    public TextField getSenderInput() {
        return senderInput;
    }

    public TextField getRecipientInput() {
        return recipientInput;
    }

    public TextField getObjectNameInput() {
        return objectNameInput;
    }

    public TextArea getTextMail() {
        return textMail;
    }
}
