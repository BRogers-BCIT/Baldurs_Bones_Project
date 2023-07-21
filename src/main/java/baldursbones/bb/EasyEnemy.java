package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;


/**
 * Easy Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EasyEnemy extends Enemy {

    // Constant: Define the number used to determine an Easy Enemy's Total Value at the end of combat.
    private static final int DIFFICULTY = 15;

    // Text file: The Text File object with the Text to display for an Easy Enemy.
    private static final File EASY_TEXT = new File("src/main/resources/baldursbones/bb/EasyEnemyText.txt");

    /**
     * Create an Easy difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The Parent's Layout Element for the Controller using the Easy Enemy Class
     */
    public EasyEnemy(final GridPane parentElement) {
        super(DIFFICULTY, EASY_TEXT, parentElement);
    }

}
