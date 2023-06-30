package baldursbones.bb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class WindowTester extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WindowTester.class.getResource("FileName.fxml"));
        Rectangle2D userScreen = Screen.getPrimary().getBounds();

        // Fullscreen sizing
        Scene scene = new Scene(fxmlLoader.load(), userScreen.getWidth(), userScreen.getHeight() - 70);

        // Set sizing
        //Scene scene = new Scene(fxmlLoader.load(), X, Y);

        // Default sizing
        //Scene scene = new Scene(fxmlLoader.load());

        // Turn off resizing
        // stage.setResizable(false);

        stage.setTitle("Test Screen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
