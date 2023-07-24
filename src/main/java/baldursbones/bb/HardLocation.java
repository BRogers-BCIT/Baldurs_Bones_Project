package baldursbones.bb;

import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * Hard Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class HardLocation extends Location {

    // Text file: The Text File object with the Text to display for a Hard Location.
    private static final File HARD_TEXT
            = new File("src/main/resources/baldursbones/bb/HardLocationText.txt");

    /**
     * Create a Hard difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the Location Value.
     * @param locationGrid    The Parent's Layout Element for the Controller using the Hard Location Class
     */
    public HardLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, HARD_TEXT, locationGrid);
    }

}
