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

    // Text file: The Text File object with the Text to display for an Easy Location.
    private static final File EASY_TEXT = new File("src/main/resources/baldursbones/bb/EasyLocationText.txt");

    /**
     * Create an Easy difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the Location Value.
     * @param locationGrid    The Parent's Layout Element for the Controller using the Easy Location Class
     */
    public EasyLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, EASY_TEXT, locationGrid);
    }

}
