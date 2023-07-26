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

    /**
     * Enables the Parent Scene Buttons and Removes the Map Info Scene from the Parent Scene.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Enable the Parent Scene Buttons.
        container.lookup("#ViewCharacter").setDisable(false);
        container.lookup("#ViewMap").setDisable(false);
        container.lookup("#SettingsButton").setDisable(false);
        // ** Temp Testing Button **
        container.lookup("#endGameTest").setDisable(false);
        // Remove the Map Info Scene from the Parent Scene.
        container.getChildren().remove(mapInfoBox);
    }

    /**
     * Sets the Parent Layout Element for the Map Info Scene and the Map object to use.
     *
     * @param parentGrid The Parent Layout Element the Character Info Scene is displayed within
     * @param gameMaps   The Map object that contains the Player Map array and Map Display methods.
     */
    public void getContainerElements(final GridPane parentGrid, final Map gameMaps) {
        container = parentGrid;
        currentMaps = gameMaps;
    }

    /**
     * Calls the Display Map method from the Map object to update the Map Grid Pane Element.
     * Passes the Grid Pane Element to be updated with the Player Map.
     */
    public void displayMap() {
        currentMaps.displayMap(mapInfoGrid);
    }
}
