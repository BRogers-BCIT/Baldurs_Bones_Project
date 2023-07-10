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

    // Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    /**
     * Loads the Main Menu java class and opens it in a window called Baldur's Bones.
     * The window is set to fill the users screen but not be full screen by leaving 70 px for the anchor bar.
     * In addition, the window is centered in the screen and set to not be resizeable.
     *
     * @param stage takes a stage object from the launch method to display the content in.
     * @throws IOException if the FXML document being loaded does not exist
     */
    @Override
    public void start(final Stage stage) throws IOException {
        // Get the Main Menu FXML file and load it into root.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the users screen to set the size and with of the game window.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the scene with the correct size and width found above.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the name of the window and pass the scene to the stage.
        stage.setTitle("Baldur's Bones");
        stage.setScene(scene);
        // Prevent the user from changing the screen size and center the window on screen.
        stage.centerOnScreen();
        stage.setResizable(false);
        // Display the window.
        stage.show();

        // Catch manual window close, cancel it, and call open quit pop-up menu
        stage.setOnCloseRequest(e -> {
            e.consume();
            openQuitPopup(stage);
        });
    }

    // Open the quit pop-up menu to catch users when quitting and give them a chance to cancel or return to main menu.
    private void openQuitPopup(final Stage currentStage) {
        try {
            // Create the stage for the pop-up and set the stage values (window type, resizing, centering, and title).
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.centerOnScreen();
            popup.setTitle("Quit");
            // Load the quit game pop-up FXML document into a root object.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitMenu.fxml"));
            Parent root = loader.load();
            // Get the controller for the new pop-up to pass the current stage to.
            QuitPopupController controller = loader.getController();
            // Pass the current stage to the quit pop-up.
            controller.getMainStage(currentStage);
            // Get the scene from the loaded root.
            Scene popupDisplay = new Scene(root);
            // Load the pop-up scene into the stage and display it. Will pause game until window is closed.
            popup.setScene(popupDisplay);
            popup.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Game launcher method.
     *
     * @param args No passed arguments
     */
    public static void main(final String[] args) {
        launch();
    }
}
