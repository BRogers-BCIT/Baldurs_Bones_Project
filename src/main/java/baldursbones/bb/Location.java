package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Location Abstract Class.
 * Controller: Location Menu
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public abstract class Location {

    // Constant: Explore Location value - not been encountered yet.
    private static final int EXPLORE_LOCATION = 11;

    // Constant: Explore Location value - already encountered.
    private static final int EXPLORE_LOCATION_FOUND = 12;

    // Constant: Combat Location value - not been encountered yet.
    private static final int FIGHT_LOCATION = 21;

    // Constant: Combat Location value - found but not beaten.
    private static final int FIGHT_LOCATION_FOUND = 22;

    // Constant: Combat Location value - found and beaten.
    private static final int FIGHT_LOCATION_BEATEN = 23;

    /**
     * FXML Element: The layout element of the controller that created the Location object.
     */
    protected final GridPane container;

    /**
     * Variable: An integer value used to track the value of the Location.
     */
    protected int locationValue;

    // Text file: The file object for the text file used for the created Location type.
    private final File locationFile;

    /**
     * Receives and Sets the field values for the Location class. Receives the values from abstract implementations.
     *
     * @param newLocationValue The static integer value used to determine area and type of Location
     * @param locationText     The text file to read the Location description text from
     * @param parentElement    The FXML element that descriptions are displayed in
     */
    public Location(final int newLocationValue, final File locationText, final GridPane parentElement) {
        locationValue = newLocationValue;
        locationFile = locationText;
        container = parentElement;
    }

    /**
     * Call the method to display the Location description based on the Location value.
     *
     * @return A boolean value indicating if the current Location is a Combat Location
     */
    public boolean getDescription() {
        if (locationValue % 100 == EXPLORE_LOCATION || locationValue % 100 == EXPLORE_LOCATION_FOUND) {
            // If: Location value matches a non-Combat Location value.
            return exploreLocation();
        } else {
            // Else: Location value matches a Combat Location value.
            return fightLocation();
        }
    }

    /**
     * Displays the description for the area's fight Location then prints a description based on location value.
     * Location value options: First encounter, returning encounter, or beaten encounter.
     *
     * @return A boolean value indicating if a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean fightLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the "Combat Location" text from the Location text file.
        try {
            Scanner fileReader = new Scanner(locationFile);
            // Skip the "Explore Location" text.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the Combat Location description on screen.
            descriptionBox.setText(fileReader.nextLine());
            // First encounter at the Location.
            if (locationValue % 100 == FIGHT_LOCATION) {
                // Display the description in the text area element on screen.
                descriptionBox.appendText(fileReader.nextLine());
                return true;
                // Returning encounter at the Location (lost first Combat).
            } else if (locationValue % 100 == FIGHT_LOCATION_FOUND) {
                // Skip to correct text file line and display the text in the text area element on screen.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine());
                return true;
                // Beaten the fight at this Location.
            } else if (locationValue % 100 == FIGHT_LOCATION_BEATEN) {
                // Skip to correct text file line and display it in the text area element on screen.
                fileReader.nextLine();
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine());
                return false;
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Displays the description for the area's Explore Location then prints a description based on Location value.
     * Location value options: First exploration or returning exploration.
     *
     * @return A false boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean exploreLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the "Explore Location" description from the Location text file.
        try {
            Scanner fileReader = new Scanner(locationFile);
            // Display the "Explore Location" description on screen.
            descriptionBox.setText(fileReader.nextLine());
            // First encounter at this Location.
            if (locationValue % 100 == EXPLORE_LOCATION) {
                // Append the additional Explore Location description.
                descriptionBox.appendText(fileReader.nextLine());
                // Returning to a found Location.
            } else {
                // Skip to correct text file line and display it in the text area element on screen.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Return false for a non-Combat Location
        return false;
    }
}
