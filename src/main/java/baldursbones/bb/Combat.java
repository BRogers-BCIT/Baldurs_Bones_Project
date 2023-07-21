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
     * Constant: The maximum (exclusive) value for a Roll. Used to generate random values from 1 to 6.
     */
    protected static final int ROLL_MAX = 7;

    /**
     * Constant: The highest valid value in a round of Combat. Exceeding this value will result in the player losing.
     */
    protected static final int TOTAL_BUST = 21;

    /**
     * Constant: A ceiling value to stop player from taking the Add Roll action indefinitely.
     * (No use of Abilities can result in valid Total Value from this number).
     */
    protected static final int TOTAL_MAX = 30;

    /**
     * Object: The Player object representing current the Player character.
     */
    protected final Player player;

    /**
     * Object: The Enemy object representing the current Enemy being fought.
     */
    protected final Enemy enemy;

    /**
     * Variable: The Player's current Total Value of the Player's Rolls.
     */
    protected int playerTotal;

    /**
     * Variable: The value of the Player's last die Rolled by the Player.
     */
    protected int lastRoll;

    /**
     * Variable: The value used to record the outcome of the Combat.
     * Regular: (-1 = Player loses, 1 = Player wins).
     * Special: (-2 = Player loses to Boss, 2 = Player wins against Boss, 0 = Tutorial Combat)
     */
    protected int outcome;

    /**
     * The Parent Layout Element the Combat class is being used by.
     */
    protected final GridPane container;

    /**
     * Creates a new Combat with a Player and Enemy object, also sets the starting values of the Combat.
     * Receives the Parent's Layout Element (Game Combat menu) which contains the Text Area to display Game Text in.
     *
     * @param playerCharacter The current Player Character object
     * @param newEnemy        A new Enemy object for the Combat
     * @param parentElement   The Parent's Layout Element for the Controller using the Combat Class
     */
    public Combat(final Player playerCharacter, final Enemy newEnemy, final GridPane parentElement) {
        player = playerCharacter;
        enemy = newEnemy;
        container = parentElement;
        playerTotal = 0;
        outcome = 0;
    }

    /**
     * Displays a Name and Description for the Combat, as well calling a method to populate the Player info.
     * Starts the Combat by calling the method to get starting Rolls and displays Combat info.
     *
     * @param combatTitle        A string to be displayed as a title for the Combat
     * @param combatDescription A string to be displayed as a description for the Combat
     */
    public void combatStarter(final String combatTitle, final String combatDescription) {
        // Set the Title of the Combat.
        Label fightTitle = (Label) container.lookup("#CombatTitle");
        fightTitle.setText(combatTitle);
        // Set the Description of the Combat.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        fightDescription.setText(combatDescription);
        // Update the Labels used to display the available uses of the Player's Abilities.
        updateAbilities();
        // Start the Combat by getting the starting Rolls and updating the Combat Info display.
        runCombat();
    }

    // Update the Player Abilities Labels when loading the page or after using an Ability.
    private void updateAbilities() {
        // Update the "Re-Roll" Ability display on the Combat menu.
        Label playerReRollUses = (Label) container.lookup("#CombatInfoReRoll");
        playerReRollUses.setText("Re-Roll Last Roll: " + player.getAbilityReRoll());
        // Update the "Add" Ability display on the Combat menu.
        Label playerAddUses = (Label) container.lookup("#CombatInfoAdd");
        playerAddUses.setText("Increase Total: " + player.getAbilityAdd());
        // Update the "Take-Away" Ability display on the Combat menu.
        Label playerTakeAwayUses = (Label) container.lookup("#CombatInfoTakeAway");
        playerTakeAwayUses.setText("Decrease Total: " + player.getAbilityTakeAway());
    }

    /**
     * Get the starting Rolls and update the Game Information display.
     */
    public void runCombat() {
        // Get the starting Rolls for the Player.
        startRoll();
        // Display the value of the last Roll by the Player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        // Display the starting Total value for the Player.
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoPlayerTotal");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

    /**
     * Sets the Player's starting Total Value and display their Rolls in the Game Info display.
     */
    protected void startRoll() {
        // Set the starting Total Value to 0.
        playerTotal = 0;
        Random rand = new Random();

        // Generate the first starting Roll and add it to the Total Value.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Create the Start Rolls string and add the Label name and the first Roll value.
        StringBuilder startRolls = new StringBuilder();
        startRolls.append("Starting Rolls: ");
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the second starting Roll and add it to the Total Value.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Add the second Roll to the Start Rolls string.
        startRolls.append(lastRoll);
        startRolls.append(", ");
        playerTotal += lastRoll;

        // Generate the final starting Roll, add it to the Total Value, and save it as the Player's last Roll.
        lastRoll = rand.nextInt(1, ROLL_MAX);
        // Add the last Roll to the Start Rolls string.
        startRolls.append(lastRoll);
        startRolls.append(".");
        playerTotal += lastRoll;

        // Update the Starting Rolls in the Game Info display.
        Label playerStartingRolls = (Label) container.lookup("#CombatInfoStartRolls");
        playerStartingRolls.setText(startRolls.toString());
    }


    /**
     * Calls the getRoll method from the Enemy to get Enemy Total Value.
     * Compares the Enemy Total Value and the Player Total Value.
     *
     * @return A string that indicates to the Combat Controller that Combat is finished.
     */
    protected String finishCombat() {
        // Empty any previous text from the Combat Description display.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        fightDescription.setText("");
        // If the Player ended with a Total Value greater than 21, set their Total Value to 0.
        if (playerTotal > TOTAL_BUST) {
            fightDescription.appendText("Rolled: " + lastRoll + ". Total is over 21, you go bust.");
            playerTotal = 0;
        }
        // Get the outcome of the Combat and set the Outcome Value of the Player object.
        outcome = enemy.compareTotal(playerTotal);
        player.setLastOutcome(outcome);
        // Display the Total Values comparison to the Player.
        fightDescription.appendText("You rolled: " + playerTotal
                + ". Your opponent rolled " + enemy.enemyTotal + ".");
        // Return a string to indicate Combat has finished.
        return "end combat";
    }

    /**
     * Generate a new Roll value between 1-6 and add it to the Total Value.
     * Also sets the last Roll to the value of the new Roll and display the new Roll in the Combat Description.
     */
    @FXML
    protected void playerRoll() {
        // Create a new Roll Value for the Player and add it to their Total Value.
        Random rand = new Random();
        lastRoll = rand.nextInt(1, ROLL_MAX);
        playerTotal += lastRoll;
        // Display the Value of the new Roll generated for the Player in the Combat Description.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        fightDescription.setText("Rolled: " + lastRoll + ". \n");
        // Update the Game Info display after each Roll action.
        updateRollInfo();
        // If the new Player Total Value exceeds the maximum accepted value.
        if (playerTotal >= TOTAL_MAX) {
            // Update the Combat Description, set the Player Total Value to 0, and end the Combat.
            fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
            playerTotal = 0;
            finishCombat();
        }
    }

    /**
     * Subtract the last Roll value from Player Total Value and add a new Roll value to the Player Total Value.
     */
    protected void playerReRoll() {
        // Define the Text Area for the Combat Description.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        // Check if the Player can use the Ability and update the Combat Description if they cannot.
        if (player.useReRoll(fightDescription)) {
            // Save the Roll that is being removed as a temp variable.
            int temp = lastRoll;
            // Remove the last Roll from the Total Value.
            playerTotal -= lastRoll;
            // Generate new Roll, add it to Total Value, and record it as the last Roll.
            Random rand = new Random();
            lastRoll = rand.nextInt(1, ROLL_MAX);
            playerTotal += lastRoll;
            // Display the removed Roll value and the newly added Roll value in the Combat Description.
            fightDescription.appendText("Removed: " + temp + " and Added: " + lastRoll + "\n");
            // If the new Player Total Value exceeds the maximum accepted value.
            if (playerTotal >= TOTAL_MAX) {
                // Update the Combat Description, set the Player Total Value to 0, and end the Combat.
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishCombat();
            }
        }
        // Update the Game Info and Ability Info displays after each Ability action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Add one to the current Total Value.
     */
    protected void playerAdd() {
        // Define the Text Area for the Combat Description.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        // Check if the Player can use the Ability and update the Combat Description if they cannot.
        if (player.useAdd(fightDescription)) {
            // If the Player can use their Ability then add one to the Total Value and update the description.
            playerTotal += 1;
            fightDescription = (TextArea) container.lookup("#CombatDescriptionTextBox");
            fightDescription.appendText("Increased Total By 1 \n");
            // If the new Player Total Value exceeds the maximum accepted value.
            if (playerTotal >= TOTAL_MAX) {
                // Update the Combat Description, set the Player Total Value to 0, and end the Combat.
                fightDescription.setText("Rolled: " + lastRoll + ". Total is over max value, game ended.");
                playerTotal = 0;
                finishCombat();
            }
        }
        // Update the Game Info and Ability Info displays after each Ability action.
        updateRollInfo();
        updateAbilities();
    }

    /**
     * Subtract one from the current Total Value.
     */
    protected void playerTakeAway() {
        // Define the Text Area for the Combat Description.
        TextArea fightDescription = (TextArea) container.lookup("#CombatDescription");
        // Check if the Player can use the Ability and update the Combat Description if they cannot.
        if (player.useTakeAway(fightDescription)) {
            // If the Player can use their Ability then remove one from the Total Value and update the description.
            playerTotal -= 1;
            fightDescription.appendText("Decreased Total By 1");
        }
        // Update the Game Info and Ability Info displays after each Ability action.
        updateRollInfo();
        updateAbilities();
    }

    // Update the Combat Information Labels.
    private void updateRollInfo() {
        // Display the value of the last Roll by the Player.
        Label playerStartTotal = (Label) container.lookup("#CombatInfoLastRoll");
        playerStartTotal.setText("Last Roll: " + lastRoll);
        // Display the starting Total Value for the Player.
        Label playerStartLastRoll = (Label) container.lookup("#CombatInfoPlayerTotal");
        playerStartLastRoll.setText("Current Total: " + playerTotal);
    }

}
