package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Settings Menu Controller.
 * Game Settings Driver
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SettingsMenuController implements Initializable {

    // Variable: Defines the current state of the Settings menu.
    // False = Regular Settings, True = Sound Settings.
    private boolean soundSettingsOpen;

    // FXML Element: The Parent Layout Element of the Settings Menu Scene.
    private GridPane container;

    // FXML Element: The Layout Element for the Settings Menu Scene.
    @FXML
    private HBox settingsMenu;

    // FXML Element: The Button that calls the close Settings Menu Scene method.
    @FXML
    private Button closeMenuButton;

    // FXML Element: The Button that calls the open the Save Games Scene method.
    @FXML
    private Button openSaveMenuButton;

    // FXML Element: The Button that calls the open the Game Info Scene method.
    @FXML
    private Button openGameInfoButton;

    // FXML Element: The Button that calls the Open Sound Settings method.
    @FXML
    private Button openSoundSettingsButton;

    // FXML Element: The Button that calls the Close Sound Settings method.
    @FXML
    private Button closeSoundSettingsButton;

    // FXML Element: The Button that calls the Quit Game method.
    @FXML
    private Button quitGameButton;

    // FXML Element: The Checkbox to set the "Disable Music Setting" state.
    // False = Enable Music, True = Disable music
    @FXML
    private CheckBox enableMusicState;

    // FXML Element: The Checkbox to set the "Disable SFX Setting" state.
    // False = Enable Music, True = Disable music
    @FXML
    private CheckBox enableSFXState;

    // Variable: Array List of Strings representing Game Stat Info for Saving Games.
    private ArrayList<String> saveGameStats;

    // Variable: Array List of Strings representing Game Ability Info for Saving Games.
    private ArrayList<String> saveGameAbilities;

    // Variable: Array List of Strings representing Game Map Info for Saving Games.
    private ArrayList<String> saveGameMaps;

    /**
     * Checks the ID of the Parent Layout Element to find which Scene it is displayed within.
     * Enables the Parent Scene Buttons and Removes the Game Info Scene from the Parent Scene.
     */
    @FXML
    public void closeSettings() {
        // Re-enable Settings Button, it is present in both primary Scenes (Main Menu & Location Menu).
        container.lookup("#SettingsButton").setDisable(false);
        // If: The Parent Scene is a Main Menu Scene, enable the Main Menu Button.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#NewGameButton").setDisable(false);
            container.lookup("#SavesButton").setDisable(false);
            container.lookup("#GameInfoButton").setDisable(false);
        } else {
            // Else: The Parent Scene is a Location Menu Scene, enable the Location Menu Buttons.
            container.lookup("#ViewCharacter").setDisable(false);
            container.lookup("#ViewMap").setDisable(false);
            // Enable the Parent Scene Text Area.
            container.lookup("#GameDescription").setDisable(false);
            // ** Temp Testing Button **
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the Game Info menu from its Parent Layout Element.
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Opens a new Saved Games Scene and removes the Settings Menu Scene from the current Scene.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openSaveMenu() throws IOException {
        // Load the Saved Games FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the Saved Games Scene and pass the Parent Layout Element.
        SaveMenuController controller = loader.getController();
        controller.setSceneVariables(container);
        if (container.getId().equals("mainMenuGrid")) {
            // If: The Parent Layout Element is the Main Menu:
            // Load the Scene into the Main Menu Display Cell.
            GridPane.setConstraints(root, 1, 1);
        } else {
            // Else: The Parent Layout Element is the Location Menu:
            // Load the Scene into the Location Menu Display Cell.
            GridPane.setConstraints(root, 2, 1);
            // Pass the Game Info to the Saved Games Scene for Saving. Stats, Abilities, Maps.
            controller.setCharacterStats(saveGameStats);
            controller.setCharacterAbilities(saveGameAbilities);
            controller.setGameMaps(saveGameMaps);
        }
        // Add the new Scene to the Parent Layout Element and remove the Settings Menu Scene.
        container.getChildren().add(root);
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Opens a new Game Info Scene and removes the Settings Menu Scene from the current Scene.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openGameInfoMenu() throws IOException {
        // Load the Game Info FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameInfoMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the Saved Games Scene and pass the Parent Layout Element.
        SaveMenuController controller = loader.getController();
        controller.setSceneVariables(container);
        if (container.getId().equals("mainMenuGrid")) {
            // If: The Parent Layout Element is the Main Menu:
            // Load the Scene into the Main Menu Display Cell.
            GridPane.setConstraints(root, 1, 1);
        } else {
            // Else: The Parent Layout Element is the Location Menu:
            // Load the Scene into the Location Menu Display Cell.
            GridPane.setConstraints(root, 2, 1);
        }
        // Add the new Scene to the Parent Layout Element and remove the Settings Menu Scene.
        container.getChildren().add(root);
        container.getChildren().remove(settingsMenu);
    }

    /**
     * Opens a new Quit Pop-Up Stage, and removes the Settings Menu Scene from the current Scene.
     *
     * @param event The Action Event object created by clicking the Quit Button
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openQuitGamePopUp(final ActionEvent event) throws IOException {
        // Create the Stage for the Quit Pop-Up and set its values (resizing, centering, and title).
        Stage popup = new Stage();
        popup.setResizable(false);
        popup.centerOnScreen();
        popup.setTitle("Quit");
        // Set the new Scene to act as a Pop-Up (Prevent actions in main Scene while Quit Pop-Up is open.)
        popup.initModality(Modality.APPLICATION_MODAL);
        // Load the Quit Pop-Up FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene.
        QuitPopupController controller = loader.getController();
        // Get the current Stage by tracing the source of the Action Event. Event -> Scene -> Stage.
        // Pass the current Stage object to the Quit Pop-Up - allows it to close the Stage.
        controller.getMainStage((Stage) ((Node) event.getSource()).getScene().getWindow());
        // Get the Scene from the loaded root.
        Scene popupDisplay = new Scene(root);
        // Load the Pop-Up Scene into the Stage and display it.
        popup.setScene(popupDisplay);
        // Show and Wait works with Application Modal to pause the main Stage while Pop-Up is open.
        popup.showAndWait();
    }

    /**
     * Switches Settings Menu between displaying Regular Settings and Sound Settings.
     */
    @FXML
    public void toggleSoundSettings() {
        // Visibility Setters for Regular Settings Elements and Sound Settings Elements.
        boolean soundSettingsVisibility;
        boolean settingsVisibility;
        // Set Visibility Setters based on current Sound Settings State.
        if (!soundSettingsOpen) {
            // If: Sound Setting are already opened, switch to Regular Settings.
            // Set Sound Settings Visibility Setter to False.
            settingsVisibility = false;
            // Set Regular Settings Visibility Setter to True.
            soundSettingsVisibility = true;
        } else {
            // Else: Regular Setting are open, switch to Sound Settings.
            // Set Sound Settings Visibility Setter to True.
            settingsVisibility = true;
            // Set Regular Settings Visibility Setter to False.
            soundSettingsVisibility = false;
        }
        // Set Regular Settings Elements Visibility with Regular Visibility Setter.
        closeMenuButton.setVisible(settingsVisibility);
        openSaveMenuButton.setVisible(settingsVisibility);
        openGameInfoButton.setVisible(settingsVisibility);
        openSoundSettingsButton.setVisible(settingsVisibility);
        quitGameButton.setVisible(settingsVisibility);
        // Set Sound Settings Elements Visibility with Sound Visibility Setter.
        enableMusicState.setVisible(soundSettingsVisibility);
        enableSFXState.setVisible(soundSettingsVisibility);
        closeSoundSettingsButton.setVisible(soundSettingsVisibility);
        // Update the Sound Settings State of the Settings Menu.
        soundSettingsOpen = soundSettingsVisibility;
    }

    /**
     * Sets the Enable Music and Enable SFX Checkboxes in the Parent Layout Element.
     */
    public void updateSoundSettings() {
        CheckBox musicState = (CheckBox) container.lookup("#EnableMusic");
        musicState.setSelected(enableMusicState.isSelected());
        CheckBox effectsState = (CheckBox) container.lookup("#EnableSFX");
        effectsState.setSelected(enableSFXState.isSelected());
    }

    /**
     * Receives the Parent Layout Element for the Settings Menu Scene.
     * Also sets the Sound Settings States to match the current Game Sound Settings.
     *
     * @param parentGrid The Parent Layout Element of the Settings Menu Scene
     */
    public void setSceneVariables(final GridPane parentGrid) {
        container = parentGrid;
        CheckBox musicState = (CheckBox) container.lookup("#EnableMusic");
        enableMusicState.setSelected(musicState.isSelected());
        CheckBox effectsState = (CheckBox) container.lookup("#EnableSFX");
        enableSFXState.setSelected(effectsState.isSelected());
    }

    /**
     * Receives Array Lists of strings containing game info to be used by the Saved Games Scene.
     *
     * @param gameStats An Array List of Game Stats: Name, Coordinates, Level, Exp, Health.
     * @param gameAbilities An Array List of Game Ability uses: Re-Roll, Add, Take-Away
     * @param gameMaps An Array List of Game Maps: Game Map, Player Map
     */
    public void setGameInfo(final ArrayList<String> gameStats,
                            final ArrayList<String> gameAbilities,
                            final ArrayList<String> gameMaps) {
        // Save the Game Info to variables that can be passed to the Saved Games Scene.
        saveGameStats = gameStats;
        saveGameAbilities = gameAbilities;
        saveGameMaps = gameMaps;
    }

    /**
     * When the Settings Scene is created set the Sound Settings to be hidden.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        soundSettingsOpen = false;
    }
}
