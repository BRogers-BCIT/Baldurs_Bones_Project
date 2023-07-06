package baldursbones.bb;

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

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 19;

    /**
     * Variable: An integer representing the number of rounds the boss lost vs won.
     * Equation: rounds lost - rounds won.
     */
    protected int roundOutcomeCount;

    // Text file: contains all dialogue to be printed by the boss enemy class.
    private final File bossEnemyText = new File("src/main/resources/baldursbones/bb/BossEnemyText.txt");

    /**
     * Create a Boss Enemy implementation of the Enemy Abstract.
     */
    public BossEnemy() {
        super();
        enemyTotal = DIFFICULTY;
        roundOutcomeCount = 0;
    }

    /**
     * Generates a "total" value for the enemy and compares the value to the passed player "total".
     * Returns a 1 (player win) if player total is greater than enemy total, otherwise returns a -1 (player loss).
     * Also update the rounds lost counter based on the outcome of the round.
     *
     * @param playerTotal an integer value representing the users end total
     * @return an integer representing the outcome of the game (1 = player win, -1 = player loss)
     */
    @Override
    public int compareTotal(final int playerTotal) {
        // Get the bosses total.
        getTotal();
        // Print the bosses total for the player. ** Update simple print line. **
        System.out.println("Boss total: " + enemyTotal + ".");
        // Compare the player total to the boss total.
        // If player total is greater, then increase the outcome counter and return a positive value.
        if (playerTotal > enemyTotal) {
            roundOutcomeCount += 1;
            return 1;
        } else {
            // Otherwise decrease the round outcome counter and return a negative value.
            roundOutcomeCount -= 1;
            return -1;
        }
    }

    /**
     * Creates the bosses total value.
     * Boss roll equation (Difficulty + (0-1) - number of rounds won + number of rounds lost).
     */
    @Override
    public void getTotal() {
        // Determine the base total (Low Roll = 19, High Roll = 20).
        enemyTotal = DIFFICULTY;
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
        // Adjust the total based on the number of rounds the boss has lost vs won.
        enemyTotal += roundOutcomeCount;
    }

    /**
     * Defines the enemy behavior if the player wins a round of combat.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void winRound() {
        // Try to read the win-round text from the BossEnemy text file.
        try {
            Scanner fileReader = new Scanner(bossEnemyText);
            // Print the text to user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Defines the enemy behavior if the player loses a round of combat.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void loseRound() {
        // Try to read the lose-round text from the BossEnemy text file.
        try {
            Scanner fileReader = new Scanner(bossEnemyText);
            // Skip the first text section.
            fileReader.nextLine();
            // Print the text to user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Define the enemy behavior (Game end text) if the player loses.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void win() {
        // Try to read the win-combat text from the BossEnemy text file.
        try {
            Scanner fileReader = new Scanner(bossEnemyText);
            // Skip the first two text sections.
            fileReader.nextLine();
            fileReader.nextLine();
            // Print the text to the user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Define the enemy behavior (Game end text) if the player loses.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void lose() {
        // Try to read the lose-combat text from the BossEnemy text file.
        try {
            Scanner fileReader = new Scanner(bossEnemyText);
            // Skip the first 3 text sections.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Print the text to the user.
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
