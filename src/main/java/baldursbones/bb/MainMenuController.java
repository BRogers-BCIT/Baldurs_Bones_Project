package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * Main Menu Controller.
 * Start Game Driver.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class MainMenuController {

    // FXML Element: The Layout Element for the Main Menu Scene.
    @FXML
    private GridPane mainMenuGrid;

    // FXML Element: The Button that calls the open Settings Scene method.
    @FXML
    private Button settingsButton;

    // FXML Element: The Button that calls the open New Game Scene method.
    @FXML
    private Button newGameButton;

    // FXML Element: The Button that calls the open Saved Games Scene method.
    @FXML
    private Button savedGamesButton;

    // FXML Element: The Button that calls the open Game Info Scene method.
    @FXML
    private Button gameInfoButton;


    /**
     * Load the Settings Scene and passes the Layout Element for the Main Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (1, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openSettingsMenu() throws IOException {
        // Load the Settings FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene and pass the Parent Layout Element.
        SettingsMenuController controller = loader.getController();
        controller.setSceneVariables(mainMenuGrid);
        // Define where to display the new Scene and add it to the Grid Pane Element.
        GridPane.setConstraints(root, 1, 1);
        mainMenuGrid.getChildren().add(root);
        // Disable the Main Menu Buttons.
        disableMenuButtons();
    }

    /**
     * Load the New Game Scene and passes the Layout Element for the Main Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (1, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openNewGameMenu() throws IOException {
        // Load the New Game FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGameMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene and pass the Parent Layout Element.
        NewGameController controller = loader.getController();
        controller.setSceneVariables(mainMenuGrid);
        // Define where to display the new Scene and add it to the Grid Pane Element.
        GridPane.setConstraints(root, 1, 1);
        mainMenuGrid.getChildren().add(root);
        // Disable the Main Menu Buttons.
        disableMenuButtons();
    }

    /**
     * Load the Saved Games Scene and passes the Layout Element for the Main Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (1, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openSaveGameMenu() throws IOException {
        // Load the Saved Games FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene and pass the Parent Layout Element.
        SaveMenuController controller = loader.getController();
        controller.setSceneVariables(mainMenuGrid);
        // Define where to display the new Scene and add it to the Grid Pane Element.
        GridPane.setConstraints(root, 1, 1);
        mainMenuGrid.getChildren().add(root);
        // Disable the Main Menu Buttons.
        disableMenuButtons();
    }

    /**
     * Load the Game Info Scene and passes the Layout Element for the Main Menu Scene to its controller.
     * Displays the Scene in the Display Scene Cell (1, 1) and disables the background Menu buttons.
     *
     * @throws IOException If the FXML file being loaded does not exist
     */
    @FXML
    public void openGameInfoMenu() throws IOException {
        // Load the Game Info FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameInfoMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene and pass the Parent Layout Element.
        GameInfoController controller = loader.getController();
        controller.setSceneVariables(mainMenuGrid);
        // Define where to display the new Scene and add it to the Grid Pane Element.
        GridPane.setConstraints(root, 1, 1);
        mainMenuGrid.getChildren().add(root);
        // Disable the Main Menu Buttons.
        disableMenuButtons();
    }

    // Disables the Main Menu Buttons while a Scene is open within the Main Menu Scene.
    private void disableMenuButtons() {
        newGameButton.setDisable(true);
        savedGamesButton.setDisable(true);
        gameInfoButton.setDisable(true);
        settingsButton.setDisable(true);
    }

    /**
     * Returns the Main Menu Parent Layout Element to a controller class.
     * Allows Controllers to make and populate new Main Menu Scenes.
     *
     * @return the Grid Pane Element for the Main Menu Scene
     */
    public GridPane getMainMenuGrid() {
        return mainMenuGrid;
    }
}
