package baldursbones.bb;

/** Medium Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class MediumEnemy extends Enemy {
    private static final int DIFFICULTY = 17;

    /** Initializes a Medium Enemy Object.
     */
    public MediumEnemy() {
        super();
        enemyRoll = DIFFICULTY;
    }
    /** Defines the enemy behavior if the player wins.
     */
    protected void win() {
        System.out.println("Congratulations! You successfully beat one of the city residents in Balur's Bones.");
        System.out.println("'Ha Ha, a you certainly know your way around a set of dice, "
                + "Been too long since i've had a good game.'");
        System.out.println("None of these sorry lot can give me a good run for my money,"
                + " I must have been getting sloppy.'");
        System.out.println("'Your a fine sort anyhow! if you every want a bit of adventure on dry land "
                + "to liven things up, you just come and find me.'");
    }
    /** Defines the enemy behavior if the player loses.
     */
    protected void lose() {
        System.out.println("Curses! You unfortunately lost to one of the city residents in Balur's Bones.");
        System.out.println("'I have to admit you play a fine game of Bones, but time cannot overcome experience.'");
        System.out.println("'I've spent many a night on the road playing dice with my crew, "
                + "and i've picked up a few tricks along the way.'");
        System.out.println("'You play a good game and are a fine judge of character. You will go far in this world.'");
    }
}
