package baldursbones.bb;

import java.util.Scanner;

/** Tutorial Location Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class TutorialLocation extends Location {

    // Location value for returning to tutorial location.
    private static final int RETURNED_TUTORIAL = 1;

    /** Creates a new location object and assigns it a location value.
     * @param newLocationType an integer representing the location's value.
     */
    public TutorialLocation(final int newLocationType) {
        super(newLocationType);
    }

    /** Gets the description of the tutorial location based on its location value.
     * @return a boolean indicating if the current location is a combat location
     */
    public boolean getDescription() {
        if (locationType == RETURNED_TUTORIAL) {
            return  fightLocation();
        } else {
            exploreLocation();
            return false;
        }
    }

    /** Prints the text for the start of the tutorial and leads into the tutorial fight.
     * @return a boolean value indicating if a fight can be started at this location
     */
    protected boolean fightLocation() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Tutorial introduction.");
        // Prompt user to continue.
        System.out.println("Enter to continue");
        scan.nextLine();
        return true;
    }

    /** Prints the text for returning to the tutorial location.
     */
    protected void exploreLocation() {
        System.out.println("Return to tutorial location text.");
        // Prompt user to continue.
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }

}
