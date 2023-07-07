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

public class MainMenuController {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    public void openSettings(ActionEvent event) throws IOException {
        // Load the settings menu FXML document.
        root = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));
        // Get the stage by tracing the source of the click event -> scene -> stage.
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Create a scene with the loaded settings menu document.
        scene = new Scene(root);
        stage.setScene(scene);
        // Center stage to the middle of the screen and prevent resizing.
        stage.centerOnScreen();
        stage.setResizable(false);
        // Display the window.
        stage.show();
    }
}
