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

    // Constant: Location value for returning to Tutorial Location class.
    private static final int RETURNED_TUTORIAL = 1;

    // Text file: Contains all descriptions to be printed by the Tutorial Location class.
    private static final File TUTORIAL_TEXT
            = new File("src/main/resources/baldursbones/bb/TutorialLocationText.txt");

    /**
     * Creates a new Tutorial Location object and assigns it a Location value.
     *
     * @param newLocationType An integer representing the Location's value.
     * @param locationGrid    The Location menu layout element that uses this Location object
     */
    public TutorialLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, TUTORIAL_TEXT, locationGrid);
    }

    /**
     * Call the method to display the Location description based on the Location value.
     * Override: Different location types than regular Locations.
     *
     * @return A boolean value indicating if the current Location is a Combat Location
     */
    @Override
    public boolean getDescription() {
        if (locationValue == RETURNED_TUTORIAL) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }

    /**
     * Displays the description for the start of the Tutorial and leads into the Tutorial fight.
     * Override: Different file layout to read then regular Locations.
     *
     * @return A boolean value indicating that a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    @Override
    protected boolean fightLocation() {
        // Try to read the "Start Tutorial" description from the Tutorial Location text file.
        try {
            Scanner fileReader = new Scanner(TUTORIAL_TEXT);
            // Display the "Start Tutorial Location" description on screen.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Prints the description for returning to the Tutorial Location.
     * Override: Different file layout to read then regular Locations.
     *
     * @return A false boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    @Override
    protected boolean exploreLocation() {
        // Try to read the "Return to Tutorial" description from the Tutorial Location text file.
        try {
            Scanner fileReader = new Scanner(TUTORIAL_TEXT);
            // Skip the first line of text.
            fileReader.nextLine();
            // Display the "Return to Tutorial Location" description on screen.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Return false for a non-Combat Location
        return false;
    }

}
