package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

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

    // FXML Element: The parent element the enemy object is being used by.
    private final GridPane container;

    /**
     * Create an Easy difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The layout of the controller using this enemy object
     */
    public EasyEnemy(final GridPane parentElement) {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
        container = parentElement;
    }

    /**
     * Define the enemy behavior (Game end text) if the player wins.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void win() {
        // Try to read the start fight text from the Easy Enemy text file.
        try {
            Scanner fileReader = new Scanner(easyEnemyText);
            // Print the text to the text area in the location window user.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
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
        // Try to read the start fight text from the Easy Enemy text file.
        try {
            Scanner fileReader = new Scanner(easyEnemyText);
            // Skip the first line of text.
            fileReader.nextLine();
            // Print the text to the text area in the location window user.
            TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
            descriptionArea.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
