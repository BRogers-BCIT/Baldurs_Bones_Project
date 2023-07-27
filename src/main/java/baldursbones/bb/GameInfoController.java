package baldursbones.bb;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Game Info Menu Controller.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class GameInfoController implements Initializable {

    // Text File: Static location of the "Game Info" Text File to read from.
    private static final File GAME_INFO_TEXT = new File("src/main/resources/baldursbones/bb/GameInfoText.txt");

    // Constant: The number of "Pages" in the Game Info Text File.
    // ** Update to match length of final Game Info Text File.
    private static final int FINAL_PAGE_VALUE = 3;

    // Variable: Tracks the "Current Page" of the Game Info Text File being displayed.
    private int currentInfoPage;

    // FXML Element: The Parent Layout Element that the Game Info Scene is displayed in.
    private GridPane container;

    // FXML Element: The Layout Element for the Game Info Scene.
    @FXML
    private HBox gameInfoMenu;

    // FXML Element: The Title of the Game Info Scene.
    // Format: Game Info Page: X / Y    (X = Current, Y = Total)
    @FXML
    private Label gameInfoTitle;

    // FXML Element: The Game Info Title for Info Element One.
    @FXML
    private Label infoTitleOne;

    // FXML Element: The Game Info Title for Info Element Two.
    @FXML
    private Label infoTitleTwo;

    // FXML Element: The Game Info Title for Info Element Three.
    @FXML
    private Label infoTitleThree;

    // FXML Element: The Game Info Text Area for Info Element One.
    @FXML
    private TextArea infoAreaOne;

    // FXML Element: he Game Info Text Area for Info Element Two.
    @FXML
    private TextArea infoAreaTwo;

    // FXML Element: he Game Info Text Area for Info Element Three.
    @FXML
    private TextArea infoAreaThree;

    /**
     * Checks the ID of the Parent Layout Element to find which Scene it is displayed within.
     * Enables the Parent Scene Buttons and Removes the Game Info Scene from the Parent Scene.
     */
    @FXML
    public void closeGameInfoMenu() {
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
        // Remove the Game Info menu from its Parent Layout Element.
        container.getChildren().remove(gameInfoMenu);
    }

    /**
     * Increment the Current Page value and calls the page update method for then new Page Info.
     * Wraps forward from the Last Page to the First Page.
     */
    public void nextPage() {
        // Increment Current Page number.
        currentInfoPage++;
        // If: the Current Page exceeds Max Pages, wrap around to the First Page.
        if (currentInfoPage > FINAL_PAGE_VALUE) {
            currentInfoPage = 1;
        }
        // Populate the Page Info for the new Page.
        populateFileInfo();
    }

    /**
     * Decrement the Current Page value and calls the page update method for then new Page Info.
     * Wraps backwards from the First Page to the Last Page.
     */
    public void prevPage() {
        // Decrement the Current Page number.
        currentInfoPage--;
        // If: the Current Page is negative, wrap around to the First Page.
        if (currentInfoPage < 1) {
            currentInfoPage = FINAL_PAGE_VALUE;
        }
        // Populate the Page Info for the new Page.
        populateFileInfo();
    }

    // Reads the Game Info Text File and displays the text to FXMl Elements in the Scene.
    private void populateFileInfo() {
        // Try to read the text from the Game Info Text File.
        try {
            Scanner fileReader = new Scanner(GAME_INFO_TEXT);
            // Call helper method to move the Read-Line to the correct file location.
            skipToCorrectInfo(fileReader);
            // Populate the Title Label.
            gameInfoTitle.setText("Game Info: Page " + fileReader.nextLine() + " / 3");
            // Populate the first Info Element Title and Description.
            infoTitleOne.setText(fileReader.nextLine());
            infoAreaOne.setText(fileReader.nextLine());
            // Populate the second Info Element Title and Description.
            infoTitleTwo.setText(fileReader.nextLine());
            infoAreaTwo.setText(fileReader.nextLine());
            // Populate the third Info Element Title and Description.
            infoTitleThree.setText(fileReader.nextLine());
            infoAreaThree.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catches any errors reading the Text File.
            throw new RuntimeException(e);
        }
    }

    // Moves the Read Line of a Scanner object to the correct line of the Game Info Text File based on the Page Number.
    // Equation (Skip per Page): 1 (Page Number) + (3 *  2 (First Title and Info)) = 7 Lines per Page.
    private void skipToCorrectInfo(final Scanner fileReader) {
        // Skip lines in the Text File Scanner to the start line for the Current Page number.
        for (int page = 1; page < currentInfoPage; page++) {
            // Page Title.
            fileReader.nextLine();
            // Info Title One and Info Text One.
            fileReader.nextLine();
            fileReader.nextLine();
            // Info Title Two and Info Text Two.
            fileReader.nextLine();
            fileReader.nextLine();
            /// Info Title Three and Info Text Three.
            fileReader.nextLine();
            fileReader.nextLine();
        }
    }

    /**
     * Sets the Parent Layout Element of the Game Info Scene.
     *
     * @param parentGrid The Parent Layout Element of the Game Info Scene
     */
    public void setSceneVariables(final GridPane parentGrid) {
        container = parentGrid;
    }

    /**
     * Sets the Starting Page to the First Page and calls the page display method.
     *
     * @param url            N/A
     * @param resourceBundle N/A
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        currentInfoPage = 1;
        populateFileInfo();
    }

}
