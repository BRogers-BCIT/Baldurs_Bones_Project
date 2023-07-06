package baldursbones.bb;

import java.util.Scanner;

/** Tutorial Enemy Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class TutorialEnemy extends Enemy {

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 14;

    /** Create a Tutorial difficulty implementation of the Enemy Abstract.
     */
    public TutorialEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
    }

    /** Generates a "total" value for the enemy and compares the value to the passed player "total".
     * Regardless of outcome return a 0 (Tutorial win or loss does not affect player).
     * @param playerRoll an integer value representing the users end total
     * @return an integer representing the outcome of the game (0 = finished tutorial)
     */
    @Override
    public int compareTotal(final int playerRoll) {
        // Set the enemy total based on enemy difficulty.
        getTotal();
        // Compare enemy and player totals.
        // If the player total is higher, then the player wins, otherwise the enemy wins.
        if (playerRoll > enemyTotal) {
            win();
        } else {
            lose();
        }
        // Regardless of outcome return 0.
        return 0;
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void win() {
        System.out.println("Tutorial loss.");
        tutorialEnd();
    }
    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void lose() {
        System.out.println("Tutorial Win.");
        tutorialEnd();
    }

    /** Displays the game text for when tutorial finishes regardless of win or lose.
     */
    protected void tutorialEnd() {
        System.out.println("Tutorial end and game description.");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
