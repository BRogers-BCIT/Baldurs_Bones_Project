package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Enemy Abstract Class.
 * Controller: Game Combat
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public abstract class Enemy {
    /**
     * Variable: Record the value representing the enemy's total at the end of combat.
     */
    protected int enemyTotal;

    /**
     * Variable: The layout element of the controller that created the Enemy object.
     */
    protected GridPane container;

    // Implementation Constant: Define the number value used to determine an enemies total at the end of combat.
    private final int enemyDifficulty;

    // Implementation Constant: The file object for the text file used for the created Enemy type.
    private final File enemyFile;

    /**
     * Receives and Sets the field values for the Enemy class. Receives the values from abstract implementations.
     *
     * @param difficultyValue The static integer value used to determine the Enemy's roll in combat
     * @param enemyText       The text file to read the Enemy description text from
     * @param parentElement   The FXML element that descriptions are displayed in
     */
    public Enemy(final int difficultyValue, final File enemyText, final GridPane parentElement) {
        enemyDifficulty = difficultyValue;
        enemyFile = enemyText;
        container = parentElement;
    }

    /**
     * Generates a "total" value for the Enemy and compares the value to the passed Player "total".
     *
     * @param playerRoll an integer value representing the users end total
     * @return an integer representing the outcome of the game (1 = Player win, -1 = Player loss)
     */
    public int compareTotal(final int playerRoll) {
        // Set the total value for the Enemy.
        getTotal();
        // Display the total value for the Boss to the Player total value.
        if (playerRoll > enemyTotal) {
            // If: The Player total > Enemy total, the Player wins.
            win();
            return 1;
        } else {
            // Else: The Enemy total >= Player total, the Player loses.
            lose();
            return -1;
        }
    }

    /**
     * Set the total value for the Enemy based on the difficulty value passed to the constructor.
     */
    public void getTotal() {
        // Set total to Enemy base difficulty.
        enemyTotal = enemyDifficulty;
        Random rand = new Random();
        // Generates either a 0 or a 1 and adds it to the total value.
        // Creates variance Combat outcomes by creating a High roll value and a Low roll value.
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
    }

    /**
     * Define the Enemy behavior (Game end text) if the Player wins.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void win() {
        // Try to read the start fight text from the Easy Enemy text file.
        try {
            Scanner fileReader = new Scanner(enemyFile);
            // Display the description in the text area element.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
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
        // Try to read the start fight text from the Easy Enemy text file.
        try {
            Scanner fileReader = new Scanner(enemyFile);
            // Skip the first line of text.
            fileReader.nextLine();
            // Display the description in the text area element.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    protected String getCombatTitle() throws FileNotFoundException {
        Scanner fileReader = new Scanner(enemyFile);
        fileReader.nextLine();
        fileReader.nextLine();
        return fileReader.nextLine();
    }

    protected String getCombatDescription() throws FileNotFoundException {
        Scanner fileReader = new Scanner(enemyFile);
        fileReader.nextLine();
        fileReader.nextLine();
        fileReader.nextLine();
        return fileReader.nextLine();
    }
}

