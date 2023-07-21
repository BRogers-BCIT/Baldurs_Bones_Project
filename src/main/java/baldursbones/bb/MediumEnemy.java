package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;


/**
 * Medium Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MediumEnemy extends Enemy {

    // Constant: Define the number used to determine a Medium Enemy's Total Value at the end of combat.
    private static final int DIFFICULTY = 17;

    // Text file: The Text File object with the Text to display for a Medium Enemy.
    private static final File MEDIUM_TEXT = new File("src/main/resources/baldursbones/bb/MediumEnemyText.txt");

    /**
     * Create a Medium difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The Parent's Layout Element for the Controller using the Medium Enemy Class
     */
    public MediumEnemy(final GridPane parentElement) {
        super(DIFFICULTY, MEDIUM_TEXT, parentElement);
    }

}
