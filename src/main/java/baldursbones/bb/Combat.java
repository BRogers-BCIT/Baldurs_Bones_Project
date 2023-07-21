package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.util.Random;


/**
 * Combat Runner.
 * Controller: Game Combat.
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
     * Constant: a ceiling value to stop player from taking the roll action indefinitely.
     * (No use of abilities can result in valid total from this value).
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
     * Variable: The players current total value of the player's rolls.
     */
    protected int playerTotal;

    /**
     * Variable: The value of the players last die rolled by the player.
     */
    protected int lastRoll;

    /**
     * Variable: The value used to record the outcome of the combat.
     * Regular: (-1 = player loses, 1 = player wins).
     * Special: (-2 = lose to boss, 2 = win against boss, 0 = tutorial)
     */
    protected int outcome;

    /**
     * The parent element the combat class is working for.
     */
    protected final GridPane container;

    /**
     * Create a new combat by saving the character and enemy objects and setting the starting values of a fight.
     * Receives the parent element (Game Combat menu) that the class will update the text area of.
     *
     * @param player        The current player character object
     * @param newEnemy      A new enemy object for the player to play against
     * @param parentElement The parent element the game combat menu is displayed in
     */
    public Combat(final Player player, final Enemy newEnemy, final GridPane parentElement) {
        pc = player;
        enemy = newEnemy;
        container = parentElement;
        playerTotal = 0;
        outcome = 0;
    }

    /**
     * Sets a name and description for the combat, as well calling a method to populate the relevant player info.
     * Starts the combat by calling the method to get starting rolls and display combat info.
     *
     * @param combatName        A string used to give a name description to the combat based on its location
     * @param combatDescription A string used to give a description of the fight when starting.
     */
    public void combatStarter(final String combatName, final String combatDescription) {
        // Set the title of the combat based on its location.
        Label fightTitle = (Label) container.lookup("#CombatDescriptionTitle");
        fightTitle.setText(combatName);
        // Give an initial description of the combat when starting based in its location.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText(combatDescription);
        // Update the Labels used to display the available uses of the player's abilities.
        updateAbilities();
        // Start the combat by getting the starting rolls and updating the combat info display.
        runCombat();
    }

    // Update the player abilities labels when loading the page or after using an ability method.
    private void updateAbilities() {
        // Update the "Add" ability display on the combat menu.
        Label playerAddUses = (Label) container.lookup("#CombatInfoAdd");
        playerAddUses.setText("(Ability) Add : " + pc.getAbilityAdd());
        // Update the "Take-Away" ability display on the combat menu.
        Label playerTakeAwayUses = (Label) container.lookup("#CombatInfoTakeAway");
        playerTakeAwayUses.setText("(Ability) Remove : " + pc.getAbilityTakeAway());
        // Update the "Re-Roll" ability display on the combat menu.
        Label playerReRollUses = (Label) container.lookup("#CombatInfoReRoll");
        playerReRollUses.setText("(Ability) Re-Roll : " + pc.getAbilityReRoll());
    }

    /**
     * Get the starting rolls and update the game information display.
     */
    public void runCombat() {
        // Get the starting rolls for the player and call the game choice loop.
        startRoll();
        // Display the value of the last roll by the player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        // Display the starting total value for the player.
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

        // Generate the first starting roll and add it to the total.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Create the start rolls string and add the label name and the first value.
        StringBuilder startRolls = new StringBuilder();
        startRolls.append("Starting Rolls: ");
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the second starting roll and add it to the total.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Add the second roll to the start rolls string.
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the final starting roll, add it to the total, and save it as the last player roll.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Add the last roll to the start rolls string.
        startRolls.append(lastRoll);
        startRolls.append(".");
        playerTotal += lastRoll;

        // Update the starting rolls in the game info display.
        Label playerStartingRolls = (Label) container.lookup("#CombatInfoStartRolls");
        playerStartingRolls.setText(startRolls.toString());
    }


    /**
     * Call the getRoll method from the enemy to get enemy roll.
     * Compare against player roll and set the outcome (true = player wins, false = player loses)
     */
    protected void finishCombat() {
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
        fightDescription.appendText("You rolled: " + lastRoll + ". Your opponent rolled " + enemy.enemyTotal + ".");
        pc.setLastOutcome(outcome);
    }

    /**
     * Generate a new roll value between 1-6.
     * Add it to total, set last roll to its value, and inform player of roll.
     */
    @FXML
    protected void playerRoll() {
        // Create a new roll for the player and add it to their total.
        Random rand = new Random();
        lastRoll = rand.nextInt(1, ROLL_MAX);
        playerTotal += lastRoll;
        // Display the value of the players new roll.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        fightDescription.setText("Rolled: " + lastRoll + ".");
        // Update the game info display after each roll action.
        updateRollInfo();
        // If the new player total exceeds the maximum then update the description and end the combat.
        if (playerTotal >= TOTAL_MAX) {
            fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
            playerTotal = 0;
            finishCombat();
        }
    }

    /**
     * Subtract the last roll value from player total and add a new roll to the player total.
     */
    protected void playerReRoll() {
        // Define the text area to display the result of the action.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        // See if the player can use the ability and update the description if they cannot.
        if (pc.useReRoll(fightDescription)) {
            // Save the roll that is being removed as a temp variable.
            int temp = lastRoll;
            // Remove the last roll from the total.
            playerTotal -= lastRoll;
            // Generate new roll, add it to total, and record it as the last roll.
            Random rand = new Random();
            lastRoll = rand.nextInt(1, ROLL_MAX);
            playerTotal += lastRoll;
            // If the player can use their ability then inform them of the results.
            fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            // Display the removed roll value and the newly added roll value.
            fightDescription.appendText("Removed: " + temp + " and Added: " + lastRoll);
            // If the new player total exceeds the maximum then update the description and end the combat.
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.appendText("\nRolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishCombat();
            }
        }
        // Update the game info and ability displays after each ability action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Add one to the current total value.
     */
    protected void playerAdd() {
        // Define the text area to display the result of the action.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        // See if the player can use the ability and update the description if they cannot.
        if (pc.useAdd(fightDescription)) {
            // If the player can use their ability then add one to the total and update the description.
            playerTotal += 1;
            fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.appendText("Added 1");
            // If the new player total exceeds the maximum then update the description and end the combat.
            if (playerTotal >= TOTAL_MAX) {
                fightDescription.appendText("\nRolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishCombat();
            }
        }
        // Update the game info and ability displays after each ability action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Subtract one from the current total value.
     */
    protected void playerTakeAway() {
        // Define the text area to display the result of the action.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
        // See if the player can use the ability and update the description if they cannot.
        if (pc.useTakeAway(fightDescription)) {
            // If the player can use their ability then remove one from the total and update the description.
            playerTotal -= 1;
            fightDescription.appendText("Subtracted 1");
        }
        // Update the game info and ability displays after each ability action.
        updateRollInfo();
        updateAbilities();
    }

    // Update the Combat information Labels.
    private void updateRollInfo() {
        // Display the value of the last roll by the player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        // Display the starting total value for the player.
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoPlayerTotal");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

}
