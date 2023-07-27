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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Location Menu Controller.
 * Main Game Driver.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class LocationMenuController implements Initializable {

    // Constant: The Location Value of a Boss Combat Location.
    private static final int BOSS_COMBAT_LOCATION = 500;

    // Constant: The difference between Location Areas. Mod by Divider gets Location Area from Location Value.
    // Equation: Location Area = Location Value % Location Area Divider.
    private static final int LOCATION_AREA_DIVIDER = 100;

    // Constant: The Size (Row and Column) of Game Map Arrays and Player Map Arrays.
    private static final int MAP_ARRAY_SIZE = 6;

    // Constant: The Location Area Value for the Tutorial Location.
    private static final int TUTORIAL_AREA_VALUE = 0;

    // Constant: The Location Area Value for Easy Locations.
    private static final int EASY_AREA_VALUE = 1;

    // Constant: The Location Area Value for Medium Locations.
    private static final int MEDIUM_AREA_VALUE = 2;

    // Constant: The Location Area Value for Hard Locations.
    private static final int HARD_AREA_VALUE = 3;

    // Constant: The Location Area Value for Boss Locations. (Boss Combat = 500)
    private static final int BOSS_AREA_VALUE = 4;

    // FXML Element: The Layout Element for the Location Menu Scene.
    @FXML
    private GridPane locationMenuGrid;

    // FXML Element: The Grid Pane Element for the Player Mini-Map.
    @FXML
    private GridPane locationMenuMap;

    // FXML Element: The Button that calls the Start Fight method.
    @FXML
    private Button combatButton;

    // FXML Element: The Button that calls the open Character Info Scene.
    @FXML
    private Button viewCharacterButton;

    // FXML Element: The Button that calls the open Map Info Scene.
    @FXML
    private Button viewMapButton;

    // FXML Element: The Button that calls the open End Game Scene.
    @FXML
    private Button endGameButton;

    // FXML Element: The Button that calls the open Settings Menu Scene.
    @FXML
    private Button settingsButton;

    // FXML Element: The Button that calls the Move North method.
    @FXML
    private Button moveNorthButton;

    // FXML Element: The Button that calls the Move East method.
    @FXML
    private Button moveEastButton;

    // FXML Element: The Button that calls the Move South method.
    @FXML
    private Button moveSouthButton;

    // FXML Element: The Button that calls the Move West method.
    @FXML
    private Button moveWestButton;

    // FXML Element: The Text Area that displays Location Menu text.
    @FXML
    private TextArea locationDescription;

    // Game Object: The Player game object for the current Game.
    private Player playerCharacter;

    // Game Object: The Map game object for the current Game.
    private Map gameMaps;

    // Game Object: The Movement game object for the current Game.
    private Movement playerMovement;

    // Game Object: The current Location object for the current Game.
    private Location currentLocation;

    // Game Object: The current Enemy object for the current Game.
    private Enemy currentEnemy;

    // Variable: A State value that indicates if the Tutorial should be skipped when starting a Game.
    private boolean skipTutorial;

    // Variable: A value that indicates the current Game State.
    private String gameState;

    /**
     * Starts the Game based on the Starting State of the Game. (Start Tutorial, Skip Tutorial, Load Game)
     */
    public void gameStarter() {
        if (Objects.equals(gameState, "Load Game")) {
            // Set the Tutorial to be Beaten for future Player Encounters.
            gameMaps.beatTutorial();
            // Get the description for the Current Location.
            checkLocation();
        } else if (!skipTutorial) {
            // If: A new Game is started and the Tutorial is not skipped.
            // Disable the Menu Buttons.
            disableMenuButtons();
            // Create Tutorial Location and get Tutorial start description.
            currentLocation = new TutorialLocation(0, locationMenuGrid);
            currentLocation.getDescription();
            // End State: Set Game State to start the Tutorial.
            gameState = "Start Tutorial";
        } else {
            // Else: A New Game is started and the Tutorial is skipped.
            // Set the Tutorial to be Beaten for future Player encounters.
            gameMaps.beatTutorial();
            // Display the Game Start Movement Message and call the Player Movement method.
            locationDescription.setText("Game Start Movement Message.");
            // End State: Calls the Movement method.
            playerMove();
        }
    }

    // Starts a Tutorial by creating a Tutorial Enemy, then starting a Tutorial Fight.
    private void tutorialCombat() {
        // Set Current Enemy to a Tutorial Enemy of Tutorial.
        currentEnemy = new TutorialEnemy(locationMenuGrid);
        // Open the Combat Menu and get the Controller.
        GameCombatController combatController = openGameCombatMenu().getController();
        // Start the Tutorial Combat, pass a Combat Title and Combat Description.
        combatController.combatStarter("Tutorial: Into Fight", "Fight with your old boss.");
        // Update the Game Map with a beaten Tutorial Location.
        gameMaps.beatTutorial();
        // End State: Wait after displaying Finish Tutorial description.
        gameState = "Finished Tutorial";
    }


    // Starts a Combat by calling the Open Combat and Start Combat methods.
    // Calls a Boss Combat if current Location Value is a Boss Combat Location.
    // combatTitle: A string to display as a Combat Title
    // combatDescription: A String to Display as a Combat Description
    private void startCombat(final String combatTitle, final String combatDescription) {
        if (gameMaps.getLocation() == BOSS_COMBAT_LOCATION) {
            // If: Current Location is a Boss Combat Location.
            bossCombat();
        } else {
            // Else: Current Combat is not a Boss Combat.
            // Open a Game Combat Scene and get its Controller.
            GameCombatController combatController = openGameCombatMenu().getController();
            // Begin a new Combat. Pass the Combat Title and Combat Description parameters.
            combatController.combatStarter(combatTitle, combatDescription);
        }
    }

    // Starts a Boss Combat by calling the Open Combat and Start Combat methods.
    // Sets and passes the Boss Combat Title and Boss Combat Description.
    private void bossCombat() {
        /// Open a Boss Combat Scene and get its Controller.
        GameCombatController combatController = openGameCombatMenu().getController();
        // Set Boss Combat Title and Boss Combat Description.
        String bossCombatTitle = "Boss Combat.";
        String bossCombatDescription = "Round: 1.";
        // Begin a new Boss Combat. Pass a Combat Title and Combat Description.
        combatController.combatStarter(bossCombatTitle, bossCombatDescription);
        // End State: Wait after displaying Finish Boss Combat description.
        gameState = "Finished Boss";
    }

    // Displays the Movement description, enables the Movement Buttons, and Updates Player Location in Movement.
    private void playerMove() {
        // Display text to player indicating Movement is possible.
        locationDescription.appendText("Movement Message");
        // Enable the Movement Buttons.
        moveNorthButton.setDisable(false);
        moveEastButton.setDisable(false);
        moveSouthButton.setDisable(false);
        moveWestButton.setDisable(false);
        // Update Movement Method with Player Location.
        playerMovement.setLocation(playerCharacter.getLocation());
    }

    /**
     * Moves the Player North if possible and updates Mini-Map. Does nothing if Movement is not possible.
     * Called By: moveWestButton.
     */
    @FXML
    public void moveNorth() {
        // Record the current player location to test check for Player Movement.
        int[] startLocation = playerCharacter.getLocation();
        // Try to move the Player North, update Game Map, and update Mini-Map.
        playerCharacter.setLocation(playerMovement.moveNorth());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        // If: Movement was valid / the Player moved. (Current Location is not starting Location.)
        if (!Arrays.equals(startLocation, playerCharacter.getLocation())) {
            // End State: Get the new Location description.
            checkLocation();
        }
    }

    /**
     * Moves the Player East if possible and updates Mini-Map. Does nothing if Movement is not possible.
     * Called By: moveWestButton.
     */
    @FXML
    public void moveEast() {
        // Record the current player location to test check for Player Movement.
        int[] startLocation = playerCharacter.getLocation();
        // Try to move the Player East, update Game Map, and update Mini-Map.
        playerCharacter.setLocation(playerMovement.moveEast());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        // If: Movement was valid / the Player moved. (Current Location is not starting Location.)
        if (!Arrays.equals(startLocation, playerCharacter.getLocation())) {
            // End State: Get the new Location description.
            checkLocation();
        }
    }

    /**
     * Moves the Player South if possible and updates Mini-Map. Does nothing if Movement is not possible.
     * Called By: moveWestButton.
     */
    @FXML
    public void moveSouth() {
        // Record the current player location to test check for Player Movement.
        int[] startLocation = playerCharacter.getLocation();
        // Try to move the Player South, update Game Map, and update Mini-Map.
        playerCharacter.setLocation(playerMovement.moveSouth());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        // If: Movement was valid / the Player moved. (Current Location is not starting Location.)
        if (!Arrays.equals(startLocation, playerCharacter.getLocation())) {
            // End State: Get the new Location description.
            checkLocation();
        }
    }

    /**
     * Moves the Player West if possible and updates Mini-Map. Does nothing if Movement is not possible.
     * Called By: moveWestButton.
     */
    @FXML
    public void moveWest() {
        // Record the current player location to test check for Player Movement.
        int[] startLocation = playerCharacter.getLocation();
        // Try to move the Player West, update Game Map, and update Mini-Map.
        playerCharacter.setLocation(playerMovement.moveWest());
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
        // If: Movement was valid / the Player moved. (Current Location is not starting Location.)
        if (!Arrays.equals(startLocation, playerCharacter.getLocation())) {
            // End State: Get the new Location description.
            checkLocation();
        }
    }

    // Gets the Location Area for the current Location, and Updates the Current Location and Current Enemy.
    // Sets Game State to Combat Location or Explore Location by on Location Type and State.
    private void checkLocation() {
        // Get the Current Location Value to pass to Location object.
        int currentLocationValue = gameMaps.getLocation();
        // Get the Location Area from the Location Value to create the Location and Enemy Objects.
        int locationArea = currentLocationValue / LOCATION_AREA_DIVIDER;
        // Update the Player Location in the Game Map and update the Location Value for the Current Location
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.updateMap();
        if (locationArea == TUTORIAL_AREA_VALUE) {
            // If: Location Area = Tutorial.
            // Set Current Location with to Tutorial Location and pass the Location Value and Parent Layout Element.
            currentLocation = new TutorialLocation(currentLocationValue, locationMenuGrid);
            // No Enemy is needed for Tutorial Location.
        } else if (locationArea == EASY_AREA_VALUE) {
            // If: Location Area = Easy.
            // Set Current Location with to Easy Location and pass the Location Value and Parent Layout Element.
            currentLocation = new EasyLocation(currentLocationValue, locationMenuGrid);
            // Set Current Enemy to an Easy Enemy object and pass the Parent Layout Element.
            currentEnemy = new EasyEnemy(locationMenuGrid);
        } else if (locationArea == MEDIUM_AREA_VALUE) {
            // If: Location Area = Medium.
            // Set Current Location with to Medium Location and pass the Location Value and Parent Layout Element.
            currentLocation = new MediumLocation(currentLocationValue, locationMenuGrid);
            // Set Current Enemy to a Medium Enemy object and pass the Parent Layout Element.
            currentEnemy = new MediumEnemy(locationMenuGrid);
        } else if (locationArea == HARD_AREA_VALUE) {
            // If: Location Area = Hard.
            // Set Current Location with to Hard Location and pass the Location Value and Parent Layout Element.
            currentLocation = new HardLocation(currentLocationValue, locationMenuGrid);
            // Set Current Enemy to a Hard Enemy object and pass the Parent Layout Element.
            currentEnemy = new HardEnemy(locationMenuGrid);
        } else if (locationArea >= BOSS_AREA_VALUE) {
            // If: Location Area = Boss.
            // Set Current Location with to Boss Location and pass the Location Value and Parent Layout Element.
            currentLocation = new BossLocation(currentLocationValue, locationMenuGrid);
            // Set Current Enemy to a Boss Enemy object and pass the Parent Layout Element.
            currentEnemy = new BossEnemy(locationMenuGrid);
        }
        // Check if Current Location is a Combat Location.
        if (currentLocation.getDescription()) {
            // If: Current Location is a Combat Location.
            // End State: Wait for user to read Location description
            gameState = "Combat Location";
        } else {
            // Else: Current Location is an Explore Location.
            // End State: Wait for user to read Location description
            gameState = "Explore Location";
        }
        // Disable Movement / Combat Buttons until Location is Finished.
        disableActionButtons();
    }

    /**
     * Creates a Combat by passing the Player object and Current Enemy object.
     * Preforms Post-Combat actions (Update Map, Win Game, or Lose Game).
     *
     * @throws FileNotFoundException If the Enemy Text File cannot be read.
     */
    @FXML
    public void startFight() throws FileNotFoundException {
        // Start a Combat and pass the Combat Title and Combat Description for the Current Enemy.
        startCombat(currentEnemy.getCombatTitle(), currentEnemy.getCombatDescription());
        // Call the Player Finish Combat display Outcome method.
        playerCharacter.finishCombat(locationDescription);
        // End State: Wait after displaying Finish Combat description.
        gameState = "Finished Combat";
        if (playerCharacter.getLastOutcome() == 1) {
            // If: The Player won the Combat, update the Game Map and Player Map.
            gameMaps.beatBattle();
        } else if (playerCharacter.getLastOutcome() == 2) {
            // If: The Player won a Boss Combat, call the "Win Game" method.
            // winGame();
            // Un-Set Game State if Player wins the Game.
            gameState = "";
        } else if (playerCharacter.getStatHealth() < 1) {
            // If: The Player has less than 1 Health, call the "Lose Game" Method.
            // loseGame();
            // Un-Set Game State if Player loses the Game.
            gameState = "";
        }
    }


    /**
     * Compares the Game State string to specific strings and calls the appropriate method if there is a match.
     * The Game State string is set whenever the game is waiting for the user to continue after an action.
     */
    @FXML
    public void gameStateCheck() {
        if (Objects.equals(gameState, "Start Tutorial")) {
            // If: Starting a Tutorial. Un-Set Game State.
            gameState = "";
            // Call Tutorial Combat starter method.
            tutorialCombat();
        } else if (Objects.equals(gameState, "Finished Tutorial")) {
            // If: Finished Tutorial Combat. Un-Set Game State.
            gameState = "";
            // Display the Game Start Movement Message and call the Player Movement method.
            locationDescription.setText("Game Start Movement Message.\n");
            playerMove();
        } else if (Objects.equals(gameState, "Explore Location")) {
            // If: Moved to an Explore Location and displayed description. Un-Set Game State.
            gameState = "";
            // Call the Player Movement method.
            playerMove();
        } else if (Objects.equals(gameState, "Combat Location")) {
            // If: Moved to a Combat Location and displayed Description. Un-Set Game State.
            gameState = "";
            // Enable the Combat Button.
            combatButton.setDisable(false);
        } else if (Objects.equals(gameState, "Finished Combat")) {
            // If a Combat was finished and Outcome description was displayed. Un-Set Game State.
            gameState = "";
            // Call Player Movement Message.
            playerMove();
        }
    }


    /**
     * Load the Character Info Scene and passes the Layout Element for the Location Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (2, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the Settings Menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the new menu and pass the location menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.setSceneVariables(locationMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        // Disable the Menu Buttons.
        disableMenuButtons();
        // Call method to pass game info to the Settings Controller
        passGameInfo(controller);
        // Disable Settings Scene Button while Another Scene is Open
        settingsButton.setDisable(true);
    }

    private void passGameInfo(final SettingsMenuController settingsController) {
        settingsController.setGameInfo(playerInfoArrayBuilder(), playerAbilitiesArrayBuilder(), gameMapsArrayBuilder());
    }

    private ArrayList<String> playerInfoArrayBuilder() {
        // Create an Array List of strings to store the Character Stats Info.
        ArrayList<String> statsInfo = new ArrayList<>();
        // Add the Player Name to the Stats Info Array.
        statsInfo.add(playerCharacter.getName());
        // Get the Player Coordinates to convert to a string.
        int[] playerCoordinates = playerCharacter.getLocation();
        // Convert the Coordinates to a string ("xy") and add them to the Stats Info Array.
        String stringCoordinates = String.valueOf(playerCoordinates[0]) + (playerCoordinates[1]);
        statsInfo.add(stringCoordinates);
        // Convert the Player Level to a string and add it to the Stats Info Array.
        statsInfo.add(String.valueOf(playerCharacter.getStatLevel()));
        // Convert the Player Exp to a string and add it to the Stats Info Array.
        statsInfo.add(String.valueOf(playerCharacter.getStatExp()));
        // Convert the Player Health to a string and add it to the Stats Info Array.
        statsInfo.add(String.valueOf(playerCharacter.getStatHealth()));
        return statsInfo;
    }

    private ArrayList<String> playerAbilitiesArrayBuilder() {
        // Create an Array List of strings to store the Ability Stats Info.
        ArrayList<String> abilityInfo = new ArrayList<>();
        // Convert the Player Re-Roll uses to a string and add it to the Ability Info Array.
        abilityInfo.add(String.valueOf(playerCharacter.getAbilityReRoll()));
        // Convert the Player Add uses to a string and add it to the Ability Info Array.
        abilityInfo.add(String.valueOf(playerCharacter.getAbilityAdd()));
        // Convert the Player Take-Away uses to a string and add it to the Ability Info Array.
        abilityInfo.add(String.valueOf(playerCharacter.getAbilityTakeAway()));
        return abilityInfo;
    }

    private ArrayList<String> gameMapsArrayBuilder() {
        // Create an Array List of strings to store the Game Maps.
        ArrayList<String> gameMapsArray = new ArrayList<>();
        // Call the Map to get the Game Map as a string and add it to the Game Maps Array
        gameMapsArray.add(gameMaps.getGameMap());
        // Call the Map to get the Player Map as a string and add it to the Player Maps Array
        gameMapsArray.add(gameMaps.getPlayerMap());
        return gameMapsArray;
    }

    /**
     * Load the Character Info Scene and passes the Layout Element for the Location Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (2, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openCharacterInfo() throws IOException {
        // Load the New Game FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CharacterInfoMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the Character Info Scene and pass the Parent Layout Element.
        CharacterInfoController controller = loader.getController();
        controller.setSceneVariables(locationMenuGrid, playerCharacter);
        // Define where to display the new Scene in the Grid Pane and add it to the Grid Pane.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        // Call the method to display the Player Info.
        controller.displayCharacter();
        // Disable the Menu Buttons.
        disableMenuButtons();
        // Disable Settings Scene Button while Another Scene is Open
        settingsButton.setDisable(true);
    }

    /**
     * Load the Map Info Scene and passes the Layout Element for the Location Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (2, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openMapInfo() throws IOException {
        // Load the Saved Games FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameMapMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the Map Info Scene and pass the Parent Layout Element.
        MapInfoController controller = loader.getController();
        controller.setSceneVariables(locationMenuGrid, gameMaps);
        // Define where to display the new Scene in the Grid Pane and add it to the Grid Pane.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        // Call the method to display the Player Map.
        controller.displayMap();
        // Disable the Menu Buttons.
        disableMenuButtons();
        // Disable Settings Scene Button while Another Scene is Open
        settingsButton.setDisable(true);
    }

    /**
     * Load the End Game Scene and passes the Layout Element for the Location Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (2, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openEndGameMenu() throws IOException {
        // Load the Game Info FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameEndMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the End Game Scene and pass the Parent Layout Element.
        EndGameController controller = loader.getController();
        controller.setSceneVariables(locationMenuGrid);
        // Define where to display the new Scene in the Grid Pane and add it to the Grid Pane.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        // Disable Menu buttons while Scene is open.
        disableMenuButtons();
        // Disable Settings Scene Button while Another Scene is Open
        settingsButton.setDisable(true);
    }

    // Opens a new Game Combat Scene, displays it in the current Scene and disables the background Menu buttons.
    // Also returns the Loader for the new Scene so caller methods can get the Scene Controller for the Combat.
    private FXMLLoader openGameCombatMenu() {
        // Load the Game Combat FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatMenu.fxml"));
        Parent root;
        try {
            // Try: Load the Game Combat FXML document into a root object.
            root = loader.load();
        } catch (IOException e) {
            // Catches any errors loading the Game Combat FXML file.
            throw new RuntimeException(e);
        }
        // Get the Controller for the Game Combat Scene and pass the Parent Layout Enemy.
        GameCombatController controller = loader.getController();
        // Also pass the Player object and the Current Enemy object for Combat creation.
        controller.setSceneVariables(locationMenuGrid, playerCharacter, currentEnemy);
        // Define where to display the new Scene in the Grid Pane and add it to the Grid Pane.
        GridPane.setConstraints(root, 2, 1);
        locationMenuGrid.getChildren().add(root);
        // Disable Menu buttons while Scene is open.
        disableMenuButtons();
        // Return the Loader to allow the Parent method to call the Start Combat method for the new Scene.
        return loader;
    }


    // Disables the Menu Buttons while a different Scene is open.
    private void disableMenuButtons() {
        settingsButton.setDisable(true);
        viewCharacterButton.setDisable(true);
        viewMapButton.setDisable(true);
        endGameButton.setDisable(true);
        locationDescription.setDisable(true);
    }

    // Disables the Movement / Combat Buttons while a message is displayed.
    private void disableActionButtons() {
        moveNorthButton.setDisable(true);
        moveEastButton.setDisable(true);
        moveSouthButton.setDisable(true);
        moveWestButton.setDisable(true);
        combatButton.setDisable(true);
    }

    /**
     * Set Player Character Name and Disable Tutorial State when starting a new Game.
     *
     * @param characterName   A string representing the Player Character Name
     * @param disableTutorial The Disable Tutorial State boolean value
     */
    public void newGameInfo(final String characterName, final boolean disableTutorial) {
        playerCharacter.setName(characterName);
        skipTutorial = disableTutorial;
    }

    /**
     * Takes an Array List of strings, converts them to the correct type, and sets the Player Stats to those values.
     *
     * @param statsArray A string Array List with 5 values.
     */
    public void loadStats(final ArrayList<String> statsArray) {
        gameState = "Load Game";
        // Set the Player Name to the Name string.
        playerCharacter.setName(statsArray.get(0));

        // Convert the Coordinates to an array of char. Format = {'0', '0'}.
        char[] coordinates = statsArray.get(1).toCharArray();
        // Set the Coordinates int array to the values from the char array.
        int[] newCoordinates = {coordinates[0], coordinates[1]};
        playerCharacter.setLocation(newCoordinates);

        // Convert the Level string to an integer and set the Player Level Stat to its value.
        int statLevel = Integer.parseInt(statsArray.get(2));
        playerCharacter.setStatLevel(statLevel);
        // Convert the Add string to an integer and set the Player Add Exp Stat to its value.
        int statExp = Integer.parseInt(statsArray.get(3));
        playerCharacter.setStatExp(statExp);
        // Convert the Take-Away string to an integer and set the Player Health Stat to its value.
        int statHealth = Integer.parseInt(statsArray.get(4));
        playerCharacter.setStatHealth(statHealth);
    }

    /**
     * Takes an Array List of strings, converts them to Integers, and sets the Player Abilities to those values.
     *
     * @param abilitiesArray A string Array List with 3 values.
     */
    public void loadAbilities(final ArrayList<String> abilitiesArray) {
        // Convert the Re-Roll string to an integer and set the Player Re-Roll Ability uses to its value.
        int reRollAbility = Integer.parseInt(abilitiesArray.get(0));
        playerCharacter.setAbilityReRoll(reRollAbility);
        // Convert the Add string to an integer and set the Player Add Ability uses to its value.
        int addAbility = Integer.parseInt(abilitiesArray.get(1));
        playerCharacter.setAbilityAdd(addAbility);
        // Convert the Take-Away string to an integer and set the Player Take-Away Ability uses to its value.
        int takeAwayAbility = Integer.parseInt(abilitiesArray.get(2));
        playerCharacter.setAbilityTakeAway(takeAwayAbility);
    }

    /**
     * Takes an Array List with two strings representing the Save Info for a Player Map and a Game Map.
     * Converts the two strings into 2D arrays and assigns them to the Map Object Game Map and Player Map.
     *
     * @param mapsArray A string Array List with 2 values.
     */
    public void loadMaps(final ArrayList<String> mapsArray) {
        // Call a method to convert the Game Map string into a 2D integer array and set the Game Map.
        convertStringGameMap(mapsArray.get(0));
        // Create a new Player Map array to pass to the Map object.
        String[][] newPlayerMap = new String[MAP_ARRAY_SIZE][MAP_ARRAY_SIZE];
        // Set the starting Row and Column of the Game Map.
        int currentRow = 0;
        int currentColum = 0;
        for (char playerMapChar : mapsArray.get(1).toCharArray()) {
            // If at the end of a Column, move to first Column of next Row.
            if (currentColum > MAP_ARRAY_SIZE) {
                currentColum = 0;
                currentRow++;
            }
            // Convert the current char being read into a string.
            String currentMapCell = Character.toString(playerMapChar);
            // Set the current Cell of the Player Map to the Current Cell string.
            newPlayerMap[currentRow][currentColum] = currentMapCell;
            // Move to the next Cell in the Map.
            currentColum++;
        }
        // Set the Player Map to the Loaded Player Map.
        gameMaps.setPlayerMap(newPlayerMap);
    }

    private void convertStringGameMap(final String stringGameMap) {
        // Create a new Game Map array to pass to the Map object.
        int[][] newGameMap = new int[MAP_ARRAY_SIZE][MAP_ARRAY_SIZE];
        // Set the starting Row and Column of the Game Map.
        int currentRow = 0;
        int currentColum = 0;
        // Create a String Builder to construct the Location Values.
        StringBuilder locationValueBuilder = new StringBuilder();
        // Iterate through each character in the Game Map string.
        for (char gameMapChar : stringGameMap.toCharArray()) {
            // If at the end of a Column, move to first Column of next Row.
            if (currentColum > MAP_ARRAY_SIZE) {
                currentColum = 0;
                currentRow++;
            }
            if (gameMapChar != ' ') {
                // If: The current char is a number, add it to the Location Value Builder.
                locationValueBuilder.append(gameMapChar);
            } else {
                // Else: Reached the end of a Location Value.
                // Convert the Built Location Value to an Integer.
                int locationValue = Integer.parseInt(locationValueBuilder.toString());
                // Set the current Game Map Cell to the Built Location Value
                newGameMap[currentRow][currentColum] = locationValue;
                // Clear the String Builder and move to the next Cell.
                locationValueBuilder = new StringBuilder();
                currentColum++;
            }
        }
        // Set the Game Map to the Loaded Game Map.
        gameMaps.setGameMap(newGameMap);
    }

    /**
     * Set enabled Buttons when starting Game and initializes Game objects.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Disable Movement / Combat Buttons.
        moveNorthButton.setDisable(true);
        moveEastButton.setDisable(true);
        moveSouthButton.setDisable(true);
        moveWestButton.setDisable(true);
        combatButton.setDisable(true);
        // Initialize Constant Game Objects. (Do not change during game).
        playerCharacter = new Player();
        gameMaps = new Map();
        playerMovement = new Movement(locationMenuGrid);
        // Set Player Location in the Map object and update the Mini-Map
        gameMaps.setPlayerLocation(playerCharacter.getLocation());
        gameMaps.displayMiniMap(locationMenuMap);
    }
}
