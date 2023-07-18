package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Tutorial Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class TutorialEnemy extends Enemy {

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 14;

    // Text file: contains all dialogue to be printed by the tutorial enemy class.
    private final File tutorialEnemyText
            = new File("src/main/resources/baldursbones/bb/TutorialEnemyText.txt");

    // FXML Element: The parent element the map info menu is displayed in.
    private final GridPane container;

    /**
     * Create a Tutorial difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The parent element of the character info menu layout
     */
    public TutorialEnemy(final GridPane parentElement) {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
        container = parentElement;
    }

    /**
     * Generates a "total" value for the enemy and compares the value to the passed player "total".
     * Regardless of outcome return a 0 (Tutorial win or loss does not affect player).
     *
     * @param playerRoll an integer value representing the users end total
     * @return an integer representing the outcome of the game (0 = finished tutorial)
     */
    @Override
    public int compareTotal(final int playerRoll) {
        // Set the enemy total based on enemy difficulty.
        getTotal();
        // Compare enemy and player totals.
        // If the player total is higher, then the player wins, otherwise the enemy wins.
        if (playerRoll > enemyTotal) {
            win();
        } else {
            lose();
        }
        // Regardless of outcome return 0.
        return 0;
    }

    /**
     * Define the enemy behavior (Game end text) if the player loses.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void win() {
        // Try to read the win tutorial combat text from the TutorialEnemy text file.
        try {
            Scanner fileReader = new Scanner(tutorialEnemyText);
            // Print the text to the text area in the location window user.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        tutorialEnd();
    }

    /**
     * Define the enemy behavior (Game end text) if the player loses.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void lose() {
        // Try to read the lose tutorial combat text from the TutorialEnemy text file.
        try {
            Scanner fileReader = new Scanner(tutorialEnemyText);
            // Skip the first line of text.
            fileReader.nextLine();
            // Print the text to the text area in the location window user.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        tutorialEnd();
    }

    /**
     * Displays the game text for when tutorial finishes regardless of win or lose.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void tutorialEnd() {
        // Try to read the end tutorial text from the TutorialEnemy text file.
        try {
            Scanner fileReader = new Scanner(tutorialEnemyText);
            // Skip the first two lines of text.
            fileReader.nextLine();
            fileReader.nextLine();
            // Print the text to the text area in the location window user.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
