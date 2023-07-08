package baldursbones.bb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /** Loads the Main Menu java class and opens it in a window called Baldur's Bones.
     * The window is set to fill the users screen but not be full screen by leaving 70 px for the anchor bar.
     * In addition, the window is centered in the screen and set to not be resizeable.
     *
     * @param stage takes a stage object from the launch method to display the content in.
     * @throws IOException if the file to be loaded does not exist.
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
