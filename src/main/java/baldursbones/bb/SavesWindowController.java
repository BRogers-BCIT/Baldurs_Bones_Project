package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SavesWindowController implements Initializable {

    @FXML private TableView saves;

    @FXML private TableColumn saveName;

    @FXML private TableColumn characterName;

    @FXML private TableColumn saveTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
        saveName.minWidthProperty().bind(saves.widthProperty().divide(3));
        characterName.minWidthProperty().bind(saves.widthProperty().divide(3));
        saveTime.minWidthProperty().bind(saves.widthProperty().divide(3));
    }
}