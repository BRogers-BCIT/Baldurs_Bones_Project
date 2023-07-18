package baldursbones.bb;

import java.util.Random;

/** Enemy Abstract Class.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public abstract class Enemy {

    /** Variable: Value of the enemies total calculated at the end of combat.
     */
    protected int enemyTotal;

    /** Enemy object constructor. Implemented in extending classes.
     */
    public Enemy() {
    }

    /** Generates a "total" value for the enemy and compares the value to the passed player "total".
     * Returns a 1 (player win) if player total is greater than enemy total, otherwise returns a -1 (player loss).
     * @param playerRoll an integer value representing the users end total
     * @return an integer representing the outcome of the game (1 = player win, -1 = player loss)
     */
    public int compareTotal(final int playerRoll) {
        // Set the enemy total based on enemy difficulty.
        getTotal();
        // Compare enemy and player totals.
        // Player wins if player is greater.
        if (playerRoll > enemyTotal) {
            return 1;
        // If enemy is tied or greater than player loses.
        } else {
            return -1;
        }
    }

    /** Set the enemies end total based on its difficulty.
     */
    public void getTotal() {
        Random rand = new Random();
        // Generate a 0 or 1 and add it to the total.
        // This adds variance to enemies by creating a High roll and a Low roll value.
        int rollVariance = rand.nextInt(0, 2);
        enemyTotal += rollVariance;
    }

    /** Define the enemy behavior (Game end text) if the player wins.
     */
    protected abstract void win();

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected abstract void lose();

}

