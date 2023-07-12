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
import java.util.ResourceBundle;

/**
 * Settings Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SettingsMenuController implements Initializable {

    // Variable: Records if the sound settings menu is opened or closed
    private boolean soundSettingsOpen;

    // FXML Element: The parent element the settings menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the settings menu.
    @FXML
    private HBox settingsMenu;

    // FXML Element: The settings menu button to close the settings menu.
    @FXML
    private Button closeMenuButton;

    // FXML Element: The settings menu button to open the save game menu.
    @FXML
    private Button openSaveMenuButton;

    // FXML Element: The settings menu button to open the game info menu.
    @FXML
    private Button openGameInfobutton;

    // FXML Element: The settings menu button to open the sound settings on the menu.
    @FXML
    private Button openSoundSettingsButton;

    // FXML Element: The settings menu button to close the sound settings on the menu.
    @FXML
    private Button closeSoundSettingsButton;

    // FXML Element: The settings menu button to close the quit the game.
    @FXML
    private Button quitGamebutton;

    // FXML Element: The settings menu checkbox to control the music settings.
    @FXML
    private CheckBox musicCheckbox;

    // FXML Element: The settings menu checkbox to control the sound effects settings.
    @FXML
    private CheckBox effectsCheckbox;

    /**
     * Removes the settings menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSettings() {
        // Set the settings button to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        // If: the current container is the main menu, enable main menu buttons.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
        } else {
            // Else: Set location menu buttons to be clickable.
            container.lookup("#locationFightButton").setDisable(false);
            container.lookup("#locationViewStats").setDisable(false);
            container.lookup("#locationViewMap").setDisable(false);
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the settings menu from the current menu window.
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
        // Get the controller for the new menu and pass the current menu layout to the class.
        SaveMenuController controller = loader.getController();
        controller.getContainerElement(container);
        // If the current container is the main menu, load scene into correct grid position.
        if (container.getId().equals("mainMenuGrid")) {
            GridPane.setConstraints(root, 0, 2);
        } else {
            // Load scene into location menu position.
            GridPane.setConstraints(root, 2, 2);
        }
        // Add new menu to the layout.
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
        // Get the controller for the new menu and pass the current menu layout to the class.
        GameInfoController controller = loader.getController();
        controller.getContainerElement(container);
        // If the current container is the main menu, load scene into correct grid position.
        if (container.getId().equals("mainMenuGrid")) {
            GridPane.setConstraints(root, 0, 2);
        } else {
            // Load scene into location menu position.
            GridPane.setConstraints(root, 2, 2);
        }
        // Add new menu to the layout.
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
     * Update the visibility of buttons whenever the user open / closes the sound settings menu.
     * ** Layout changes may also be needed in the future. **
     */
    @FXML
    public void toggleSoundSettings() {
        // Visibility setters for regular menu and sound settings menu
        boolean soundSettingsVisibility;
        boolean settingsVisibility;
        // Set the visibility values for each menu.
        if (!soundSettingsOpen) {
            settingsVisibility = false;
            soundSettingsVisibility = true;
        } else {
            settingsVisibility = true;
            soundSettingsVisibility = false;
        }
        // Update menu visibility based on visibility setter values.
        closeMenuButton.setVisible(settingsVisibility);
        openSaveMenuButton.setVisible(settingsVisibility);
        openGameInfobutton.setVisible(settingsVisibility);
        openSoundSettingsButton.setVisible(settingsVisibility);
        quitGamebutton.setVisible(settingsVisibility);
        musicCheckbox.setVisible(soundSettingsVisibility);
        effectsCheckbox.setVisible(soundSettingsVisibility);
        closeSoundSettingsButton.setVisible(soundSettingsVisibility);
        soundSettingsOpen = soundSettingsVisibility;
    }

    /**
     * Gets the status of the sound menu and passes them to the game driver.
     * ** Currently uses print lines for testing **
     */
    @FXML
    public void updateSoundSettings() {
        // ** TEMP PRINT INSTEAD OF PASS VARIABLE STATUS **
        System.out.println("Music Disabled: " + musicCheckbox.isSelected() + ".");
        System.out.println("Sound Effects Disabled: " + effectsCheckbox.isSelected() + ".");
    }


    /**
     * Takes the parent element that the layout will be displayed in and saves it. Disables settings button on menu.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    /**
     * When the settings menu is first opened, set the sound settings variable to be false.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        soundSettingsOpen = false;
    }
}
