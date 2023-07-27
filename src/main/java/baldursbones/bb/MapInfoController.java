package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


/**
 * Map Info Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MapInfoController {

    // FXML Element: The Parent Layout Element the Map Info Scene is displayed within.
    private GridPane container;

    // FXML Element: The Layout Element for the Map Info Scene.
    @FXML
    private HBox mapInfoBox;

    // FXML Element: The Grid Pane object used to display Game Map.
    @FXML
    private GridPane mapInfoGrid;

    // Object: The Map object that contains the Map Display method.
    private Map currentMaps;

    // Variable: A boolean value used to track if Music is enabled.
    private boolean enableMusicState;

    // Variable: A boolean value used to track if SFX are enabled.
    private boolean enableSFXState;

    /**
     * Enables the Parent Scene Buttons and Removes the Map Info Scene from the Parent Scene.
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
        // Remove the Map Info Scene from the Parent Scene.
        container.getChildren().remove(mapInfoBox);
    }

    /**
     * Sets the Parent Layout Element for the Map Info Scene and the Map object to use.
     *
     * @param parentGrid  The Parent Layout Element the Character Info Scene is displayed within
     * @param gameMaps    The Map object that contains the Player Map array and Map Display methods.
     * @param enableMusic A boolean value that indicates if Music is currently enabled
     * @param enableSFX   A boolean value that indicates if SFX are currently enabled
     */
    public void setSceneVariables(final GridPane parentGrid, final Map gameMaps,
                                  final boolean enableMusic, final boolean enableSFX) {
        container = parentGrid;
        currentMaps = gameMaps;
        enableMusicState = enableMusic;
        enableSFXState = enableSFX;
    }

    /**
     * Calls the Display Map method from the Map object to update the Map Grid Pane Element.
     * Passes the Grid Pane Element to be updated with the Player Map.
     */
    public void displayMap() {
        currentMaps.displayMap(mapInfoGrid);
    }
}
