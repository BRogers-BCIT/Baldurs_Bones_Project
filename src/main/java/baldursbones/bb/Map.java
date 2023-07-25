package baldursbones.bb;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.InputStream;
import java.util.Objects;

/**
 * Game Map & Player Map.
 * Controller: Location Menu
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Map {

    // Constant: The size of each Cell of the Map Grid Pane Element.
    private static final int IMAGE_SIZE = 30;

    // Constant: The column of the Tutorial Location in the Game Map.
    private static final int TUTORIAL_COLUMN = 0;

    // Constant: The row of the Tutorial Location in the Game Map.
    private static final int TUTORIAL_ROW = 6;

    // Constant: Define the Maximum Row and Column Values of the Game Map and Player Map grids.
    private static final int MAP_SIZE = 7;

    // Constant: Define the Location Value to assign to the Tutorial Location when it is beaten.
    private static final int BEATEN_TUTORIAL = 1;

    // Constant: Define the factor for removing the Location Area digit from the Location Value.
    private static final int LOCATION_DIVIDER = 100;

    // Constant: The 2 ending digits of a Location Value indicating a non-visited Explore Location.
    private static final int NEW_EXPLORE_LOCATION = 11;

    // Constant: The 2 ending digits of a Location Value indicating a visited Explore Location.
    private static final int EXPLORE_LOCATION = 12;

    // Constant: The 2 ending digits of a Location Value indicating a non-visited Combat Location.
    private static final int NEW_COMBAT_LOCATION = 21;

    // Constant: The 2 ending digits of a Location Value indicating a visited, non-beaten, Combat Location.
    private static final int COMBAT_LOCATION = 22;

    // Constant: The 2 ending digits of a Location Value indicating a beaten Combat Location.
    private static final int BEATEN_LOCATION = 23;

    // Constant: Starting Location Values of each Cell in the Game Map Grid.
    private static final int[][] STARTING_MAP =
            {{311, 311, 321, 321, 321, 411, 500},
                    {221, 311, 311, 321, 321, 411, 411},
                    {211, 221, 221, 221, 321, 321, 321},
                    {121, 211, 211, 211, 221, 321, 321},
                    {121, 121, 121, 211, 221, 311, 321},
                    {111, 111, 121, 211, 221, 311, 311},
                    {0, 111, 121, 121, 211, 221, 311}};

    // Constant: The starting ASCII values of each Cell of the Player Map Grid.
    private static final String[][] STARTING_MAP_PLAYER =
            {{"?", "?", "?", "?", "?", "?", "X"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"0", "?", "?", "?", "?", "?", "?"}};

    // Variable: Records the X & Y Values of the Player Location.
    private int[] playerLocation;

    // Variable: The 2D array that tracks the Location Values of each Cell in the Game Map grid.
    private final int[][] mapArray;

    // Variable: The 2D array that tracks the ASCII Values of each Cell in the Player Map grid.
    private final String[][] playerMapArray;

    /**
     * Initializes a new map object by setting the Game Map and the Player Map to their starting values.
     */
    public Map() {
        mapArray = STARTING_MAP;
        playerMapArray = STARTING_MAP_PLAYER;
    }

    /**
     * Sets the last Player Location tuple to the current X & Y values of the Player object.
     *
     * @param currentPlayerLocation A tuple representing the X & Y values of the Players Location
     */
    public void setPlayerLocation(final int[] currentPlayerLocation) {
        playerLocation = currentPlayerLocation;
    }

    /**
     * Updates the Game Map and Player map whenever a Player moves to a Location.
     * If the Location Value is match a non-visited Location, then the Maps will be updated.
     */
    public void updateMap() {
        // Get the Location Value of the current Player Location and Mod by 100 to find the last 2 digits.
        // If the Digits match a not visited Explore Location then update the Map.
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == NEW_EXPLORE_LOCATION) {
            // If: the current Location is an unexplored non-Combat Location, set it to be found.
            mapArray[playerLocation[0]][playerLocation[1]] = EXPLORE_LOCATION;
            playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
        }
        // Get the Location Value of the current Player Location and Mod by 100 to find the last 2 digits.
        // If the Digits match a not visited Combat Location then update the Map.
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == NEW_COMBAT_LOCATION) {
            // If: the current Location is an unexplored Combat Location, set it to be found.
            mapArray[playerLocation[0]][playerLocation[1]] = COMBAT_LOCATION;
            playerMapArray[playerLocation[  0]][playerLocation[1]] = "!";
        }
    }

    /**
     * Takes a 8x8 Grid Pane object and updates Rows and Columns 1-7 with the Player Map.
     * Takes the ASCII values from the Player Map array and converts them to images to display.
     *
     * @param mapDisplay The Grid Pane Element to insert the images into
     */
    public void displayMap(final GridPane mapDisplay) {
        // Iterate through each Row of the Map.
        for (int row = 0; row < MAP_SIZE; row++) {
            // For each Row, iterate through each Column of the Map
            for (int column = 0; column < MAP_SIZE; column++) {
                // Record the current Row and Column Coordinates as a tuple.
                int[] currentCoordinates = {row, column};
                // Define an Image View object to be inserted and an Image object for the Image View to use.
                ImageView currentLocationIcon = new ImageView();
                Image cellImage;
                // If: the current Coordinates match the Player Coordinates.
                if (playerLocation[0] == currentCoordinates[0] && playerLocation[1] == currentCoordinates[1]) {
                    // Get the Image assosiated with the Player ASCII Character "@".
                    cellImage = new Image(asciiToImage("@"), IMAGE_SIZE, IMAGE_SIZE, true, true);
                } else {
                    // Else: get the Image assosiated with the ASCII Character in the current Coordinates.
                    cellImage = new Image(asciiToImage(playerMapArray[row][column]),
                            IMAGE_SIZE, IMAGE_SIZE, true, true);
                }
                // Load the Image for the current Cell into the Image View to be displayed.
                currentLocationIcon.setImage(cellImage);
                // Set the Row and Column for the current Image to display in.
                // Grid Panes work Column then Row so switch usage order.
                // Increase Column and Row values by 1 to allow for Coordinate marking on map.
                GridPane.setConstraints(currentLocationIcon, column + 1, row + 1);
                // Insert the new Image into the Grid Pane.
                mapDisplay.getChildren().add(currentLocationIcon);
            }
        }
    }

    /**
     * Takes a 3x3 Grid Pane object and updates it with the Cells around the current Player Location.
     * Takes the ASCII values from the Player Map array and converts them to images to display.
     *
     * @param miniMapDisplay The Grid Pane Element to insert the images into
     */
    public void displayMiniMap(final GridPane miniMapDisplay) {
        // Remove the old Cell Images from the Map.
        miniMapDisplay.getChildren().clear();
        // Create a value to track which Row of the Grid Pane is being updated.
        int showRow = 0;
        // Starting iterating through Rows at one Row before the Player Row, and stopping one Row after.
        for (int row = playerLocation[0] - 1; row <= playerLocation[0] + 1; row++) {
            // Create a value to track which Column of the Grid Pane is being updated.
            int showColumn = 0;
            // Starting iterating through Columns at one Column before the Player Column, and stopping one Column after.
            for (int column = playerLocation[1] - 1; column <= playerLocation[1] + 1; column++) {
                // Record the current Row and Column Coordinates as a tuple.
                int[] currentCoordinates = {row, column};
                // Set the Cell Image to the Out-Of-Bounds Image using the O-O-B ASCII Character "/".
                // This Image will be updated if the current Coordinates fall inside the Game Map.
                Image cellImage = new Image(asciiToImage("/"), IMAGE_SIZE, IMAGE_SIZE, true, true);
                // Define the new Image View for the current Grid Cell.
                ImageView currentLocationIcon = new ImageView();
                // If: the current Coordinates match the Player Coordinates.
                if (playerLocation[0] == currentCoordinates[0] && playerLocation[1] == currentCoordinates[1]) {
                    // Get the Image assosiated with the Player ASCII Character "@".
                    cellImage = new Image(asciiToImage("@"), IMAGE_SIZE, IMAGE_SIZE, true, true);
                } else if (row >= 0 && row < MAP_SIZE && column >= 0 && column < MAP_SIZE) {
                    // Else If: the current Coordinates fall inside the Game Map:
                    // Get the Image assosiated with the ASCII Character in the current Coordinates.
                    cellImage = new Image(asciiToImage(playerMapArray[row][column]),
                            IMAGE_SIZE, IMAGE_SIZE, true, true);
                }
                // Load the Cell Image into the Image View to be displayed.
                currentLocationIcon.setImage(cellImage);
                // Set the Row and Column for the current Image to display in.
                // Grid Panes work Column then Row so switch usage order.
                GridPane.setConstraints(currentLocationIcon, showColumn, showRow);
                // Insert the new Image into the Grid Pane.
                miniMapDisplay.getChildren().add(currentLocationIcon);
                // Increment the Column being checked.
                showColumn += 1;
            }
            // Increment the Row being checked.
            showRow += 1;
        }
    }

    /**
     * Returns the Location Value of the cell matching the Player Coordinates.
     *
     * @return A three digit integer representing the Location Value of the current Player Cell.
     */
    public int getLocation() {
        return mapArray[playerLocation[0]][playerLocation[1]];
    }

    /**
     * Update the Maps with the correct values for a beaten Combat Location at the current Player Cell.
     */
    public void beatBattle() {
        // Set the new Location Area Value for the Game Map
        // New Location Value equals (Old Value / 100 * 100 = Location Area)
        int beatenLocationValue = mapArray[playerLocation[0]][playerLocation[1]] / LOCATION_DIVIDER * LOCATION_DIVIDER;
        // Update the Location Value with the Location Area Value + the Beaten Combat Type & Status Value.
        mapArray[playerLocation[0]][playerLocation[1]] = beatenLocationValue + BEATEN_LOCATION;
        // Update the Player Map with a beaten Location ASCII Character.
        playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
    }

    /**
     * Set the Tutorial Location Value to be visited and beaten.
     */
    public void beatTutorial() {
        mapArray[TUTORIAL_ROW][TUTORIAL_COLUMN] = BEATEN_TUTORIAL;
    }

    // Convert the ASCII Character's from the Player Map to Images by Returning an Image File path.
    // Returned Images are used to display the Player Map.
    private InputStream asciiToImage(final String playerMapAscii) {
        // Define the File path string.
        String fileName;
        if (Objects.equals(playerMapAscii, "0")) {
            // "0" = Tutorial Location
            fileName = "tempStartImage.png";
        } else if (Objects.equals(playerMapAscii, "X")) {
            // "0" = Boss Location
            fileName = "tempBossImage.png";
        } else if (Objects.equals(playerMapAscii, "?")) {
            // "0" = Un-Explored Location
            fileName = "tempNewImage.png";
        } else if (Objects.equals(playerMapAscii, "!")) {
            // "0" = Un-Beaten Combat Location
            fileName = "tempEnemyImage.png";
        } else if (Objects.equals(playerMapAscii, "#")) {
            // "0" = Beaten Combat / Explored Location
            fileName = "tempFoundImage.png";
        } else if (Objects.equals(playerMapAscii, "@")) {
            // "@" = Player Location
            fileName = "tempPlayerImage.png";
        } else {
            // Non-Matching Values = Out Of Bounds
            // Mini-Map passes "/" for O-O-B.
            fileName = "tempBoundsImage.png";
        }
        // Return an Input Stream for the Image file.
        return getClass().getResourceAsStream(fileName);
    }
}

