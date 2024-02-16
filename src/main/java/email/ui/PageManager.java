package email.ui;

import email.ui.layouts.NavBar;
import email.ui.views.ClientManagerPage;
import email.ui.views.HomePage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PageManager extends VBox {

  private Node[] pages;
  private Stage stage;

  private NavBar navBar = new NavBar(this);

  private HBox pageContainer = new HBox(10);

  public PageManager(Stage stage) {
    super(10);
    this.setAlignment(Pos.CENTER);
    this.stage = stage;

    // PAGES
    this.pages = new Node[] { new HomePage(this), new ClientManagerPage() };

    this.getChildren().addAll(navBar, pageContainer);
    this.pageContainer.setPrefHeight(700);
    this.pageContainer.setAlignment(Pos.CENTER);
    this.goToPage(HomePage.class);
  }

  public void goToPage(Class<? extends Node> page) {
    pageContainer.getChildren().clear();
    for (Node node : pages) {
      if (node.getClass() == page) {
        pageContainer.getChildren().add(node);
      }
    }
  }

  public <T extends Node> T getPage(Class<T> page) {
    for (Node node : pages) {
      if (node.getClass() == page) {
        return (T) node;
      }
    }
    return null;
  }

}
