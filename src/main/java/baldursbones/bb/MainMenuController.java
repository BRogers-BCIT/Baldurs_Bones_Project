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
 * Start Game Driver.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MainMenuController implements Initializable {

    // Variable: Contains a loaded FXML file to be passed to the stage.
    private Parent root;

    // FXML Element: The layout object for the Main Menu that new scenes are loaded into.
    @FXML
    private GridPane mainMenuGrid;

    // FXML Element: Menu button that calls the open Settings Menu method.
    @FXML
    private Button openSettingsButton;

    // FXML Element: Menu button that calls the open New Game menu method.
    @FXML
    private Button newGameButton;

    // FXML Element: Menu button that calls the open Saved Games menu method.
    @FXML
    private Button savedGamesButton;

    // FXML Element: Settings button that calls the open Game Info menu method.
    @FXML
    private Button gameInfoButton;


    /**
     * Load the Settings Menu document and pass the parent container to its controller.
     * Displays the scene it in the center of the grid (0, 2) and disables the background Main Menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the Settings Menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the parent layout element to the class.
        SettingsMenuController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        // Disable the menu buttons in the background
        disableMenuButtons();
    }

    /**
     * Load the Game Info menu document and pass the parent container to its controller.
     * Displays the scene it in the center of the grid (0, 2) and disables the background Main Menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openNewGameMenu() throws IOException {
        // Load the New Game menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGameMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the parent layout element to the class.
        NewGameController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        // Disable the menu buttons in the background
        disableMenuButtons();
    }

    /**
     * Load the Saved Games menu document and pass the parent container to its controller.
     * Displays the scene it in the center of the grid (0, 2) and disables the background Main Menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openSaveGameMenu() throws IOException {
        // Load the Saved Games menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the parent layout element to the class.
        SaveMenuController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        // Disable the menu buttons in the background
        disableMenuButtons();
    }

    /**
     * Load the Game Info menu document and pass the parent container to its controller.
     * Displays the scene it in the center of the grid (0, 2) and disables the background Main Menu buttons.
     *
     * @throws IOException if the fxml file being loaded does not exist
     */
    @FXML
    public void openGameInfoMenu() throws IOException {
        // Load the Game Info menu FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameInfoMenu.fxml"));
        root = loader.load();
        // Get the controller for the new menu and pass the parent layout element to the class.
        GameInfoController controller = loader.getController();
        controller.getContainerElement(mainMenuGrid);
        // Define where to display the new menu and add it to the layout.
        GridPane.setConstraints(root, 0, 2);
        mainMenuGrid.getChildren().add(root);
        // Disable the menu buttons in the background
        disableMenuButtons();
    }

    // Disables the Main Menu background buttons while a menu scene is open in the main scene.
    private void disableMenuButtons() {
        newGameButton.setDisable(true);
        savedGamesButton.setDisable(true);
        gameInfoButton.setDisable(true);
        openSettingsButton.setDisable(true);
    }

    /**
     * Passes the Main Menu parent container object to a controller class.
     * Method is invoked to find the menu ID of a scene's parent container.
     *
     * @return the grid pane object for the Main Menu layout
     */
    public GridPane getMainMenuGrid() {
        return mainMenuGrid;
    }

    // Invoked when a Main Menu is created to set any initial values.
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
    }

}
