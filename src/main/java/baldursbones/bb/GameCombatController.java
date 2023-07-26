package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.Objects;

/**
 * Game Combat Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class GameCombatController {

    // The Parent Layout Element the Game Combat Scene is displayed in.
    private GridPane container;

    // The Layout Element for the Game Combat Scene.
    @FXML
    private GridPane gameCombatGrid;

    // Game Object: The current Player object for the Character Info and Combat Classes.
    private Player player;

    // Game Object: The current Enemy object for the Combat class.
    private Enemy enemy;

    // Game Object: The current Combat object for the Game Combat Controller.
    private Combat currentCombat;

    // Variable: A value hold the game in a specific State while waiting for user to confirm after a game action.
    private String gameState;

    /**
     * Enables the Parent Scene Buttons and Removes the Game Combat Scene from the Parent Scene.
     */
    public void closeGameCombatMenu() {
        // Enable the Parent Scene Buttons.
        container.lookup("#ViewCharacter").setDisable(false);
        container.lookup("#ViewMap").setDisable(false);
        container.lookup("#SettingsButton").setDisable(false);
        // ** Temp Testing Button **
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Game Info Scene from the Parent Scene.
        container.getChildren().remove(gameCombatGrid);
    }

    /**
     * Creates a new Combat object for the Game Combat Scene and calls the Combat Starter method.
     *
     * @param combatTitle       A string representing the title of the combat.
     * @param combatDescription A string representing a description for the start of combat.
     */
    public void combatStarter(final String combatTitle, final String combatDescription) {
        currentCombat = new Combat(player, enemy, gameCombatGrid);
        currentCombat.combatStarter(combatTitle, combatDescription);
    }

    /**
     * Calls the Combat Roll method when the Player Roll Button is clicked.
     */
    @FXML
    public void playerRoll() {
        currentCombat.playerRoll();
    }

    /**
     * Calls the Combat "Re-Roll" Ability method when the Player Roll Button is clicked.
     */
    @FXML
    public void playerReRoll() {
        currentCombat.playerReRoll();
    }

    /**
     * Calls the Combat "Add" Ability method when the Player Roll Button is clicked.
     */
    @FXML
    public void playerAdd() {
        currentCombat.playerAdd();
    }

    /**
     * Calls the Combat "Take-Away" Ability method when the Player Roll Button is clicked.
     */
    @FXML
    public void playerTakeAway() {
        currentCombat.playerTakeAway();
    }

    /**
     * When the player clicks the Hold Button, call the Combat method for ending a round of Combat.
     * A Game State will be returned to inform the Controller what to do once the Player continues.
     */
    @FXML
    public void playerHold() {
        // Set the Game State for the End of a Combat Round.
        gameState = currentCombat.finishCombat();
        // Disable Buttons while game state is un-resolved.
        gameCombatGrid.lookup("#RollButton").setDisable(true);
        gameCombatGrid.lookup("#HoldButton").setDisable(true);
        gameCombatGrid.lookup("#AddButton").setDisable(true);
        gameCombatGrid.lookup("#TakeAwayButton").setDisable(true);
        gameCombatGrid.lookup("#ReRollButton").setDisable(true);
    }

    /**
     * Progresses a Combat after a round has finished. Proceeds based on the current Game State.
     * End Combat: Close the Game Combat Scene.
     * End Round: Re-Enable the Buttons, a new Round against the Boss has started.
     */
    @FXML
    public void endCombat() {
        if (Objects.equals(gameState, "end combat")) {
            // If: The Game State is "end combat", close the Game Combat Scene.
            closeGameCombatMenu();
        } else if (Objects.equals(gameState, "end round")) {
            // Else If: The Game State is "end round", re-enable the Combat Buttons.
            gameCombatGrid.lookup("#RollButton").setDisable(false);
            gameCombatGrid.lookup("#HoldButton").setDisable(false);
            gameCombatGrid.lookup("#AddButton").setDisable(false);
            gameCombatGrid.lookup("#TakeAwayButton").setDisable(false);
            gameCombatGrid.lookup("#ReRollButton").setDisable(false);
            // Un-set Game State.
            gameState = "";
        }
    }

    /**
     * Sets the Parent Layout Element of the Game Combat Scene. Also sets the Player and Enemy objects.
     *
     * @param parentGrid      The Parent Layout Element of the character info Scene layout
     * @param playerCharacter The Player object to be used in the Combat for this Combat Menu
     * @param currentEnemy    The Enemy object to be used in the Combat for this Combat Menu
     */
    public void setSceneVariables(final GridPane parentGrid, final Player playerCharacter, final Enemy currentEnemy) {
        container = parentGrid;
        player = playerCharacter;
        enemy = currentEnemy;
        // Set the default Game State.
        gameState = "";
    }

}
