package baldursbones.bb;

import java.util.Random;

/** Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 * Note: Abstract class has no tests
 */
public abstract class Enemy {
    /** integer to track the roll value of the enemy.
     */
    protected int enemyRoll;

    /** Initializes an Easy Enemy Object.
     */
    public Enemy() {
    }

    /** Compares the users roll to the enemy's roll.
     * @param playerRoll an integer representing the users end roll value
     * @return an integer representing the outcome of the game
     */
    public int compareRoll(final int playerRoll) {
        getRoll();
        if (playerRoll > enemyRoll) {
            return 1;
        } else {
            return -1;
        }
    }

    /** Creates the enemy roll based on its difficulty rating.
     */
    public void getRoll() {
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyRoll += rollVariance;
        System.out.println("Your opponent rolled a " + enemyRoll + " for their total.\n");
    }
    /** Defines the enemy behavior if the player wins.
     */
    protected abstract void win();
    /** Defines the enemy behavior if the player loses.
     */
    protected abstract void lose();
}

