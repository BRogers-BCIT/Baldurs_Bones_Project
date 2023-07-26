package baldursbones.bb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Game Driver.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class GameDriver extends Application {

    // Define the amount of space to leave available for the Anchor Bar.
    private static final int ANCHOR_BAR_SIZE = 65;

    /**
     * Loads the Main Menu FXML document and opens it in a new window.
     * Sets formatting for the window: full screen sizing, center on screen, disable resizing, set window title.
     *
     * @param stage Takes a Stage object from the launch method to open in a new window.
     * @throws IOException If the FXML document being loaded does not exist
     */
    @Override
    public void start(final Stage stage) throws IOException {
        // Load the Main Menu FXML document into a root object.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the screen to set the size of the Stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();
        // Catch any Close Window Action Event: cancel the Action Event and call the Quit Game Pop-Up menu.
        stage.setOnCloseRequest(e -> {
            e.consume();
            openQuitPopup(stage);
        });

    }

    // Open the Quit Pop-Up when a quit Action Event is caught.
    // Allows users to cancel the quit action, return to Main Menu, or close the game.
    private void openQuitPopup(final Stage currentStage) {
        try {
            // Create the Stage for the Quit Pop-Up and set its values (resizing, centering, and title).
            Stage popup = new Stage();
            popup.setResizable(false);
            popup.centerOnScreen();
            popup.setTitle("Quit");
            // Set the new Scene to act as a Pop-Up (Prevent actions in main Scene while Quit Pop-Up is open.)
            popup.initModality(Modality.APPLICATION_MODAL);
            // Load the Quit Pop-Up FXML document into a root object.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitMenu.fxml"));
            Parent root = loader.load();
            // Get the Controller for the new Scene.
            QuitPopupController controller = loader.getController();
            // Pass the current Stage object to the Quit Pop-Up - allows it to close the Stage.
            controller.getMainStage(currentStage);
            // Get the Scene from the loaded root.
            Scene popupDisplay = new Scene(root);
            // Load the Pop-Up Scene into the Stage and display it.
            popup.setScene(popupDisplay);
            // Show and Wait works with Application Modal to pause the main Stage while Pop-Up is open.
            popup.showAndWait();
        } catch (IOException e) {
            // Catch errors if the FXML file being loaded does not exist.
            throw new RuntimeException(e);
        }
    }

    /**
     * Game launcher method.
     *
     * @param args No passed arguments
     */
    public static void main(final String[] args) {
        // Creates a new Stage and calls the Start() method to launch a JavaFX window.
        launch();
    }
}
