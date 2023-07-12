package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * New Game Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class NewGameController {

    // Constant: Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // FXML Element: The parent element the new game menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the new game menu.
    @FXML
    private HBox newGameMenu;

    //FXML Element: The text field for the user to enter their character name.
    @FXML
    private TextField characterName;

    //FXML Element: The checkbox that allows players to disable the tutorial.
    @FXML
    private CheckBox disableTutorial;

    /**
     * Removes the new game menu layout from the main menu and makes the main menu buttons clickable again.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void closeNewGameMenu(final ActionEvent event) throws IOException {
        // If the current container is the main menu, allow the menu to be closed.
        if (container.getId().equals("mainMenuGrid")) {
            // Set main menu buttons to be clickable.
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
            container.lookup("#openSettingsButton").setDisable(false);
            // Remove the new game menu from the main menu window.
            container.getChildren().remove(newGameMenu);
        } else {
            // Return to main menu
            // Load the Main Menu FXML document.
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
    }

    /**
     * Opens the Game Location window (main window for gameplay) in place of the current scene.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void openGameWindow(final ActionEvent event) throws IOException {
        // Load the Game Location menu FXML document.
        // Load the saved games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationMenu.fxml"));
        Parent root = loader.load();
        // Get the stage by tracing the source of the click event -> scene -> stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Pass game info (character name and tutorial enabled) from window to game method.
        if (getGameStartInfo(loader)) {
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
    }

    // Gets the character name from the text field and if the tutorial is disabled from the checkbox.
    // Takes the controller for the new game window and passes the values to the controller.
    // Prevents new game from being started without a character name.
    // Limitations: ** CURRENTLY PRINTS FOR TESTING AND DOES NOT PASS VALUES **.
    private boolean getGameStartInfo(final FXMLLoader loader) {
        boolean startNewGame = true;
        if (characterName.getText().equals("")) {
            startNewGame = false;
            // ** TEMP PRINT INSTEAD OF VISUAL OUTPUT **
            System.out.println("No Character Name. Invalid Start.");
        } else {
            // ** TEMP PRINT INSTEAD OF PASS VARIABLE STATUS **
            System.out.println("Character Name: " + characterName.getText() + ".");
            System.out.println("Tutorial Disabled: " + disableTutorial.isSelected() + ".");
        }
        return startNewGame;
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
