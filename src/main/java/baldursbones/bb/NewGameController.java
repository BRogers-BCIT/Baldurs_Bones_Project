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

/**
 * New Game Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class NewGameController {

    // Constant: Define the amount of space to leave available for the Anchor Bar.
    private static final int ANCHOR_BAR_SIZE = 65;

    // Constant: Maximum length of a Character Name (Prevents issues with using Character Name).
    private static final int MAX_NAME_LENGTH = 12;

    // FXML Element: The Parent Layout Element the New Game Scene is displayed within.
    private GridPane container;

    // FXML Element: The Layout Element for the New Game Scene.
    @FXML
    private HBox newGameMenu;

    //FXML Element: The Text Input Field used to get the Character Name string for the Player.
    @FXML
    private TextField characterName;

    //FXML Element: Label that displays any Errors with Character Name when starting a New Game.
    @FXML
    private Label errorOutput;

    //FXML Element: Checkbox that defines the Skip Tutorial value when starting a New Game.
    @FXML
    private CheckBox disableTutorial;

    // Variable: A boolean value used to track if Music is enabled.
    private boolean enableMusicState;
    // Variable: A boolean value used to track if SFX are enabled.
    private boolean enableSFXState;

    /**
     * Finds ID of Parent Layout Element to find the Parent Scene (Location Menu Controller or Main Menu Controller).
     * If (Location Menu): Create a new to Main Menu Scene in the current Stage.
     * Otherwise (Main Menu): Close the current Scene and Enable the Main Menu Buttons.
     *
     * @param event The Action Event object created by clicking the load game FXML Button
     * @throws IOException If the FXML document being loaded does not exist
     */
    @FXML
    public void closeNewGameMenu(final ActionEvent event) throws IOException {
        // If the current container is a Location Menu, create a new Main Menu Scene.
        // ** Note: If a user closes a New Game Scene opened from an End Game Scene, the game would soft-lock. **
        // ** Fix: Instead of closing the End Game Scene, the Scene returns to a new Main Menu Scene. **
        if (container.getId().equals("locationMenuGrid")) {
            // Load the Main Menu FXML document into a root object.
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = mainLoader.load();
            // Get the Controller for the Main Menu and get the Grid Pane object from the Controller.
            MainMenuController controller = mainLoader.getController();
            // Get the current Stage by tracing the source of the Action Event. Event -> Scene -> Stage.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Get the size of the screen to set the size of the Stage.
            Rectangle2D userScreen = Screen.getPrimary().getBounds();
            // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
            Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
            // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Baldur's Bones");
            // Pass the Sound Settings to the Location Menu Controller
            controller.setSoundSettings(enableMusicState, enableSFXState);
            // Display the window.
            stage.show();
        } else {
            // Else: The current Container is the Main Menu, enable Main Menu Buttons.
            container.lookup("#NewGameButton").setDisable(false);
            container.lookup("#SavesButton").setDisable(false);
            container.lookup("#GameInfoButton").setDisable(false);
            container.lookup("#SettingsButton").setDisable(false);
            // Remove the Game Info menu from its parent menu object.
            container.getChildren().remove(newGameMenu);
        }
    }

    /**
     * Loads a new Game Location Scene into the current Stage.
     *
     * @param event The Action Event object created by clicking the New Game Button
     * @throws IOException If the FXML document being loaded does not exist
     */
    @FXML
    public void openGameWindow(final ActionEvent event) throws IOException {
        // Load the Game Location FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationMenu.fxml"));
        Parent root = loader.load();
        // Get the current Stage by tracing the source of the Action Event. Event -> Scene -> Stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // If: The user entered a valid Character Name.
        if (getGameStartInfo(loader)) {
            // Get the size of the screen to set the size of the Stage.
            Rectangle2D userScreen = Screen.getPrimary().getBounds();
            // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
            Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
            // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Baldur's Bones");
            // Display the window.
            stage.show();
            // Get Controller of Location Menu Scene.
            LocationMenuController gameDriverController = loader.getController();
            // Pass the Sound Settings to the Location Menu Controller
            gameDriverController.setSoundSettings(enableMusicState, enableSFXState);
            // Call the Start Game method in the Location Menu Controller.
            gameDriverController.gameStarter();
        }
    }

    // Checks that there is a valid Character Name in the Name Text Field.
    // Returns a boolean value to indicate if there is a valid Character Name.
    // Takes the Loader for a new Location Menu Controller.
    private boolean getGameStartInfo(final FXMLLoader loader) {
        if (characterName.getText().equals("")) {
            // If: Character Name is empty, prevent the game from starting and display a No-Name error.
            errorOutput.setVisible(true);
            errorOutput.setText("No Character Name.");
            // Prevent a New Game from starting.
            return false;
        } else if (characterName.getText().length() > MAX_NAME_LENGTH) {
            // If: Character Name is too long, prevent the game from starting and display a Name-Length error.
            errorOutput.setVisible(true);
            errorOutput.setText("Character Name Too Long. (Max 12 Characters).");
            // Prevent a New Game from starting.
            return false;
        } else {
            // Else: A valid name is present.
            // Get controller of Game Location Scene from the FXML Loader.
            LocationMenuController gameDriverController = loader.getController();
            // Else: Pass the New Game values to the Location Menu controller.
            gameDriverController.newGameInfo(characterName.getText(), disableTutorial.isSelected());
            // Allow a New Game to start.
            return true;
        }
    }

    /**
     * Receives the Parent Layout Element for the New Game Scene.
     *
     * @param parentGrid  The Parent Layout Element of the New Game Scene
     * @param enableMusic A boolean value that indicates if Music is currently enabled
     * @param enableSFX   A boolean value that indicates if SFX are currently enabled
     */
    public void setSceneVariables(final GridPane parentGrid, final boolean enableMusic, final boolean enableSFX) {
        container = parentGrid;
        enableMusicState = enableMusic;
        enableSFXState = enableSFX;
    }
}
