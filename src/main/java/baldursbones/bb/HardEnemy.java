package baldursbones.bb;

/** Hard Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class HardEnemy extends Enemy {
    private static final int DIFFICULTY = 18;

    /** Initializes a Hard Enemy Object.
     */
    public HardEnemy() {
        super();
        enemyRoll = DIFFICULTY;
    }
    /** Defines the enemy behavior if the player wins.
     */
    protected void win() {
        System.out.println("Congratulations! You successfully beat one of the merchants in Balur's Bones.");
        System.out.println("'Well then color me surprised, "
                + "been a long time since i've been beaten in a game of Bones.'");
        System.out.println("'I know when i'm outmatched. You read me like a book and played like a fiddle.'");
        System.out.println("'You're a fine judge of character, I will give you that. Make a fine merchant you would.'");
    }
    /** Defines the enemy behavior if the player loses.
     */
    protected void lose() {
        System.out.println("Curses! You unfortunately lost to one of the merchants in Balur's Bones.");
        System.out.println("'Well played my fine fellow, you certainly gave me a run for my money there.'");
        System.out.println("'Nevertheless a man in my field of work becomes a bit of an expert at reading people.");
        System.out.println("'Regardless you provided me with a fine distraction and a bit of mirth,"
                + " I thank you for that.");
    }
}
