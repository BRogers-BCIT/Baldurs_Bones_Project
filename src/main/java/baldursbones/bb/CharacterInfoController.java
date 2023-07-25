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

    // The Layout Element the Character Info menu is displayed within.
    private GridPane container;

    // The Layout Element for the Character Info menu.
    @FXML
    private GridPane characterInfoGrid;

    // Object: The Player object for the current game.
    private Player currentPlayer;

    /**
     * Sets the Parent Layout (Location Menu) Buttons to be enabled and closes the Character Info scene.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Set location menu buttons to be enabled.
        container.lookup("#ViewCharacter").setDisable(false);
        container.lookup("#ViewMap").setDisable(false);
        container.lookup("#endGameTest").setDisable(false);
        container.lookup("#SettingsButton").setDisable(false);
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
