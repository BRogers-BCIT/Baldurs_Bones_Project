package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
        // Set the title of the combat based on its location.
        Label fightTitle = (Label) container.lookup("#CombatDescriptionTitle");
        fightTitle.setText(combatName);
        // Give a description of the combat based in its location.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("Start Combat");
        // Update the display of the player's abilities.
        updateAbilities();
        // Start the combat by getting the starting rolls and displaying the roll info to the user.
        runCombat();
    }

    // Update the player abilities labels when loading the page or after a use.
    private void updateAbilities() {
        // Update the Add ability tracker on the combat menu.
        Label playerAddUses = (Label) container.lookup("#CombatInfoAdd");
        playerAddUses.setText("(Ability) Add : " + pc.abilityAdd);
        // Update the Take Away ability tracker on the combat menu.
        Label playerTakeAwayUses = (Label) container.lookup("#CombatInfoTakeAway");
        playerTakeAwayUses.setText("(Ability) Remove : " + pc.abilityTakeAway);
        // Update the Re-Roll ability tracker on the combat menu.
        Label playerReRollUses = (Label) container.lookup("#CombatInfoReRoll");
        playerReRollUses.setText("(Ability) Re-Roll : " + pc.abilityReRoll);
    }

    /**
     * Print the player stats, get the starting rolls and call the game choice loop.
     * When the loop ends call the combat end method and return the outcome.
     */
    public void runCombat() {
        // Get the starting rolls for the player and call the game choice loop.
        startRoll();
        // Print the starting total value and last roll value for the player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoPlayerTotal");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

    /**
     * Sets the players roll to its starting value and informs them of their rolls.
     */
    protected void startRoll() {
        // Set the starting total to 0 and print the players starting rolls.
        playerTotal = 0;
        Random rand = new Random();

        // Generate the first starting roll, print it and add it to the total.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        StringBuilder startRolls = new StringBuilder();
        startRolls.append("Starting Rolls: ");
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
        // If the player ends with a total greater than 21, assign them to auto fail.
        if (playerTotal >= TOTAL_BUST) {
            TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Rolled: " + lastRoll + ". Total is over 21, you go bust.");
            playerTotal = 0;
        }
        // Get the outcome of the combat and pass the outcome value to the player.
        outcome = enemy.compareTotal(playerTotal);
        pc.setLastOutcome(outcome);
    }

    /**
     * Generate a new roll value between 1-6.
     * Add it to total, set last roll to its value and inform player of roll.
     */
    @FXML
    protected void playerRoll() {
        // Create a new roll for the player and add it to their total.
        Random rand = new Random();
        lastRoll = rand.nextInt(1, ROLL_MAX);
        playerTotal += lastRoll;
        // Inform the player about their new roll.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("Rolled: " + lastRoll + ".");
        // Update the game info and abilities after each action.
        updateRollInfo();
        updateAbilities();
        // If the player total is over the max then set them to 0 and end the game.
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
            // If the player can use their ability then inform them of the results.
            TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Removed: " + temp + " and Added: " + lastRoll);
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishGame();
            }
        }
        // Update the game info and abilities after each action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Add one to the current total value.
     */
    protected void playerAdd() {
        // If the player can use their ability then use it and add one to the total.
        if (pc.useAdd()) {
            playerTotal += 1;
            TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Added 1");
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishGame();
            }
        }
        // Update the game info and abilities after each action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Subtract one from the current total value.
     */
    protected void playerTakeAway() {
        // If the player can use their ability then use it and remove one from the total.
        if (pc.useTakeAway()) {
            playerTotal -= 1;
            TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.setText("Subtracted 1");
        }
        // Update the game info and abilities after each action.
        updateRollInfo();
        updateAbilities();
    }

    private void updateRollInfo() {
        // Update the current total value and last roll value for the player on screen.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoPlayerTotal");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

}
