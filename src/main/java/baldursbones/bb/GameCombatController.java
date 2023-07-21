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

    // The parent element the game combat menu is displayed in.
    private GridPane container;

    // The layout element for the game combat menu.
    @FXML
    private GridPane gameCombatGrid;

    // Game Object: The current Player object for display and combat class.
    private Player player;

    // Game Object: The current Enemy object for the combat class.
    private Enemy enemy;

    // Game Object: The current Combat object for the Game Combat menu.
    private Combat currentCombat;

    // Variable: Tracks a value to preform the correct action when the text area is clicked.
    private String gameState;

    /**
     * Create a new combat for the game combat menu and call the combat starter method.
     *
     * @param combatTitle        A string used to represent the title of the combat.
     * @param combatDescription A string used to provide a description of the event at the start of combat.
     */
    public void combatStarter(final String combatTitle, final String combatDescription) {
        currentCombat = new Combat(player, enemy, gameCombatGrid);
        currentCombat.combatStarter(combatTitle, combatDescription);
    }

    /**
     * When the player clicks the add roll button, call the combat method for adding a roll.
     */
    @FXML
    public void playerRoll() {
        currentCombat.playerRoll();
    }

    /**
     * When the player clicks the Add ability button, call the combat method for  using an "Add" ability.
     */
    @FXML
    public void playerAdd() {
        currentCombat.playerAdd();
    }

    /**
     * When the player clicks the Take-Away ability button, call the combat method for using a "Take Away" ability.
     */
    @FXML
    public void playerTakeAway() {
        currentCombat.playerTakeAway();
    }

    /**
     * When the player clicks the Re-Roll ability button, call the combat method for  using a "Re-Roll" ability.
     */
    @FXML
    public void playerReRoll() {
        currentCombat.playerReRoll();
    }

    /**
     * When the player clicks the hold total button, call the combat method for ending the game.
     * Also disables the game buttons and sets the menu to wait for user to continue through game results.
     */
    @FXML
    public void playerHold() {
        gameState = currentCombat.finishCombat();
        gameCombatGrid.lookup("#GameActionRoll").setDisable(true);
        gameCombatGrid.lookup("#GameActionHold").setDisable(true);
        gameCombatGrid.lookup("#PlayerAbilityAdd").setDisable(true);
        gameCombatGrid.lookup("#PlayerAbilityTakeAway").setDisable(true);
        gameCombatGrid.lookup("#PlayerAbilityReRoll").setDisable(true);
    }

    /**
     * Allows the player to close the combat and progress by clicking the text area when combat is finished.
     */
    @FXML
    public void endCombat() {
        if (Objects.equals(gameState, "end combat")) {
            closeGameCombatMenu();
        } else if (Objects.equals(gameState, "end round")) {
            gameCombatGrid.lookup("#GameActionRoll").setDisable(false);
            gameCombatGrid.lookup("#GameActionHold").setDisable(false);
            gameCombatGrid.lookup("#PlayerAbilityAdd").setDisable(false);
            gameCombatGrid.lookup("#PlayerAbilityTakeAway").setDisable(false);
            gameCombatGrid.lookup("#PlayerAbilityReRoll").setDisable(false);
        }
    }

    /**
     * Removes the map info menu layout from the Location Menu and makes the buttons clickable again.
     */
    public void closeGameCombatMenu() {
        // Set location menu buttons to be clickable.
        container.lookup("#locationViewStats").setDisable(false);
        container.lookup("#locationViewMap").setDisable(false);
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Game Info menu from the location menu window.
        container.getChildren().remove(gameCombatGrid);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it. Also disables the Settings Menu.
     *
     * @param parentGrid      The parent element of the character info menu layout
     * @param playerCharacter The Player object to be used in the Combat for this Combat Menu
     * @param currentEnemy    The Enemy object to be used in the Combat for this Combat Menu
     */
    public void getContainerElement(final GridPane parentGrid, final Player playerCharacter, final Enemy currentEnemy) {
        container = parentGrid;
        player = playerCharacter;
        enemy = currentEnemy;
        // Set the combat to be in progress.
        gameState = "";
        // Disable the settings button when the menu is opened.
        container.lookup("#openSettingsButton").setDisable(false);
    }

}
