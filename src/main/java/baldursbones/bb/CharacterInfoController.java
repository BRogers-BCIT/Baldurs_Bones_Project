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

    // Variable: A boolean value used to track if Music is enabled.
    private boolean enableMusicState;

    // Variable: A boolean value used to track if SFX are enabled.
    private boolean enableSFXState;

    /**
     * Enables the Parent Scene Buttons and Removes the Character Info Scene from the Parent Scene.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Enable the Parent Scene Buttons.
        container.lookup("#ViewCharacter").setDisable(false);
        container.lookup("#ViewMap").setDisable(false);
        container.lookup("#SettingsButton").setDisable(false);
        // Enable the Parent Scene Text Area.
        container.lookup("#GameDescription").setDisable(false);
        // ** Temp Testing Button **
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Character Info Scene from the Parent Scene.
        container.getChildren().remove(characterInfoGrid);
    }

    /**
     * Sets the Parent Layout Element of the Character Info Scene and the Player object to read info from.
     *
     * @param parentGrid      The Parent Layout Element of the Character Info Scene
     * @param playerCharacter The Player object that is used to update the Player Info.
     * @param enableMusic     A boolean value that indicates if Music is currently enabled
     * @param enableSFX       A boolean value that indicates if SFX are currently enabled
     */
    public void setSceneVariables(final GridPane parentGrid, final Player playerCharacter,
                                  final boolean enableMusic, final boolean enableSFX) {
        container = parentGrid;
        currentPlayer = playerCharacter;
        enableMusicState = enableMusic;
        enableSFXState = enableSFX;
    }

    /**
     * Calls the display Character Info method from the Player object.
     */
    public void displayCharacter() {
        currentPlayer.displayStats(characterInfoGrid);
    }
}
