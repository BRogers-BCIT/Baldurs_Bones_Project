package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Location Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class LocationMenuController implements Initializable {

    // Save an FXML file to be loaded into the scene.
    private Parent root;

    // The layout object for the location menu that new menus are loaded into.
    @FXML
    private GridPane locationMenuGrid;

    // Menu button that calls the open fight menu method.
    @FXML
    private Button startFightButton;

    // Menu button that calls the view character menu method.
    @FXML
    private Button viewCharacterButton;

    // Menu button that calls the view map menu method.
    @FXML
    private Button viewMapButton;

    // Menu button that calls the end game method for testing.
    @FXML
    private Button endGameButton;

    // Menu button that calls the open settings method.
    @FXML
    private Button openSettingsButton;


    /**
     * Load the settings menu document, display it in the center of the screen, and disable all location menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the settings menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
        openSettingsButton.setDisable(true);
    }

    /**
     * Load the character info menu document, display it in the center of the screen. Call button disable method.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openCharacterInfo() throws IOException {
        // Load the new game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CharacterInfoMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        CharacterInfoController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /**
     * Load the map display menu document, display it in the center of the screen. Call button disable method.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openMapInfo() throws IOException {
        // Load the saved games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMapMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        MapInfoController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /**
     * Load the game end menu document, display it in the center of the screen. Call button disable method.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openEndGameMenu() throws IOException {
        // Load the game info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameEndMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        EndGameController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /**
     * Load the game combat menu document, display it in the center of the screen. Call button disable method.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openGameCombatMenu() throws IOException {
        // Load the game info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        GameCombatController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    // Disables the location menu buttons while a different menu is open.
    private void disableMenuButtons() {
        startFightButton.setDisable(true);
        viewCharacterButton.setDisable(true);
        viewMapButton.setDisable(true);
        endGameButton.setDisable(true);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }
}
