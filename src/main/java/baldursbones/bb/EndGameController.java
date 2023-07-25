package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * End Game Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class EndGameController {

    // Define the amount of space to leave available for the Anchor Bar.
    private static final int ANCHOR_BAR_SIZE = 65;

    // The Parent Layout Element that the End Game Scene is displayed in.
    private GridPane container;

    // The Layout Element for the End Game Scene.
    @FXML
    private HBox endGameBox;

    /**
     * Creates a new Stage and opens the Main Menu Scene in the new Stage.
     *
     * @param event The event object generated by clicking the Quit Button
     * @throws IOException If the FXML document being loaded does not exist
     */
    @FXML
    public void closeEndGameMenu(final ActionEvent event) throws IOException {
        // Load the Main Menu FXML document into a root object.
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = mainLoader.load();
        // Get the Controller for the Main Menu and get the Grid Pane object from the Controller.
        MainMenuController controller = mainLoader.getController();
        container = controller.getMainMenuGrid();
        // Find the current Stage by tracing the source of the click event. Event -> Scene -> Stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Find the size of the users screen and use it to set the size and with of the game window.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Create a Scene with the loaded Main Menu document and set it as the current Scene in the new Stage.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        stage.setScene(scene);
        // Center Stage to the middle of the screen, prevent resizing, and set the Title.
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();
    }

    /**
     * Opens a new Main Menu Scene in place of the current Scene and open the Saved Games Scene within it.
     *
     * @param event The event object generated by clicking the Load Game Button
     * @throws IOException If the fxml file being loaded does not exist
     */
    @FXML
    public void openSaveMenu(final ActionEvent event) throws IOException {
        // Call the close End Game Menu method to open a new Main Menu and save its Layout Element.
        closeEndGameMenu(event);
        // Load the Saved Games FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMenu.fxml"));
        Parent root = loader.load();
        // Get the controller for the Saved Games Scene and pass the End Game Parent Layout Element to the class.
        SaveMenuController controller = loader.getController();
        controller.getContainerElement(container);
        // Define the Coordinates to display the new Scene in the Parent Layout Element
        GridPane.setConstraints(root, 1, 1);
        // Display the new Scene
        container.getChildren().add(root);
    }

    /**
     * Creates a New Game Scene and removes the End Game Scene for the Stage.
     *
     * @throws IOException If the fxml file being loaded does not exist
     */
    @FXML
    public void openNewGameMenu() throws IOException {
        // Load the New Game FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGameMenu.fxml"));
        Parent root = loader.load();
        // Get the Controller for the new Scene and pass the current Parent Layout Element.
        NewGameController controller = loader.getController();
        controller.getContainerElement(container);
        // Define the Coordinates to display the new Scene in the Parent Layout Element.
        GridPane.setConstraints(root, 2, 1);
        // Display the New Game Scene and remove the End Game Scene.
        container.getChildren().add(root);
        container.getChildren().remove(endGameBox);
    }

    /**
     * Sets the Parent Layout Element of the End Game Scene.
     *
     * @param parentGrid The Parent Layout Element for the End Game Scene
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

}
