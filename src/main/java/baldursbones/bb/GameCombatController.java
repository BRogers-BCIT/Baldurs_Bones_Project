package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

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

    /**
     * Create a new combat for the game combat menu and call the combat starter method.
     *
     * @param locationName a string used to represent the name of the location for the combat.
     */
    public void combatStarter(final String locationName) {
        currentCombat = new Combat(player, enemy, gameCombatGrid);
        currentCombat.combatStarter(locationName);
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
     */
    @FXML
    public void playerHold() {
        currentCombat.finishGame();
    }

    /**
     * Removes the map info menu layout from the location menu and makes the buttons clickable again.
     */
    @FXML
    public void closeGameCombatMenu() {
        // Set location menu buttons to be clickable.
        container.lookup("#locationFightButton").setDisable(false);
        container.lookup("#locationViewStats").setDisable(false);
        container.lookup("#locationViewMap").setDisable(false);
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Game Info menu from the location menu window.
        container.getChildren().remove(gameCombatGrid);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it. Also re-enables Settings Menu.
     *
     * @param parentGrid      The parent element of the character info menu layout
     * @param playerCharacter the Player object to be used in the Combat for this Combat Menu
     * @param currentEnemy    the Enemy object to be used in the Combat for this Combat Menu
     */
    public void getContainerElement(final GridPane parentGrid, final Player playerCharacter, final Enemy currentEnemy) {
        container = parentGrid;
        player = playerCharacter;
        enemy = currentEnemy;
        container.lookup("#openSettingsButton").setDisable(false);
    }

}
