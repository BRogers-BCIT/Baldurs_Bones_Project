package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WindowTestController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}