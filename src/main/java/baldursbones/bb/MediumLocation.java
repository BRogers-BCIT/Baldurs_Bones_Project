package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * Medium Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MediumLocation extends Location {

    // Text file: Contains all dialogue to be printed by the Medium Location class.
    private static final File MEDIUM_TEXT
            = new File("src/main/resources/baldursbones/bb/MediumLocationText.txt");

    /**
     * Create a Medium difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the location's value.
     * @param locationGrid    The layout element of the controller using this Location object
     */
    public MediumLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, MEDIUM_TEXT, locationGrid);
    }

}
