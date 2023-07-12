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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    // Text File: The number of lines in the text file per save.
    private static final int SAVE_FILE_LINES = 4;

    // Text File: Static location of the save files to read from.
    private static final File SAVE_FILES = new File("src/main/resources/baldursbones/bb/SaveFiles.txt");

    // Constant: Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // FXML Element: The parent element the saved games menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the saved games menu.
    @FXML
    private HBox saveGameMenu;

    // FXML Element: The table that the save files are contained in.
    @FXML
    private TableView<SaveFile> saveFileTable;

    // FXML Element: The table column that contains the save file names.
    @FXML
    private TableColumn<SaveFile, String> nameColumn;

    // FXML Element: The table column that contains the save file character names.
    @FXML
    private TableColumn<SaveFile, String> characterColumn;

    // FXML Element: The table column that contains the save file times.
    @FXML
    private TableColumn<SaveFile, String> timeColumn;

    // FXML Element: The text field for the user to enter the save name into.
    @FXML
    private TextField saveNameField;

    // Array Variable: An array of the save file information.
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

    private boolean saveFileChecker() throws FileNotFoundException {
        // Determines if saving is possible.
        boolean enableSave = true;
        // If the container is in the main menu, prevent a save.
        if (container.getId().equals("mainMenuGrid")) {
            // ** TEMP OUTPUT **
            enableSave = false;
            System.out.println("No Saving Here. TEMP");
        } else {
            // Find the name of the new save file and open the save files document.
            String newSaveName = saveNameField.getText();
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Iterate through the save file.
            while (readSaves.hasNextLine()) {
                // Get the next file name
                String currentLine = readSaves.nextLine();
                // If you find a matching file name, set enable save to false and break the loop.
                if (currentLine.equals(newSaveName)) {
                    // ** TEMP OUTPUT **
                    // ** Possible set to overwrite by passing the name to the delete method. **
                    enableSave = false;
                    System.out.println("Save File Already Exists. TEMP");
                    break;
                } else {
                    // If the save name does not match the new save name, then skip the lines for that save file.
                    for (int i = 1; i < SAVE_FILE_LINES; i++) {
                        readSaves.nextLine();
                    }
                }
            }
        }
        return enableSave;
    }
    /**
     * Writes a new set of save file information to the bottom of the save file document.
     * Will not create a new save on the main menu.
     * ** Needs code to get game the info and make a new save info file. **
     * ** Needs FXMl document area to if display saving is unavailable **
     *
     * @throws FileNotFoundException if the saved files text document cannot be opened by the valid save checker
     * @throws RuntimeException if the saved files text document cannot be opened
     */
    public void addNewSaveFile() throws FileNotFoundException {
        // Check that the container is not the main menu and the save file name is not repeated.
        if (saveFileChecker()) {
            try {
                // Open the "save files" file with a writer that appends saves to the end of the file.
                FileWriter writeFile = new FileWriter(SAVE_FILES, true);
                writeFile.write(saveNameField.getText() + System.getProperty("line.separator"));
                writeFile.write("Temp Char" + System.getProperty("line.separator"));
                writeFile.write(java.time.LocalDate.now() + System.getProperty("line.separator"));
                writeFile.write("Temp Location" + System.getProperty("line.separator"));
                writeFile.close();
                // Get the new save file information and populate the table.
                getSavedInfo();
            } catch (IOException e) {
                // Catch any issues opening and writing in the file.
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Finds the selected row, deletes that row from the saved files text document, and updates the save files table.
     * ** Need to implement way to find the saved game info file and delete it as well **
     *
     * @throws IOException if the files to be read or written to cannot be found
     */
    public void deleteSaveFile() throws IOException {
        // Get the save file row that is currently selected and save the file name.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        String deleteFileName = saveFile.getFileName();
        // Open a scanner to read the old saves file
        Scanner readSaves = new Scanner(SAVE_FILES);
        // Create a new file to replace the old saves file and open it in a writer.
        File newSaves = new File("SaveFiles.txt");
        FileWriter writeSaves = new FileWriter(newSaves, false);
        // Call the file updater method.
        updateSavesFile(writeSaves, readSaves, deleteFileName);
        // Close the reader and the writer.
        readSaves.close();
        writeSaves.close();
        // Replace the old saves file with the new saves file.
        Path newSave = Paths.get("SaveFiles.txt");
        Path oldSave = Paths.get("src/main/resources/baldursbones/bb");
        Files.move(newSave, oldSave.resolve(newSave.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        // Update the table
        getSavedInfo();
    }

    // Reads through the old save file and creates a new one with all the saves except for the deleted one.
    private void updateSavesFile(final FileWriter writeSaves, final Scanner readSaves, final String deleteFileName)
            throws IOException {
        // Iterate through the save file.
        while (readSaves.hasNextLine()) {
            // Get the next file name
            String currentLine = readSaves.nextLine();
            // If you find the file name, skip writing that section and record the current line.
            if (currentLine.equals(deleteFileName)) {
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    readSaves.nextLine();
                }
            } else {
                // Write the file name
                writeSaves.write(currentLine + System.getProperty("line.separator"));
                // If it is not the file to delete then write it to the new save file.
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    currentLine = readSaves.nextLine();
                    writeSaves.write(currentLine + System.getProperty("line.separator"));
                }
            }
        }
    }

    /**
     * Opens the SaveFile object and reads its data to be passed to the game.
     * Then it opens the Game Location window (main window for gameplay) in place of the current scene.
     * ** Needs a save game info files to read from using the get data file. **
     * ** Needs a game method to pass the info from the save game info file to. **
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the text document being loaded does not exist
     */
    public void loadSaveFile(final ActionEvent event) throws IOException {
        // Get the save file row that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        // ** TEMP ** Print the save file data.
        System.out.println(saveFile.getFileName());
        System.out.println(saveFile.getCharacterName());
        System.out.println(saveFile.getSaveTime());
        System.out.println(saveFile.getDataFile());
        openGameWindow(event);
    }

    // Populate the saved file table with the save file object info.
    private void populateTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("saveTime"));
        saveFileTable.setItems(saveFiles);
    }

    // Creates an array of objects representing the save file data in memory.
    private void getSavedInfo() {
        try {
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Clear any old save file info from the save file array.
            saveFiles.clear();
            // For each set of 4 lines (save info length),
            // Record those lines to an object and add it to the save file array.
            while (readSaves.hasNextLine()) {
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

    /**
     * When the saved files menu is opened, get the saved data and update the table.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        saveFiles = FXCollections.observableArrayList();
        // Try to read the save files from the save files text document and update the saves table
        getSavedInfo();
    }
}
