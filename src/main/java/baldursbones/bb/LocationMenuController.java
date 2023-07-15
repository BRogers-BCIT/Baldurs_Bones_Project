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
 * Main Game Driver.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class LocationMenuController implements Initializable {

    // Variable: Save an FXML file to be loaded into the scene.
    private Parent root;

    // FXML Element: The layout object for the Location Menu that new menus are loaded into.
    @FXML
    private GridPane locationMenuGrid;

    // FXML Element: A grid pane object used to display a 3x3 view of the player map.
    @FXML
    private GridPane locationMenuMap;

    // FXML Element: Menu button that calls the Start Fight method.
    @FXML
    private Button startFightButton;

    // FXML Element: Menu button that calls the View Character method.
    @FXML
    private Button viewCharacterButton;

    // FXML Element: Menu button that calls the View Map method.
    @FXML
    private Button viewMapButton;

    // FXML Element: Menu button that calls the End Game method for testing.
    @FXML
    private Button endGameButton;

    // FXML Element: Menu button that calls the open Settings Menu method.
    @FXML
    private Button openSettingsButton;

    // FXML Element: Movement button for north. Calls move player method.
    @FXML
    private Button moveNorthButton;

    // FXML Element: Movement button for south. Calls move player method.
    @FXML
    private Button moveSouthButton;

    // FXML Element: Movement button for east. Calls move player method.
    @FXML
    private Button moveEastButton;

    // FXML Element: Movement button for west. Calls move player method.
    @FXML
    private Button moveWestButton;

    // Game Object: The game object for the Player Character fields and methods.
    private Player playerCharacter;

    // Game Object: The game object for the Map (Player and Game) fields and methods.
    private Map gameMaps;

    // Game Object: The game object for Movement fields and methods.
    private Movement playerMovement;

    // Game Object: The current Location object for the players position.
    private Location currentLocation;

    // Game Object: The current Enemy object for the players position.
    private Enemy currentEnemy;

    // Variable: Used to indicate if the tutorial should be skipped when starting a new game.
    private boolean skipTutorial;

    /**
     * Load the Settings Menu document, display it in the center of the screen, and disable all location menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the Settings Menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 2);
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
        // Load the New Game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CharacterInfoMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        CharacterInfoController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 2);
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
        // Load the Saved Games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMapMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        MapInfoController controller = loader.getController();
        controller.getContainerElements(locationMenuGrid, gameMaps);
        controller.displayMap();
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 2);
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
        // Load the Game Info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameEndMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        EndGameController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 2);
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
        disableMenuButtons();
        // Load the Game Info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        GameCombatController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 2);
        locationMenuGrid.getChildren().add(root);
    }

    // Disables the location menu buttons while a different menu is open.
    private void disableMenuButtons() {
        openSettingsButton.setDisable(true);
        startFightButton.setDisable(true);
        viewCharacterButton.setDisable(true);
        viewMapButton.setDisable(true);
        endGameButton.setDisable(true);
    }

    /** When a new game is created get the character name and the skip tutorial value from the new game controller.
     *
     * @param characterName a string representing the character name for this run
     * @param disableTutorial a boolean value determining if the tutorial section should be skipped
     */
    public void getGameInfo(final String characterName, final boolean disableTutorial) {
        playerCharacter.setName(characterName);
        skipTutorial = disableTutorial;
    }

    /** Set the movement buttons to be disabled when first opening a location menu.
     * @param url N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        moveNorthButton.setDisable(true);
        moveSouthButton.setDisable(true);
        moveEastButton.setDisable(true);
        moveWestButton.setDisable(true);
        playerCharacter = new Player();
        gameMaps = new Map();
        playerMovement = new Movement();
        gameMaps.displayMiniMap(locationMenuMap);
    }
}
