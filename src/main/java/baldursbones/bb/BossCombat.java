package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * Boss Fight Combat Extension.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class BossCombat extends Combat {

    // Constant: Maximum number of rounds of combat against boss.
    private static final int MAX_ROUNDS = 3;

    // Variable: Record the number of rounds the Player has won against the Boss.
    private int roundsWon;

    // Variable: Record the number of rounds the Player has lost to the Boss.
    private int roundsLost;

    // Counter: Records the current round of combat. Min = 1, Max = Max_Rounds.
    private int rounds;

    // Variable: Boss Enemy object used to call Boss Enemy methods.
    private final BossEnemy bossEnemy;

    /**
     * Create a new combat by saving the character and enemy objects and setting the starting values of a fight.
     * Receives the parent element (Game Combat menu) that the class will update the text area of.
     *
     * @param player        The current player character object
     * @param newEnemy      A new enemy object for the player to play against
     * @param parentElement The parent element the game combat menu is displayed in
     */
    public BossCombat(final Player player, final BossEnemy newEnemy, final GridPane parentElement) {
        super(player, newEnemy, parentElement);
        bossEnemy = newEnemy;
    }

    /**
     * Call the getRoll method from the enemy to get enemy roll.
     * Compare against player roll and set the outcome (true = player wins, false = player loses)
     * @return A string value to let the Combat Controller know if a round finished or the Combat finished.
     */
    @Override
    protected String finishCombat() {
        rounds += 1;
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("");
        // If the player ends with a total greater than 21, assign them to auto fail.
        if (playerTotal > TOTAL_BUST) {
            fightDescription.appendText("Rolled: " + lastRoll + ". Total is over 21, you go bust.");
            playerTotal = 0;
        }
        // Get the outcome of the combat and pass the outcome value to the player.
        outcome = enemy.compareTotal(playerTotal);
        // Display the end roll comparison to the player.
        fightDescription.appendText("\nYou rolled: " + lastRoll + ". Your opponent rolled " + enemy.enemyTotal + ".");
        if (outcome == 1) {
            bossEnemy.winRound();
            roundsWon += 1;
            super.combatStarter("Boss Combat", "Round: " + rounds);
        } else if (outcome == -1) {
            bossEnemy.loseRound();
            roundsLost += 1;
            super.combatStarter("Boss Combat", "Round: " + rounds);
        }
        if (rounds < MAX_ROUNDS && roundsLost < 2 && roundsWon < 2) {
            setOutcome();
            return "end combat";
        }
        return "end round";
    }

    /**
     * Set the outcome value based on the number of rounds won or lost.
     */
    protected void setOutcome() {
        // If rounds won == 2 then the player wins. Set the outcome variable to 2.
        if (roundsWon == 2) {
            pc.setLastOutcome(2);
            enemy.win();
        }
        // If rounds lost == 2 then the player loses. Set the outcome variable to -2.
        if (roundsLost == 2) {
            pc.setLastOutcome(-2);
            enemy.lose();
        }
    }
}
