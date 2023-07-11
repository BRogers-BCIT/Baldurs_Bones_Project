package baldursbones.bb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Saved Games Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SaveMenuController implements Initializable {
    // Static location of the save files to read from.
    private static final File SAVE_FILES = new File("src/main/resources/baldursbones/bb/SavedGames.txt");

    // Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // The parent element the saved games menu is displayed in.
    private GridPane container;

    // The layout element for the saved games menu.
    @FXML
    private HBox saveGameMenu;

    // The table that the save files are contained in.
    @FXML
    private TableView<SaveFile> saveFileTable;

    // The table column that contains the save file names.
    @FXML
    private TableColumn<SaveFile, String> nameColumn;

    // The table column that contains the save file character names.
    @FXML
    private TableColumn<SaveFile, String> characterColumn;

    // The table column that contains the save file times.
    @FXML
    private TableColumn<SaveFile, String> timeColumn;

    // An array of the save file information.
    private ObservableList<SaveFile> saveFiles;

    /**
     * Removes the saved games menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSaveMenu() {
        container.lookup("#openSettingsButton").setDisable(false);
        // If the current container is the main menu, enable main menu buttons.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
        } else {
            // Set location menu buttons to be clickable.
            container.lookup("#locationFightButton").setDisable(false);
            container.lookup("#locationViewStats").setDisable(false);
            container.lookup("#locationViewMap").setDisable(false);
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the settings menu from the main menu window.
        container.getChildren().remove(saveGameMenu);
    }

    /**
     * Opens the Game Location window (main window for gameplay) in place of the current scene.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the FXML document being loaded does not exist
     */
    @FXML
    public void openGameWindow(final ActionEvent event) throws IOException {
        // Load the Game Location menu FXML document.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LocationMenu.fxml")));
        // Get the stage by tracing the source of the click event -> scene -> stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Get the size of the users screen to set the size and with of the game window.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Create a scene with the loaded Game Location menu document.
        // Load the scene with the correct size and width found above.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        stage.setScene(scene);
        // Center stage to the middle of the screen, prevent resizing, and set title.
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    // Populate the saved file table with the save file object info.
    private void populateTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("saveTime"));
        saveFileTable.setItems(saveFiles);
    }

    /**
     * When the saved files menu is opened create an array of objects representing the save file data in memory.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        saveFiles = FXCollections.observableArrayList();
        // Try to read the save files from the save files text document.
        try {
            Scanner readSaves = new Scanner(SAVE_FILES);
            while (readSaves.hasNext()) {
                String fileName = readSaves.nextLine();
                String characterName = readSaves.nextLine();
                String saveTime = readSaves.nextLine();
                String saveFile = readSaves.nextLine();
                saveFiles.add(new SaveFile(fileName, characterName, saveTime, saveFile));
            }
        } catch (FileNotFoundException e) {
            // Catch any errors reading the text file
            throw new RuntimeException(e);
        }
        populateTable();
    }
}
