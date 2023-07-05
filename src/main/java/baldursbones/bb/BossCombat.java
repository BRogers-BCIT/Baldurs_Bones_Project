package baldursbones.bb;

import java.util.Scanner;

/** Boss Fight Combat Extension.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossCombat extends Combat {

    // Constant: maximum number of rounds of combat against boss.
    private static final int MAX_ROUNDS = 3;

    /** Class Value: Record the number of rounds the boss has won against the player.
     */
    protected int roundsWon;

    /** Class Value: Record the number of rounds the boss has lost to the player.
     */
    protected int roundsLost;

    // Counter: Records the current round of combat. Min = 1, Max = Max_Rounds.
    private int rounds;


    /** Create a new combat class for a boss fight combat. Takes a passed player object and a new boss enemy object.
     * @param player the current character object for the player
     * @param newBoss a new boss type enemy object
     */
    public BossCombat(final Player player, final BossEnemy newBoss) {
        super(player, newBoss);
        rounds = 0;
        roundsWon = 0;
        roundsLost = 0;
    }

    /** Informs the player of their current stats and calls the combat loop to start the beginning of the combat.
     * Returns the outcome of the round when finished (-2 = loss, +2 = win).
     * @return An integer that represents the outcome of the combat
     */
    @Override
    public int startCombat() {
        Scanner scan = new Scanner(System.in);

        // Print the players stats and prompt the player to continue.
        System.out.println("Begin final boss fight.");
        System.out.println("Your current stats are: ");
        pc.getStats();
        System.out.println("Enter to continue");
        scan.nextLine();

        // Call the combat loop method.
        combatLoop();

        // Find the final outcome of the combat and return it (-2 = loss, +2 = win).
        setOutcome();
        return outcome;
    }

    // Begins a while loop to repeat combat rounds in a best-of-three.
    private void combatLoop() {

        // While less than 3 rounds have been player and neither the player has won two rounds.
        while (rounds < MAX_ROUNDS && roundsWon < 2 && roundsLost < 2) {

            // Inform player of current round status.
            System.out.println("Start new round. Current round: " + (rounds + 1) + ".");
            System.out.println("Rounds won: " + roundsWon + ".");
            System.out.println("Rounds lost: " + roundsLost + ".");

            // Call new starting rolls and invoke the player combat loop.
            startRoll();
            playerChoice = 1;
            super.playerChoose();

            // Get the bosses roll for the round and compare to the player.
            // (+1 = player win, -1 = player loss.)
            outcome = enemy.compareRoll(playerTotal);

            // Compare boss and player rolls and increment the rounds won or lost.
            if (outcome > 0) {
                roundsWon += 1;
                ((BossEnemy) enemy).winRound();
            } else {
                roundsLost += 1;
                ((BossEnemy) enemy).loseRound();
            }

            // Increment the number of rounds fought and prompt player to continue.
            rounds += 1;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue \n");
            scan.nextLine();
        }
    }

    /** Set the outcome value based on the number of rounds won or lost.
     */
    protected void setOutcome() {
        // If rounds won == 2 then the player wins and sets the outcome to 2.
        if (roundsWon == 2) {
            outcome = 0;
            outcome += 2;
        }
        // If rounds lost == 2 then the player loses and sets the outcome to -2.
        if (roundsLost == 2) {
            outcome = 0;
            outcome -= 2;
        }
        // If neither the boss nor the player has won 2 rounds then do not set the outcome.
    }

}
