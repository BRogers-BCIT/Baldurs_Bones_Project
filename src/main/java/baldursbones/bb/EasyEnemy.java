package baldursbones.bb;

/** Easy Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class EasyEnemy extends Enemy {
    private static final int DIFFICULTY = 15;

    /** Initializes an Easy Enemy Object.
     */
    public EasyEnemy() {
        super();
        enemyRoll = DIFFICULTY;
    }
    /** Defines the enemy behavior if the player wins.
     */
    protected void win() {
        System.out.println("Congratulations! You successfully beat one of the port dwellers in Balur's Bones.");
        System.out.println("'Well, I'll be. Its been a long time since ive played someone outside these lot.'");
        System.out.println("'Guess i'm getting rusty. Either way, you played well. You would make a fine sailor.'");
    }
    /** Defines the enemy behavior if the player loses.
     */
    protected void lose() {
        System.out.println("Curses! You unfortunately lost to one of the port dwellers in Balur's Bones.");
        System.out.println("'Ah well, happens to us all sometimes friend."
                + " With experience like mine you get a sense for these things.'");
        System.out.println("'Nevertheless you played a good game and lifted my spirits."
                + " I thank you for the distraction.'");
    }
}
