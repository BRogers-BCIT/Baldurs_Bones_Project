package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Settings Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SaveMenuController {

    // The parent element the settings menu is displayed in.
    private GridPane container;

    // The layout element for the settings menu.
    @FXML
    private HBox saveGameMenu;

    /**
     * Removes the settings menu layout from the main menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSaveMenu() {
        // Set main menu buttons to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        container.lookup("#newGameButton").setDisable(false);
        container.lookup("#savedGamesButton").setDisable(false);
        container.lookup("#gameInfoButton").setDisable(false);
        // Remove the settings menu from the main menu window.
        container.getChildren().remove(saveGameMenu);
    }

    /** Opens the Game Location window (main window for gameplay) in place of the current scene.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void openGameWindow(final ActionEvent event) throws IOException {
        // Load the Game Location menu FXML document.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LocationMenu.fxml")));
        // Get the stage by tracing the source of the click event -> scene -> stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Create a scene with the loaded Game Location menu document.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        // Center stage to the middle of the screen and prevent resizing.
        stage.centerOnScreen();
        stage.setResizable(false);
        // Display the window.
        stage.show();
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout.
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
