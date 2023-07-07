package baldursbones.bb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GameDriver extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Get the Main Menu FXML file and load it into root.
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        // Get the size of the users screen.
        // The scene will be loaded with a width equal to the users screen and a slightly smaller height.
        // This lets the bar at the top of the window display, showing the minimize, full screen, and close options.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the scene with the correct size and width.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - 70);
        // Set the name of the window, pass the scene to the stage, and display the window.
        stage.setTitle("Baldur's Bones");
        stage.setScene(scene);
        // Center stage to the middle of the screen and prevent resizing.
        stage.centerOnScreen();
        stage.setResizable(false);
        // Display the window.
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
