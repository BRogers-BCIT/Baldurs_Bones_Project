package baldursbones.bb;

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
    private final File bossLocationText = new File("src/main/resources/baldursbones/bb/BossLocationText.txt");

    // Constant: Boss fight location value = 500.

    /** Creates a new location object and assigns it a location value.
     * @param newLocationType an integer representing the location's value.
     */
    public BossLocation(final int newLocationType) {
        super(newLocationType);
    }

    /**
     * Gets the description of the boss location based on its location value.

     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        // Check Non-combat location encounters and call handler.
        if (locationValue == EXPLORE_LOCATION || locationValue == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
            // Otherwise call the boss fight encounter handler.
        } else {
            return fightLocation();
        }
    }

    /**
     * Prints the text for a boss fight encounter.
     * @throws RuntimeException if text file is missing
     * @return a boolean value indicating to start a fight at the location
     */
    protected boolean fightLocation() {
        // Try to read the start fight text from the BossLocation text file.
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(bossLocationText);
            System.out.println(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Prints the base text for a boss exploration location then prints text based on the location value.
     * Either print first visit text description or the location return text description.
     * @throws RuntimeException if text file is missing
     */
    protected void exploreLocation() {
        // Try to read the "explore location" text from the BossLocation text file.
        try {
            // Create a new scanner for the text file and skip the first section.
            Scanner fileReader = new Scanner(bossLocationText);
            fileReader.nextLine();
            if (locationValue == EXPLORE_LOCATION) {
                // If the location has not been visited, then print the second text line for new locations.
                System.out.println(fileReader.nextLine());
            } else {
                // If the location has been visited, then print the third text line for visited locations.
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
            }
            // Prompt user to continue. ** Replace with button press. **
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue");
            scan.nextLine();
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }
}
