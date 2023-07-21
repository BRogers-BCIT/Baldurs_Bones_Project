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

    // Constant: Defines the static difficulty value of the Boss Enemy class.
    private static final int DIFFICULTY = 19;

    // Text file: Contains all dialogue to be printed by the Boss Enemy class.
    private static final File BOSS_FILE = new File("src/main/resources/baldursbones/bb/BossEnemyText.txt");

    /**
     * Variable: An integer representing the number of rounds the Boss has lost vs won.
     * Equation: rounds lost - rounds won.
     */
    protected int roundOutcomeCount;

    /**
     * Create a Boss type Enemy object and Pass the difficulty value, text file, and parent FXML element to super.
     *
     * @param parentElement The layout element for the controller using this Enemy object
     */
    public BossEnemy(final GridPane parentElement) {
        super(DIFFICULTY, BOSS_FILE, parentElement);
        roundOutcomeCount = 0;
    }

    /**
     * Generates a "total" value for the Enemy and compares the value to the passed Player "total".
     * Override: Keep a running internal total of rounds the Boss Enemy has won vs lost.
     *
     * @param playerTotal An integer value representing the end total value for the Player
     * @return An integer representing the outcome of the game (1 = Player win, -1 = Player loss)
     */
    @Override
    public int compareTotal(final int playerTotal) {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Get the total value for the Boss.
        getTotal();
        // Display the total value for the Boss to the Player total value.
        descriptionBox.setText("Boss total: " + enemyTotal + ".");
        // Compare the Player total to the Boss total.
        // Update the counter and return the result.
        if (playerTotal > enemyTotal) {
            // If: The Player total > Enemy total, the Player wins.
            roundOutcomeCount += 1;
            return 1;
        } else {
            // Else: The Enemy total >= Player total, the Player loses.
            roundOutcomeCount -= 1;
            return -1;
        }
    }

    /**
     * Creates the total value for the Boss Enemy.
     * Equation: Difficulty + (0-1) - (number of rounds won + number of rounds lost).
     */
    @Override
    public void getTotal() {
        // Determine the base total (Low Roll = 19, High Roll = 20).
        enemyTotal = DIFFICULTY;
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
        // Adjust the total based on the current value of the round outcome counter.
        enemyTotal += roundOutcomeCount;
    }

    /**
     * Defines the Enemy behavior if the Player wins a round of combat (Naming is defined by context of combat class).
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void winRound() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the win-round text from the Boss Enemy text file.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Display the description in the text area element.
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Defines the Enemy behavior if the Player loses a round of combat (Naming is defined by context of combat class).
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void loseRound() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the lose-round text from the Boss Enemy text file.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first line of the text file.
            fileReader.nextLine();
            // Display the description in the text area element.
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Define the Enemy behavior (Game end text) if the Player loses.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void win() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the win-combat text from the Boss Enemy text file.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first 2 lines of the text document.
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the description in the text area element.
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Define the Enemy behavior (Game end text) if the Player loses.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void lose() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the lose-combat text from the Boss Enemy text file.
        try {
            Scanner fileReader = new Scanner(BOSS_FILE);
            // Skip the first 3 lines of the text document.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the description in the text area element.
            descriptionBox.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
