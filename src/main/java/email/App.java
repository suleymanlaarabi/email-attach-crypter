package email;

import email.ui.Home;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    // constants for the application window
    private static final String APP_NAME = "Secure Email Client";
    private static final int WINDOW_WIDTH = 980;
    private static final int WINDOW_HEIGHT = 700;

    // path to the file containing the clients

    @Override
    public void start(Stage primaryStage) {
        // Window initial configuration
        try {
            configureWindow(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        primaryStage.show();

        // Run later to center the window on the screen after size is calculated
        Platform.runLater(() -> {
            try {
                primaryStage.centerOnScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void configureWindow(Stage stage) {
        stage.setTitle(APP_NAME);
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        // Create the main scene
        Scene mainScene = new Scene(new Home(stage));
        stage.setScene(mainScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
