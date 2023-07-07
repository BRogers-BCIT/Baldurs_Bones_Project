package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsMenuController {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    public void closeSettings(ActionEvent event) throws IOException {
        // Load the main menu FXML document.
        root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        // Get the stage by tracing the source of the click event -> scene -> stage.
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Get the size of the users screen.
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
}
