package email.ui.form;

import java.io.File;

import email.services.file.FileManager;
import email.ui.views.HomePage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class FileEncryptForm extends HBox {

    // dependencies
    private HomePage home;

    private FileManager fileManager = new FileManager();

    private VBox selectedFileForm = new VBox(10);
    private Button selectFileButton = new Button("Select file");
    private FileChooser fileChooser = new FileChooser();
    private Text fileSelected = new Text("No file selected");

    private VBox buttonBox = new VBox(10);
    private Button encryptButton = new Button("Encrypt");
    private Button decryptButton = new Button("Decrypt");

    private Alert alert = new Alert(AlertType.INFORMATION);

    public FileEncryptForm(MailForm mailForm, HomePage home) {
        super(10);
        // init attributes
        this.home = home;

        // init this component style
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(200);

        // init attributes components style
        this.fileChooser.setInitialDirectory(new File("."));
        this.selectedFileForm.setAlignment(Pos.CENTER);
        this.selectFileButton.setPrefWidth(200);
        this.encryptButton.setPrefWidth(200);
        this.decryptButton.setPrefWidth(200);
        this.buttonBox.setAlignment(Pos.CENTER);

        this.init();
        this.initEvents();
    }

    private void init() {
        this.selectedFileForm.getChildren().addAll(this.selectFileButton, this.fileSelected, buttonBox);
        this.buttonBox.getChildren().addAll(this.encryptButton, this.decryptButton);
        this.getChildren().addAll(selectedFileForm);
    }

    private void initEvents() {
        this.selectFileButton.setOnAction(event -> {
            File file = this.fileChooser.showOpenDialog(this.home.getPageManager().getStage());
            if (file != null && file.exists() && file.isFile()) {
                try {
                    this.fileManager.setFile(file);
                    this.fileSelected.setText(file.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        this.encryptButton.setOnAction(event -> {
            if (!this.checkIfFormIsFilled()) {
                this.autoFillAlert();
                alert.showAndWait();
                return;
            }
            this.fileSelected.setText(
                    fileManager.encryptFile(this.getRecipientInputText(), this.getSenderInputText()).getName());
        });

        this.decryptButton.setOnAction(event -> {
            fileManager.decryptFile();
        });
    }

    public void autoFillAlert() {
        if (this.fileManager.getFile() == null) {
            alert.setContentText("Please select a file");
            return;
        }
        if (this.getRecipientInputText().isEmpty() || this.getSenderInputText().isEmpty()) {
            alert.setContentText("Please fill the recipient and sender fields");
        }

    }

    public Boolean checkIfFormIsFilled() {
        if (this.getRecipientInputText().isEmpty() || this.getSenderInputText().isEmpty()) {
            return false;
        }
        if (this.fileManager.getFile() == null) {
            return false;
        }
        return true;
    }

    // PUBLIC GETTERS
    public FileManager getFileManager() {
        return fileManager;
    }

    public Alert getAlert() {
        return alert;
    }

    // PRIVATE GETTERS
    private String getRecipientInputText() {
        return this.home.getMailForm().getSenderInput().getText();
    }

    private String getSenderInputText() {
        return this.home.getMailForm().getSenderInput().getText();
    }
}
