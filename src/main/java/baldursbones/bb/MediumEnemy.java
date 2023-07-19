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

    // Constant: Defines the static difficulty of the Medium Enemy class.
    private static final int DIFFICULTY = 17;

    // Text file: Contains all dialogue to be printed by the Medium Enemy class.
    private static final File MEDIUM_TEXT = new File("src/main/resources/baldursbones/bb/MediumEnemyText.txt");

    /**
     * Create a Medium difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The layout element for the controller using this Enemy object
     */
    public MediumEnemy(final GridPane parentElement) {
        super(DIFFICULTY, MEDIUM_TEXT, parentElement);
    }

}
