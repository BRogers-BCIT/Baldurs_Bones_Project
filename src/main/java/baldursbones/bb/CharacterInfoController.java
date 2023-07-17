package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * Character Info Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class CharacterInfoController {

    // The parent element the character info menu is displayed in.
    private GridPane container;

    // The layout element for the character info menu.
    @FXML
    private GridPane characterInfoGrid;

    // Object: The game player object with the current game information.
    private Player currentPlayer;

    /**
     * Removes the character info menu layout from the location menu and makes the buttons clickable again.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Set location menu buttons to be clickable.
        container.lookup("#locationFightButton").setDisable(false);
        container.lookup("#locationViewStats").setDisable(false);
        container.lookup("#locationViewMap").setDisable(false);
        container.lookup("#endGameTest").setDisable(false);
        container.lookup("#openSettingsButton").setDisable(false);
        // Remove the character info menu from the location menu window.
        container.getChildren().remove(characterInfoGrid);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the character info menu layout
     * @param playerCharacter The player object that contains the methods to update the player info menu.
     */
    public void getContainerElements(final GridPane parentGrid, final Player playerCharacter) {
        container = parentGrid;
        currentPlayer = playerCharacter;
    }

    /**
     * Method call used to display / update the player info in the player info menu.
     */
    public void displayCharacter() {
        currentPlayer.displayStats(characterInfoGrid);
    }
}
