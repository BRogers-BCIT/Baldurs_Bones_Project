package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Game Info Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class GameInfoController {

    // The parent element the game info menu is displayed in.
    private GridPane container;

    // The layout element for the game info menu.
    @FXML
    private HBox gameInfoMenu;

    /**
     * Removes the game info menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeGameInfoMenu() {
        container.lookup("#openSettingsButton").setDisable(false);
        // If the current container is the main menu, enable main menu buttons.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
        } else {
            // Set location menu buttons to be clickable.
            container.lookup("#locationFightButton").setDisable(false);
            container.lookup("#locationViewStats").setDisable(false);
            container.lookup("#locationViewMap").setDisable(false);
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the game info menu from the main menu window.
        container.getChildren().remove(gameInfoMenu);
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }
}
