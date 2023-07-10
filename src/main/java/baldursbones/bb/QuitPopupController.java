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
    // Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // The parent scene that the quit pop-up was called from.
    private Stage parentStage;

    /**
     * Closes the quit page pop-up menu.
     *
     * @param event the event object created by clicking the cancel quit button
     */
    @FXML
    public void cancelQuit(final ActionEvent event) {
        // Get the current window from the event and close it.
        Stage currentPopup = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentPopup.close();
    }

    /**
     * Opens a new main menu window and closes the pop-up.
     *
     * @param event the event object created by clicking the return to menu button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void returnToMainMenu(final ActionEvent event) throws IOException {
        // Get the Main Menu FXML file and load it into root.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the users screen to set the size and with of the game window.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the scene with the correct size and width found above.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the name of the window and pass the scene to the stage.
        parentStage.setTitle("Baldur's Bones");
        parentStage.setScene(scene);
        // Prevent the user from changing the screen size and centers the window on screen.
        parentStage.centerOnScreen();
        parentStage.setResizable(false);
        // Pass the event to cancel quit to close the pop-up.
        cancelQuit(event);
    }

    /**
     * Closes the main game window and the quit game pop-up.
     *
     * @param event the event object created by clicking the quit game button
     */
    @FXML
    public void quitGame(final ActionEvent event) {
        // Close the main window.
        parentStage.close();
        // Pass the event to cancel quit to close the pop-up.
        cancelQuit(event);
    }

    /**
     * Gets the stage object of the main window to close it if needed.
     *
     * @param mainStage the stage object of the current main window
     */
    public void getMainStage(final Stage mainStage) {
        parentStage = mainStage;
    }
}
