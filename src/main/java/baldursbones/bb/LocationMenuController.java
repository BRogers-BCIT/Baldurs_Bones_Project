package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Location Menu Controller.
 * Main Game Driver.
 *
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

    // FXML Element: The text area that displays location area descriptions.
    @FXML
    private TextArea locationDescription;

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

    // Variable: Used to record the current game state when waiting for the player to continue after text is displayed.
    private String gameState;

    /**
     * Checks if the tutorial option is enabled and either starts the tutorial or skips the tutorial.
     * If playing tutorial: get the description for starting the tutorial and set the game state to start tutorial.
     * If skip tutorial: call the display a message about skipping the tutorial and call the movement method.
     */
    public void gameStarter() {
        // If: the tutorial is not being skipped.
        if (!skipTutorial) {
            // Disable the menu buttons.
            disableMenuButtons();
            // Create a tutorial location, and call method to display the tutorial start description.
            currentLocation = new TutorialLocation(0, locationMenuGrid);
            currentLocation.getDescription();
            // Set the Game State to be waiting for the user to continue.
            gameState = "start tutorial";
        } else {
            // Else: display the start game message and call the Movement method.
            locationDescription.setText("Game Start Movement Message.");
            playerMove();
        }
    }

    private void tutorialCombat() {
        // Create an Enemy of Tutorial type and pass the JavaFX layout element.
        currentEnemy = new TutorialEnemy(locationMenuGrid);
        // Open the combat menu and get the controller.
        // The combat menu will contain the player and enemy objects.
        GameCombatController combatController = openGameCombatMenu().getController();
        // Begin the Tutorial Combat. Also pass a Combat Name and Combat Description.
        combatController.combatStarter("Tutorial: Into Fight", "Fight with your old boss.");
        // Update the game map to have the tutorial be beaten.
        gameMaps.beatTutorial();
        // Set the Tutorial Combat to be over, and wait for the user to continue before calling the Movement method.
        gameState = "finish tutorial";
    }

    @FXML
    public void regularCombat(final String combatTitle, final String combatDescription) {
        // Open the combat menu and get the controller.
        // The combat menu will contain the player and enemy objects.
        GameCombatController combatController = openGameCombatMenu().getController();
        // Begin the Tutorial Combat. Also pass a Combat Name and Combat Description.
        combatController.combatStarter(combatTitle, combatDescription);
        // Set the Combat to be over, and wait for the user to continue before calling the Movement method.
        gameState = "finish combat";
    }

    private void bossCombat() {
        // Open the combat menu and get the controller.
        // The combat menu will contain the player and enemy objects.
        GameCombatController combatController = openGameCombatMenu().getController();
        // Begin the Tutorial Combat. Also pass a Combat Name and Combat Description.
        combatController.combatStarter("Boss Combat", "Round: 1");
        // Set the Combat to be over, and wait for the user to continue before calling the Movement method.
        gameState = "finish combat";
    }

    private void playerMove() {
        locationDescription.appendText("Movement Message");
        moveNorthButton.setDisable(false);
        moveEastButton.setDisable(false);
        moveSouthButton.setDisable(false);
        moveWestButton.setDisable(false);
        playerMovement.setLocation(playerCharacter.getLocation());
    }

    @FXML
    public void moveNorth() {
        playerCharacter.setLocation(playerMovement.moveNorth());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        checkLocation();
    }

    @FXML
    public void moveEast() {
        playerCharacter.setLocation(playerMovement.moveEast());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        checkLocation();
    }

    @FXML
    public void moveSouth() {
        playerCharacter.setLocation(playerMovement.moveSouth());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        checkLocation();
    }

    @FXML
    public void moveWest() {
        playerCharacter.setLocation(playerMovement.moveWest());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        checkLocation();
    }

    private void checkLocation() {
        disableActionButtons();
        int currentLocationValue = gameMaps.getLocation();
        int locationArea = currentLocationValue / 100;
        if (locationArea == 0) {
            currentLocation = new TutorialLocation(currentLocationValue, locationMenuGrid);
        } else if (locationArea == 1) {
            currentLocation = new EasyLocation(currentLocationValue, locationMenuGrid);
            currentEnemy = new EasyEnemy(locationMenuGrid);
        } else if (locationArea == 2) {
            currentLocation = new MediumLocation(currentLocationValue, locationMenuGrid);
            currentEnemy = new MediumEnemy(locationMenuGrid);
        } else if (locationArea == 3) {
            currentLocation = new HardLocation(currentLocationValue, locationMenuGrid);
            currentEnemy = new HardEnemy(locationMenuGrid);
        } else if (locationArea >= 4) {
            currentLocation = new BossLocation(currentLocationValue, locationMenuGrid);
            currentEnemy = new BossEnemy(locationMenuGrid);
        }
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.updateMap();
        if (currentLocation.getDescription()) {
            gameState = "combat location";
        } else {
            gameState = "explore location";
        }
    }

    @FXML
    public void startFight() throws FileNotFoundException {
        if (currentLocation.locationValue != 500) {
            regularCombat(currentEnemy.getCombatTitle(), currentEnemy.getCombatDescription());
            playerCharacter.finishBattle(locationDescription);
            if (playerCharacter.getLastOutcome() == 1) {
                gameMaps.beatBattle(playerCharacter.getLocation());
            }
        } else {
            bossCombat();
        }
    }


    /**
     * Compares the Game State string to specific strings and calls the appropriate method if there is a match.
     * The Game State string is set whenever the game is waiting for the user to continue after an action.
     */
    @FXML
    public void gameStateCheck() {
        if (Objects.equals(gameState, "start tutorial")) {
            gameState = "";
            tutorialCombat();
        } else if (Objects.equals(gameState, "finish tutorial")) {
            gameState = "";
            locationDescription.setText("Game Start Movement Message.\n");
            playerMove();
        } else if (Objects.equals(gameState, "explore location")) {
            gameState = "";
            playerMove();
        }  else if (Objects.equals(gameState, "combat location")) {
            gameState = "";
            startFightButton.setDisable(false);
        } else if (Objects.equals(gameState, "finish combat")) {
            gameState = "";
            playerMove();
        }
    }


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
        GridPane.setConstraints(root, 2, 1);
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
        controller.getContainerElements(locationMenuGrid, playerCharacter);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        controller.displayCharacter();
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
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        controller.displayMap();
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
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /**
     * Load the game combat menu document, display it in the center of the screen. Call button disable method.
     *
     * @return the FXML loader for the combat menu for the method that called this to use
     * @throws RuntimeException if the fxml file being loaded does not exist
     */
    @FXML
    public FXMLLoader openGameCombatMenu() {
        disableMenuButtons();
        // Define the FXML file to load.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatMenu.fxml"));
        try {
            // Try: Load the Game Info menu FXML document into a root object.
            root = loader.load();
        } catch (IOException e) {
            // Catches any errors loading the Combat Menu FXML file.
            throw new RuntimeException(e);
        }
        // Get the controller for the new menu and pass the location menu layout to the class.
        GameCombatController controller = loader.getController();
        controller.getContainerElement(locationMenuGrid, playerCharacter, currentEnemy);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        return loader;
    }


    // Disables the location menu buttons while a different menu is open.
    private void disableMenuButtons() {
        openSettingsButton.setDisable(true);
        startFightButton.setDisable(true);
        viewCharacterButton.setDisable(true);
        viewMapButton.setDisable(true);
        endGameButton.setDisable(true);
    }

    // Disables the location action buttons while the player is displayed a message.
    private void disableActionButtons() {
        moveNorthButton.setDisable(true);
        moveEastButton.setDisable(true);
        moveSouthButton.setDisable(true);
        moveWestButton.setDisable(true);
        startFightButton.setDisable(true);
    }

    /**
     * When a new game is created get the character name and the skip tutorial value from the new game controller.
     *
     * @param characterName   a string representing the character name for this run
     * @param disableTutorial a boolean value determining if the tutorial section should be skipped
     */
    public void getGameInfo(final String characterName, final boolean disableTutorial) {
        playerCharacter.setName(characterName);
        skipTutorial = disableTutorial;
    }

    /**
     * Set the movement buttons to be disabled when first opening a location menu.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        moveNorthButton.setDisable(true);
        moveEastButton.setDisable(true);
        moveSouthButton.setDisable(true);
        moveWestButton.setDisable(true);
        startFightButton.setDisable(true);
        playerCharacter = new Player();
        gameMaps = new Map();
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        playerMovement = new Movement(locationMenuGrid);
        gameMaps.displayMiniMap(locationMenuMap);
    }
}
