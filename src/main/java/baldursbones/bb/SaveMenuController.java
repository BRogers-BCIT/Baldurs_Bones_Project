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
import javafx.scene.control.Label;
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
 * Save Function Driver.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SaveMenuController implements Initializable {

    // Constant: The number of lines in the text file per save.
    private static final int SAVE_FILE_LINES = 4;

    // Text File: Static location of the Save Files document to read from.
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

    // FXML Element: The text field for the user to enter the Save File name into.
    @FXML
    private TextField saveNameField;

    // Array Variable: An array of the Save File information.
    private ObservableList<SaveFile> saveFiles;

    //FXML Element: A label used to display any messages to the user when creating or deleting a save.
    @FXML
    private Label saveOutput;

    // Variable (Character Stats): The name of the character being saved into a Save Info File.
    private String charName = "Temp Name";

    // Variable (Character Stats): The level of the character being saved into a Save Info File.
    private String charLevel = "Temp Level";

    // Variable (Character Stats): The experience of the character being saved into a Save Info File.
    private String charExp = "Temp Exp";

    // Variable (Character Stats): The health of the character being saved into a Save Info File.
    private String charHealth = "Temp Health";

    // Variable (Character Stats): The location of the character being saved into a Save Info File.
    private String charLocation = "Temp Location";

    // Variable (Character Ability): The number of uses the player has of the "Add" ability.
    private String charAbilityAdd = "Temp Ability Add";

    // Variable (Character Ability): The number of uses the player has of the "Take Away" ability.
    private String charAbilityTakeAway = "Temp Ability Take-Away";

    // Variable (Character Ability): The number of uses the player has of the "Re-Roll" ability.
    private String charAbilityReRoll = "Temp Ability Re-Roll";

    // Variable (Maps): The 2D array used by the game to track locations.
    private String gameMap = "Temp Game Map";

    // Variable (Maps): The 2D array used by the game to display the player map.
    private String playerMap = "Temp Player Map";

    /**
     * Removes the Saved Games menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeSaveMenu() {
        // Set the settings button to be clickable.
        container.lookup("#openSettingsButton").setDisable(false);
        // If the current container is the Main Menu, enable Main Menu buttons.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#newGameButton").setDisable(false);
            container.lookup("#savedGamesButton").setDisable(false);
            container.lookup("#gameInfoButton").setDisable(false);
        } else {
            // Else: Set location menu buttons to be clickable.
            container.lookup("#locationFightButton").setDisable(false);
            container.lookup("#locationViewStats").setDisable(false);
            container.lookup("#locationViewMap").setDisable(false);
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the Saved Games menu from the current menu window.
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
        // Get the stage by tracing the source of the click event. Event -> Scene -> Stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    }

    // Populate the Saved File table with the Save File object info.
    private void populateTable() {
        // Set the columns to read from an associated field in the Save File object class.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("saveTime"));
        // Update the table with the Save Files array of objects.
        saveFileTable.setItems(saveFiles);
    }

    // Creates an array of objects representing the Save File data in memory.
    private void getSavedInfo() {
        try {
            // Try to read the Saved Files document.
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Clear any old Save File info from the Save File array.
            saveFiles.clear();
            // For each set of 4 lines (Save Info length = File Name, Character Name, Save Tile, Info File Location).
            while (readSaves.hasNextLine()) {
                // Record those lines to an object and add it to the Save File array.
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

    /**
     * Checks for valid save and writes a new set of Save File information to the bottom of the Save File document.
     * Limitations: there is nowhere to pull the character name information from to populate into the document.
     * Currently, uses a temp string to record character name.
     * Waiting For: Game Location controller to be created with getCharacterName method to call from.
     * Fix: Implement a setter and getter method for the character name in the Game Location controller.
     *
     * @throws FileNotFoundException if the Saved Files text document cannot be opened by the valid save checker
     * @throws RuntimeException      if the Saved Files text document cannot be opened
     */
    public void addNewSaveFile() throws FileNotFoundException {
        // Check that the container is not the Main Menu and the Save File name is not null or repeated.
        if (saveFileChecker()) {
            try {
                // Open the "Save Files" file with a writer and append the new save information to the end of the file.
                FileWriter writeFile = new FileWriter(SAVE_FILES, true);
                writeFile.write(saveNameField.getText() + System.getProperty("line.separator"));
                writeFile.write("Temp Char" + System.getProperty("line.separator"));
                writeFile.write(java.time.LocalDate.now() + System.getProperty("line.separator"));
                // Define the file name as "saveFileName" + Info.txt.
                writeFile.write(saveNameField.getText() + "Info.txt" + System.getProperty("line.separator"));
                // Close the file reader.
                writeFile.close();
                // Create the associated info file, update Saved Files information, and populate the Save Files table.
                createInfoFile(saveNameField.getText());
                saveOutput.setVisible(true);
                saveOutput.setStyle("-fx-background-color: black");
                saveOutput.setText("Saved File: " + saveNameField.getText() + ".");
                getSavedInfo();
            } catch (IOException e) {
                // Catch any issues opening and writing in the file.
                throw new RuntimeException(e);
            }
        }
    }

    // Determine if it is possible for the user to create a new save.
    // Limitations: Invalid save name / location error prints to terminal instead of displaying in game window.
    // Waiting For: FXML Element in the Saved Games window to update if the save attempt is invalid.
    // Fix: Replace print methods with update to FXML element.
    private boolean saveFileChecker() throws FileNotFoundException {
        // Determines if saving is currently possible.
        boolean enableSave = true;
        // If the container is in the Main Menu, prevent a save.
        if (container.getId().equals("mainMenuGrid")) {
            enableSave = false;
            saveOutput.setVisible(true);
            saveOutput.setStyle("-fx-background-color: red");
            saveOutput.setText("Cannot Save On Main Menu");
        } else if (saveNameField.getText().equals("")) {
            enableSave = false;
            saveOutput.setVisible(true);
            saveOutput.setStyle("-fx-background-color: red");
            saveOutput.setText("Un-Named Save File.");
        } else {
            // Create a scanner to check the Save File names.
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Iterate through the Save File and get the next file name.
            while (readSaves.hasNextLine()) {
                String currentLine = readSaves.nextLine();
                // If you find a name that matches the current file name, set enable save to false and break the loop.
                if (currentLine.equals(saveNameField.getText())) {
                    enableSave = false;
                    saveOutput.setVisible(true);
                    saveOutput.setStyle("-fx-background-color: red");
                    saveOutput.setText("Save File Already Exists With That Name");
                    break;
                } else {
                    // If the Save File name does not match the new Save File name:
                    // Skip the lines associated with that Save File.
                    for (int i = 1; i < SAVE_FILE_LINES; i++) {
                        readSaves.nextLine();
                    }
                }
                // Close the file reader.
                readSaves.close();
            }
        }
        // Return a boolean value indicating if it is a valid save.
        return enableSave;
    }

    /**
     * Finds the selected row, delete that row from the Saved Files document, and update the Save Files table.
     *
     * @throws IOException if the files to be read or written to cannot be found
     */
    public void deleteSaveFile() throws IOException {
        // Get the Save File row that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        // If a file is selected then save the file name and start the deletion process.
        if (saveFile != null) {
            String deleteFileName = saveFile.getFileName();
            // Call the method to delete the associated Save Info file
            deleteInfoFile(deleteFileName);
            // Open a scanner to read the old Save Files file.
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Create a new file to replace the old Saved Files document and open it in a writer.
            File newSaves = new File("SaveFiles.txt");
            FileWriter writeSaves = new FileWriter(newSaves, false);
            // Call the file updater create a copy of the Saved Files document without the selected save information.
            updateSavesFile(writeSaves, readSaves, deleteFileName);
            // Close the reader and the writer.
            readSaves.close();
            writeSaves.close();
            // Replace the old Saved Files document with the updated copy.
            // Finds the new file name and the old files path then moves the old file into that folder.
            // StandardCopyOption.REPLACE_EXISTING removes the old file and replaces it with the new one.
            Path newSave = Paths.get("SaveFiles.txt");
            Path oldSave = Paths.get("src/main/resources/baldursbones/bb");
            Files.move(newSave, oldSave.resolve(newSave.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            // Update the table with the new Saved Files document.
            getSavedInfo();
        } else {
            saveOutput.setVisible(true);
            saveOutput.setStyle("-fx-background-color: red");
            saveOutput.setText("No File Selected.");
        }
    }

    // Reads through the old Save File and creates a copy of it.
    // Copy is created without the save information for the selected file name.
    private void updateSavesFile(final FileWriter writeSaves, final Scanner readSaves, final String deleteFileName)
            throws IOException {
        // Iterate through the Save File.
        while (readSaves.hasNextLine()) {
            // Get the next file name
            String currentLine = readSaves.nextLine();
            // If: current line matches the selected file name, skip writing that section and move to the next file.
            if (currentLine.equals(deleteFileName)) {
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    readSaves.nextLine();
                }
            } else {
                // Else: Write the file name to the file.
                writeSaves.write(currentLine + System.getProperty("line.separator"));
                // Write the rest of the save info into the new Save File document.
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    currentLine = readSaves.nextLine();
                    writeSaves.write(currentLine + System.getProperty("line.separator"));
                }
            }
        }
    }

    /**
     * Opens the SaveFile document and reads the game data to be passed to the Location Menu.
     * Then it opens the Game Location window (main window for gameplay) in place of the current scene.
     * Limitations: There is no Location Menu controller to pass the game info to, that loads a new game.
     * Waiting For: A setGameValues method to pass the file to that will assign the file info to the game variables.
     * Fix: Replace the print loop with a method call in the open game window method that passes the info file.
     * Open game window method will need to be updated to accept a File type parameter.
     *
     * @param event the event object created by clicking the load game button
     * @throws IOException if the text document being loaded does not exist
     */
    public void loadSaveFile(final ActionEvent event) throws IOException {
        // Get the Save File row that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        // Get the document assosiated with that Save File and load it into a file object.
        String fileName = saveFile.getDataFile();
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileName);
        // Open the info file in a scanner for reading.
        Scanner infoReader = new Scanner(infoFile);
        // ** Replace with method to pass the game information file to the Location Menu. **
        // ** Location Menu will have a method to read the file and assign the values to variables. **
        for (int infoElement = 1; infoElement <= 10; infoElement++) {
            // ** TEMP OUTPUT ** For each line of the info file: print the line to the terminal for testing.
            System.out.println(infoReader.nextLine());
        }
        // Close the file reader.
        infoReader.close();
        // Call the method to open a Location Menu window.
        openGameWindow(event);
    }

    // Takes a Save File name from the new save method and makes an associated Save Info file.
    // Limitations: There is no way for the controller to get the game information to write into the information file.
    // Waiting For: Location menu being able to call a method in this class that takes the game information as strings.
    // Fix: Write test strings into the save file test file reading and writing.
    private void createInfoFile(final String infoFileName) {
        // Add the ending "Info" and extension ".txt" to the file name.
        String fileNameFormatted = infoFileName + "Info.txt";
        // Define the location for the new file.
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileNameFormatted);
        try {
            // Try to create a file with the assigned file name.
            System.out.println("Created Info File: " + infoFile.createNewFile());
        } catch (IOException e) {
            // Catches any  errors creating the file.
            throw new RuntimeException(e);
        }
        try {
            // Create a writer object to fill the file with character info.
            FileWriter writeFileInfo = new FileWriter(infoFile);
            // Write the values into the text file, one line per value.
            // ** TEMP INPUT ** There is no container controller to pass data to the controller.
            // Temp strings are used instead of game values.
            writeFileInfo.write(charName + System.getProperty("line.separator"));
            writeFileInfo.write(charLevel + System.getProperty("line.separator"));
            writeFileInfo.write(charExp + System.getProperty("line.separator"));
            writeFileInfo.write(charHealth + System.getProperty("line.separator"));
            writeFileInfo.write(charLocation + System.getProperty("line.separator"));
            writeFileInfo.write(charAbilityAdd + System.getProperty("line.separator"));
            writeFileInfo.write(charAbilityTakeAway + System.getProperty("line.separator"));
            writeFileInfo.write(charAbilityReRoll + System.getProperty("line.separator"));
            writeFileInfo.write(gameMap + System.getProperty("line.separator"));
            writeFileInfo.write(playerMap + System.getProperty("line.separator"));
            // Close the writer.
            writeFileInfo.close();
        } catch (IOException e) {
            // Catches any errors writing the info file.
            throw new RuntimeException(e);
        }
    }

    // Takes a Save File name, formats it and looks for the file, then deletes the associated info file if able.
    private void deleteInfoFile(final String infoFileName) {
        // Add the ending "Info" and extension ".txt" to the file name.
        String fileNameFormatted = "src/main/resources/baldursbones/bb/" + infoFileName + "Info.txt";
        // Look for a file with a matching name in the resources folder and delete it if able.
        File deleteFile = new File(fileNameFormatted);
        saveOutput.setVisible(true);
        saveOutput.setStyle("-fx-background-color: black");
        saveOutput.setText("Deleted File: " + deleteFile.delete());
    }

    /**
     * Receive the current characters stats as strings and saves them to the character stat variables.
     *
     * @param name     the characters name as a string
     * @param level    the characters level as a one digit string (1-9)
     * @param exp      the characters experience as a one digit string (1-9)
     * @param health   the characters health as a one digit string (1-9)
     * @param location the characters location as two numbers seperated by a comma (X,Y)
     */
    public void setCharacterStats(final String name, final String level, final String exp,
                                  final String health, final String location) {
        this.charName = name;
        this.charLevel = level;
        this.charExp = exp;
        this.charHealth = health;
        this.charLocation = location;
    }

    /**
     * Receive the current characters ability uses as strings and saves them to the character stat variables.
     *
     * @param abilityAdd      The number of uses of the Add ability the character has as one digit string
     * @param abilityTakeAway The number of uses of the Take-Away ability the character has as one digit string
     * @param abilityReRoll   The number of uses of the Re-Roll ability the character has as one digit string
     */
    public void setCharacterAbilities(final String abilityAdd, final String abilityTakeAway,
                                      final String abilityReRoll) {
        this.charAbilityAdd = abilityAdd;
        this.charAbilityTakeAway = abilityTakeAway;
        this.charAbilityReRoll = abilityReRoll;
    }

    /**
     * Receives the current values of the game map and player map as strings and saves them to the map variables.
     *
     * @param gameMapString   A string version of the 2D array in format (111 112 113 114 ...)
     * @param playerMapString A string version of the 2D array in format (# # @ @ ! ...)
     */
    public void setGameMaps(final String gameMapString, final String playerMapString) {
        this.gameMap = gameMapString;
        this.playerMap = playerMapString;
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the Settings Menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    /**
     * When the Saved Games menu is opened, get the saved data and update the table.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Define the data type of the array of Save File classes.
        saveFiles = FXCollections.observableArrayList();
        // Try to read the Save Files from the Save Files document and update the Save Files table.
        getSavedInfo();
    }
}
