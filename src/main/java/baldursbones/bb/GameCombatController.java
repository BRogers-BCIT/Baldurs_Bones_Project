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
     * @param parentGrid The parent element of the character info menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
        container.lookup("#openSettingsButton").setDisable(false);
    }

}
