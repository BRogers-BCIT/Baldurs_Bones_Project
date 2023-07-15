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

    // FXML Element: The parent element the map info menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the map info menu.
    @FXML
    private HBox mapInfoBox;

    // FXML Element: A grid pane object used to display a 7x7 view of the player map.
    @FXML
    private GridPane mapInfoGrid;

    // Object: The game maps object with the current game information.
    private Map currentMaps;

    /**
     * Removes the map info menu layout from the location menu and makes the buttons clickable again.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Set location menu buttons to be clickable.
        container.lookup("#locationFightButton").setDisable(false);
        container.lookup("#locationViewStats").setDisable(false);
        container.lookup("#locationViewMap").setDisable(false);
        container.lookup("#endGameTest").setDisable(false);
        container.lookup("#openSettingsButton").setDisable(false);
        // Remove the map info menu from the location menu window.
        container.getChildren().remove(mapInfoBox);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the character info menu layout
     * @param gameMaps The map object that contains the methods to update the map grid pane.
     */
    public void getContainerElements(final GridPane parentGrid, final Map gameMaps) {
        container = parentGrid;
        currentMaps = gameMaps;
    }

    /**
     * Method call used to display / update the map info in the map grid pane.
     */
    public void displayMap() {
        currentMaps.displayMap(mapInfoGrid);
    }
}
