package baldursbones.bb;

/** Easy Enemy Implementation.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EasyEnemy extends Enemy {

    // Define the static difficulty of the enemy.
    // Final roll will be either (DIFFICULTY) or (DIFFICULTY + 1) based on high/low roll generation.
    private static final int DIFFICULTY = 15;

    /** Create an Easy difficulty implementation of the Enemy Abstract.
     */
    public EasyEnemy() {
        super();
        // Set the starting total of enemy to its difficulty.
        enemyTotal = DIFFICULTY;
    }

    /** Define the enemy behavior (Game end text) if the player wins.
     */
    protected void win() {
        System.out.println("Easy combat win.");
    }

    /** Define the enemy behavior (Game end text) if the player loses.
     */
    protected void lose() {
        System.out.println("Easy combat loss.");
    }

}
