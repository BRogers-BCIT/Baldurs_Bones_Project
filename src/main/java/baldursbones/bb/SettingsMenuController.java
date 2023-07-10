package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Settings Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SettingsMenuController {

    // The parent element the settings menu is displayed in.
    private GridPane container;

    // The layout element for the settings menu.
    @FXML
    private HBox settingsMenu;

    /**
     * Removes the settings menu layout from the main menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSettings() {
        // Set main menu buttons and the settings button to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        container.lookup("#newGameButton").setDisable(false);
        container.lookup("#savedGamesButton").setDisable(false);
        container.lookup("#gameInfoButton").setDisable(false);
        // Remove the settings menu from the main menu window.
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Load the saved games menu document, remove the settings menu scene and display the load saves menu.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSaveMenu() throws IOException {
        // Load the saved games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        SaveMenuController controller = loader.getController();
        controller.getContainerElement(container);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        container.getChildren().add(root);
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Load the game info document, remove the settings menu scene and display the game info menu.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openGameInfoMenu() throws IOException {
        // Load the game info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameInfoMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        GameInfoController controller = loader.getController();
        controller.getContainerElement(container);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        container.getChildren().add(root);
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Load the quit game pop-up document, remove the settings menu scene and display the quit game pop-up.
     *
     * @param event the event object created by clicking the button that called this method
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openQuitGamePopUp(final ActionEvent event) throws IOException {
        // Create the stage for the pop-up and set the stage values (window type, resizing, centering, and title).
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        popup.centerOnScreen();
        popup.setTitle("Quit");
        // Load the quit game pop-up FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new pop-up to pass the current stage to.
        QuitPopupController controller = loader.getController();
        // Get the current stage from the event and pass it to the quit-pop.
        controller.getMainStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        Scene popupDisplay = new Scene(root);
        // Load the pop-up scene into the stage and display it. Will pause game until window is closed.
        popup.setScene(popupDisplay);
        popup.showAndWait();
    }


    /**
     * Takes the parent element that the layout will be displayed in and saves it. Disables settings button on menu.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
