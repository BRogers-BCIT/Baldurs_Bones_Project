package baldursbones.bb;

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

    /**
     * Create a Tutorial difficulty implementation of the Enemy Abstract.
     */
    public TutorialEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
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
            // Print the text to user.
            System.out.println(fileReader.nextLine());
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
            // Print the text to user.
            System.out.println(fileReader.nextLine());
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
            // Print the text to user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Prompt the user to continue. ** Replace with button press. **
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
