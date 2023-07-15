package baldursbones.bb;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Game Map & Player Map.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Map {

    // Constant: Define the starting X position of a character in a new game.
    private static final int STARTING_X = 0;

    // Constant: Define the starting Y position of a character in a new game.
    private static final int STARTING_Y = 6;

    // Constant: Define the size of the game map grid.
    private static final int MAP_SIZE = 7;

    // Constant: Define the location value of the tutorial square when beaten.
    private static final int BEATEN_TUTORIAL = 1;

    // Constant: Factor for dividing different areas of the game map.
    private static final int LOCATION_DIVIDER = 100;

    // Constant: The 2 ending digits of a locations value indicating a new explore location.
    private static final int NEW_EXPLORE_LOCATION = 11;

    // Constant: The 2 ending digits of a locations value indicating a found explore location.
    private static final int EXPLORE_LOCATION = 12;

    // Constant: The 2 ending digits of a locations value indicating a new combat location.
    private static final int NEW_COMBAT_LOCATION = 21;

    // Constant: The 2 ending digits of a locations value indicating an unbeaten combat location.
    private static final int COMBAT_LOCATION = 22;

    // Constant: The 2 ending digits of a locations value indicating a beaten combat location.
    private static final int BEATEN_LOCATION = 23;

    // Constant: Starting value of the game map of location values.
    private static final int[][] STARTING_MAP =
            {{311, 311, 321, 321, 321, 411, 500},
                    {221, 311, 311, 321, 321, 411, 411},
                    {211, 221, 221, 221, 321, 321, 321},
                    {121, 211, 211, 211, 221, 321, 321},
                    {121, 121, 121, 211, 221, 311, 321},
                    {111, 111, 121, 211, 221, 311, 311},
                    {0, 111, 121, 121, 211, 221, 311}};

    // Constant: Starting value of the players visual map.
    private static final String[][] STARTING_MAP_PLAYER =
            {{"?", "?", "?", "?", "?", "?", "!"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"?", "?", "?", "?", "?", "?", "?"},
                    {"0", "?", "?", "?", "?", "?", "?"}};

    // Variable: Records the X,Y values of the players last location.
    private int[] lastPlayerLocation;

    // Variable: Stored variable of the game map to be updated.
    private final int[][] mapArray;

    // Variable: Stored variable of the player map to be updated.
    private final String[][] playerMapArray;

    /**
     * Initializes a map object and sets the game map and player map to their starting values.
     */
    public Map() {
        lastPlayerLocation = new int[2];
        lastPlayerLocation[0] = STARTING_Y;
        mapArray = STARTING_MAP;
        playerMapArray = STARTING_MAP_PLAYER;
    }

    /**
     * Sets the last player location to the location passed to the map object.
     *
     * @param currentPlayerLocation A tuple representing the current location of the player
     */
    public void setPlayerLocation(final int[] currentPlayerLocation) {
        lastPlayerLocation = currentPlayerLocation;
    }

    /**
     * Takes the players location and updates the game and player map with found locations.
     *
     * @param playerLocation A tuple representing the current location of the player
     */
    public void updateMap(final int[] playerLocation) {
        // If the current location is an unexplored location, set it to be found.
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == NEW_EXPLORE_LOCATION) {
            playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
            mapArray[playerLocation[0]][playerLocation[1]] = EXPLORE_LOCATION;
        }

        // If the current location is an unbeaten combat location, set it to be found.
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == NEW_COMBAT_LOCATION) {
            playerMapArray[playerLocation[0]][playerLocation[1]] = "$";
            mapArray[playerLocation[0]][playerLocation[1]] = COMBAT_LOCATION;
        }
    }

    /**
     * Updates the Map Info grid with the current player map.
     *
     * @param mapDisplay the grid pane object to update with the map icons
     */
    public void displayMap(final GridPane mapDisplay) {
        // Iterate through the columns for each row. Row 1 (1-7), Row 2 (1-7) ...
        for (int row = 0; row < MAP_SIZE; row++) {
            for (int column = 0; column < MAP_SIZE; column++) {
                // ** TEMP OUTPUT ** Replace with images eventually.
                // Get the ascii icon used to represent space types and load it into a label to display.
                Label currentLocationIcon = new Label(playerMapArray[row][column]);
                // Load the icon into the correct spot of the map grid and update the map.
                // Maps work column row so the order is switched.
                GridPane.setConstraints(currentLocationIcon, column, row);
                mapDisplay.getChildren().add(currentLocationIcon);
            }
        }
    }

    /**
     * Updates the Location Menu min-map with the current player map.
     *
     * @param miniMapDisplay the grid pane object to update with the map icons
     */
    public void displayMiniMap(final GridPane miniMapDisplay) {
        // Used to track which row of the grid to update because grid is smaller than map.
        int showRow = 0;
        for (int row = lastPlayerLocation[0] + 1; row >= lastPlayerLocation[0] - 1; row--) {
            // Used to track which column of the grid to update because grid is smaller than map.
            int showColumn = 0;
            for (int column = lastPlayerLocation[1] + 1; column >= lastPlayerLocation[1] - 1; column--) {
                // Set label to out of map icon
                Label currentLocationIcon = new Label("X");
                // If the location is inside the map array then update the label to its icon.
                if (row >= 0 && row < MAP_SIZE && column >= 0 && column < MAP_SIZE) {
                    currentLocationIcon = new Label(playerMapArray[row][column]);
                }
                // Load the icon into the correct spot of the map grid and update the map.
                // Maps work column row so the order is switched.
                GridPane.setConstraints(currentLocationIcon, showRow, showColumn);
                miniMapDisplay.getChildren().add(currentLocationIcon);
                // Increment column to update.
                showColumn += 1;
            }
            // Increment row to update.
            showRow += 1;
        }
    }

    /**
     * Takes the player's current location and returns the location value of those coordinates.
     *
     * @return a three digit integer representing the location value
     */
    public int getLocation() {
        return mapArray[lastPlayerLocation[0]][lastPlayerLocation[1]];
    }

    /**
     * Update the game map with the correct value for a beaten combat location and update the player map.
     *
     * @param playerLocation the X,Y coordinates of the location to be updated
     */
    public void beatBattle(final int[] playerLocation) {
        // Set the value of the current player location in the game map
        // Set to (XYZ / 100 * 100 + 23).
        // This gets the location area and sets it to the beaten location value.
        mapArray[playerLocation[0]][playerLocation[1]] =
                (mapArray[playerLocation[0]][playerLocation[1]]
                        / LOCATION_DIVIDER) * LOCATION_DIVIDER + BEATEN_LOCATION;
        // Update the player map with a beaten location.
        playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
    }

    /**
     * Sets the tutorial locations value to be finished for future encounters.
     */
    public void beatTutorial() {
        mapArray[STARTING_Y][STARTING_X] = BEATEN_TUTORIAL;
    }

}

