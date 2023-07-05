package baldursbones.bb;

import java.util.Scanner;

/** Easy Location Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EasyLocation extends Location {

    // Explore location: not been encountered yet.
    private static final int EXPLORE_LOCATION = 111;

    // Explore location: already encountered.
    private static final int EXPLORE_LOCATION_FOUND = 112;

    // Combat location: not been encountered yet.
    private static final int FIGHT_LOCATION = 121;

    // Combat location: found but not beaten.
    private static final int FIGHT_LOCATION_FOUND = 122;

    // Combat location: found and beaten.
    private static final int FIGHT_LOCATION_BEATEN = 123;

    /** Creates a new location object and assigns it a location area and type.
     * @param newLocationType an integer representing the location's area and type.
     */
    public EasyLocation(final int newLocationType) {
        super(newLocationType);
    }

    /** Gets the description of the easy location based on its location type.
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        // Non-combat location types.
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }

    /** Prints the text for an easy location fight encounter then prints based on location value.
     * Location type options: first encounter, returning encounter, or beaten encounter.
     * @return a boolean value indicating if a fight can be started at this location
     */
    protected boolean fightLocation() {
        System.out.println("Easy Location: combat description");
        // First encounter at the location
        if (locationType == FIGHT_LOCATION) {
            System.out.println("Easy Location: First encounter combat description.");
            return true;
        // Returning encounter at the location (lost first fight).
        } else if (locationType == FIGHT_LOCATION_FOUND) {
            System.out.println("Easy Location: Returning combat description.");
                return true;
        // Beaten the fight at this location.
        } else if (locationType == FIGHT_LOCATION_BEATEN) {
            System.out.println("Easy Location: Beaten combat description.");
            return false;
        }
        return false;
    }

    /** Prints the base text for an easy exploration location then prints text based on the location value.
     * Either print first visit text description or the location return text description.
     */
    protected void exploreLocation() {
        System.out.println("Easy Location: location description");
        // First encounter at this location.
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("Easy Location: First visit description.");
        // Returning to encountered location.
        } else {
            System.out.println("Easy Location: Location return description.");
        }
        // Prompt user to continue.
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }

}
