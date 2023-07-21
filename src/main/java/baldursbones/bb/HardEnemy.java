package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * Hard Enemy Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class HardEnemy extends Enemy {

    // Constant: Define the number used to determine a Hard Enemy's Total Value at the end of combat.
    private static final int DIFFICULTY = 18;

    // Text file: The Text File object with the Text to display for a Hard Enemy.
    private static final File HARD_TEXT = new File("src/main/resources/baldursbones/bb/HardEnemyText.txt");

    /**
     * Create a Hard difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The Parent's Layout Element for the Controller using the Hard Enemy Class
     */
    public HardEnemy(final GridPane parentElement) {
        super(DIFFICULTY, HARD_TEXT, parentElement);
    }

}
