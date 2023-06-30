package baldursbones.bb;

import eu.hansolo.tilesfx.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SavesWindowController implements Initializable {

    @FXML private TableView<TestSaves> saves;

    @FXML private TableColumn<TestSaves, String> saveName;

    @FXML private TableColumn<TestSaves, String> characterName;

    @FXML private TableColumn<TestSaves, String> saveTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
        saveName.minWidthProperty().bind(saves.widthProperty().divide(3));
        saveName.setCellValueFactory(new PropertyValueFactory<>("saveName"));
        characterName.minWidthProperty().bind(saves.widthProperty().divide(3));
        characterName.setCellValueFactory(new PropertyValueFactory<> ("characterName"));
        saveTime.minWidthProperty().bind(saves.widthProperty().divide(3));
        saveTime.setCellValueFactory(new PropertyValueFactory<> ("saveTime"));

        saves.setItems(getSaves());
    }

    public ObservableList<TestSaves> getSaves() {
        ObservableList<TestSaves> saves = FXCollections.observableArrayList();
        saves.add(new TestSaves("Save 1", "Will", "5 days ago"));
        saves.add(new TestSaves("Save 2", "Bill", "4 days ago"));
        saves.add(new TestSaves("Save 3", "Ned", "3 days ago"));
        saves.add(new TestSaves("Save 4", "Ted", "2 days ago"));
        saves.add(new TestSaves("Save 5", "Edd", "1 days ago"));
        return saves;
    }
}