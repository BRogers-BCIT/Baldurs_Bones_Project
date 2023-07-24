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

    // Constant: The Location Value for a Non-Combat Location that the Player has not explored.
    private static final int EXPLORE_LOCATION = 411;

    // Constant: The Location Value for a Non-Combat Location that the Player has explored.
    private static final int EXPLORE_LOCATION_FOUND = 412;

    // Text file: The Text File object with the Text to display for a Boss Location.
    private static final File BOSS_TEXT = new File("src/main/resources/baldursbones/bb/BossLocationText.txt");

    /**
     * Create a Boss implementation of the Location Abstract.
     *
     * @param newLocationType An integer representing the Location Value.
     * @param locationGrid    The Parent's Layout Element for the Controller using the Boss Location Class
     */
    public BossLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType, BOSS_TEXT, locationGrid);
    }

    /**
     * Find the Location type from the Location Value and call the Combat check method.
     * Override: Different Location Types than regular Location Implementations.
     *
     * @return A boolean value from the Combat Check indicating if the Location is a Combat Location
     */
    public boolean getDescription() {
        if (locationValue == EXPLORE_LOCATION || locationValue == EXPLORE_LOCATION_FOUND) {
            // If: Location Value matches a non-Combat Location Value.
            return exploreLocation();
        } else {
            // Else: Location Value matches a Combat Location Value.
            return fightLocation();
        }
    }

    /**
     * Displays the description for the Explore Location then prints a description based on State of the Location.
     * Location value options: First exploration or Returning exploration.
     * Override: Different file layout than regular Location Implementations.
     *
     * @return A boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean exploreLocation() {
        // Try to read the "Explore Location" description from the Boss Location Text File.
        try {
            // Define the FXMl Element to display the game text in.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            // Create a new Scanner for the Text File and skip the first section.
            Scanner fileReader = new Scanner(BOSS_TEXT);
            fileReader.nextLine();
            if (locationValue == EXPLORE_LOCATION) {
                // If: The Location has not been visited, then display the First Visit Location description.
                descriptionBox.setText(fileReader.nextLine());
            } else {
                // Else: The Location has been visited, then display the Returning Visit Location description.
                fileReader.nextLine();
                descriptionBox.setText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return False to disable Combat at the Location.
        return false;
    }

    /**
     * Displays the description for the start of the Boss Combat returns True to allow a Boss Combat.
     * Override: Different file layout than regular Location Implementations.
     *
     * @return A boolean value indicating that a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean fightLocation() {
        // Try to read the "Boss Fight" description from the Boss Location Text File.
        try {
            // Define the FXMl Element to display the game text in.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            // Create a new Scanner for the Text File and display the Boss Combat text.
            Scanner fileReader = new Scanner(BOSS_TEXT);
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return True to allow a Boss Combat at the Location.
        return true;
    }
}
