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

    // Text file: The Text File object with the Text to display for a Medium Location.
    private static final File MEDIUM_TEXT
            = new File("src/main/resources/baldursbones/bb/MediumLocationText.txt");

    /**
     * Create a Medium difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the Location Value.
     * @param locationGrid    The Parent's Layout Element for the Controller using the Medium Location Class
     */
    public MediumLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, MEDIUM_TEXT, locationGrid);
    }

}
