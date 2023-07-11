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

    // Static location of the game info text file to read from.
    private static final File GAME_INFO_TEXT = new File("src/main/resources/baldursbones/bb/GameInfoText.txt");

    // The number of "pages" in the text file, used to wrap final page into first page.
    private static final int FINAL_PAGE_VALUE = 3;

    // tracks which page of game information to display.
    private int currentInfoPage;

    // The parent element the game info menu is displayed in.
    private GridPane container;

    // The layout element for the game info menu.
    @FXML
    private HBox gameInfoMenu;

    // The title of the game info page with current page number
    @FXML
    private Label gameInfoTitle;

    // The title of the first game info text area
    @FXML
    private Label infoTitleOne;

    // The title of the first game info text area
    @FXML
    private Label infoTitleTwo;

    // The title of the first game info text area
    @FXML
    private Label infoTitleThree;

    // The text area for the first set of game info
    @FXML
    private TextArea infoAreaOne;

    // The text area for the second set of game info
    @FXML
    private TextArea infoAreaTwo;

    // The text area for the third set of game info
    @FXML
    private TextArea infoAreaThree;

    /**
     * Removes the game info menu layout from the current menu and makes the buttons clickable again.
     */
    @FXML
    public void closeGameInfoMenu() {
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
        // Remove the game info menu from the main menu window.
        container.getChildren().remove(gameInfoMenu);
    }

    /**
     * Sets the current page to be one higher and calls the page population method. Last page wraps to first page.
     */
    public void nextPage() {
        currentInfoPage++;
        if (currentInfoPage > FINAL_PAGE_VALUE) {
            currentInfoPage = 1;
        }
        populateFileInfo();
    }

    /**
     * Sets the current page to be one lower and calls the page population method. First page wraps to last page.
     */
    public void prevPage() {
        currentInfoPage--;
        if (currentInfoPage < 1) {
            currentInfoPage = FINAL_PAGE_VALUE;
        }
        populateFileInfo();
    }

    // Takes the scanner class and skips it to read from the correct area.
    private void skipToCorrectInfo(final Scanner fileReader) {
        // Skip lines to the info for the correct page.
        for (int page = 1; page < currentInfoPage; page++) {
            // Page Number.
            fileReader.nextLine();
            // Info Title and Area One.
            fileReader.nextLine();
            fileReader.nextLine();
            // Info Title and Area Two.
            fileReader.nextLine();
            fileReader.nextLine();
            // Info Title and Area Three.
            fileReader.nextLine();
            fileReader.nextLine();
        }
    }


    // Populates the labels and text areas on the menu with the correct info by reading from a text document.
    // @throws RuntimeException if the file being loaded to read from does not exist
    private void populateFileInfo() {
        // Try to read the correct page from the game info the text file.
        try {
            // try to open the file in a scanner.
            Scanner fileReader = new Scanner(GAME_INFO_TEXT);
            // Move the reader to the correct file location
            skipToCorrectInfo(fileReader);
            // Populate the game title with the correct page number.
            gameInfoTitle.setText("Game Info: Page " + fileReader.nextLine() + " / 3");
            // Populate the information titles and text areas.
            infoTitleOne.setText(fileReader.nextLine());
            infoAreaOne.setText(fileReader.nextLine());
            infoTitleTwo.setText(fileReader.nextLine());
            infoAreaTwo.setText(fileReader.nextLine());
            infoTitleThree.setText(fileReader.nextLine());
            infoAreaThree.setText(fileReader.nextLine());
        } catch (FileNotFoundException e) {
            // Catch any errors opening the page.
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes the parent element that the layout will be displayed in and saves it.
     *
     * @param parentGrid The parent element of the settings menu layout
     */
    public void getContainerElement(final GridPane parentGrid) {
        container = parentGrid;
    }

    /**
     * Sets the starting page to populate information from and calls the population method.
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
