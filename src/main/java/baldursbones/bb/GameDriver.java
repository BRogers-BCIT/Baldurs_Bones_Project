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
     * Loads the Main Menu FXML document and opens it in a window called Baldur's Bones.
     * The window is set to fill the users screen while leaving space for anchor bar on top.
     * In addition, the window is centered in the screen and set to not be resizeable.
     *
     * @param stage takes a stage object from the launch method to display the content in.
     * @throws IOException if the FXML document being loaded does not exist
     */
    @Override
    public void start(final Stage stage) throws IOException {
        // Load the Main Menu FXML document into a root object.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Get the size of the users screen to determine the size and with of the new game stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the scene with the size and width found above.
        // (Height - Size of anchor bar allows anchor bar to display)
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new scene in the stage object, center the stage, prevent resizing, and set the window title.
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();

        // Catch manual window close, cancel it, and call the Quit Game pop-up menu.
        stage.setOnCloseRequest(e -> {
            e.consume();
            openQuitPopup(stage);
        });

    }

    // Open the quit pop-up menu to catch users when quitting.
    // Allows users to cancel the close, return to Main Menu, or close the game.
    private void openQuitPopup(final Stage currentStage) {
        try {
            // Create the stage for the pop-up and set its values (pop-up window, resizing, centering, and title).
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.centerOnScreen();
            popup.setTitle("Quit");
            // Load the Quit Game pop-up FXML document into a root object.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuitMenu.fxml"));
            Parent root = loader.load();
            // Get the controller for the new pop-up.
            QuitPopupController controller = loader.getController();
            // Pass the current stage object to the Quit Game pop-up to allow it to close the window if needed.
            controller.getMainStage(currentStage);
            // Get the scene from the loaded root.
            Scene popupDisplay = new Scene(root);
            // Load the pop-up scene into the stage and display it.
            // Application Modal option set above will pause the main window until the pop-up is closed.
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
