package baldursbones.bb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Boss Fight Combat Extension.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossCombat extends Combat {

    // Constant: maximum number of rounds of combat against boss.
    private static final int MAX_ROUNDS = 3;

    /**
     * Class Value: Record the number of rounds the boss has won against the player.
     */
    protected int roundsWon;

    /**
     * Class Value: Record the number of rounds the boss has lost to the player.
     */
    protected int roundsLost;

    // Counter: Records the current round of combat. Min = 1, Max = Max_Rounds.
    private int rounds;

    // Text file: contains all dialogue to be printed by the boss combat class.
    private final File bossCombatText = new File("src/main/resources/baldursbones/bb/BossCombatText.txt");


    /**
     * Create a boss combat extension of the combat class.
     * Takes a passed player object and a new boss type enemy object.
     *
     * @param player  the current character object for the player
     * @param newBoss a new boss type enemy object
     */
    public BossCombat(final Player player, final BossEnemy newBoss) {
        super(player, newBoss);
        rounds = 0;
        roundsWon = 0;
        roundsLost = 0;
    }

    /**
     * Calls the players current stats and calls the combat loop to start the beginning of the combat.
     * It then finds the outcome of the fight and returns it. (-2 = loss, +2 = win).
     *
     * @return An integer that represents the outcome of the combat
     */
    @Override
    public int startCombat() {
        Scanner scan = new Scanner(System.in);
        // Initialize a scanner to read the BossCombat text file.
        try {
            Scanner textReader = new Scanner(bossCombatText);
            // Print the beginning of boss fight text from the boss fight text file.
            System.out.println(textReader.nextLine());
            // Call the players stats from the player object. ** Update simple print line. **
            System.out.println("Your current stats are: ");
            pc.getStats();
            // Prompt the user to continue. ** Replace with button press. **
            System.out.println("Enter to continue");
            scan.nextLine();
            // Call the combat loop method.
            combatLoop();
            // Find the final outcome of the combat and return it (-2 = loss, +2 = win).
            setOutcome();
            return outcome;
            //Catch any errors with opening text file.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Begins a while loop to repeat combat rounds in a best-of-three.
    private void combatLoop() {
        // While less than 3 rounds have been played AND neither the player has won two rounds.
        while (rounds < MAX_ROUNDS && roundsWon < 2 && roundsLost < 2) {
            // Inform player of current round and the number of rounds won by each player.
            // ** Update simple print lines. **
            System.out.println("Start new round. Current round: " + (rounds + 1) + ".");
            System.out.println("Rounds won: " + roundsWon + ".");
            System.out.println("Rounds lost: " + roundsLost + ".");
            // Create new starting rolls for the round and invoke the player choice loop.
            startRoll();
            playerChoice = 1;
            super.playerChoose();
            // Get the bosses total for the round and compare it to the players total.
            // Return +1 = player win and -1 = player loss.
            outcome = enemy.compareRoll(playerTotal);
            // Based on the returned outcome value increment the associated counter and call associated method.
            if (outcome > 0) {
                roundsWon += 1;
                ((BossEnemy) enemy).winRound();
            } else {
                roundsLost += 1;
                ((BossEnemy) enemy).loseRound();
            }
            // Increment the number of rounds fought.
            rounds += 1;
            // Prompt the user to continue. ** Replace with button press. **
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue \n");
            scan.nextLine();
        }
    }

    /**
     * Set the outcome value based on the number of rounds won or lost.
     */
    protected void setOutcome() {
        // If rounds won == 2 then the player wins. Set the outcome variable to 2.
        if (roundsWon == 2) {
            outcome = 0;
            outcome += 2;
        }
        // If rounds lost == 2 then the player loses. Set the outcome variable to -2.
        if (roundsLost == 2) {
            outcome = 0;
            outcome -= 2;
        }
        // If neither the boss nor the player has won 2 rounds then do not set the outcome.
    }
}
