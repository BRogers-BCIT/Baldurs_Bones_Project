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

    /**
     * Create a new combat for the game combat menu and call the combat starter method.
     *
     * @param locationName a string used to represent the name of the location for the combat.
     * @return an integer used to represent the outcome of the combat.
     */
    public int combatStarter(final String locationName) {
        Combat combat = new Combat(player, enemy, gameCombatGrid);
        return combat.combatStarter(locationName);
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
