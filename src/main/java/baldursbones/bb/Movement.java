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

    // Constant: Minimum valid value of a player coordinate.
    private static final int MAP_LOWER_BOUNDARY = 0;

    // Constant: Maximum valid value of a player coordinate.
    private static final int MAP_UPPER_BOUNDARY = 7;

     // FXML Element: The layout element of the controller that created the Location object.
    private final GridPane container;

    /**
     * Variable: The X,Y coordinates of the new location.
     */
    protected int[] location;

    /**
     * Initializes a movement object and sets the player coordinates to their starting values.
     */
    public Movement(final GridPane parentElement) {
        container = parentElement;
    }

    public void setLocation(final int[] currentPlayerLocation) {
        location = currentPlayerLocation;
    }

    /**
     * Checks if an upward move is possible and either updates player coordinates or returns the current ones.
     *
     * @return the updated X,Y coordinates of the players locations
     */
    protected int[] moveNorth() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[0] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();
            // Otherwise update the player location and return the new value.
        } else {
            location[0] = location[0] - 1;
        }
        return location;
    }

    /**
     * Checks if a downward move is possible and either updates player coordinates or returns the current ones.
     *
     * @return the updated X,Y coordinates of the players locations
     */
    protected int[] moveSouth() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[0] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();

            // Otherwise update the player location and return the new value.
        } else {
            location[0] = location[0] + 1;
        }
        return location;
    }

    /**
     * Checks if a left move is possible and either updates player coordinates or returns the current ones.
     *
     * @return the updated X,Y coordinates of the players locations
     */
    protected int[] moveWest() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[1] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();

            // Otherwise update the player location and return the new value.
        } else {
            location[1] = location[1] - 1;
        }
        return location;
    }

    /**
     * Checks if a right move is possible and either updates player coordinates or returns the current ones.
     *
     * @return the updated X,Y coordinates of the players locations
     */
    protected int[] moveEast() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[1] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();

            // Otherwise update the player location and return the new value.
        } else {
            location[1] = location[1] + 1;
        }
        return location;
    }

    /**
     * Informs the player of an invalid movement and resets the player movement choice.
     */
    protected void cantMove() {
        TextArea descriptionArea = (TextArea) container.lookup("#GameTextArea");
        descriptionArea.setText("Invalid movement. Out of Bounds.");
    }

}
