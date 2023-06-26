package baldursbones.bb;

import java.util.Random;

/** Boss Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class BossEnemy extends Enemy {
    private static final int DIFFICULTY = 19;
    /** An integer representing the number of rounds the boss has lost.
     */
    protected int numberOfLosses;

    /** Initializes a Boss Enemy Object.
     */
    public BossEnemy() {
        super();
        enemyRoll = DIFFICULTY;
        numberOfLosses = 0;
    }

    /** Compares the users roll to a boss's roll.
     * @param playerRoll an integer representing the users end roll value
     * @return an integer representing the result of the round
     */
    @Override
    public int compareRoll(final int playerRoll) {
        getRoll();
        System.out.println("Your opponent rolled a " + enemyRoll + " for their total.\n");
        if (playerRoll > enemyRoll) {
            numberOfLosses += 1;
            return 1;
        } else {
            numberOfLosses -= 1;
            return -1;
        }
    }
    /** Creates the boss roll based on the difficulty rating and the number of rounds the player has won.
     */
    @Override
    public void getRoll() {
        enemyRoll = DIFFICULTY;
        Random rand = new Random();
        int rollVariance = rand.nextInt(0, 2);
        enemyRoll += rollVariance;
        enemyRoll += numberOfLosses;
    }

    /** Defines the enemy behavior if the player wins a round.
     */
    protected void winRound() {
        System.out.println("You out rolled the adventurer without going over, you win this round.");
        System.out.println("'Congratulations my good fellow, it appears you have won this round.'");
    }
    /** Defines the enemy behavior if the player loses a round.
     */
    protected void loseRound() {
        System.out.println("Unfortunately the adventurer out rolled you, as such he takes this round.");
        System.out.println("'Ah, the fickle hand of fate! "
                + "It appears the kind lady fortune is not on your side this round.'");
    }

    /** Defines the boss behavior if the player wins.
     */
    protected void win() {
        System.out.println("Congratulations! you won two rounds against Volo before he could beat you.");
        System.out.println(" You have won this game of Bones.");
        System.out.println("'Good show, oh good show indeed!");
        System.out.println(" It has been far too long since someone has been able to show such a "
                + "performance against my talents, wit, and impeccable luck.");
        System.out.println("There are not many who can best the great Volo in a game "
                + "of fortunes so you should hold yourself in high esteem for that.");
        System.out.println("I pronounce you the winner and wish you the best in your future endeavors.");
        System.out.println("For now I must go, my next literary masterpiece awaits.'");
    }
    /** Defines the boss behavior if the player loses.
     */
    protected void lose() {
        System.out.println("Curses! Volo won two rounds against you before you could beat him.");
        System.out.println("'Good show, ahh a good show indeed, you almost had me for a second there.");
        System.out.println(" But no creature can best Volo! Ah, I jest of course, "
        + "such vanities lead only to an early grave in my profession.");
        System.out.println("Forgive me for now I must regale my fans "
                + "with tales of mighty beasts and blood pumping adventures");
    }
}
