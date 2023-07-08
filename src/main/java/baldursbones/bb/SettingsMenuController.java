package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Settings Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SettingsMenuController {

    // The parent element the settings menu is displayed in.
    private GridPane container;

    // The layout element for the settings menu.
    @FXML
    private HBox settingsMenu;

    /** Removes the settings menu layout from the main menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSettings() {
        // Set main menu buttons to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        container.lookup("#newGameButton").setDisable(false);
        container.lookup("#savedGamesButton").setDisable(false);
        container.lookup("#gameInfoButton").setDisable(false);
        // Remove the settings menu from the main menu window.
        container.getChildren().remove(settingsMenu);
    }

    /** Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout.
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
