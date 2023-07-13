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

    // Text File: Static location of the Save Files to read from.
    private static final File SAVE_FILES = new File("src/main/resources/baldursbones/bb/SaveFiles.txt");

    // Constant: Define the amount of pixels to leave of the top of the screen for the anchor bar.
    private static final int ANCHOR_BAR_SIZE = 70;

    // FXML Element: The parent element the Saved Games menu is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the Saved Games menu.
    @FXML
    private HBox saveGameMenu;

    // FXML Element: The table that the Save Files are contained in.
    @FXML
    private TableView<SaveFile> saveFileTable;

    // FXML Element: The table column that contains the Save File names.
    @FXML
    private TableColumn<SaveFile, String> nameColumn;

    // FXML Element: The table column that contains the Save File character names.
    @FXML
    private TableColumn<SaveFile, String> characterColumn;

    // FXML Element: The table column that contains the Save File times.
    @FXML
    private TableColumn<SaveFile, String> timeColumn;

    // FXML Element: The text field for the user to enter the save name into.
    @FXML
    private TextField saveNameField;

    // Array Variable: An array of the Save File information.
    private ObservableList<SaveFile> saveFiles;

    /**
     * Removes the Saved Games menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSaveMenu() {
        container.lookup("#openSettingsButton").setDisable(false);
        // If the current container is the Main Menu, enable Main Menu buttons.
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
        // Remove the Settings Menu from the Main Menu window.
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
     * @param parentGrid The parent element of the Settings Menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    private boolean saveFileChecker() throws FileNotFoundException {
        // Determines if saving is possible.
        boolean enableSave = true;
        // If the container is in the Main Menu, prevent a save.
        if (container.getId().equals("mainMenuGrid")) {
            // ** TEMP OUTPUT **
            enableSave = false;
            System.out.println("No Saving Here. TEMP");
        } else {
            // Find the name of the new Save File and open the saved files document.
            String newSaveName = saveNameField.getText();
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Iterate through the Save File.
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
                    // If the save name does not match the new save name, then skip the lines for that Save File.
                    for (int i = 1; i < SAVE_FILE_LINES; i++) {
                        readSaves.nextLine();
                    }
                }
            }
            readSaves.close();
        }
        return enableSave;
    }
    /**
     * Writes a new set of Save File information to the bottom of the Save File document.
     * Will not create a new save on the Main Menu.
     * ** Needs code to get game the info and make a new Save Info file. **
     * ** Needs FXMl document area to if display saving is unavailable **
     *
     * @throws FileNotFoundException if the saved files text document cannot be opened by the valid save checker
     * @throws RuntimeException if the saved files text document cannot be opened
     */
    public void addNewSaveFile() throws FileNotFoundException {
        // Check that the container is not the Main Menu and the Save File name is not repeated.
        if (saveFileChecker()) {
            try {
                // Open the "Save Files" file with a writer that appends saves to the end of the file.
                FileWriter writeFile = new FileWriter(SAVE_FILES, true);
                writeFile.write(saveNameField.getText() + System.getProperty("line.separator"));
                writeFile.write("Temp Char" + System.getProperty("line.separator"));
                writeFile.write(java.time.LocalDate.now() + System.getProperty("line.separator"));
                // Define the file name as saveFileName + Info.txt.
                writeFile.write(saveNameField.getText() + "Info.txt" + System.getProperty("line.separator"));
                writeFile.close();
                // Create the associated info file.
                createInfoFile(saveNameField.getText());
                // Get the new Save File information and populate the table.
                getSavedInfo();
            } catch (IOException e) {
                // Catch any issues opening and writing in the file.
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Finds the selected row, deletes that row from the saved files text document, and updates the Save Files table.
     * ** Need to implement way to find the Saved Games info file and delete it as well **
     *
     * @throws IOException if the files to be read or written to cannot be found
     */
    public void deleteSaveFile() throws IOException {
        // Get the Save File row that is currently selected and save the file name.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        if (saveFile != null) {
            String deleteFileName = saveFile.getFileName();
            // Call the method to delete the associated info file
            deleteInfoFile(deleteFileName);
            // Open a scanner to read the old saves file.
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
            // Update the table with the new saves text document.
            getSavedInfo();
        } else {
            // ** TEMP OUTPUT ** Put warning element into display window later.
            // Display a warning to terminal if the user attempts to delete a file without selecting one.
            System.out.println("Warning: Deleting file without file selected.");
        }
    }

    // Reads through the old Save File and creates a new one with all the saves except for the deleted one.
    private void updateSavesFile(final FileWriter writeSaves, final Scanner readSaves, final String deleteFileName)
            throws IOException {
        // Iterate through the Save File.
        while (readSaves.hasNextLine()) {
            // Get the next file name
            String currentLine = readSaves.nextLine();
            // If you find the file name, skip writing that section and record the current line.
            if (currentLine.equals(deleteFileName)) {
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    readSaves.nextLine();
                }
            } else {
                // Write the file name to the file.
                writeSaves.write(currentLine + System.getProperty("line.separator"));
                // If it is not the file to delete then write it to the new Save File.
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
     * ** Needs a Save Game info files to read from using the get data file. **
     * ** Needs a game method to pass the info from the Save Game info file to. **
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the text document being loaded does not exist
     */
    public void loadSaveFile(final ActionEvent event) throws IOException {
        // Get the Save File row that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        // Get the matching info file name and load it into a file object.
        String fileName = saveFile.getDataFile();
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileName);
        // Open the info file in a scanner for reading.
        Scanner infoReader = new Scanner(infoFile);
        // For each info element in the info file print it to screen for testing.
        for (int infoElement = 1; infoElement <= 10; infoElement++) {
            // ** TEMP ** Print the Save File data instead of passing it.
            System.out.println(infoReader.nextLine());
        }
        // Close the file reader
        infoReader.close();
        // Call the method to open a New Game
        openGameWindow(event);
    }

    // Populate the saved file table with the Save File object info.
    private void populateTable() {
        // Set the columns to read from an associated field in the saved file object class.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("saveTime"));
        // Update the table with the Save Files array of objects.
        saveFileTable.setItems(saveFiles);
    }

    // Creates an array of objects representing the Save File data in memory.
    private void getSavedInfo() {
        try {
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Clear any old Save File info from the Save File array.
            saveFiles.clear();
            // For each set of 4 lines (Save Info length),
            // Record those lines to an object and add it to the Save File array.
            while (readSaves.hasNextLine()) {
                String fileName = readSaves.nextLine();
                String characterName = readSaves.nextLine();
                String saveTime = readSaves.nextLine();
                String saveFile = readSaves.nextLine();
                saveFiles.add(new SaveFile(fileName, characterName, saveTime, saveFile));
            }
            // Close the file reader
            readSaves.close();
        } catch (FileNotFoundException e) {
            // Catch any errors reading the text file
            throw new RuntimeException(e);
        }
        // Update the Save Files array and the Save Files table with the new saves.
        populateTable();
    }

    // Takes a Save File name from a new save and makes an associated Save Info file.
    private void createInfoFile(final String infoFileName) {
        // Add the ending and extension to the file name;
        String fileNameFormatted = infoFileName + "Info.txt";
        // Define the file location
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileNameFormatted);
        try {
            // Tries to create a file with the file name.
            System.out.println("Created Info File: " + infoFile.createNewFile());
        } catch (IOException e) {
            // Catches any  errors creating the file.
            throw new RuntimeException(e);
        }
        try {
            // Create a writer object to fill the file with character info.
            FileWriter writeFileInfo = new FileWriter(infoFile);
            // Get values from the getContainerValues method.
            // Write the values into the text file, one line per value.
            writeFileInfo.write("Character Name" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Level" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Experience" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Health" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Location" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Add Ability" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Subtract Ability" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Re-Roll Ability" + System.getProperty("line.separator"));
            writeFileInfo.write("Player Map" + System.getProperty("line.separator"));
            writeFileInfo.write("Game Map" + System.getProperty("line.separator"));
            writeFileInfo.close();
        } catch (IOException e) {
            // Catches any errors writing the info file.
            throw new RuntimeException(e);
        }
    }

    // Takes a Save File name and deletes the associated info file.
    private void deleteInfoFile(final String infoFileName) {
        // Add the ending and extension to the file name.
        String fileNameFormatted = "src/main/resources/baldursbones/bb/" + infoFileName + "Info.txt";
        // Find the file and delete it if able.
        File deleteFile = new File(fileNameFormatted);
        System.out.println("Deleted File: " + deleteFile.delete());
    }

    /**
     * When the saved files menu is opened, get the saved data and update the table.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Define the data type of the array of Save File classes.
        saveFiles = FXCollections.observableArrayList();
        // Try to read the Save Files from the Save Files text document and update the saves table
        getSavedInfo();
    }
}
