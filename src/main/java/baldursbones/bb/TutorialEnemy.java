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

    // Constant: Defines the static difficulty value of the Enemy.
    private static final int DIFFICULTY = 14;

    // Text file: Contains all dialogue to be printed by the Tutorial Enemy class.
    private static final File TUTORIAL_FILE
            = new File("src/main/resources/baldursbones/bb/TutorialEnemyText.txt");


    /**
     * Create a Tutorial type Enemy object and pass the difficulty value, text file, and parent FXML element to super.
     *
     * @param parentElement The layout of the controller using this Enemy object
     */
    public TutorialEnemy(final GridPane parentElement) {
        super(DIFFICULTY, TUTORIAL_FILE, parentElement);
    }

    /**
     * Generates a "total" value for the Enemy and compares the value to the passed Player "total".
     * Override: Always returns an outcome of 0 & call Tutorial end description.
     * Tutorial combat does not impact the Player so the end result is not saved.
     *
     * @param playerRoll An integer value representing the end total value for the Player
     * @return An integer representing the outcome of the combat (0 = finished Tutorial)
     */
    @Override
    public int compareTotal(final int playerRoll) {
        // Set the Enemy total based on Enemy difficulty.
        getTotal();
        // Compare Enemy and Player totals and return the result.
        if (playerRoll > enemyTotal) {
            // If: The Player total is higher than the Enemy total, the Player wins.
            win();
        } else {
            // Else: The Enemy total is equal to or grater than the Player total.
            lose();
        }
        tutorialFinish();
        // Regardless of outcome return 0. (Tutorial fight does not affect Player).
        return 0;
    }

    /**
     * Displays the description for finishing the Tutorial.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void tutorialFinish() {
        // Try to read the finish Tutorial description from the Tutorial Enemy text file.
        try {
            Scanner fileReader = new Scanner(enemyFile);
            // Skip the first two lines of text.
            fileReader.nextLine();
            fileReader.nextLine();
            // Print the description to the text area in the Location Menu description text area.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.appendText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
