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

    // Constant: Location value for returning to tutorial location.
    private static final int RETURNED_TUTORIAL = 1;

    // Text file: contains all dialogue to be printed by the tutorial location class.
    private final File tutorialLocationText
            = new File("src/main/resources/baldursbones/bb/TutorialLocationText.txt");

    // FXML Element: The parent element the location object will display information in.
    private final GridPane container;

    /**
     * Creates a new location object and assigns it a location value.
     *
     * @param newLocationType An integer representing the location's value.
     * @param locationGrid    The location menu layout element that uses this location object
     */
    public TutorialLocation(final int newLocationType, final GridPane locationGrid) {
        super(newLocationType);
        container = locationGrid;
    }

    /**
     * Gets the description of the tutorial location based on its location value.
     *
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        if (locationValue == RETURNED_TUTORIAL) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }

    /**
     * Prints the text for the start of the tutorial and leads into the tutorial fight.
     *
     * @return a boolean value indicating if a fight can be started at this location
     * @throws RuntimeException if text file is missing
     */
    protected boolean fightLocation() {
        // Try to read the start tutorial text from the TutorialLocation text file.
        try {
            Scanner fileReader = new Scanner(tutorialLocationText);
            // Print the text to the text area in the location window user.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Prints the text for returning to the tutorial location.
     *
     * @throws RuntimeException if text file is missing
     */
    protected void exploreLocation() {
        // Try to read the return to tutorial text from the TutorialLocation text file.
        try {
            Scanner fileReader = new Scanner(tutorialLocationText);
            // Skip the first line of text.
            fileReader.nextLine();
            // Print the text to the text area in the location window user.
            TextArea descriptionBox = (TextArea) container.lookup("#GameTextArea");
            descriptionBox.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
