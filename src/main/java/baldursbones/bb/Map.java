package baldursbones.bb;

/** Map.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Map {

    // Define the starting X position of a character in a new game.
    private static final int STARTING_X = 0;

    // Define the starting Y position of a character in a new game.
    private static final int STARTING_Y = 6;

    // Define the location value of the tutorial square when beaten.
    private static final int BEATEN_TUTORIAL = 1;

    // Factor for dividing different areas of the game map.
    private static final int LOCATION_DIVIDER = 100;

    // The 2 ending digits of a locations value indicating a new explore location.
    private static final int NEW_EXPLORE_LOCATION = 11;

    // The 2 ending digits of a locations value indicating a found explore location.
    private static final int EXPLORE_LOCATION = 12;

    // The 2 ending digits of a locations value indicating a new combat location.
    private static final int NEW_COMBAT_LOCATION = 21;

    // The 2 ending digits of a locations value indicating an unbeaten combat location.
    private static final int COMBAT_LOCATION = 22;

    // The 2 ending digits of a locations value indicating a beaten combat location.
    private static final int BEATEN_LOCATION = 23;

    // Starting value of the game map of location values.
    private static final int[][] STARTING_MAP =
            {{311, 311, 321, 321, 321, 411, 500},
            {221, 311, 311, 321, 321, 411, 411},
            {211, 221, 221, 221, 321, 321, 321},
            {121, 211, 211, 211, 221, 321, 321},
            {121, 121, 121, 211, 221, 311, 321},
            {111, 111, 121, 211, 221, 311, 311},
            {0, 111, 121, 121, 211, 221, 311}};

    // Starting value of the players visual map.
    private static final String[][] STARTING_MAP_PLAYER =
            {{"?", "?", "?", "?", "?", "?", "X"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"0", "?", "?", "?", "?", "?", "?"}};

    // Records the X,Y values of the players last location.
    private int[] lastPlayerLocation;

    // Stored variable of the game map to be updated.
    private final int[][] mapArray;

    // Stored variable of the player map to be updated.
    private final String[][] playerMapArray;

    /** Initializes a map object and sets the game map and player map to their starting values.
     */
    public Map() {
        lastPlayerLocation = new int[2];
        lastPlayerLocation[0] = STARTING_Y;
        mapArray = STARTING_MAP;
        playerMapArray = STARTING_MAP_PLAYER;
    }

    /** Sets the last player location to the location passed to the map object.
     * @param currentPlayerLocation A tuple representing the current location of the player
     */
    public void setPlayerLocation(final int[] currentPlayerLocation) {
        lastPlayerLocation = currentPlayerLocation;
    }

    /** Takes the players location and updates the game and player map with found locations.
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
            playerMapArray[playerLocation[0]][playerLocation[1]] = "!";
            mapArray[playerLocation[0]][playerLocation[1]] = COMBAT_LOCATION;
        }
    }

    /** Displays the game map to the player.
     */
    public void displayMap() {
        // For the row X.
        for (int x = 0; x < mapArray.length; x++) {
            // For column Y.
            for (int y = 0; y < mapArray.length; y++) {
                // If X,Y is player location print player value.
                if (x == lastPlayerLocation[0] && y == lastPlayerLocation[1]) {
                    System.out.print(" @ ");
                // Otherwise print the player map value.
                } else {
                    System.out.print(" " + playerMapArray[x][y] + " ");
                }
            }
            // Print a new line.
            System.out.println();
        }
    }

    /** Takes the player's current location and returns the location value of those coordinates.
     * @return a three digit integer representing the location value
     */
    public int getLocation() {
        return mapArray[lastPlayerLocation[0]][lastPlayerLocation[1]];
    }

    /** Update the game map with the correct value for a beaten combat location and update the player map.
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

    /** Sets the tutorial locations value to be finished for future encounters.
     */
    public void beatTutorial() {
        mapArray[STARTING_Y][STARTING_X] = BEATEN_TUTORIAL;
    }

}

