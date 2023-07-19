package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * Easy Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EasyLocation extends Location {

    // Text file: Contains all dialogue to be printed by the Easy Location class.
    private static final File EASY_TEXT = new File("src/main/resources/baldursbones/bb/EasyLocationText.txt");

    /**
     * Create an Easy difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the location's value.
     * @param locationGrid    The layout element of the controller using this Location object
     */
    public EasyLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, EASY_TEXT, locationGrid);
    }

}
