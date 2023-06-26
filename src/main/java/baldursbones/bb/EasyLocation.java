package baldursbones.bb;

import java.util.Scanner;

/** Easy Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class EasyLocation extends Location {
    private static final int EXPLORE_LOCATION = 111;
    private static final int EXPLORE_LOCATION_FOUND = 112;
    private static final int FIGHT_LOCATION = 121;
    private static final int FIGHT_LOCATION_FOUND = 122;

    /** Initializes a new easy location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public EasyLocation(final int newLocationType) {
        super(newLocationType);
    }
    /** Gets the description of the easy location based on its location type.
     * @return a boolean determining whether to make a new easy enemy object
     */
    public boolean getDescription() {
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }
    /** Prints the text for an easy fight encounter or explored fight location.
     * @return a boolean indicating there is an enemy to fight at this location
     */
    protected boolean fightLocation() {
        System.out.println("\n\n");
        System.out.println("You enter a rundown tavern in search of a game of Bones.");
        System.out.println("While you walk amidst the common folk looking to unwind "
                + "you hear a ruckus in the back of the tavern.");
        if (locationType == FIGHT_LOCATION || locationType == FIGHT_LOCATION_FOUND) {
            System.out.println("Upon further investigation you find a group of sailors and deckhands playing dice.");
            System.out.println("As you approach one of them beckons to you and says,");
            System.out.println("'ahh good, fresh blood, "
                    + "it was beginning to get boring taking money from these chumps.'");
            System.out.println("'I can tell by the look of ya that yer wanting a game of Bones.");
            System.out.println("C'mon then sit down and i'll show you how its done.");
            if (locationType == FIGHT_LOCATION_FOUND) {
                System.out.println("One of the ruffians you ran into earlier smiles as they see you approach,");
                System.out.println("'Back for more are you, want me to show you how a its done? "
                        + "I guess I can oblige.'");
            }
            return true;
        } else {
            System.out.println("Having taken the local gamblers for their pride. "
                    + "You hear loud shouting and singing from the back.");
            System.out.println("the tavern regulars seem to have taken to enjoying ale"
                    + " as well as some more rowdy entertainment.");
            System.out.println("The patrons here don't seem to be in a state to be included "
                    + "to play a game of Baldur's Bones.");
            return false;
        }
    }
    /** Prints the text for an easy exploration location or explored easy location.
     */
    protected void exploreLocation() {
        System.out.println("\n\n");
        System.out.println("As you walk through the docks in search of a game,");
        System.out.println("you enter a street that does not appear to have any taverns for you to visit.");
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("As you walk along the street you take your time to enjoy your time on dry land.");
            System.out.println("After taking in the salt air and listening to the fishmongers");
            System.out.println("hawking their fresh catches you decide to move onwards and upwards.");
        } else {
            System.out.println("You recognize the local shops from you earlier travels and continue along your way.");
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
