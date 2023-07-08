package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MainMenuController implements Initializable {

    // Save an FXML file to be loaded into the scene.
    private Parent root;

    // The layout object for the main menu that new menus are loaded into.
    @FXML
    private GridPane mainMenuGrid;

    // Settings button that calls the open settings method.
    @FXML
    private Button openSettingsButton;

    // Settings button that calls the open new game method.
    @FXML
    private Button newGameButton;

    // Settings button that calls the open saved games method.
    @FXML
    private Button savedGamesButton;

    // Settings button that calls the open game info method.
    @FXML
    private Button gameInfoButton;


    /** Load the settings menu document, display it in the center of the screen, and disable all main menu buttons.
     *
     * @throws IOException if the fxml file to load does not exist.
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the settings menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /** Load the new game menu document, display it in the center of the screen, and disable all main menu buttons.
     *
     * @throws IOException if the fxml file to load does not exist.
     */
    @FXML
    public void openNewGameMenu() throws IOException {
        // Load the new game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGameMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        NewGameController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /** Load the saved games menu document, display it in the center of the screen, and disable all main menu buttons.
     *
     * @throws IOException if the fxml file to load does not exist.
     */
    @FXML
    public void openSaveGameMenu() throws IOException {
        // Load the saved games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    /** Load the game info menu document, display it in the center of the screen, and disable all main menu buttons.
     *
     * @throws IOException if the fxml file to load does not exist.
     */
    @FXML
    public void openGameInfoMenu() throws IOException {
        // Load the game info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameInfoMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the main menu layout to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        disableMenuButtons();
    }

    // Disables the main menu buttons while a different menu is open.
    private void disableMenuButtons() {
        newGameButton.setDisable(true);
        savedGamesButton.setDisable(true);
        gameInfoButton.setDisable(true);
        openSettingsButton.setDisable(true);
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }
}
