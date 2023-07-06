package baldursbones.bb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Game Driver.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Driver {

    // Constant: Factor for dividing different areas of the game map
    private static final int LOCATION_DIVIDER = 100;

    // Equation: Location divided * location multiplier gives you location area range

    // Constant: 100 * 0 = Tutorial value range (000-099)
    private static final int TUTORIAL_LOCATION_MULTIPLIER = 0;

    // Constant: 100 * 1 = Easy value range (100-199)
    private static final int EASY_LOCATION_MULTIPLIER = 1;

    // Constant: 100 * 2 = Medium value range (200-299)
    private static final int MEDIUM_LOCATION_MULTIPLIER = 2;

    // Constant: 100 * 3 = Hard value range (300-399)
    private static final int HARD_LOCATION_MULTIPLIER = 3;

    // Constant: 100 * 4 = Boss Explore Locations value range (400-499)
    private static final int BOSS_LOCATION_MULTIPLIER = 4;

    // Constant: 100 * 5 = Boss Fight value (500)
    private static final int BOSS_FIGHT = 500;

    // Object: Stores the player object for the current game.
    private final Player playerCharacter;

    // Object: Stores the map object for the current game.
    private final Map gameMaps;

    // Object: Stores a movement object used for the player movement methods.
    private final Movement movementDriver;

    // Object: Stores the location object of the current location.
    private Location currentLocation;

    // Variable: Records the value of the current location to be checked.
    private int currentLocationValue;

    // Variable: Records if a location has an available fight.
    private boolean isFightLocation;

    // Object:  Contains an enemy object to be used in a combat
    private Enemy currentEnemy;

    // Object:  Contains the object for a currently running combat.
    private Combat currentCombat;

    // Variable: Used to pass the outcome of a combat from a combat to a player object.
    private int outcome;

    // Variable: Used to track whether the game has ended. (gameState != 0)
    private int gameState;

    // Text file: contains all dialogue to be printed by the driver class.
    private final File gameText = new File("src/main/resources/baldursbones/bb/GameText.txt");

    /**
     * Creates a game driver object and sets starting values.
     */
    public Driver() {
        // Set the game to be running then make new player, map, and movement objects.
        gameState = 1;
        playerCharacter = new Player();
        gameMaps = new Map();
        movementDriver = new Movement();
    }

    /**
     * Starts the game by calling the tutorial then beginning the gameplay loop.
     */
    public void startGame() {
        tutorial();
        gameLoop();
    }

    private void gameLoop() {

        // While loop to run the game while the game is not won or lost.
        while (gameState != 0) {

            // Start by displaying the map and letting the player move.
            gameMaps.displayMap();
            playerCharacter.setLocation(movementDriver.playerMove(playerCharacter.getLocation()));

            // Update the player location, call the location handler, and update the map.
            gameMaps.setPlayerLocation(playerCharacter.getLocation());
            locationHandler();
            gameMaps.updateMap(playerCharacter.getLocation());

            // If the location is a fight location.
            if (isFightLocation) {

                // Prompt user to continue and call the combat. ** Replace with button press. **
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter to continue \n");
                scan.nextLine();
                combatHandler();

                // Get the outcome from the combat and call the outcome handler.
                outcome = currentCombat.startCombat();
                outcomeHandler();
            }
        }
    }

    // Get the location value of the current player location then call the appropriate methods.
    private void locationHandler() {

        // Location value getter and setter.
        currentLocationValue = gameMaps.getLocation();

        // if current location / 100 == 0 then create tutorial location with the current location value.
        if (currentLocationValue / LOCATION_DIVIDER == TUTORIAL_LOCATION_MULTIPLIER) {
            currentLocation = new TutorialLocation(currentLocationValue);
            isFightLocation = currentLocation.getDescription();
        }

        // if current location / 100 == 1 then create easy location with the current location value.
        if ((currentLocationValue / LOCATION_DIVIDER) == EASY_LOCATION_MULTIPLIER) {
            currentLocation = new EasyLocation(currentLocationValue);
            isFightLocation = currentLocation.getDescription();
        }

        // if current location / 100 == 2 then create medium location with the current location value.
        if ((currentLocationValue / LOCATION_DIVIDER) == MEDIUM_LOCATION_MULTIPLIER) {
            currentLocation = new MediumLocation(currentLocationValue);
            isFightLocation = currentLocation.getDescription();
        }

        // if current location / 100 == 3 then create hard location with the current location value.
        if ((currentLocationValue / LOCATION_DIVIDER) == HARD_LOCATION_MULTIPLIER) {
            currentLocation = new HardLocation(currentLocationValue);
            isFightLocation = currentLocation.getDescription();
        }

        // if current location / 100 >= 4 then create boss location with the current location value.
        if (currentLocationValue > (LOCATION_DIVIDER * BOSS_LOCATION_MULTIPLIER)) {
            currentLocation = new BossLocation(currentLocationValue);
            isFightLocation = currentLocation.getDescription();
        }
    }

    // Create an enemy object and start a combat based on the value of the current location.
    private void combatHandler() {

        // Create a boss enemy and call a boss fight.
        if ((currentLocationValue == BOSS_FIGHT)) {
            currentEnemy = new BossEnemy();
            currentCombat = new BossCombat(playerCharacter, (BossEnemy) currentEnemy);

        } else {

            // if current location / 100 == 1 then create an easy enemy.
            if ((currentLocationValue / LOCATION_DIVIDER) == EASY_LOCATION_MULTIPLIER) {
                currentEnemy = new EasyEnemy();
            }
            // if current location / 100 == 2 then create a medium enemy.
            if ((currentLocationValue / LOCATION_DIVIDER) == MEDIUM_LOCATION_MULTIPLIER) {
                currentEnemy = new MediumEnemy();
            }
            // if current location / 100 == 3 then create a hard enemy.
            if ((currentLocationValue / LOCATION_DIVIDER) == HARD_LOCATION_MULTIPLIER) {
                currentEnemy = new HardEnemy();
            }

            // Create a regular combat with the appropriate enemy object.
            currentCombat = new Combat(playerCharacter, currentEnemy);
        }
    }

    // Call the appropriate methods based on the outcome returned at the end of a fight.
    private void outcomeHandler() {

        // 2 is returned by beating the boss: call the game win method.
        if (outcome == 2) {
            win();
        } else {

            // 1 is returned by beating a regular enemy: get the win battle text and mark the location as beaten.
            if (outcome == 1) {
                currentEnemy.win();
                gameMaps.beatBattle(playerCharacter.getLocation());

                // -1 is returned by losing to a regular enemy: get the lose battle text.
            } else if (outcome == -1) {
                currentEnemy.lose();
            }

            // Pass the outcome value to the player to update stats.
            // Then, if the player has less than 1 health then call the game lose method.
            playerCharacter.finishBattle(outcome);
            if (playerCharacter.getHealth() <= 0) {
                lose();
            }
        }
    }

    // Tutorial method. Creates a tutorial location, starts the tutorial fight, and marks the tutorial as finished.
    private void tutorial() {

        // Create a new tutorial location and get the description.
        currentLocation = new TutorialLocation(LOCATION_DIVIDER * TUTORIAL_LOCATION_MULTIPLIER);
        currentLocation.getDescription();

        // Create a tutorial enemy and call the tutorial fight.
        currentEnemy = new TutorialEnemy();
        currentCombat = new Combat(playerCharacter, currentEnemy);
        currentCombat.startCombat();

        // Mark the tutorial location as beaten
        gameMaps.beatTutorial();
    }

    // Defines the game behaviors and text to be printed on game victory.
    private void win() {
        // Try to read the win-game text from the Driver text file.
        try {
            Scanner fileReader = new Scanner(gameText);
            // Print the text to user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Define game state as being finished
        gameState = 0;
    }

    // Defines the game behaviors and text to be printed on game loss.
    private void lose() {
        // Try to read the lose-game text from the Driver text file.
        try {
            Scanner fileReader = new Scanner(gameText);
            // Skip to correct print line.
            fileReader.nextLine();
            // Print the text to user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Define game state as being finished
        gameState = 0;
    }

    /**
     * Game launcher method.
     *
     * @param args No passed arguments
     */
    public static void main(final String[] args) {
        // Create a new driver class and invoke the game start method.
        Driver game = new Driver();
        game.startGame();
    }

}
