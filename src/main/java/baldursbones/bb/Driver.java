package baldursbones.bb;

import java.util.Scanner;

/** Game Driver.
 * @author Braden Rogers
 * @version 2023-TermProject
 * Note: Diver method contains only caller and loop methods.
 *      No tests are possible.
 */
public class Driver {

    // Factor dividing different areas of the game map
    private static final int LOCATION_DIVIDER = 100;

    // Location divided * location multiplier gives you location area range

    // 100 * 0 = Tutorial (000-099)
    private static final int TUTORIAL_LOCATION_MULTIPLIER = 0;

    // 100 * 1 = Easy (100-199)
    private static final int EASY_LOCATION_MULTIPLIER = 1;

    // 100 * 2 = Medium (200-299)
    private static final int MEDIUM_LOCATION_MULTIPLIER = 2;

    // 100 * 1 = Hard (300-399)
    private static final int HARD_LOCATION_MULTIPLIER = 3;

    // 100 * 1 = Easy(100-199)
    private static final int BOSS_LOCATION_MULTIPLIER = 4;

    // 100 * 1 = Easy(100-199)
    private static final int BOSS_FIGHT = 500;
    private final Player playerCharacter;
    private final Map gameMaps;
    private final Movement movementDriver;
    private Location currentLocation;
    private int currentLocationType;
    private boolean isFightLocation;
    private Enemy currentEnemy;
    private Combat currentCombat;
    private int outcome;
    private int gameState;

    /** Creates a game driver object and sets starting values.
     */
    public Driver() {
        gameState = 1;
        playerCharacter = new Player();
        gameMaps = new Map();
        movementDriver = new Movement();
    }
    /** Starts the game and calls the game loop.
     */
    public void startGame() {
        tutorial();
        gameLoop();
    }
    private void gameLoop() {
        while (gameState != 0) {
            gameMaps.displayMap();
            playerCharacter.setLocation(movementDriver.playerMove(playerCharacter.getLocation()));
            gameMaps.setPlayerLocation(playerCharacter.getLocation());
            locationHandler();
            gameMaps.updateMap(playerCharacter.getLocation());
            if (isFightLocation) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter to continue \n");
                scan.nextLine();
                combatHandler();
                outcome = currentCombat.startCombat();
                outcomeHandler();
            }
        }
    }

    // Get the location value and area of the current player location then call the appropriate methods.
    private void locationHandler() {
        // Location value getter and setter.
        currentLocationType = gameMaps.getLocation();

        // if current location / 100 == 0 then create tutorial location with the current location value.
        if (currentLocationType / LOCATION_DIVIDER == TUTORIAL_LOCATION_MULTIPLIER) {
            currentLocation = new TutorialLocation(currentLocationType);
            isFightLocation = currentLocation.getDescription();
        }
        if ((currentLocationType / LOCATION_DIVIDER) == EASY_LOCATION_MULTIPLIER) {
            currentLocation = new EasyLocation(currentLocationType);
            isFightLocation = currentLocation.getDescription();
        }
        if ((currentLocationType / LOCATION_DIVIDER) == MEDIUM_LOCATION_MULTIPLIER) {
            currentLocation = new MediumLocation(currentLocationType);
            isFightLocation = currentLocation.getDescription();
        }
        if ((currentLocationType / LOCATION_DIVIDER) == HARD_LOCATION_MULTIPLIER) {
            currentLocation = new HardLocation(currentLocationType);
            isFightLocation = currentLocation.getDescription();
        }
        if (currentLocationType > (LOCATION_DIVIDER * BOSS_LOCATION_MULTIPLIER)) {
            currentLocation = new BossLocation(currentLocationType);
            isFightLocation = currentLocation.getDescription();
        }
    }

    // Create an enemy object and start a combat based on the value of the current location.
    private void combatHandler() {

        // Create a boss enemy and call a boss fight.
        if ((currentLocationType == BOSS_FIGHT)) {
            currentEnemy = new BossEnemy();
            currentCombat = new BossCombat(playerCharacter, (BossEnemy) currentEnemy);

        } else {

            // if current location / 100 == 1 then create an easy enemy.
            if ((currentLocationType / LOCATION_DIVIDER) == EASY_LOCATION_MULTIPLIER) {
                currentEnemy = new EasyEnemy();
            }
            // if current location / 100 == 2 then create a medium enemy.
            if ((currentLocationType / LOCATION_DIVIDER) == MEDIUM_LOCATION_MULTIPLIER) {
                currentEnemy = new MediumEnemy();
            }
            // if current location / 100 == 3 then create a hard enemy.
            if ((currentLocationType / LOCATION_DIVIDER) == HARD_LOCATION_MULTIPLIER) {
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
        System.out.println("Game Win Text.");
        // Define game state as being finished
        gameState = 0;
    }

    // Defines the game behaviors and text to be printed on game loss.
    private void lose() {
        System.out.println("Game Win Text.");
        // Define game state as being finished
        gameState = 0;
    }

    /** Game launcher method.
     * @param args No passed arguments
     */
    public static void main(final String[] args) {
        // Create a new driver class and invoke the game start method.
        Driver game = new Driver();
        game.startGame();
    }

}
