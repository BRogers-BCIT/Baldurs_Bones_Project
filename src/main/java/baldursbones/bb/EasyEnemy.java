package baldursbones.bb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Easy Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EasyEnemy extends Enemy {

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 15;

    // Text file: contains all dialogue to be printed by the easy enemy class.
    private final File easyEnemyText = new File("src/main/resources/baldursbones/bb/EasyEnemyText.txt");

    /**
     * Create an Easy difficulty implementation of the Enemy Abstract.
     */
    public EasyEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
    }

    /**
     * Define the enemy behavior (Game end text) if the player wins.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void win() {
        // Try to read the start fight text from the EasyEnemy text file.
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(easyEnemyText);
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
        // Try to read the start fight text from the EasyEnemy text file.
        try {
            // Create a new scanner for the text file and print the second section.
            Scanner fileReader = new Scanner(easyEnemyText);
            fileReader.nextLine();
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
