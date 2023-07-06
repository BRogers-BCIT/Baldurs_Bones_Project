package baldursbones.bb;

import java.util.Scanner;

/** Boss Location Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossLocation extends Location {

    // Constant: Non-combat location value that surrounds the boss location.
    private static final int EXPLORE_LOCATION = 411;

    // Constant: Found value of non-combat locations.
    private static final int EXPLORE_LOCATION_FOUND = 412;

    // Constant: Boss fight location value = 500.

    /** Creates a new location object and assigns it a location value.
     * @param newLocationType an integer representing the location's value.
     */
    public BossLocation(final int newLocationType) {
        super(newLocationType);
    }

    /** Gets the description of the boss location based on its location value.
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        // Non-combat location values.
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        // Boss fight encounter.
        } else {
            return fightLocation();
        }
    }

    /** Prints the text for a boss fight encounter.
     * @return a boolean value indicating to start a fight at the location
     */
    protected boolean fightLocation() {
        System.out.println("Boss fight encounter start");
        return true;
    }

    /** Prints the base text for a boss exploration location then prints text based on the location value.
     * Either print first visit text description or the location return text description.
     */
    protected void exploreLocation() {
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("Non-combat boss location first encounter.");
        } else {
            System.out.println("Non-combat boss location returning encounter.");
        }
        // Prompt user to continue
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }

}
