package baldursbones.bb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Quit Popup Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class QuitPopupController {

    // Constant: Define the amount of pixels to leave of the top of the screen for the anchor bar.
    // Used when creating a new container class scene (Main Menu / Location Menu)
    private static final int ANCHOR_BAR_SIZE = 70;

    // The parent stage that the quit pop-up was called by. Used to close the parent window if needed.
    private Stage parentStage;

    /**
     * Closes the Quit Game pop-up menu.
     *
     * @param event the event object created by clicking the cancel quit button
     */
    @FXML
    public void cancelQuit(final ActionEvent event) {
        // Get the stage by tracing the source of the click event. Event -> Scene -> Stage.
        Stage currentPopup = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Invoke the close method on the Quit Game pop-up menu.
        currentPopup.close();
    }

    /**
     * Opens a new Main Menu window and closes the Quit Game pop-up.
     *
     * @param event the event object created by clicking the load game FXML button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void returnToMainMenu(final ActionEvent event) throws IOException {
        // Get the Main Menu FXML file and load it into root.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the users screen to set the size and with of the game window.
        // Get the size of the users screen to determine the size and with of the new game stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the scene with the size and width found above.
        // (Height - Size of anchor bar allows anchor bar to display)
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new scene in the stage object, center the stage, prevent resizing, and set the window title.
        parentStage.setScene(scene);
        parentStage.centerOnScreen();
        parentStage.setResizable(false);
        parentStage.setTitle("Baldur's Bones");
        // Display the window.
        parentStage.show();
        // Call method to close the pop-up.
        cancelQuit(event);
    }

    /**
     * Closes the main game window by closing its stage object. Calls method to close the Quit Game pop-up.
     *
     * @param event the event object created by clicking the quit game button
     */
    @FXML
    public void quitGame(final ActionEvent event) {
        // Invoke the close method on the main window stage to close the main window.
        parentStage.close();
        // Call method to close the pop-up.
        cancelQuit(event);
    }

    /**
     * Gets the stage object of the window that called this class. Used to close the parent window if needed.
     *
     * @param mainStage the stage object for the current main window
     */
    public void getMainStage(final Stage mainStage) {
        parentStage = mainStage;
    }
}
