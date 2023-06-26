package baldursbones.bb;

import java.util.Scanner;

/** Hard Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class HardLocation extends Location {
    private static final int EXPLORE_LOCATION = 311;
    private static final int EXPLORE_LOCATION_FOUND = 312;
    private static final int FIGHT_LOCATION = 321;
    private static final int FIGHT_LOCATION_FOUND = 322;

    /** Initializes a new hard location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public HardLocation(final int newLocationType) {
        super(newLocationType);
    }
    /** Gets the description of the hard location based on its location type.
     * @return a boolean determining whether to make a new hard enemy object
     */
    public boolean getDescription() {
        if (locationType == EXPLORE_LOCATION || locationType == EXPLORE_LOCATION_FOUND) {
            exploreLocation();
            return false;
        } else {
            return fightLocation();
        }
    }
    /** Prints the text for a hard fight encounter or explored fight location.
     * @return a boolean indicating there is an enemy to fight at this location
     */
    protected boolean fightLocation() {
        System.out.println("\n\n");
        System.out.println("As you walk through the merchant district you are taken "
                + "with the splendor of one of its taverns.");
        System.out.println("You decide to take a look inside in hopes of finding a worthy opponent to prove yourself.");
        System.out.println("As you scan the lush interior, you notice a group of well dressed individuals.");
        if (locationType == FIGHT_LOCATION || locationType == FIGHT_LOCATION_FOUND) {
            System.out.println("As you approach the group of merchants, you notice of of the merchants "
                    + "who seems quite bored with the polite small talk he is engaging in.");
            System.out.println("They seize upon your entrance and speak with enthusiasm,");
            System.out.println("'You there, what a fine youth you are, remind me of myself some time ago.'");
            System.out.println("'Come, spare me of this boredom "
                    + "and I will engage you in a rousing game of Baldur's Bones.");
            if (locationType == FIGHT_LOCATION_FOUND) {
                System.out.println("Having sparked the spirit of competition in the merchants, "
                        + "they are now engrossed in games of chance amount themselves.");
                System.out.println("Having an odd number, one of the merchants who was observing beckons you over.");
                System.out.println("'You there, care to join me for a game,"
                        + " I will tell you some of my personal tricks if you do.'");
            }
            return true;
        } else {
            System.out.println("Having enjoyed a good game of Baldur's Bones the merchants at this establishment,");
            System.out.println("seem to have taken to discussing more financially inclined matters.");
            System.out.println("They don't notice you at all as you enter as the are engrossed in their conversation.");
            System.out.println("There does not seem to be a game of Bones to be played here.");
            System.out.println("It is likely time to move onwards.");
            return false;
        }
    }
    /** Prints the text for a hard exploration location or explored hard location.
     */
    protected void exploreLocation() {
        System.out.println("\n\nYou walk the well worn cobbles of the mercantile district, looking about as you walk,");
        System.out.println("Despite yourself you are momentarily taken with the splendors of the great city.");
        if (locationType == EXPLORE_LOCATION) {
            System.out.println("You take a moment to briefly ponder the wonders not oft seen to a lowly crewman.");
            System.out.println("Masterworks of armor and weaponry, magical trinkets that boggle the mind, "
                    + "and more exquisite treasures surround you.");
            System.out.println("Having taken a chance to enjoy this moment of being surround by luxury, "
                    + "you decide to move onwards");
        } else {
            System.out.println("There are infinite wonders to ponder any any street of the merchant district.");
            System.out.println("You know that you have spent long enough staring slack-jawed at the windows.");
            System.out.println("Time to continue along your way, greatness awaits.");
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
