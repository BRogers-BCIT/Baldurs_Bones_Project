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

    // Constant: The digit used to track the Area of a Location in a Location Value.
    private static final int LOCATION_DIGIT = 100;

    // Constant: The final 2 digits of a Location Value indicating: Non-Combat, Not Visited.
    private static final int EXPLORE_LOCATION = 11;

    // Constant: The final 2 digits of a Location Value indicating: Non-Combat, Visited.
    private static final int EXPLORE_LOCATION_FOUND = 12;

    // Constant: The final 2 digits of a Location Value indicating: Combat, Not Visited.
    private static final int FIGHT_LOCATION = 21;

    // Constant: The final 2 digits of a Location Value indicating: Combat, Visited, Non-Beaten.
    private static final int FIGHT_LOCATION_FOUND = 22;

    // Constant: The final 2 digits of a Location Value indicating: Combat, Visited, Beaten.
    private static final int FIGHT_LOCATION_BEATEN = 23;

    /**
     * FXML Element: The Parent Layout Element the Location class is being used by.
     */
    protected final GridPane container;

    /**
     * Variable: A 3-digit value that represents the area, Location type, and Location state of a map coordinate.
     * Digit 1: Area: (0 - Tutorial, 1 - Easy, 2 - Medium, 3 - Hard, 4 - Boss Area, 5 - Boss Combat)
     * Digit 2: Location Type: (1 - Explore, 2 - Combat)
     * Digit 3: Location State: (1 - Not Found, 2 - Found, 3 - Beaten (Combat Only))
     */
    protected int locationValue;

    // Text file: The Text File object to be used for Location implementation.
    private final File locationFile;

    /**
     * Receives and Sets the field values for the Location class. Receives the values from abstract implementations.
     *
     * @param newLocationValue The Location Value for a Location, used to determine Location area, type, and state
     * @param locationText     The Text File to read the Location display text from
     * @param parentElement    The Parent's Layout Element for the Controller using the Enemy Class
     */
    public Location(final int newLocationValue, final File locationText, final GridPane parentElement) {
        locationValue = newLocationValue;
        locationFile = locationText;
        container = parentElement;
    }

    /**
     * Find the Location type from the Location Value and call the Combat check method.
     *
     * @return A boolean value from the Combat Check indicating if the Location is a Combat Location
     */
    public boolean getDescription() {
        if (locationValue % LOCATION_DIGIT == EXPLORE_LOCATION
                || locationValue % LOCATION_DIGIT == EXPLORE_LOCATION_FOUND) {
            // If: Location Value matches a non-Combat Location Value.
            return exploreLocation();
        } else {
            // Else: Location Value matches a Combat Location Value.
            return fightLocation();
        }
    }

    /**
     * Displays the description for the Explore Location then prints a description based on State of the Location.
     * Location Value options: First exploration or Returning exploration.
     *
     * @return A boolean value indicating that a Combat cannot be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean exploreLocation() {
        // Try to read the "Explore Location" description from the Location Text File.
        try {
            Scanner fileReader = new Scanner(locationFile);
            // Display the "Explore Location" description in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            descriptionBox.setText(fileReader.nextLine() + "\n");
            // First encounter at this Location.
            if (locationValue % LOCATION_DIGIT == EXPLORE_LOCATION) {
                // Display the Explore Location text.
                descriptionBox.appendText(fileReader.nextLine() + "\n");
                // Returning to a Found Location.
            } else {
                // Display the Returning Location text.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine() + "\n");
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return False to prevent Combat at Explore Locations.
        return false;
    }

    /**
     * Displays the description for the Combat Location then prints a description based on State of the Location.
     * Location States options: First encounter, Returning encounter, or Beaten encounter.
     *
     * @return A boolean value indicating if a Combat can be started at this Location
     * @throws RuntimeException If the text document being loaded does not exist
     */
    protected boolean fightLocation() {
        // Try to read the "Combat Location" text from the Location Text File.
        try {
            Scanner fileReader = new Scanner(locationFile);
            // Skip the "Explore Location" text.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the Combat Location description in the Game Description display.
            TextArea descriptionBox = (TextArea) container.lookup("#GameDescription");
            descriptionBox.setText(fileReader.nextLine() + "\n");
            // First encounter at the Location.
            if (locationValue % LOCATION_DIGIT == FIGHT_LOCATION) {
                // Display the First Encounter text.
                descriptionBox.appendText(fileReader.nextLine() + "\n");
                // Return True to allow Combat at this Location.
                return true;
                // Returning encounter at the Location (lost first Combat).
            } else if (locationValue % LOCATION_DIGIT == FIGHT_LOCATION_FOUND) {
                // Display the Return Encounter text.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine() + "\n");
                // Return True to allow Combat at this Location.
                return true;
                // Beaten the fight at this Location.
            } else if (locationValue % LOCATION_DIGIT == FIGHT_LOCATION_BEATEN) {
                // Display the Beaten Combat text.
                fileReader.nextLine();
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine() + "\n");
                // Return False to prevent Combat at a beaten Location.
                return false;
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
        // Return False to prevent a Combat at the Location.
        return false;
    }
}
