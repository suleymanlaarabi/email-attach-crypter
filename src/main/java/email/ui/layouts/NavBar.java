package email.ui.layouts;

import email.ui.PageManager;
import email.ui.views.ClientManagerPage;
import email.ui.views.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

public class NavBar extends HBox {

  Button home = new Button("Home");
  Button clientManager = new Button("Client Manager GPG");
  PageManager pageManager;

  public NavBar(PageManager pageManager) {
    super(10);
    this.pageManager = pageManager;
    this.setAlignment(Pos.CENTER);
    this.setBackground(Background.fill(Paint.valueOf("#e6e6e6")));
    this.setPadding(new Insets(20, 0, 20, 0));

    this.init();
    this.initEvents();
  }

  public void init() {
    this.getChildren().addAll(this.home, this.clientManager);

  }

  public void initEvents() {
    this.home.setOnAction(e -> this.pageManager.goToPage(HomePage.class));
    this.clientManager.setOnAction(e -> this.pageManager.goToPage(ClientManagerPage.class));
  }
}
