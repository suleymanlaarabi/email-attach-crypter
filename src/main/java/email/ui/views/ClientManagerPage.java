package email.ui.views;

import java.io.File;

import email.services.gpg.GpgManager;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class ClientManagerPage extends VBox {

  GpgManager gpgManager = new GpgManager();

  // components
  private Text title = new Text("GPG Client Manager : " + gpgManager.getVersions());

  private HBox buttonsBox = new HBox(10);
  private Button importKeyButton = new Button("Import key");
  private Button exportKeyButton = new Button("Export key");

  private FileChooser importKeyFileChooser = new FileChooser();
  DirectoryChooser exportKDirectoryChooser = new DirectoryChooser();

  public ClientManagerPage() {
    super(10);
    this.init();
    this.initEvents();
  }

  public void init() {
    this.buttonsBox.getChildren().addAll(this.importKeyButton, this.exportKeyButton);
    this.getChildren().addAll(this.title, this.buttonsBox);
  }

  public void initEvents() {
    this.importKeyButton.setOnAction(e -> {
      File file = this.importKeyFileChooser.showOpenDialog(this.getScene().getWindow());
      if (file != null) {
      }
    });
    this.exportKeyButton.setOnAction(e -> {
      File file = this.exportKDirectoryChooser.showDialog(this.getScene().getWindow());
      if (file != null) {
      }
    });
  }
}
