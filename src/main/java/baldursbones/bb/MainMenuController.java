package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenuController {

    private Stage stage;

    private Scene scene;

    private Parent root;

    @FXML
    private GridPane MainMenuGrid;

    @FXML
    public void openSettings(ActionEvent event) throws IOException {
        // Load the settings menu FXML document.
        root = FXMLLoader.load(getClass().getResource("SettingsMenu.fxml"));

        MainMenuGrid.setConstraints(root, 0, 2);

        MainMenuGrid.getChildren().add(root);
    }
}
