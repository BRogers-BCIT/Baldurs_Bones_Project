package baldursbones.bb;

/** Hard Enemy Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class HardEnemy extends Enemy {

    // Define the static difficulty of the enemy.
    // Final roll will be either (DIFFICULTY) or (DIFFICULTY + 1) based on high/low roll generation.
    private static final int DIFFICULTY = 18;

    /** Create a Hard difficulty implementation of the Enemy Abstract.
     */
    public HardEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void win() {
        System.out.println("Hard combat win.");
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void lose() {
        System.out.println("Hard combat loss.");
    }

}
