package baldursbones.bb;

import java.util.Scanner;

/** Tutorial Location.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class TutorialLocation extends Location {
    private static final int RETURNED_TUTORIAL = 13;
    /** Initializes a new easy location object.
     * @param newLocationType an integer representing what location type it is and if it has been explored
     */
    public TutorialLocation(final int newLocationType) {
        super(newLocationType);
    }
    /** Gets the description of the easy location based on its location type.
     * @return a boolean determining whether to start the tutorial fight
     */
    public boolean getDescription() {
        if (locationType == RETURNED_TUTORIAL) {
            return  fightLocation();
        } else {
            exploreLocation();
            return false;
        }
    }
    /** Prints the text for the start of the tutorial leading to the tutorial fight.
     * @return a boolean indicating the fight should begin
     */
    protected boolean fightLocation() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Greetings player, in Baldur's Bones you will take control of "
                + "an aspiring sailor who longs to be a captain.");
        System.out.println("In order to earn the respect of your fellow sailors you must "
                + "prove yourself in a popular game of gambling.");
        System.out.println("It is a game of dice, commonly known as as Baldur's Bones.");
        System.out.println("You have just arrived ashore from a fishing trip with your mentor "
                        + "Karnus Stonewind, an aging hill dwarf.");
        System.out.println("He has decided it is time for you to learn how to continue on your own in this world");
        System.out.println("'Well I am regretful to say that my time has come, "
                + "my bones ache and the sea wind chills me to my core.");
        System.out.println("It is time for me to return to my homeland in the hills. ");
        System.out.println("Before I go I have taught you almost everything you need to know to be a captain, "
                + "but one thing remains.");
        System.out.println("I must teach you the game of Baldur's Bones.'");
        System.out.println("The game is simple, you roll three 6-sided die and take that total, "
                        + "then one die at a time you may add to that roll.");
        System.out.println("The closer you get to 21 without going over the better.");
        System.out.println("Two players face off, with the challenger rolling first and the defender rolling second.");
        System.out.println("While the game is a game of luck and skill I have a secret up my sleeve. "
                + "Play me in a game and I will show you.");
        System.out.println("Enter to continue");
        scan.nextLine();
        return true;
    }
    /** Prints the text for returning to the tutorial location.
     */
    protected void exploreLocation() {
        System.out.println("\n\nAs you walk back to your ship you take a moment to clear your mind and relax.");
        System.out.println("'I can do this' you think to yourself. "
                        + "I just need to remember to focus and think about the odds.");
        System.out.println("Your destiny does not await you here, head into town and prove yourself.");
        System.out.println("A worthy crew and seaborn adventurers await you.");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
