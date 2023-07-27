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
import java.util.ArrayList;
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

    // Constant: The number of lines in the Text File assosiated with each Save File.
    private static final int SAVE_FILE_LINES = 4;

    // Text File: The Master Save Directory Text File.
    private static final File SAVE_FILES = new File("src/main/resources/baldursbones/bb/SaveFiles.txt");

    // Constant: Define the amount of space to leave available for the Anchor Bar.
    private static final int ANCHOR_BAR_SIZE = 65;

    // FXML Element: The Parent Layout Element that the Saved Games Scene is displayed within.
    private GridPane container;

    // FXML Element: The Layout Element for the Saved Games Scene.
    @FXML
    private HBox saveGameMenu;

    // FXML Element: The Table View that Save Files are displayed within.
    @FXML
    private TableView<SaveFile> saveFileTable;

    // FXML Element: The Column of the Table View that contains the Save File - Names.
    @FXML
    private TableColumn<SaveFile, String> nameColumn;

    // FXML Element: The Column of the Table View that contains the Save File - Character Names.
    @FXML
    private TableColumn<SaveFile, String> characterColumn;

    // FXML Element: The Column of the Table View that contains the Save File - Save Times.
    @FXML
    private TableColumn<SaveFile, String> timeColumn;

    // FXML Element: The Text Field Input for the user to enter a Save File Name.
    @FXML
    private TextField saveNameField;

    // Array Variable: An Array of the Save File objects representing the Master Save Directory.
    private ObservableList<SaveFile> saveFiles;

    //FXML Element: The Label used to display Error or Result text to the user.
    @FXML
    private Label saveOutput;

    // Variable (Character Stats): The Name String of the Character being written into a Save Info File.
    private String characterName = "Temp Name";

    // Variable (Character Stats): The Level Stat of the Character being written into a Save Info File.
    private String statLevel = "Temp Level";

    // Variable (Character Stats): The Experience Stat of the Character being written into a Save Info File.
    private String statExp = "Temp Exp";

    // Variable (Character Stats): The Health Stat of the Character being written into a Save Info File.
    private String statHealth = "Temp Health";

    // Variable (Character Stats): The Location Coordinates of the Character being written into a Save Info File.
    private String characterCoordinates = "Temp Location";

    // Variable (Character Ability): The number of uses of the "Re-Roll" ability being written into a Save Info File.
    private String abilityReRoll = "Temp Ability Re-Roll";

    // Variable (Character Ability): The number of uses of the "Add" ability being written into a Save Info File.
    private String abilityAdd = "Temp Ability Add";

    // Variable (Character Ability): The number of uses of the "Take-Away" ability being written into a Save Info File.
    private String abilityTakeAway = "Temp Ability Take-Away";

    // Variable (Maps): A 2D array representing the Game Map being written into a Save Info File.
    private String gameMap = "Temp Game Map";

    // Variable (Maps): A 2D array representing the Player Map being written into a Save Info File.
    private String playerMap = "Temp Player Map";

    // Variable: A boolean value used to track if Music is enabled.
    private boolean enableMusicState;

    // Variable: A boolean value used to track if SFX are enabled.
    private boolean enableSFXState;

    /**
     * Checks the ID of the Parent Layout Element to find which Scene it is displayed within.
     * Enables the Parent Scene Buttons and Removes the Game Info Scene from the Parent Scene.
     */
    @FXML
    public void closeSaveMenu() {
        // Re-enable Settings Button, it is present in both primary Scenes (Main Menu & Location Menu).
        container.lookup("#SettingsButton").setDisable(false);
        // If: The Parent Scene is a Main Menu Scene, enable the Main Menu Button.
        if (container.getId().equals("mainMenuGrid")) {
            container.lookup("#NewGameButton").setDisable(false);
            container.lookup("#SavesButton").setDisable(false);
            container.lookup("#GameInfoButton").setDisable(false);
        } else {
            // Else: The Parent Scene is a Location Menu Scene, enable the Location Menu Buttons.
            container.lookup("#ViewCharacter").setDisable(false);
            container.lookup("#ViewMap").setDisable(false);
            // Enable the Parent Scene Text Area.
            container.lookup("#GameDescription").setDisable(false);
            // ** Temp Testing Button **
            container.lookup("#endGameTest").setDisable(false);
        }
        // Remove the Saved Games Scene from its Parent Layout Element.
        container.getChildren().remove(saveGameMenu);
    }

    // ** Add Pass Save File Method **

    /**
     * Loads a new Game Location Scene into the current Stage.
     *
     * @param event The Action Event object created by clicking the New Game Button
     * @throws IOException If the FXML document being loaded does not exist
     */
    @FXML
    public void openGameWindow(final ActionEvent event) throws IOException {
        // Load the Game Location FXML document into a root object.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LocationMenu.fxml"));
        Parent root = loader.load();
        // Get the current Stage by tracing the source of the Action Event. Event -> Scene -> Stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Get the size of the screen to set the size of the Stage.
        Rectangle2D userScreen = Screen.getPrimary().getBounds();
        // Load the Scene with the size. Leave extra space in height to display the Anchor Bar.
        Scene scene = new Scene(root, userScreen.getWidth(), userScreen.getHeight() - ANCHOR_BAR_SIZE);
        // Set the new Scene in the Stage object, center the Stage, prevent resizing, and set the window title.
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Baldur's Bones");
        // Display the window.
        stage.show();
        // Get Controller of Location Menu Scene.
        LocationMenuController gameDriverController = loader.getController();
        // Pass the Game Info to the Location Menu Controller.
        gameDriverController.loadStats(getCharacterStats());
        gameDriverController.loadAbilities(getCharacterAbilities());
        gameDriverController.loadMaps(getGameMaps());
        // Pass the Sound Settings to the Location Menu Controller
        gameDriverController.setSoundSettings(enableMusicState, enableSFXState);
        // Call the Start Game method in the Location Menu Controller.
        gameDriverController.gameStarter();
    }

    /**
     * Checks for that the Save File has a valid File Name,
     * Writes a new set of Save File information to the bottom of the Master Save Directory Text File.
     *
     * @throws FileNotFoundException If the Master Save Directory Files Text Document cannot be opened by checker
     * @throws RuntimeException      If the Master Save Directory Files Text Document cannot be opened
     */
    public void addNewSaveFile() throws FileNotFoundException {
        // Check that a new Save File can be created.
        if (saveFileChecker()) {
            // Try to open the Master Save Directory Text File.
            try {
                FileWriter writeFile = new FileWriter(SAVE_FILES, true);
                // Write the File Name the user entered into the Master Save Directory.
                writeFile.write(saveNameField.getText() + System.getProperty("line.separator"));
                // Write the Character Name into the Master Save Directory.
                writeFile.write(characterName + System.getProperty("line.separator"));
                // Write the current Date into the Master Save Directory.
                writeFile.write(java.time.LocalDate.now() + System.getProperty("line.separator"));
                // Write the name of the Info File associated with the Save File into the Master Save Directory.
                writeFile.write(saveNameField.getText() + "Info.txt" + System.getProperty("line.separator"));
                // Close the File Writer.
                writeFile.close();
                // Create the Info File for the new Save File.
                createInfoFile(saveNameField.getText());
                // Display a message that the Save File was created successfully.
                saveOutput.setVisible(true);
                saveOutput.setText("Saved File: " + saveNameField.getText() + " Successfully.");
                // Update the Table View with the new Save File.
                getSavedInfo();
            } catch (IOException e) {
                // Catch any issues opening or writing in the Text File.
                throw new RuntimeException(e);
            }
        }
    }

    // Checks to see if a new Save File can be created. Checks: Current Location & File Name.
    private boolean saveFileChecker() throws FileNotFoundException {
        // Determines if a new Save File can be created.
        boolean enableSave = true;
        // If the current Scene is the Main Menu, prevent a save.
        if (container.getId().equals("mainMenuGrid")) {
            // Prevent a save and display an error message.
            enableSave = false;
            saveOutput.setVisible(true);
            saveOutput.setText("No Game Data Available To Save");
        } else if (saveNameField.getText().equals("")) {
            // Prevent a save and display an error message.
            enableSave = false;
            saveOutput.setVisible(true);
            saveOutput.setText("File Name Cannot Be Empty.");
        } else {
            // Create a scanner to check the Save File Names.
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Create loop to check if the Save Name matches any existing saves.
            // Loop checks all Names or until a match is found.
            while (readSaves.hasNextLine()) {
                // Iterate through the Save File and get the next Save File Name.
                String currentLine = readSaves.nextLine();
                if (currentLine.equals(saveNameField.getText())) {
                    // If: Found Name matches the Save Name, prevent a save and display an error message.
                    enableSave = false;
                    saveOutput.setVisible(true);
                    saveOutput.setText("Save File Already Exists With That Name");
                    // Stop checking files.
                    break;
                } else {
                    // Else: Found Name does not match Save Name, skip the Save File in the Text File.
                    for (int i = 1; i < SAVE_FILE_LINES; i++) {
                        readSaves.nextLine();
                    }
                }
                // Close the File Reader.
                readSaves.close();
            }
        }
        // Return a boolean value indicating if a Save File can be created.
        return enableSave;
    }

    // Takes a Save File name from the new save method and makes an associated Save Info Text File.
    // Writes the character information into the Save Info Text File.
    private void createInfoFile(final String infoFileName) {
        // Format the Text File name by add the ending "Info" and extension ".txt" to the file name.
        String fileNameFormatted = infoFileName + "Info.txt";
        // Define the location for the new Text File.
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileNameFormatted);
        try {
            // Try to create a new Text File with the formatted File Name.
            System.out.println("Created Info File: " + infoFile.createNewFile());
        } catch (IOException e) {
            // Catches any  errors creating the Text File.
            throw new RuntimeException(e);
        }
        try {
            // Create a writer object to fill the file with character info.
            FileWriter writeFileInfo = new FileWriter(infoFile);
            // Write the Game Info into the Info File
            // Stats: Name, Coordinates, Level, Exp, Health.
            writeFileInfo.write(characterName + System.getProperty("line.separator"));
            writeFileInfo.write(characterCoordinates + System.getProperty("line.separator"));
            writeFileInfo.write(statLevel + System.getProperty("line.separator"));
            writeFileInfo.write(statExp + System.getProperty("line.separator"));
            writeFileInfo.write(statHealth + System.getProperty("line.separator"));
            // Abilities: Re-Roll, Add, Take-Away.
            writeFileInfo.write(abilityReRoll + System.getProperty("line.separator"));
            writeFileInfo.write(abilityAdd + System.getProperty("line.separator"));
            writeFileInfo.write(abilityTakeAway + System.getProperty("line.separator"));
            // Maps: Game Map, Player Map.
            writeFileInfo.write(gameMap + System.getProperty("line.separator"));
            writeFileInfo.write(playerMap + System.getProperty("line.separator"));
            // Close the File Writer.
            writeFileInfo.close();
        } catch (IOException e) {
            // Catches any errors writing the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the selected Save File from Master Save Directory, and the assosiated Info File.
     *
     * @throws IOException If the files to be read or written to cannot be found
     */
    @FXML
    public void deleteSaveFile() throws IOException {
        // Get the Save File row that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        if (saveFile != null) {
            // If: A Save File is selected, get the Save File Name.
            String deleteFileName = saveFile.getFileName();
            // Call helper method to delete the Save Info File associated with the Save File.
            deleteInfoFile(deleteFileName);
            // Open a File Reader for the Master Save Directory.
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Create a new Text File to replace the Master Save Directory and open it in a File Writer.
            File newSaves = new File("SaveFiles.txt");
            FileWriter writeSaves = new FileWriter(newSaves, false);
            // Call the helper method to create a copy of the Master Save Directory without the deleted Save File.
            updateSavesFile(writeSaves, readSaves, deleteFileName);
            // Close the File Reader and the File Writer.
            readSaves.close();
            writeSaves.close();
            // Replace the Master Save Directory with the new version (without the deleted file).
            Path newSave = Paths.get("SaveFiles.txt");
            Path oldSave = Paths.get("src/main/resources/baldursbones/bb");
            // Moves the new file to resources folder and overwrites the existing file with the same name.
            // The new Master Save Directory will overwrite the old Master Save Directory.
            Files.move(newSave, oldSave.resolve(newSave.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            // Update the Save Files Array and Saved Files Table View with the new Master Save Directory.
            getSavedInfo();
        } else {
            // Else: If no Save File is selected, display an error message.
            saveOutput.setVisible(true);
            saveOutput.setText("No Save File Selected.");
        }
    }

    // Takes a Save File Name and looks for a matching Save Info File.
    // If it finds a matching Save Info File, it deletes it and displays a success message.
    private void deleteInfoFile(final String infoFileName) {
        // Find the matching Info File by adding the ending "Info" and extension ".txt" to the file name.
        // Save File = Test, Info File = TestInfo.txt.
        String fileNameFormatted = "src/main/resources/baldursbones/bb/" + infoFileName + "Info.txt";
        // Look for an Info File with a matching name in the resources' folder and delete it.
        File deleteFile = new File(fileNameFormatted);
        // Display a success message.
        saveOutput.setVisible(true);
        saveOutput.setText("Deleted File: " + deleteFile.delete() + " Successfully.");
    }

    /**
     * Gets the Info File assosiated with a selected Save File object from the Table View and saves its values.
     * Calls the method to load a game in a new Location Menu Scene.
     *
     * @param event The Action Event object created by clicking the Load Game Button
     * @throws IOException If the Text File being loaded does not exist
     */
    @FXML
    public void loadSaveFile(final ActionEvent event) throws IOException {
        // Get the Save File object that is currently selected.
        SaveFile saveFile = saveFileTable.getSelectionModel().getSelectedItem();
        // Get the Save Info Text File from that Save File object and load it into a file object.
        String fileName = saveFile.getInfoFile();
        File infoFile = new File("src/main/resources/baldursbones/bb/" + fileName);
        // Open the Info File in a scanner for reading.
        Scanner infoReader = new Scanner(infoFile);
        // Assign the Info Element to be passed to the Game to local variables.
        // Stats: Name, Level, Exp, Health, Coordinates.
        characterName = infoReader.nextLine();
        characterCoordinates = infoReader.nextLine();
        statLevel = infoReader.nextLine();
        statExp = infoReader.nextLine();
        statHealth = infoReader.nextLine();
        // Abilities: Re-Roll, Add, Take-Away.
        abilityReRoll = infoReader.nextLine();
        abilityAdd = infoReader.nextLine();
        abilityTakeAway = infoReader.nextLine();
        //// Maps: Game Map, Player Map.
        gameMap = infoReader.nextLine();
        playerMap = infoReader.nextLine();
        // Close the File Reader.
        infoReader.close();
        // Call the method to open a Location Menu Scene.
        openGameWindow(event);
    }

    // Populate the Saved File Table View with Save File object Array.
    private void populateTable() {
        // Set the Table Columns to read from an associated Value in the Save File object.
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("characterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("saveTime"));
        // Update the Table View with the Array of Save File Objects.
        saveFileTable.setItems(saveFiles);
    }

    // Creates an Array of Save File Objects representing the data in the Master Save Directory Text File.
    private void getSavedInfo() {
        // Try to read the Master Save Directory Text File.
        try {
            Scanner readSaves = new Scanner(SAVE_FILES);
            // Clear any old Save File objects from the Save File array.
            saveFiles.clear();
            // While there are more Saves in the Master Save Directory Text File.
            while (readSaves.hasNextLine()) {
                // Record the File Name, Character Name, Save Time, and Info File for that Save File.
                String fileName = readSaves.nextLine();
                String charName = readSaves.nextLine();
                String saveTime = readSaves.nextLine();
                String saveFile = readSaves.nextLine();
                // Add the recorded info to the Save File Array as a new Save File object.
                saveFiles.add(new SaveFile(fileName, charName, saveTime, saveFile));
            }
            // Close the File Reader for the Master Save Directory.
            readSaves.close();
        } catch (FileNotFoundException e) {
            // Catch any errors reading the Text File.
            throw new RuntimeException(e);
        }
        // Update the Save Files Table View with the new Save File Array.
        populateTable();
    }

    // Reads through the Master Save Directory and makes a copy of it in a new file.
    // If it finds a Save File that matches the passed name, the Save File is skipped.
    // Finally, replaces the old Save File Directory, deleting the skipped Save File.
    private void updateSavesFile(final FileWriter writeSaves, final Scanner readSaves, final String deleteFileName)
            throws IOException {
        // Iterate through the Master Save Directory Text File.
        while (readSaves.hasNextLine()) {
            // Get the next Save File Name.
            String currentLine = readSaves.nextLine();
            if (currentLine.equals(deleteFileName)) {
                // If: current Save File Name matches the selected Save File Name, skip writing that section.
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    readSaves.nextLine();
                }
            } else {
                // Else: Write the Save File Name and Info to the Master Save Directory.
                writeSaves.write(currentLine + System.getProperty("line.separator"));
                for (int i = 1; i < SAVE_FILE_LINES; i++) {
                    currentLine = readSaves.nextLine();
                    writeSaves.write(currentLine + System.getProperty("line.separator"));
                }
            }
        }
    }

    /**
     * Receives Game Info: Stats as an Array List of strings and saves them to the Character Stat variables.
     *
     * @param characterStats An Array List of strings containing Character Stat Info.
     */
    public void setCharacterStats(final ArrayList<String> characterStats) {
        this.characterName = characterStats.get(0);
        this.characterCoordinates = characterStats.get(1);
        this.statLevel = characterStats.get(2);
        this.statExp = characterStats.get(3);
        this.statHealth = characterStats.get(4);
    }

    /**
     * Receives Game Info: Abilities as an Array List of strings and saves them to the Ability variables.
     *
     * @param characterAbilities An Array List of strings containing Character Ability Info.
     */
    public void setCharacterAbilities(final ArrayList<String> characterAbilities) {
        this.abilityReRoll = characterAbilities.get(0);
        this.abilityAdd = characterAbilities.get(1);
        this.abilityTakeAway = characterAbilities.get(2);
    }

    /**
     * Receives Game Info: Maps as an Array List of strings and saves them to the Map variables.
     *
     * @param gameMaps An Array List of strings containing Game Map Info.
     */
    public void setGameMaps(final ArrayList<String> gameMaps) {
        this.gameMap = gameMaps.get(0);
        this.playerMap = gameMaps.get(1);
    }

    // Returns an ArrayList of Strings that represent the Character Stats.
    // Return: Name String, Coordinates String, Level String, Exp String, Health String.
    private ArrayList<String> getCharacterStats() {
        ArrayList<String> characterStats = new ArrayList<>();
        characterStats.add(characterName);
        characterStats.add(characterCoordinates);
        characterStats.add(statLevel);
        characterStats.add(statExp);
        characterStats.add(statHealth);
        return characterStats;
    }

    // Returns an ArrayList of Strings that represent the Character Abilities.
    // Return: Ability Re-Roll String, Ability Add String, Ability Take-Away String.
    private ArrayList<String> getCharacterAbilities() {
        ArrayList<String> characterAbilities = new ArrayList<>();
        characterAbilities.add(abilityReRoll);
        characterAbilities.add(abilityAdd);
        characterAbilities.add(abilityTakeAway);
        return characterAbilities;
    }


    // Returns an ArrayList of Strings that represent the Character Stats.
    // Return: Game Map String, Player Map String.
    private ArrayList<String> getGameMaps() {
        ArrayList<String> gameMaps = new ArrayList<>();
        gameMaps.add(gameMap);
        gameMaps.add(playerMap);
        return gameMaps;
    }

    /**
     * Receives the Parent Layout Element for the Saved Games Scene.
     *
     * @param parentGrid  The Parent Layout Element of the New Game Scene
     * @param enableMusic A boolean value that indicates if Music is currently enabled
     * @param enableSFX   A boolean value that indicates if SFX are currently enabled
     */
    public void setSceneVariables(final GridPane parentGrid, final boolean enableMusic, final boolean enableSFX) {
        container = parentGrid;
        enableMusicState = enableMusic;
        enableSFXState = enableSFX;
    }

    /**
     * When the Saved Games Scene is created get all the Save Files and update the Save Files Table View.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Create the Array for the Save File objects.
        saveFiles = FXCollections.observableArrayList();
        // Read the Master Save Directory and create an Array of Save File.
        // Calls the method to update the Table View with the Save File object Info.
        getSavedInfo();
    }
}
