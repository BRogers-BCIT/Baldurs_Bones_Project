package baldursbones.bb;

import java.util.Scanner;

/** Tutorial Enemy.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class TutorialEnemy extends Enemy {
    private static final int DIFFICULTY = 14;

    /** Initializes an Easy Enemy Object.
     */
    public TutorialEnemy() {
        super();
        enemyRoll = DIFFICULTY;
    }
    /** Compares the users roll to the enemy's roll.
     * Also displays explanation of game mechanics to player.
     * @param playerRoll an integer representing the users end roll value
     * @return an integer representing the outcome of the game
     */
    @Override
    public int compareRoll(final int playerRoll) {
        getRoll();
        if (playerRoll > enemyRoll) {
            win();
        } else {
            lose();
        }
        return 0;
    }
    /** Defines the enemy behavior if the player wins.
     */
    protected void win() {
        System.out.println("\nAh, well done my friend. You know your stuff and are ready to begin.");
        tutorialEnd();
    }
    /** Defines the enemy behavior if the player loses.
     */
    protected void lose() {
        System.out.println("\nAh, a shame, but a good showing altogether, you are ready to begin.");
        tutorialEnd();
    }

    /** Displays the game text for when tutorial finishes regardless of win or lose.
     */
    protected void tutorialEnd() {
        System.out.println("This here is a talisman of renown, the more well known and respected you are,"
                + " the more powerful it is.");
        System.out.println("It will allow you to shape luck to your will and influence the dice.");
        System.out.println("As a beginner it will allow you to re-roll a die one time, "
                + "removing the previous roll and adding a new one.");
        System.out.println("As you become more well-known you will earn more talents with it "
                + "and the ability to use it more.");
        System.out.println("To earn the respect to crew a ship you will need to beat the famous adventurer Volo.");
        System.out.println("He often resides in the Yawning Portal, a tavern in the north east of the city.");
        System.out.println("To play him you will need to earn some renown.");
        System.out.println("Every time you beat someone in battle you will gain reputation, earn enough and "
                + "you will gain renown.");
        System.out.println("The higher your renown the more respected people you can play and the better your "
                + "talisman will become.\n");
        System.out.println("Be careful however, lose and you will lose credibility.");
        System.out.println("Lose to often and you will become too disgraced to ever be a captain in Waterdeep.");
        System.out.println("I wish you the best of luck, I have always held you as a good "
                + "friend and close companion. Farewell...");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
    }
}
