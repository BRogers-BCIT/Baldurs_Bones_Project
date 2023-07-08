package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * New Game Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class NewGameController {

    // The parent element the new game menu is displayed in.
    private GridPane container;

    // The layout element for the new game menu.
    @FXML
    private HBox newGameMenu;

    /** Removes the new game menu layout from the main menu and makes the buttons clickable again.
     */
    @FXML
    public void closeNewGameMenu() {
        // Set main menu buttons to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        container.lookup("#newGameButton").setDisable(false);
        container.lookup("#savedGamesButton").setDisable(false);
        container.lookup("#gameInfoButton").setDisable(false);
        // Remove the new game menu from the main menu window.
        container.getChildren().remove(newGameMenu);
    }

    /** Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout.
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
