package baldursbones.bb;

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

    /**
     * Creates a new location object and assigns it a location value.
     *
     * @param newLocationType an integer representing the location's value.
     */
    public MediumLocation(final int newLocationType) {
        super(newLocationType);
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
        // Try to read the combat location text from the MediumLocation text file.
        try {
            Scanner fileReader = new Scanner(mediumLocationText);
            // Skip the "explore location" text.
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Print the text to user.
            System.out.println(fileReader.nextLine());
            // First encounter at the location.
            if (locationValue == FIGHT_LOCATION) {
                System.out.println(fileReader.nextLine());
                return true;
                // Returning encounter at the location (lost first fight).
            } else if (locationValue == FIGHT_LOCATION_FOUND) {
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
                return true;
                // Beaten the fight at this location.
            } else if (locationValue == FIGHT_LOCATION_BEATEN) {
                fileReader.nextLine();
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
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
        // Try to read the "explore location" text from the MediumLocation text file.
        try {
            Scanner fileReader = new Scanner(mediumLocationText);
            // Print the "explore location" text to user.
            System.out.println(fileReader.nextLine());
            // First encounter at this location.
            if (locationValue == EXPLORE_LOCATION) {
                System.out.println(fileReader.nextLine());
                // Returning to encountered location.
            } else {
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
        // Prompt the user to continue. ** Replace with button press. **
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }

}
