package baldursbones.bb;

import java.util.Scanner;

/** Medium Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class MediumLocation extends Location {
    private static final int EXPLORE_LOCATION = 211;
    private static final int EXPLORE_LOCATION_FOUND = 212;
    private static final int FIGHT_LOCATION = 221;
    private static final int FIGHT_LOCATION_FOUND = 222;

    /** Initializes a new medium location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public MediumLocation(final int newLocationType) {
        super(newLocationType);
    }
    /** Gets the description of the medium location based on its location type.
     * @return a boolean determining whether to make a new medium enemy object
     */
    public boolean getDescription() {
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }
    /** Prints the text for a medium fight encounter or explored fight location.
     * @return a boolean indicating there is an enemy to fight at this location
     */
    protected boolean fightLocation() {
        System.out.println("\n\n");
        System.out.println("You decide to enter one of the many cities inn's "
                + "in hopes of finding a game to prove yourself.");
        System.out.println("As you walk through the tavern you cannot help but notice a "
                + "group of individuals carrying weapons and armor.");
        if (locationType == FIGHT_LOCATION || locationType == FIGHT_LOCATION_FOUND) {
            System.out.println("They might be adventures or they might be mercenaries "
                    + "but they will make a good tale all the same.");
            System.out.println("As you approach the table one of the beacons to you jovially. ");
            System.out.println("'You there, you look like you enjoy a bit of fun.");
            System.out.println("Come now my friends here tire me wounding their pride, humor me with a game.");
            if (locationType == FIGHT_LOCATION_FOUND) {
                System.out.println("As you approach the table a familiar face looks up at you.");
                System.out.println("Ahh my friend, back again so soon. Keen on another game to regain your honor?");
            }
            return true;
        } else {
            System.out.println("The individuals who were once sat at the table "
                    + "are now engrossed in conversation with the innkeeper.");
            System.out.println("They appears engaged whatever business they are in town for."
                    + " It is unlikely you will find a game here.");
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue");
            scan.nextLine();
            return false;
        }
    }
    /** Prints the text for a medium exploration location or explored medium location.
     */
    protected void exploreLocation() {
        System.out.println("\n\n");
        System.out.println("As you walk through the residential areas of town you take a moment "
                + "to enjoy the quiet joys of city living.");
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("You take in the sights and smells of the town.");
            System.out.println("Fresh baked bread, roasting meat on splits for sale, the quiet thrum of conversation.");
            System.out.println("Nevertheless there are certainly no games of Bones to be played here."
                    + " It is time to move onwards.");
        } else {
            System.out.println("While you would love to sit an enjoy the life of the city folk, "
                    + "you have a goal to accomplish and crew to find.");
            System.out.println("You must move onwards if you wish to become a captain.");
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
