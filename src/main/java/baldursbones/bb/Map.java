package baldursbones.bb;

/** Map.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class Map {
    private static final int STARTING_X = 0;
    private static final int STARTING_Y = 6;
    private static final int BEATEN_TUTORIAL = 13;
    private static final int LOCATION_DIVIDER = 100;
    private static final int UNEXPLORED_LOCATION = 11;
    private static final int UNBEATEN_LOCATION = 21;
    private static final int BEATEN_LOCATION = 23;
    private static final int[][] STARTING_MAP =
            {{311, 311, 321, 321, 321, 411, 500},
            {221, 311, 311, 321, 321, 411, 411},
            {211, 221, 221, 221, 321, 321, 321},
            {121, 211, 211, 211, 221, 321, 321},
            {121, 121, 121, 211, 221, 311, 321},
            {111, 111, 121, 211, 221, 311, 311},
            {11, 111, 121, 121, 211, 221, 311}};

    private static final String[][] STARTING_MAP_PLAYER =
            {{"?", "?", "?", "?", "?", "?", "X"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"?", "?", "?", "?", "?", "?", "?"},
            {"0", "?", "?", "?", "?", "?", "?"}};

    private int[] lastPlayerLocation;
    private final int[][] mapArray;
    private final String[][] playerMapArray;

    /** Initializes a map object and sets the starting event map and player map to initial values.
     */
    public Map() {
        lastPlayerLocation = new int[2];
        lastPlayerLocation[0] = STARTING_Y;
        mapArray = STARTING_MAP;
        playerMapArray = STARTING_MAP_PLAYER;
    }

    /** Sets the last player location to the players current location.
     * @param currentPlayerLocation The tuple of the players location
     */
    public void setPlayerLocation(final int[] currentPlayerLocation) {
        lastPlayerLocation = currentPlayerLocation;
    }
    /** Takes the players location and updates the event and player map accordingly.
     * @param playerLocation an integer tuple representing the players position on the map
     */
    public void updateMap(final int[] playerLocation) {
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == UNEXPLORED_LOCATION) {
            playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
            mapArray[playerLocation[0]][playerLocation[1]] += 1;
        }
        if (mapArray[playerLocation[0]][playerLocation[1]] % LOCATION_DIVIDER == UNBEATEN_LOCATION) {
            playerMapArray[playerLocation[0]][playerLocation[1]] = "!";
            mapArray[playerLocation[0]][playerLocation[1]] += 1;
        }
    }

    /** Displays the game map to the player.
     */
    public void displayMap() {
        for (int y = 0; y < mapArray.length; y++) {
            for (int x = 0; x < mapArray.length; x++) {
                if (x == lastPlayerLocation[1] && y == lastPlayerLocation[0]) {
                    System.out.print(" @ ");
                } else {
                    System.out.print(" " + playerMapArray[y][x] + " ");
                }
            }
            System.out.println();
        }
    }
    /** Takes the players current location and returns the event value of that location.
     * @return a three digit integer representing the location type
     */
    public int getLocation() {
        return mapArray[lastPlayerLocation[0]][lastPlayerLocation[1]];
    }

    /** Updates the map after a player beats an enemy encounter on that square.
     * @param playerLocation the tuple location to be updated
     */
    public void beatBattle(final int[] playerLocation) {
        mapArray[playerLocation[0]][playerLocation[1]] =
                (mapArray[playerLocation[0]][playerLocation[1]]
                        / LOCATION_DIVIDER) * LOCATION_DIVIDER + BEATEN_LOCATION;
        playerMapArray[playerLocation[0]][playerLocation[1]] = "#";
    }

    /** Sets the tutorial location as finished for future encounters.
     */
    public void beatTutorial() {
        mapArray[STARTING_Y][STARTING_X] = BEATEN_TUTORIAL;
    }
}

