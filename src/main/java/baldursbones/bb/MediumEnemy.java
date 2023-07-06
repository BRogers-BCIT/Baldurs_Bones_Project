package baldursbones.bb;

/** Medium Enemy Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MediumEnemy extends Enemy {

    // Constant: Defines the static difficulty of the enemy.
    private static final int DIFFICULTY = 17;

    /** Create a Medium difficulty implementation of the Enemy Abstract.
     */
    public MediumEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void win() {
        System.out.println("Medium combat win.");
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void lose() {
        System.out.println("Medium combat loss.");
    }

}
