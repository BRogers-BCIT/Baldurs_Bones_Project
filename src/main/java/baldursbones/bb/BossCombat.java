package baldursbones.bb;

import java.util.Scanner;

/** Boss Combat.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class BossCombat extends Combat {
    private static final int MAX_ROUNDS = 3;
    /** The number of rounds the player has lost.
     */
    protected int roundsWon;
    /** The number of rounds the player has won.
     */
    protected int roundsLost;
    private int rounds;
    /** Creates a new combat class with the player and the passed boss enemy.
     * @param player the players character object
     * @param newEnemy the boss object the player should play against
     */
    public BossCombat(final Player player, final BossEnemy newEnemy) {
        super(player, newEnemy);
        rounds = 0;
        roundsWon = 0;
        roundsLost = 0;
    }

    /** Starts the combat beginning of the first combat round and returns outcome when finished.
     * @return the integer outcome value of the combat
     */
    @Override
    public int startCombat() {
        System.out.println("You have begun a game of Baldur's Bones with the adventurer Volo.");
        System.out.println("Your current stats are: ");
        pc.getStats();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter to continue");
        scan.nextLine();
        combatLoop();
        setOutcome();
        return outcome;
    }
    private void combatLoop() {
        while (rounds < MAX_ROUNDS && roundsWon < 2 && roundsLost < 2) {
            System.out.println("You start a new round with Volo. It is currently round " + (rounds + 1) + ".");
            System.out.println("You have won " + roundsWon + " rounds against Volo.");
            System.out.println("You have lost " + roundsLost + " rounds against Volo.");
            startRoll();
            playerChoice = 1;
            super.playerChoose();
            outcome = enemy.compareRoll(playerRoll);
            if (outcome > 0) {
                roundsWon += 1;
                ((BossEnemy) enemy).winRound();
            } else {
                roundsLost += 1;
                ((BossEnemy) enemy).loseRound();
            }
            rounds += 1;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue \n");
            scan.nextLine();
        }
    }

    /** Sets the outcome of the fight based on the number of rounds won or lost.
     */
    protected void setOutcome() {
        if (roundsWon == 2) {
            outcome = 0;
            outcome += 2;
        }
        if (roundsLost == 2) {
            outcome = 0;
            outcome -= 2;
        }
    }
}
