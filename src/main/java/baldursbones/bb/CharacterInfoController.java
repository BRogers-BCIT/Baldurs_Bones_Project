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

    // The Layout Element the Character Info Scene is displayed within.
    private GridPane container;

    // The Layout Element for the Character Info Scene.
    @FXML
    private GridPane characterInfoGrid;

    // Object: The Player object for the current game.
    private Player currentPlayer;

    /**
     * Sets the Parent Layout (Location Scene) Buttons to be enabled and closes the Character Info Scene.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Set Location Scene buttons to be enabled.
        container.lookup("#ViewCharacter").setDisable(false);
        container.lookup("#ViewMap").setDisable(false);
        container.lookup("#SettingsButton").setDisable(false);
        // ** Temp Testing Button **
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Character Info Scene from the Location Scene.
        container.getChildren().remove(characterInfoGrid);
    }

    /**
     * Sets the Parent Layout Element to display inside and the player object to read display info from.
     *
     * @param parentGrid The Parent Layout Element of the Character Info Scene
     * @param playerCharacter The Player object that is used to update the Player Info.
     */
    public void getContainerElements(final GridPane parentGrid, final Player playerCharacter) {
        container = parentGrid;
        currentPlayer = playerCharacter;
    }

    /**
     * Calls the display Character Info method from the Player object.
     */
    public void displayCharacter() {
        currentPlayer.displayStats(characterInfoGrid);
    }
}
