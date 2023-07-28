package baldursbones.bb;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


/**
 * Movement.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */

public class Movement {

    // Constant: Minimum valid Value of a Player Coordinate.
    private static final int MAP_LOWER_BOUNDARY = 0;

    // Constant: Maximum valid Value of a Player Coordinate.
    private static final int MAP_UPPER_BOUNDARY = 6;

    /**
     * Variable: The X,Y coordinates of the Location the Player moves too.
     */
    protected int[] location;

    // FXML Element: The Parent Layout Element the Movement class is being used by.
    private final GridPane container;

    /**
     * Initializes a Movement object and sets the Parent Layout Element of the Class.
     *
     * @param parentElement The Parent's Layout Element for the Controller using the Movement Class
     */
    public Movement(final GridPane parentElement) {
        container = parentElement;
    }

    /**
     * Update the Player Coordinates with new Values from the Player object.
     *
     * @param playerLocation The X & Y Coordinates of the current Player Location.
     */
    public void setLocation(final int[] playerLocation) {
        location = playerLocation;
    }

    /**
     * Checks if moving North is possible for the current Player Location.
     * If the movement is possible, decrease the Row value of the Player Location.
     *
     * @return The updated X & Y Coordinates of the new Player Location
     */
    protected int[] moveNorth() {
        if (location[0] - 1 < MAP_LOWER_BOUNDARY) {
            // If: the new Player Coordinate will put the Player Location outside the map area.
            cantMove();
        } else {
            // Else: update the Player Coordinates.
            location[0] = location[0] - 1;
        }
        // Return the Coordinate Values regardless of if they were updated.
        return location;
    }

    /**
     * Checks if moving South is possible for the current Player Location.
     * If the movement is possible, increase the Row value of the Player Location.
     *
     * @return The updated X & Y Coordinates of the new Player Location
     */
    protected int[] moveSouth() {
        if (location[0] + 1 > MAP_UPPER_BOUNDARY) {
            // If: the new Player Coordinate will put the Player Location outside the map area.
            cantMove();
        } else {
            // Else: update the Player Coordinates.
            location[0] = location[0] + 1;
        }
        // Return the Coordinate Values regardless of if they were updated.
        return location;
    }

    /**
     * Checks if moving West is possible for the current Player Location.
     * If the movement is possible, decrease the Column value of the Player Location.
     *
     * @return The updated X & Y Coordinates of the new Player Location
     */
    protected int[] moveWest() {
        if (location[1] - 1 < MAP_LOWER_BOUNDARY) {
            // If: the new Player Coordinate will put the Player Location outside the map area.
            cantMove();
        } else {
            // Else: update the Player Coordinates.
            location[1] = location[1] - 1;
        }
        // Return the Coordinate Values regardless of if they were updated.
        return location;
    }

    /**
     * Checks if moving East is possible for the current Player Location.
     * If the movement is possible, increase the Column value of the Player Location.
     *
     * @return The updated X & Y Coordinates of the new Player Location
     */
    protected int[] moveEast() {
        if (location[1] + 1 > MAP_UPPER_BOUNDARY) {
            // If: the new Player Coordinate will put the Player Location outside the map area.
            cantMove();
        } else {
            // Else: update the Player Coordinates.
            location[1] = location[1] + 1;
        }
        // Return the Coordinate Values regardless of if they were updated.
        return location;
    }

    /**
     * Informs the player of an invalid movement and resets the player movement choice.
     */
    protected void cantMove() {
        TextArea descriptionArea = (TextArea) container.lookup("#GameDescription");
        descriptionArea.setText("Invalid movement. Out of Bounds.\n");
        descriptionArea.appendText("Movement Text.");
    }

}
