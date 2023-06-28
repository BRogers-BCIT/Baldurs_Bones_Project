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
        FXMLLoader fxmlLoader = new FXMLLoader(WindowTester.class.getResource("test-view.fxml"));
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), userScreen.getWidth(), userScreen.getHeight() - 70);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}