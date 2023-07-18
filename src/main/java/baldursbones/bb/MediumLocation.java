package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Medium Location Implementation.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MediumLocation extends Location {

    // Constant: Explore location value - not been encountered yet.
    private static final int EXPLORE_LOCATION = 211;

    // Constant: Explore location value - already encountered.
    private static final int EXPLORE_LOCATION_FOUND = 212;

    // Constant: Combat location value - not been encountered yet.
    private static final int FIGHT_LOCATION = 221;

    // Constant: Combat location value - found but not beaten.
    private static final int FIGHT_LOCATION_FOUND = 222;

    // Constant: Combat location value - found and beaten.
    private static final int FIGHT_LOCATION_BEATEN = 223;

    // Text file: contains all dialogue to be printed by the medium location class.
    private final File mediumLocationText
            = new File("src/main/resources/baldursbones/bb/MediumLocationText.txt");

    // FXML Element: The parent element the location object will display information in.
    private final GridPane container;

    /**
     * Creates a new location object and assigns it a location value.
     *
     * @param newLocationType An integer representing the location's value.
     * @param locationGrid    The location menu layout element that uses this location object
     */
    public MediumLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType);
        container = locationGrid;
    }

    /**
     * Gets the description of the medium location based on its location value.
     *
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        // Non-combat location values.
        if (locationValue == EXPLORE_LOCATION || locationValue == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }

    /**
     * Prints the text for a medium location fight encounter then prints based on location value.
     * Location value options: first encounter, returning encounter, or beaten encounter.
     *
     * @return a boolean value indicating if a fight can be started at this location
     * @throws RuntimeException if text file is missing
     */
    protected boolean fightLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the combat location text from the Medium Location text file.
        try {
            Scanner fileReader = new Scanner(mediumLocationText);
            // Skip the "explore location" text.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the fight location text on screen.
            descriptionBox.setText(fileReader.nextLine());
            // First encounter at the location.
            if (locationValue == FIGHT_LOCATION) {
                // Display the text in the text area element on screen.
                descriptionBox.appendText(fileReader.nextLine());
                return true;
                // Returning encounter at the location (lost first fight).
            } else if (locationValue == FIGHT_LOCATION_FOUND) {
                // Skip to correct text file line and display the text in the text area element on screen.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine());
                return true;
                // Beaten the fight at this location.
            } else if (locationValue == FIGHT_LOCATION_BEATEN) {
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
     * Prints the base text for a medium exploration location then prints text based on the location value.
     * Either print first visit text description or the location return text description.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void exploreLocation() {
        // Define the text area to write information into.
        TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
        // Try to read the "explore location" text from the Medium Location text file.
        try {
            Scanner fileReader = new Scanner(mediumLocationText);
            // Display the "explore location" text on screen.
            descriptionBox.setText(fileReader.nextLine());
            // First encounter at this location.
            if (locationValue == EXPLORE_LOCATION) {
                // Append the additional explore location text.
                descriptionBox.appendText(fileReader.nextLine());
                // Returning to encountered location.
            } else {
                // Skip to correct text file line and display it in the text area element on screen.
                fileReader.nextLine();
                descriptionBox.appendText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

}
