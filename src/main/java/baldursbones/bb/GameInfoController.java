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

    // Text File: Static location of the "Game Info" text file to read from.
    private static final File GAME_INFO_TEXT = new File("src/main/resources/baldursbones/bb/GameInfoText.txt");

    // Constant: The number of "pages" in the text file, used to wrap final page into first page.
    // Update whenever Game Info document is updated to match the new length.
    private static final int FINAL_PAGE_VALUE = 3;

    // Variable: Tracks which "page" of the Game Info document is currently being displayed.
    private int currentInfoPage;

    // FXML Element: The parent container element that the Game Info scene is displayed in.
    private GridPane container;

    // FXML Element: The layout element for the Game Info menu.
    @FXML
    private HBox gameInfoMenu;

    // FXML Element: The title (Label) of the Game Info page. Used to display current page and total page numbers.
    // Format: Game Info Page: X / Y    (X = Current, Y = Total)
    @FXML
    private Label gameInfoTitle;

    // FXML Element: The label of the first text area for the Game Info page.
    // Used to note what game element the text area provides information about.
    @FXML
    private Label infoTitleOne;

    // FXML Element: The label of the second text area for the Game Info page.
    // Used to note what game element the text area provides information about.
    @FXML
    private Label infoTitleTwo;

    // FXML Element: The label of the third text area for the Game Info page.
    // Used to note what game element the text area provides information about.
    @FXML
    private Label infoTitleThree;

    // FXML Element: The first text area for the Game Info page.
    // Used to note display information about the game element defined in its label.
    @FXML
    private TextArea infoAreaOne;

    // FXML Element: The second text area for the Game Info page.
    // Used to note display information about the game element defined in its label.
    @FXML
    private TextArea infoAreaTwo;

    // FXML Element: The third text area for the Game Info page.
    // Used to note display information about the game element defined in its label.
    @FXML
    private TextArea infoAreaThree;

    /**
     * Removes the Game Info menu layout from its parent layout element.
     * Queries the ID of the parent object to find its container menu type and re-enables the correct menu buttons.
     */
    @FXML
    public void closeGameInfoMenu() {
        // Regardless of container type, re-enable the Settings Menu button.
        container.lookup("#openSettingsButton").setDisable(false);
        // If: The current container is the Main Menu, enable Main Menu buttons.
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
        // Remove the Game Info menu from its parent menu object.
        container.getChildren().remove(gameInfoMenu);
    }

    /**
     * Increment the current page value and calls the page population method.
     * If the page number exceeds the total number of pages, wrap the page value to the first page.
     */
    public void nextPage() {
        // Increment page number.
        currentInfoPage++;
        // Check if page number exceeds pages, wrap to first page if it does.
        if (currentInfoPage > FINAL_PAGE_VALUE) {
            currentInfoPage = 1;
        }
        // Populate the page information for the new page.
        populateFileInfo();
    }

    /**
     * Decrement the current page value and calls the page population method.
     * If the page number is lower than one (the first page), wrap the page value to the last page.
     */
    public void prevPage() {
        // Decrement the page number.
        currentInfoPage--;
        // Check if page number is lower than one (the first page), wrap to last page if it does.
        if (currentInfoPage < 1) {
            currentInfoPage = FINAL_PAGE_VALUE;
        }
        // Populate the page information for the new page.
        populateFileInfo();
    }

    // Takes a scanner object and moves the read line to the correct line of the Game Info text document.
    // Correct line for the current page = current page value * 7.
    // Equation: 1 (Page Number) + 2 (First Title and Info) + 2 (Second Title and Info) + 2 (Third Title and Info).
    private void skipToCorrectInfo(final Scanner fileReader) {
        // Skip lines to the info for the correct page.
        for (int page = 1; page < currentInfoPage; page++) {
            // Page Number.
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


    // Populates the labels and text areas on the Game Info menu.
    // Calls method to find the correct area in the text document then reads the next 7 lines to find the correct info.
    private void populateFileInfo() {
        // Try to read the correct page from the Game Info the text file.
        try {
            // Try to open the file in a scanner.
            Scanner fileReader = new Scanner(GAME_INFO_TEXT);
            // Call method to move the reader to the correct file location.
            skipToCorrectInfo(fileReader);
            // Populate the game title with the correct page number.
            gameInfoTitle.setText("Game Info: Page " + fileReader.nextLine() + " / 3");
            // Populate the first info title and text area.
            infoTitleOne.setText(fileReader.nextLine());
            infoAreaOne.setText(fileReader.nextLine());
            // Populate the second info title and text area.
            infoTitleTwo.setText(fileReader.nextLine());
            infoAreaTwo.setText(fileReader.nextLine());
            // Populate the third info title and text area.
            infoTitleThree.setText(fileReader.nextLine());
            infoAreaThree.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catches any errors opening the page.
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the parent layout element that scene will be displayed in and saves it to a variable.
     *
     * @param parentGrid The parent layout element of the Game Info scene
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    /**
     * Sets the starting page to the first page and calls the population method to display the first pages information.
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
