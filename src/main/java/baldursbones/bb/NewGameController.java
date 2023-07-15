package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    // Used when creating a new container class scene (Main Menu / Location Menu)
    private static final int ANCHOR_BAR_SIZE = 70;

    // Constant: Define the max number of characters in a player character name.
    // Prevent issues with displaying player name in game dialogue.
    private static final int MAX_NAME_LENGTH = 12;

    // FXML Element: The parent element that the New Game menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the New Game menu.
    @FXML
    private HBox newGameMenu;

    //FXML Element: The text field used to get the character name string from the user.
    @FXML
    private TextField characterName;

    //FXML Element: A label used to display any errors to the user when starting a new game.
    @FXML
    private Label errorOutput;

    //FXML Element: A checkbox that allows players to skip the tutorial in a new game.
    // Boolean value is passed to game driver class to signify the tutorial is to be skipped.
    // Passing method needs to be implemented.
    @FXML
    private CheckBox disableTutorial;

    /**
     * Removes the Game Info menu layout from its parent layout element.
     * Queries the ID of the parent object to find its container menu type and re-enables the correct menu buttons.
     *
     * @param event the event object created by clicking the load game FXML button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void closeNewGameMenu(final ActionEvent event) throws IOException {
        // If the current container is the Main Menu, allow the menu to be closed.
        if (container.getId().equals("mainMenuGrid")) {
            // If: The current container is the Main Menu, enable Main Menu buttons.
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
            container.lookup("#openSettingsButton").setDisable(false);
            // Remove the Game Info menu from its parent menu object.
            container.getChildren().remove(newGameMenu);
        } else {
            // Else: A new Main Menu should be created.
            // Invoked when a play ends a game -> Selects new game -> Closes new game menu.
            // Load the Game Info menu FXML document into a root object.
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
            // Get the stage by tracing the source of the click event. Event -> Scene -> Stage.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Get the size of the users screen to determine the size and with of the new game stage.
            Rectangle2D userScreen = Screen.getPrimary().getBounds();
            // Load the scene with the size and width found above.
            // (Height - Size of anchor bar allows anchor bar to display)
            Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
            // Set the new scene in the stage object, center the stage, prevent resizing, and set the window title.
            stage.setScene(scene);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationMenu.fxml"));
        Parent root = loader.load();
        // Get the stage by tracing the source of the click event. Event -> Scene -> Stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Call a check to see if the user has entered a character name.
        // If they have, pass the character and tutorial skip boolean variable to the game controller.
        if (getGameStartInfo(loader)) {
            // Get the size of the users screen to determine the size and with of the new game stage.
            Rectangle2D userScreen = Screen.getPrimary().getBounds();
            // Load the scene with the size and width found above.
            // (Height - Size of anchor bar allows anchor bar to display)
            Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
            // Set the new scene in the stage object, center the stage, prevent resizing, and set the window title.
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Baldur's Bones");
            // Display the window.
            stage.show();
        }
    }

    // Gets the character name from the text field and the selection value from the skip tutorial checkbox.
    // Checks for a non-empty character name to prevent a new game from being started without a character name.
    // Takes the loader object for the New Game window to finds its controller.
    // Passes the character name string and the skip tutorial boolean variable.
    private boolean getGameStartInfo(final FXMLLoader loader) {
        // Get controller of Game Location window.
        LocationMenuController gameDriverController = loader.getController();

        if (characterName.getText().equals("")) {
            errorOutput.setVisible(true);
            errorOutput.setText("No Character Name.");
            return false;
        } else if (characterName.getText().length() > MAX_NAME_LENGTH) {
            errorOutput.setVisible(true);
            errorOutput.setText("Character Name Too Long. (Max 12 Characters).");
            return false;
        } else {
            // pass the start game values to the Location Menu controller.
            gameDriverController.getGameInfo(characterName.getText(), disableTutorial.isSelected());
            return true;
        }
    }

    /**
     * Takes the parent layout element that scene will be displayed in and saves it to a variable.
     *
     * @param parentGrid The parent layout element of the New Game scene
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
