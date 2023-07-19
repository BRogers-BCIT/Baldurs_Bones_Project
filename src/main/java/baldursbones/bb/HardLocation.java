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

    // Text file: Contains all dialogue to be printed by the Hard Location class.
    private static final File HARD_TEXT
            = new File("src/main/resources/baldursbones/bb/HardLocationText.txt");

    /**
     * Create a Hard difficulty implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the location's value.
     * @param locationGrid    The layout element of the controller using this Location object
     */
    public HardLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, HARD_TEXT, locationGrid);
    }

}
