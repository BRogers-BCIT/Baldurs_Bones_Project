package baldursbones.bb;

import java.util.Scanner;

/** Driver.
 * @author Braden Rogers
 * @version 2023-TermProject
 * Note: Diver method contains only caller and loop methods.
 *      No tests are possible.
 */
public class Driver {
    private static final int LOCATION_DIVIDER = 100;
    private static final int EASY_LOCATION_MULTIPLIER = 1;
    private static final int MEDIUM_LOCATION_MULTIPLIER = 2;
    private static final int HARD_LOCATION_MULTIPLIER = 3;
    private static final int BOSS_LOCATION_MULTIPLIER = 4;
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
    private void locationHandler() {
        currentLocationType = gameMaps.getLocation();
        if (currentLocationType == 0) {
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
    private void combatHandler() {
        if ((currentLocationType == BOSS_FIGHT)) {
            currentEnemy = new BossEnemy();
            currentCombat = new BossCombat(playerCharacter, (BossEnemy) currentEnemy);
        } else {
            if ((currentLocationType / LOCATION_DIVIDER) == EASY_LOCATION_MULTIPLIER) {
                currentEnemy = new EasyEnemy();
            }
            if ((currentLocationType / LOCATION_DIVIDER) == MEDIUM_LOCATION_MULTIPLIER) {
                currentEnemy = new MediumEnemy();
            }
            if ((currentLocationType / LOCATION_DIVIDER) == HARD_LOCATION_MULTIPLIER) {
                currentEnemy = new HardEnemy();
            }
            currentCombat = new Combat(playerCharacter, currentEnemy);
        }
    }
    private void outcomeHandler() {
        if (outcome == 2) {
            win();
        } else {
            if (outcome == 1) {
                currentEnemy.win();
                gameMaps.beatBattle(playerCharacter.getLocation());
            } else if (outcome == -1) {
                currentEnemy.lose();
            }
            playerCharacter.finishBattle(outcome);
            if (playerCharacter.getHealth() <= 0) {
                lose();
            }
        }
    }
    private void tutorial() {
        currentLocation = new TutorialLocation(LOCATION_DIVIDER);
        currentLocation.getDescription();
        currentEnemy = new TutorialEnemy();
        currentCombat = new Combat(playerCharacter, currentEnemy);
        currentCombat.startCombat();
        gameMaps.beatTutorial();
    }
    private void win() {
        System.out.println("\n\n");
        System.out.println("Having defeated the famous adventurer Volo, you have proven yourself as a capable leader.");
        System.out.println("Now you have earned the reputation necessary to captain a vessel.");
        System.out.println("Able to achieve their dream of sailing the oceans wide, you walk back into the city,");
        System.out.println("You grin as your mind fills with tales of the sea.");
        System.out.println("You win.");
        gameState = 0;
    }
    private void lose() {
        System.out.println("\n\n");
        System.out.println("Having faced several losses to the fickle hands of fate you "
                + "have lost too much credibility in the town of Waterdeep.");
        System.out.println("Your dreams of sailing the seas as a captain are not dissuaded");
        System.out.println("and you being you venture to a further port in hopes that the chance to prove yourself "
                + "is just around the corner.");
        System.out.println("You Lose");
        gameState = 0;
    }
    /** Driver method.
     * @param args N/A
     */
    public static void main(final String[] args) {
        Driver game = new Driver();
        game.startGame();
    }
}
