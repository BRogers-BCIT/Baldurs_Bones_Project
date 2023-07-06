package baldursbones.bb;

import java.util.Random;

/** Boss Enemy Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossEnemy extends Enemy {

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 19;

    /** Variable: An integer representing the number of rounds the boss lost vs won.
     * Equation: rounds lost - rounds won.
     */
    protected int roundOutcomeCount;

    /** Create a Boss Enemy implementation of the Enemy Abstract.
     */
    public BossEnemy() {
        super();
        enemyTotal = DIFFICULTY;
        roundOutcomeCount = 0;
    }

    /** Generates a "total" value for the enemy and compares the value to the passed player "total".
     * Returns a 1 (player win) if player total is greater than enemy total, otherwise returns a -1 (player loss).
     * Also update the rounds lost counter based on the outcome of the round.
     * @param playerRoll an integer value representing the users end total
     * @return an integer representing the outcome of the game (1 = player win, -1 = player loss)
     */
    @Override
    public int compareRoll(final int playerRoll) {
        getRoll();
        System.out.println("Boss total: " + enemyTotal + ".");
        if (playerRoll > enemyTotal) {
            roundOutcomeCount += 1;
            return 1;
        } else {
            roundOutcomeCount -= 1;
            return -1;
        }
    }

    /** Creates the boss roll.
     * Boss roll equation (Difficulty + (0-1) - number of rounds won + number of rounds lost).
     */
    @Override
    public void getRoll() {
        // Determine the base total (Low Roll = 19, High Roll = 20).
        enemyTotal = DIFFICULTY;
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
        // Adjust the total based on the number of rounds the boss has lost vs won.
        enemyTotal += roundOutcomeCount;
    }

    /** Defines the enemy behavior if the player wins a round of combat.
     */
    protected void winRound() {
        System.out.println("Boss round win.");
    }

    /** Defines the enemy behavior if the player loses a round of combat.
     */
    protected void loseRound() {
        System.out.println("Boss round loss.");
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void win() {
        System.out.println("Boss combat win.");
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void lose() {
        System.out.println("Boss combat loss.");
    }

}
