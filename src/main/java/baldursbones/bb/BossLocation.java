package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Boss Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossLocation extends Location {

    // Constant: Non-combat location value that surrounds the boss location.
    private static final int EXPLORE_LOCATION = 411;

    // Constant: Found value of non-combat locations.
    private static final int EXPLORE_LOCATION_FOUND = 412;

    // Text file: contains all dialogue to be printed by the boss location class.
    private static final File BOSS_TEXT = new File("src/main/resources/baldursbones/bb/BossLocationText.txt");

    // Constant: Boss fight location value = 500.

    /**
     * Creates a new Boss Location object and assigns it a Location value.
     *
     * @param newLocationType An integer representing the Location's value.
     * @param locationGrid    The Location menu layout element that uses this Location object
     */
    public BossLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, BOSS_TEXT, locationGrid);
    }

    /**
     * Gets the description of the boss location based on its location value.
     * Override: Different location types than regular Locations.
     *
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        // Check Non-combat location encounters and call handler.
        if (locationValue == EXPLORE_LOCATION || locationValue == EXPLORE_LOCATION_FOUND) {
            return exploreLocation();
        } else {
            return fightLocation();
        }
    }

    /**
     * Displays the description for the start of the Boss encounter and leads into the Boss Fight.
     * Override: Different file layout to read then regular locations.
     *
     * @return A boolean value indicating that a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean fightLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the "Boss Fight" description from the Boss Location text file.
        try {
            // Create a new scanner for the text file and display the Boss Fight description.
            Scanner fileReader = new Scanner(BOSS_TEXT);
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Prints the base description for a Boss Exploration Location then prints based on the location value.
     * Location Value Options: First visit text description or Location return text description.
     * Override: Different file layout to read then regular Locations.
     *
     * @return A false boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean exploreLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the "Explore Location" description from the Boss Location text file.
        try {
            // Create a new scanner for the text file and skip the first section.
            Scanner fileReader = new Scanner(BOSS_TEXT);
            fileReader.nextLine();
            if (locationValue == EXPLORE_LOCATION) {
                // If: The location has not been visited, then display the new Location description.
                descriptionBox.setText(fileReader.nextLine());
            } else {
                // Else: The location has been visited, then display the retuning Location description.
                fileReader.nextLine();
                descriptionBox.setText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return false;
    }

}
