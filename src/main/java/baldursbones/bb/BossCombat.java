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

    // Constant: The Player Outcome value for winning a Boss Combat.
    private static final int WIN = 2;

    // Constant: The Player Outcome value for losing a Boss Combat.
    private static final int LOSE = -2;

    // Variable: Records the number of Rounds the Player has won against the Boss.
    private int roundsWon;

    // Variable: Records the number of Rounds the Player has lost to the Boss.
    private int roundsLost;

    // Counter: Records the current Round of Combat. Used to display current Round to the User.
    private int rounds;

    // Variable: Boss Enemy object used to call Boss Enemy methods.
    private final BossEnemy bossEnemy;

    /**
     * Creates a new Combat with a Player and (Boss) Enemy object, also sets the starting values of the Combat.
     * Receives the Parent's Layout Element (Game Combat menu) which contains the Text Area to display Game Text in.
     *
     * @param player        The current Player Character object
     * @param newEnemy      A new (Boss) Enemy object for the Combat
     * @param parentElement The Parent's Layout Element for the Controller using the Boss Combat Class
     */
    public BossCombat(final Player player, final BossEnemy newEnemy, final GridPane parentElement) {
        super(player, newEnemy, parentElement);
        // Save the Enemy object to invoke Boss Enemy specific methods.
        bossEnemy = newEnemy;
    }

    /**
     * Calls the getRoll method from the (Boss) Enemy to get Total Value for the Round of Combat.
     * Compares against Player Total Value to the Boss Total Value and sets the Outcome value of the Player Object.
     * (2 = Player wins, -2 = Player loses)
     *
     * @return A String value that informs the Combat Controller if a Round finished or the Combat finished.
     */
    @Override
    protected String finishCombat() {
        // Increment the number of Rounds and empty the Text Area for new Game Text.
        rounds += 1;
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        fightDescription.setText("");
        // If the Player ends with a total greater than 21, assign them to auto fail.
        if (playerTotal > TOTAL_BUST) {
            fightDescription.appendText("You rolled a total of : " + lastRoll + ". Total is over 21, you go bust.\n");
            playerTotal = 0;
        }
        // Get the Outcome of the Combat Round to be used to find thr Round winner.
        outcome = enemy.compareTotal(playerTotal);
        // Display the end Total Values for the Player and the Enemy.
        fightDescription.appendText("You rolled: " + lastRoll + " & Your opponent rolled: " + enemy.enemyTotal + ".");
        // If the Outcome equals a 1, then the Player has won.
        if (outcome == 1) {
            // Display the Boss Enemy win Round Game Text and increment the Player Rounds won counter.
            bossEnemy.winRound(container);
            roundsWon += 1;
            // Start a new Combat and update the Combat Description to display the number of Rounds played.
            super.combatStarter("Boss Combat", "Round: " + rounds);
        } else if (outcome == -1) {
            // Display the Boss Enemy lose Round Game Text and increment the Player Rounds lost counter.
            bossEnemy.loseRound(container);
            roundsLost += 1;
            // Start a new Combat and update the Combat Description to display the number of Rounds played.
            super.combatStarter("Boss Combat", "Round: " + rounds);
        }
        // If the Player has either won or lost 2 Rounds then the Combat is over.
        if (roundsLost < 2 && roundsWon < 2) {
            // Set the Player Outcome value and return that Boss Combat is finished.
            setOutcome();
            return "end combat";
        }
        // If the Player has neither won nor lost 2 Rounds, return that a Boss Combat Round was finished.
        return "end round";
    }

    /**
     * Set the Player Outcome value if the Player has either won or lost 2 Rounds.
     */
    protected void setOutcome() {
        // If the Player has won 2 Rounds then the Player wins. Set the Player Outcome value to 2.
        if (roundsWon == 2) {
            player.setLastOutcome(WIN);
            enemy.win();
        }
        // If the Player has lost 2 Rounds then the Player loses. Set the Player Outcome value to -2.
        if (roundsLost == 2) {
            player.setLastOutcome(-LOSE);
            enemy.lose();
        }
    }
}
