package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Boss Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossEnemy extends Enemy {

    // Constant: The number used to determine the Boss's Total Value at the end of Combat.
    private static final int DIFFICULTY = 14;

    // Text file: The Text File object to be used by the Boss Enemy.
    private static final File BOSS_FILE
            = new File("src/main/resources/baldursbones/bb/TutorialEnemyText.txt");

    /**
     * Variable: An integer representing the number of Rounds the Boss has lost vs won.
     * Equation: Rounds Lost - Rounds Won.
     */
    protected int roundOutcomeCount;

    /**
     * Create a Boss type Enemy object and Pass the Difficulty Value, Text File, and Parent Element to abstract.
     *
     * @param parentElement The layout element for the Controller using the Boss Enemy object
     */
    public BossEnemy(final GridPane parentElement) {
        super(DIFFICULTY, BOSS_FILE, parentElement);
        roundOutcomeCount = 0;
    }

    /**
     * Generates the Total Value for the Enemy and compares the value to the passed Player Total Value.
     * Override: Adds a counter to track the number of Rounds won and lost by the Boss.
     *
     * @param playerTotal The Total Value for the Player in a Combat
     * @return An integer representing the outcome of the game (1 = Player win, -1 = Player loss)
     */
    @Override
    public int compareTotal(final int playerTotal) {
        // Get the Total Value for the Boss.
        getTotal();
        // Compare the Player total to the Boss total.
        // Update the Round Outcome counter, then return the result.
        if (playerTotal > enemyTotal) {
            // If: The Player total > Boss total, the Player wins.
            roundOutcomeCount += 1;
            return 1;
        } else {
            // Else: The Boss total >= Player total, the Player loses.
            roundOutcomeCount -= 1;
            return -1;
        }
    }

    /**
     * Set the Total Value for the Enemy based on the Difficulty Value passed to the constructor.
     * Override: Also increases / decreases the Total Value based on the Rounds won and lost by the Boss.
     * Equation: (Difficulty Value) + (0 - 1) + (Rounds Lost) - (Rounds Won)
     */
    @Override
    public void getTotal() {
        // Determine the base total (Low Roll = 19, High Roll = 20).
        enemyTotal = DIFFICULTY;
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
        // Adjust the total based on the current value of the Round Outcome counter.
        enemyTotal += roundOutcomeCount;
    }

    /**
     * Defines the Enemy behavior if the Player wins a Round of Combat.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void winRound() {
        // Try to read the win Round text from the Boss Text File.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Display the win Round text in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * Defines the Enemy behavior if the Player loses a Round of Combat.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void loseRound() {
        // Try to read the lose Round text from the Boss Text File.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first line of the Text File.
            fileReader.nextLine();
            // Display the win Round text in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * Display the win Combat text for Boss Enemy.
     * Override: Boss Text File has a different layout from regular Enemies.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void win() {
        // Try to read the win Combat text from the Boss Text File.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first 2 lines of the Text File.
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the win Combat in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * Display the lose Combat text for the Boss Enemy.
     * Override: Boss Text File has a different layout from regular Enemies.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void lose() {
        // Try to read the lose Combat text from the Boss Text File.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first 3 lines of the Text File.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the lose Combat text in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }
}
