package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Tutorial Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class TutorialLocation extends Location {

    // Constant: Location Value for returning to the Tutorial Location.
    private static final int RETURNED_TUTORIAL = 1;

    // Text file: The Text File object with the Text to display for the Tutorial Location.
    private static final File TUTORIAL_TEXT
            = new File("src/main/resources/baldursbones/bb/TutorialLocationText.txt");

    /**
     * Create a Tutorial implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the Location Value.
     * @param locationGrid    The Parent's Layout Element for the Controller using the Tutorial Location Class
     */
    public TutorialLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, TUTORIAL_TEXT, locationGrid);
    }

    /**
     * Find the Location type from the Location Value and call the Combat check method.
     * Override: Different Location Types than regular Location Implementations.
     *
     * @return A boolean value from the Combat Check indicating if the Location is a Combat Location
     */
    @Override
    public boolean getDescription() {
        if (locationValue == RETURNED_TUTORIAL) {
            return exploreLocation();
        } else {
            return fightLocation();
        }
    }

    /**
     * Displays the text for returning to the Tutorial Location.
     * Override: Different file layout than regular Location Implementations.
     *
     * @return A boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    @Override
    protected boolean exploreLocation() {
        // Try to read the "Return to Tutorial" description from the Tutorial Location Text File.
        try {
            Scanner fileReader = new Scanner(TUTORIAL_TEXT);
            // Skip the Tutorial Combat text.
            fileReader.nextLine();
            // Display the Return to Tutorial description in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return false for a non-Combat Location.
        return false;
    }

    /**
     * Displays the text for the start of the Tutorial and returns True to allow a Tutorial Combat.
     * Override: Different file layout than regular Location Implementations.
     *
     * @return A boolean value indicating that a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    @Override
    protected boolean fightLocation() {
        // Try to read the "Start Tutorial" description from the Tutorial Location Text File.
        try {
            Scanner fileReader = new Scanner(TUTORIAL_TEXT);
            // Display the Start Tutorial description in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return True to start the Tutorial Combat.
        return true;
    }
}
