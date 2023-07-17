package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Random;


/**
 * Combat.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Combat {

    /**
     * Constant: The maximum (exclusive) value for a roll. Limit rolls to 1 - 6.
     */
    protected static final int ROLL_MAX = 7;

    /**
     * Constant: The highest valid value in a round of combat. Exceeding Total_Bust will result in the player losing.
     */
    protected static final int TOTAL_BUST = 21;

    /**
     * Constant: a ceiling value to stop player from rolling forever (No use of abilities can result in valid total).
     */
    protected static final int TOTAL_MAX = 30;

    /**
     * Object: The player object representing current the player character.
     */
    protected final Player pc;

    /**
     * Object: The enemy object representing the current enemy being fought.
     */
    protected final Enemy enemy;

    /**
     * Variable: The players current roll total.
     */
    protected int playerTotal;

    /**
     * Variable: The value of the players last die rolled by the player.
     */
    protected int lastRoll;

    /**
     * Variable: The value that records what the player choose to do.
     */
    protected int playerChoice;

    /**
     * Variable: The value used to record the outcome of the combat (-1 = player loses, 1 = player wins).
     */
    protected int outcome;

    // The parent element the game combat menu is displayed in.
    private final GridPane container;

    /**
     * Create a new combat and sets initial values.
     * Takes a player character object and a new enemy object.
     *
     * @param player        the current player character object
     * @param newEnemy      a new enemy object for the player to play against\
     * @param parentElement The parent element the game combat menu is displayed in.
     */
    public Combat(final Player player, final Enemy newEnemy, final GridPane parentElement) {
        pc = player;
        enemy = newEnemy;
        playerTotal = 0;
        playerChoice = 1;
        outcome = 0;
        container = parentElement;
    }

    /**
     * Sets a name and description for the combat as well as populating the relevant player info.
     *
     * @param combatName a string used to give a name description to the combat based on its location
     */
    public void combatStarter(final String combatName) {
        Label fightTitle = (Label) container.lookup("#CombatDescriptionTitle");
        fightTitle.setText(combatName);
        Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("Start Combat");
        updateAbilities();
        runCombat();
    }

    // Update the player abilities labels when loading the page or after a use.
    private void updateAbilities() {
        Label playerAddUses = (Label) container.lookup("#CombatInfoAdd");
        playerAddUses.setText("(Ability) Add Uses : " + pc.abilityAdd);
        Label playerTakeAwayUses = (Label) container.lookup("#CombatInfoTakeAway");
        playerTakeAwayUses.setText("(Ability) Take Away Uses : " + pc.abilityTakeAway);
        Label playerReRollUses = (Label) container.lookup("#CombatInfoReRoll");
        playerReRollUses.setText("(Ability) Re-Roll Uses : " + pc.abilityReRoll);
    }

    /**
     * Print the player stats, get the starting rolls and call the game choice loop.
     * When the loop ends call the combat end method and return the outcome.
     */
    public void runCombat() {
        // Get the starting rolls for the player and call the game choice loop.
        startRoll();
        // Print the starting total and last roll of the player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoStartRolls");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoStartRolls");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

    /**
     * Sets the players roll to its starting value and informs them of their rolls.
     */
    protected void startRoll() {
        // Set the starting total to 0 and print the players starting rolls.
        playerTotal = 0;
        System.out.println("Starting rolls: ");
        Random rand = new Random();

        // Generate the first starting roll, print it and add it to the total.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        StringBuilder startRolls = new StringBuilder();
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the second starting roll, print it and add it to the total.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the final starting roll, print it, add it to the total, and save it as the last player roll.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        startRolls.append(lastRoll);
        startRolls.append(".");
        playerTotal += lastRoll;

        // Print the starting rolls string to the Game Combat menu.
        Label playerStartingRolls = (Label) container.lookup("#CombatInfoStartRolls");
        playerStartingRolls.setText(startRolls.toString());
    }


    /**
     * Call the getRoll method from the enemy to get enemy roll.
     * Compare against player roll and set the outcome (true = player wins, false = player loses)
     */
    protected void finishGame() {
        if (playerTotal >= TOTAL_BUST) {
            Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Rolled: " + lastRoll + ". Total is over 21, you go bust.");
            playerTotal = 0;
        }
        outcome = enemy.compareTotal(playerTotal);
        pc.setLastOutcome(outcome);
    }

    /**
     * Generate a new roll value between 1-6.
     * Add it to total, set last roll to its value and inform player of roll.
     */
    @FXML
    protected void playerRoll() {
        Random rand = new Random();
        lastRoll = rand.nextInt(1, ROLL_MAX);
        playerTotal += lastRoll;
        Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("Rolled: " + lastRoll + ".");
        if (playerTotal >= TOTAL_MAX) {
            fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
            playerTotal = 0;
            finishGame();
        }
    }

    /**
     * Subtract the last roll value from player total and add a new roll to the player total.
     */
    protected void playerReRoll() {
        // Call use Re-Roll method to check if the ability can be used.
        if (pc.useReRoll()) {
            // Save the roll being removed
            int temp = lastRoll;
            // Remove the last roll from the total.
            playerTotal -= lastRoll;
            // Generate new roll, add it to total, and record it as the last roll.
            Random rand = new Random();
            lastRoll = rand.nextInt(1, ROLL_MAX);
            playerTotal += lastRoll;
            Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Removed: " + temp + " and Added: " + lastRoll);
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishGame();
            }
        }
        updateAbilities();
    }

    /**
     * Add one to the current total value.
     */
    protected void playerAdd() {
        if (pc.useAdd()) {
            playerTotal += 1;
            Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Added 1");
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishGame();
            }
        }
        updateAbilities();
    }

    /**
     * Subtract one from the current total value.
     */
    protected void playerTakeAway() {
        if (pc.useTakeAway()) {
            playerTotal -= 1;
            Label fightDescription = (Label) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Subtracted 1");
        }
        updateAbilities();
    }

}
