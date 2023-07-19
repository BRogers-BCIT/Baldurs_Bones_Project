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

    // Constant: Defines the static difficulty of the Hard Enemy class.
    private static final int DIFFICULTY = 18;

    // Text file: Contains all dialogue to be printed by the Hard Enemy class.
    private static final File HARD_TEXT = new File("src/main/resources/baldursbones/bb/HardEnemyText.txt");

    /**
     * Create a Hard difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The layout element for the controller using this Enemy object
     */
    public HardEnemy(final GridPane parentElement) {
        super(DIFFICULTY, HARD_TEXT, parentElement);
    }

}
