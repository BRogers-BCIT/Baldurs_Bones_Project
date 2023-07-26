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
 * Quit Pop-Up Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class QuitPopupController {

    // Constant: Define the amount of space to leave available for the Anchor Bar.
    private static final int ANCHOR_BAR_SIZE = 65;

    // The Parent Stage of the Quit Pop-Up Scene.
    private Stage parentStage;

    /**
     * Closes the Quit Pop-Up Scene.
     *
     * @param event The Action Event object created by clicking the Cancel Quit Button
     */
    @FXML
    public void closeQuit(final ActionEvent event) {
        // Get the current Stage by tracing the source of the Action Event. Event -> Scene -> Stage.
        Stage currentPopup = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Invoke the close method on the Quit Pop-Up Stage.
        currentPopup.close();
    }

    /**
     * Open a new Main Menu Scene and close the Quit Pop-Up Stage.
     *
     * @param event the Action Event object created by clicking the load game FXML Button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void returnToMainMenu(final ActionEvent event) throws IOException {
        // Get the Main Menu FXML file and load it into root.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the screen to set the size of the Stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
        parentStage.setScene(scene);
        parentStage.centerOnScreen();
        parentStage.setResizable(false);
        parentStage.setTitle("Baldur's Bones");
        // Display the window.
        parentStage.show();
        // Call method to close the Quit Pop-Up.
        closeQuit(event);
    }

    /**
     * Closes the Parent Stage by invoking the close method. Also calls method to close the Quit Pop-Up Stage.
     *
     * @param event The Action Event object created by clicking the Quit Game Button
     */
    @FXML
    public void quitGame(final ActionEvent event) {
        // Invoke the close method on the Parent Stage of the Quit Pop-Up.
        parentStage.close();
        // Call method to close the Quit Pop-Up Stage.
        closeQuit(event);
    }

    /**
     * Receives the Stage object for the Primary Game Window (Parent Stage).
     *
     * @param mainStage The Stage object for the Primary Game Window
     */
    public void getMainStage(final Stage mainStage) {
        parentStage = mainStage;
    }
}
