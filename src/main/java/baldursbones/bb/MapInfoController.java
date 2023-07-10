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

    // The parent element the map info menu is displayed in.
    private GridPane container;

    // The layout element for the map info menu.
    @FXML
    private HBox mapInfoBox;

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
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

}
