package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * End Game Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EndGameController {

    // Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // The parent element the game combat menu is displayed in.
    private GridPane container;

    // The layout element for the game combat menu.
    @FXML
    private HBox endGameBox;

    /**
     * Opens the Game Location window (main window for gameplay) in place of the current scene.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void closeGameCombatMenu(final ActionEvent event) throws IOException {
        // Load the Game Location menu FXML document.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the stage by tracing the source of the click event -> scene -> stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Get the size of the users screen to set the size and with of the game window.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Create a scene with the loaded Game Location menu document.
        // Load the scene with the correct size and width found above.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        stage.setScene(scene);
        // Center stage to the middle of the screen, prevent resizing, and set title.
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();
    }

    /**
     * Load the save menu document, remove the settings menu scene and display the load saves menu.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSaveMenu() throws IOException {
        // Load the new game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        SaveMenuController controller = loader.getController();
        controller.getContainerElement(container);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        container.getChildren().add(root);
        container.getChildren().remove(endGameBox);
    }

    /**
     * Load the new game menu document, display it in the center of the screen, and disable all main menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openNewGameMenu() throws IOException {
        // Load the new game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGameMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        NewGameController controller = loader.getController();
        controller.getContainerElement(container);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        container.getChildren().add(root);
        container.getChildren().remove(endGameBox);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the character info menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

}
