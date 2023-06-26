package baldursbones.bb;

import java.util.Scanner;

/** Movement.
 * @author Jaskarn Bhatti
 * @version 2023-TermProject
 */

public class Movement {
    private static final int[] START_LOCATION = {7, 0};
    private static final int MAP_LOWER_BOUNDARY = 0;
    private static final int MAP_UPPER_BOUNDARY = 7;
    private static final int MOVE_UP = 1;
    private static final int MOVE_DOWN = 2;
    private static final int MOVE_LEFT = 3;
    private static final int MOVE_RIGHT = 4;

    /** The tuple of the location moved to.
     */
    protected int[] location;
    /** The value of the players choice.
     */
    protected int playerChoice;
    /** Initializes the location to the initial start coordinates.
     */
    public Movement() {
        location = START_LOCATION;
    }

    /** Creates loop to allow player to move until they enter a valid movement.
     * @param currentLocation the tuple of the current player location
     * @return the tuple of the new player location
     */
    public int[] playerMove(final int[] currentLocation) {
        Scanner scan = new Scanner(System.in);
        location = currentLocation;
        playerChoice = 0;
        while (playerChoice == 0) {
            System.out.println("Select the movement you want to make");
            System.out.println("1 - Up, 2 - Down");
            System.out.println("3 - Left, 4 - Right");
            playerChoice = scan.nextInt();
            location = playerMoveDirector();
        }
        return location;
    }

    /** Calls the movement method based on the player choice value.
     * @return the tuple of the new player location
     */
    private int[] playerMoveDirector() {
        if (playerChoice == MOVE_UP) {
            return moveUp();
        } else if (playerChoice == MOVE_DOWN) {
            return moveDown();
        } else if (playerChoice == MOVE_LEFT) {
            return moveLeft();
        } else if (playerChoice == MOVE_RIGHT) {
            return moveRight();
        } else {
            System.out.println("That is an invalid choice.");
            return START_LOCATION;
        }
    }

    /** Takes value moved up and updates players Location coordinates.
     * @return updated int array coordinates representing location
     */
    private int[] moveUp() {
        // Because 2d array starts from 0 and goes down going up the map y coordinate would get smaller
        if (location[0] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();
        } else {
            location[0] = location[0] - 1;
        }
        return location;
    }

    /** Takes value moved down and updates players Location coordinates.
     * @return updated int array coordinates representing location
     */
    private int[] moveDown() {
        // going down the 2d array the y values get bigger
        if (location[0] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();
        } else {
            location[0] = location[0] + 1;
        }
        return location;
    }

    /** Takes value moved left and updates players Location coordinates.
     * @return updated int array coordinates representing location
     */
    private int[] moveLeft() {
        if (location[1] - 1 < MAP_LOWER_BOUNDARY) {
            cantMove();
        } else {
            location[1] = location[1] - 1;
        }
        return location;
    }

    /** Takes value moved right and updates players Location coordinates.
     * @return updated int array coordinates representing location
     */
    private int[] moveRight() {
        if (location[1] + 1 >= MAP_UPPER_BOUNDARY) {
            cantMove();
        } else {
            location[1] = location[1] + 1;
        }
        return location;
    }

    /** Prints out message if the player cant move in that direction.
     */
    protected void cantMove() {
        playerChoice = 0;
        System.out.println("\nYou cannot move in that direction.\n");
    }

}
