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

    // Constant: Defines the static difficulty of the Easy Enemy class.
    private static final int DIFFICULTY = 15;

    // Text file: Contains all dialogue to be printed by the Easy Enemy class.
    private static final File EASY_TEXT = new File("src/main/resources/baldursbones/bb/EasyEnemyText.txt");

    /**
     * Create an Easy difficulty implementation of the Enemy Abstract.
     *
     * @param parentElement The layout element for the controller using this Enemy object
     */
    public EasyEnemy(final GridPane parentElement) {
        super(DIFFICULTY, EASY_TEXT, parentElement);
    }

}
