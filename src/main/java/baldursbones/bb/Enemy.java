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
     * Variable: Record the Total Value for the Enemy object.
     */
    protected int enemyTotal;

    /**
     * The Parent Layout Element the Enemy class is being used by.
     */
    protected GridPane container;

    // Implementation Constant: Define the number used to determine an Enemy's Total Value at the end of combat.
    private final int enemyDifficulty;

    // Implementation Constant: The Text File object to be used for Enemy implementation.
    private final File enemyFile;

    /**
     * Receives and Sets the field values for the Enemy class. Receives the values from abstract implementations.
     *
     * @param difficultyValue The static integer value used to determine the Enemy's Total Value at the end of Combat
     * @param enemyText       The Text File to read the Enemy display text from
     * @param parentElement   The Parent's Layout Element for the Controller using the Enemy Class
     */
    public Enemy(final int difficultyValue, final File enemyText, final GridPane parentElement) {
        enemyDifficulty = difficultyValue;
        enemyFile = enemyText;
        container = parentElement;
    }

    /**
     * Returns a Combat Title for the Implemented Enemy Type.
     *
     * @return A string to display as the Title for the Combat with they Enemy Implementation Type.
     * @throws FileNotFoundException If the text document being loaded does not exist
     */
    protected String getCombatTitle() throws FileNotFoundException {
        Scanner fileReader = new Scanner(enemyFile);
        fileReader.nextLine();
        fileReader.nextLine();
        return fileReader.nextLine();
    }

    /**
     * Returns a Combat Description for the Implemented Enemy Type.
     *
     * @return A string to display as the Description for the Combat with they Enemy Implementation Type.
     * @throws FileNotFoundException If the text document being loaded does not exist
     */
    protected String getCombatDescription() throws FileNotFoundException {
        Scanner fileReader = new Scanner(enemyFile);
        fileReader.nextLine();
        fileReader.nextLine();
        fileReader.nextLine();
        return fileReader.nextLine();
    }

    /**
     * Generates the Total Value for the Enemy and compares the value to the passed Player Total Value.
     *
     * @param playerTotal The Total Value for the Player in a Combat
     * @return An integer representing the outcome of the game (1 = Player win, -1 = Player loss)
     */
    public int compareTotal(final int playerTotal) {
        // Set the Total Value for the Enemy.
        getTotal();
        // Compare the Total Value for the Enemy to the Player Total Value.
        if (playerTotal > enemyTotal) {
            // If: The Player Total Value > Enemy Total Value, the Player wins.
            win();
            return 1;
        } else {
            // Else: The Enemy Total Value >= Player Total Value, the Player loses.
            lose();
            return -1;
        }
    }

    /**
     * Set the Total Value for the Enemy based on the Difficulty Value passed to the constructor.
     */
    public void getTotal() {
        // Set Total Value to Enemy base Difficulty Value.
        enemyTotal = enemyDifficulty;
        Random rand = new Random();
        // Generates either a 0 or a 1 and adds it to the Total Value.
        // Creates variance Combat outcomes by creating a High-Roll value and a Low-Roll value.
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
    }

    /**
     * Display the win Combat text for Enemy Implementation Type.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void win() {
        // Try to read the win Combat text from the Easy Enemy Text File.
        try {
            Scanner fileReader = new Scanner(enemyFile);
            // Display the win Combat text in the Game Description display.
            TextArea descriptionArea = (TextArea) container.lookup("#GameDescription");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * Display the lose Combat text for Enemy Implementation Type.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void lose() {
        // Try to read the lose Combat text from the Easy Enemy Text File.
        try {
            Scanner fileReader = new Scanner(enemyFile);
            // Skip the first line of the Text File.
            fileReader.nextLine();
            // Display the lose Combat text in the Game Description display.
            TextArea descriptionArea = (TextArea) container.lookup("#GameDescription");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }
}

