package baldursbones.bb;

import java.util.Scanner;

/** Boss Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class BossLocation extends Location {
    private static final int EXPLORE_LOCATION = 411;
    private static final int EXPLORE_LOCATION_FOUND = 412;

    /** Initializes a new boss location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public BossLocation(final int newLocationType) {
        super(newLocationType);
    }
    /** Gets the description of the boss location based on its location type.
     * @return a boolean determining whether to make a new boss enemy object
     */
    public boolean getDescription() {
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }
    /** Prints the text for a boss fight encounter.
     * @return a boolean indicating to make a boss object
     */
    protected boolean fightLocation() {
        System.out.println("\n\n");
        System.out.println("As you walk to the back of the tavern the noise begins to swell. "
                + "Sitting at back is the center of attention.");
        System.out.println("He is a human with a well trimmed beard "
                + "wearing a puffy white shirt and a small black cap.");
        System.out.println(" Were it not for the crowd around him who hang on his every word "
                + "he would not seem particularly notable. ");
        System.out.println("However as you approach, you are drawn in by his natural charisma"
                + " and pleasant demeanor.");
        System.out.println(" As you approach he seems to pick you out of the crowd and says: ");
        System.out.println("'Well greeting there my young friend, you look like an enterprising fellow. "
                + "Care for a game of Baldur's Bones?'");
        System.out.println("Drawn in by his natural charm, you feel you cannot resist and take a seat at the table "
                        + "as one of his admirers stands. ");
        System.out.println("He rolls up his sleeves and draws a black leather cup from a pouch at his side");
        System.out.println("He scoops up the set of finely carved ivory dice from the table.");
        System.out.println("'As is custom I believe that you would roll first'");
        System.out.println("Drawn in with no chance to refuse,"
                + "the game for your chance to finally become a captain begins.");
        return true;
    }
    /** Prints the text for a boss exploration location or explored boss location.
     */
    protected void exploreLocation() {
        System.out.println("\n\n");
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("As you enter the tavern a bartender, "
                            + "a half-orc with a finely groomed mustache and goatee - call out to you.");
            System.out.println(" 'Oy there, if you are looking for Volo I know he is somewhere around here.'");
            System.out.println("'Just follow the noise and look for a crowd, "
                    + "he cant seem to help but cause a commotion every time he comes in here.'");
            System.out.println("You cant see anyone who fits that description, so you decide to move along.");
        } else {
            System.out.println("As you continue to wander the Yawning Portal "
                    + "A gnome sitting at the front calls out to you.");
            System.out.println("'bartenders busy right now, if you want a drink, you'll have to wait.'");
            System.out.println(" If your looking for Volo he's in the back somewhere making "
                    + "a ruckus. There does not seem to be anyone to play around here.");
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
