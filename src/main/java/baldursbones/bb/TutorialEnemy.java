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

    // Constant: The number used to determine the Tutorial Enemy's Total Value at the end of combat.
    private static final int DIFFICULTY = 14;

    // Text file: The Text File object to be used by the Tutorial Enemy.
    private static final File TUTORIAL_FILE
            = new File("src/main/resources/baldursbones/bb/TutorialEnemyText.txt");


    /**
     * Create a Tutorial type Enemy object and pass the Difficulty Value, Text File, and Parent Element to abstract.
     *
     * @param parentElement The Layout Element  for the Controller using the Tutorial Enemy object
     */
    public TutorialEnemy(final GridPane parentElement) {
        super(DIFFICULTY, TUTORIAL_FILE, parentElement);
    }

    /**
     * Generates the Total Value for the Enemy and compares the value to the passed Player Total Value.
     * Override: Always returns an outcome of 0 + calls the finish Tutorial description method.
     *
     * @param playerRoll The Total Value for the Player in a Combat
     * @return An integer representing the outcome of the game (1 = Player win, -1 = Player loss)
     */
    @Override
    public int compareTotal(final int playerRoll) {
        // Set the Enemy Total Value based on Enemy Difficulty Value.
        getTotal();
        // Compare Enemy and Player totals and return the result.
        if (playerRoll > enemyTotal) {
            // If: The Player Total Value is higher than the Enemy Total Value, the Player wins the Combat.
            win();
        } else {
            // Else: The Enemy Total Value is equal to or greater than the Player Total Value.
            // The Player loses the Combat.
            lose();
        }
        tutorialFinish();
        // Regardless of outcome return 0. (Tutorial Combat does not affect Player).
        return 0;
    }

    /**
     * Display the win End Tutorial description.
     *
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected void tutorialFinish() {
        // Try to read the finish Tutorial text from the Tutorial Enemy Text File.
        try {
            Scanner fileReader = new Scanner(TUTORIAL_FILE);
            // Skip the first two lines of the Text File.
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the End Tutorial text in the Location Menu Description display.
            TextArea descriptionArea = (TextArea) container.lookup("#GameDescription");
            // Add a new line to the Display Area before adding the Tutorial Finish text.
            descriptionArea.appendText(fileReader.nextLine() + "\n");
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }
}
