package baldursbones.bb;

import java.util.Scanner;

/** Movement.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */

public class Movement {

    // Starting location of the player character.
    private static final int[] START_LOCATION = {7, 0};

    // Minimum valid value of a player coordinate.
    private static final int MAP_LOWER_BOUNDARY = 0;

    // Maximum valid value of a player coordinate.
    private static final int MAP_UPPER_BOUNDARY = 7;

    // Integer value representing a player choice to move upwards.
    private static final int MOVE_UP = 1;

    // Integer value representing a player choice to move downwards.
    private static final int MOVE_DOWN = 2;

    // Integer value representing a player choice to move left.
    private static final int MOVE_LEFT = 3;

    // Integer value representing a player choice to move right.
    private static final int MOVE_RIGHT = 4;

    /** The X,Y coordinates of the new location.
     */
    protected int[] location;

    /** The integer value used to save the players movement choice.
     */
    protected int playerChoice;

    /** Initializes a movement object and sets the player coordinates to their starting values.
     */
    public Movement() {
        location = START_LOCATION;
    }

    /** Creates loop to let players choose a movement option until they enter a valid movement.
     * @param currentLocation the X,Y coordinates of the current player location
     * @return the X,Y coordinates of the new player location
     */
    public int[] playerMove(final int[] currentLocation) {
        // Record the current location and reset the movement choice.
        Scanner scan = new Scanner(System.in);
        location = currentLocation;
        playerChoice = 0;

        // While the player choice is 0 let the player choose a movement
        while (playerChoice == 0) {
            System.out.println("Select the movement you want to make");
            System.out.println("1 - Up, 2 - Down");
            System.out.println("3 - Left, 4 - Right");
            playerChoice = scan.nextInt();
            // Call the method to check valid player movements.
            location = playerMoveDirector();
        }

        // Return the new player location.
        return location;
    }

    /** Calls the movement method related to the player choice value.
     * @return the X,Y coordinates of the new player location
     */
    private int[] playerMoveDirector() {
        if (playerChoice == MOVE_UP) {
            // Call the move upwards method.
            return moveUp();

        } else if (playerChoice == MOVE_DOWN) {
            // Call the move downwards method.
            return moveDown();

        } else if (playerChoice == MOVE_LEFT) {
            // Call the move left method.
            return moveLeft();

        } else if (playerChoice == MOVE_RIGHT) {
            // Call the move right method.
            return moveRight();

        // If no valid movement was passed then reset the player choice and return their current location.
        } else {
            System.out.println("That is an invalid choice.");
            return START_LOCATION;
        }
    }

    /** Checks if an upward move is possible and either updates player coordinates or returns current ones.
     * @return the updated X,Y coordinates of the players locations
     */
    private int[] moveUp() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[0] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();

        // Otherwise update the player location and return the new value.
        } else {
            location[0] = location[0] - 1;
        }
        return location;
    }

    /** Checks if a downward move is possible and either updates player coordinates or returns current ones.
     * @return the updated X,Y coordinates of the players locations
     */
    private int[] moveDown() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[0] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();

        // Otherwise update the player location and return the new value.
        } else {
            location[0] = location[0] + 1;
        }
        return location;
    }

    /** Checks if a left move is possible and either updates player coordinates or returns current ones.
     * @return the updated X,Y coordinates of the players locations
     */
    private int[] moveLeft() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[1] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();

        // Otherwise update the player location and return the new value.
        } else {
            location[1] = location[1] - 1;
        }
        return location;
    }

    /** Checks if a right move is possible and either updates player coordinates or returns current ones.
     * @return the updated X,Y coordinates of the players locations
     */
    private int[] moveRight() {
        // If the movement would put the player out of the map call the "cannot move" method.
        if (location[1] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();

        // Otherwise update the player location and return the new value.
        } else {
            location[1] = location[1] + 1;
        }
        return location;
    }

    /** Informs the player of an invalid movement and resets the player movement choice.
     */
    protected void cantMove() {
        playerChoice = 0;
        System.out.println("Invalid move.");
    }

}
