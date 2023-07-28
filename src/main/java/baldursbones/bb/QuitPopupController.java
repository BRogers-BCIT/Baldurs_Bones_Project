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

    // Variable: A boolean value used to track if Music is enabled.
    private boolean enableMusicState;

    // Variable: A boolean value used to track if SFX are enabled.
    private boolean enableSFXState;

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
        // Load the Main Menu FXML document into a root object.
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Parent root = mainLoader.load();
        // Get the Controller for the Main Menu and get the Grid Pane object from the Controller.
        MainMenuController controller = mainLoader.getController();
        // Get the size of the screen to set the size of the Stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
        parentStage.setScene(scene);
        parentStage.centerOnScreen();
        parentStage.setResizable(false);
        parentStage.setTitle("Baldur's Bones");
        // Pass the Sound Settings to the Location Menu Controller
        controller.setSoundSettings(enableMusicState, enableSFXState);
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
     * Sets the Sound Settings values for the Quit Pop-Up when creating a new Scene.
     *
     * @param enableMusic A boolean value indicating if Music is enabled
     * @param enableSFX   A boolean value indicating if SFX is enabled
     */
    public void setSoundSettings(final boolean enableMusic, final boolean enableSFX) {
        enableMusicState = enableMusic;
        enableSFXState = enableSFX;
    }

    /**
     * Receives the Stage object for the Primary Game Window (Parent Stage).
     *
     * @param mainStage The Stage object for the Primary Game Window
     */
    public void setSceneVariables(final Stage mainStage) {
        parentStage = mainStage;
        enableMusicState = true;
        enableSFXState = true;
    }
}
