package baldursbones.bb;

import java.util.Random;
import java.util.Scanner;

/** Combat.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class Combat {
   /** The maximum (exclusive) value for a roll.
   */
   protected static final int ROLL_MAX = 7;
   /** The total value at which a player will always lose.
    */
   protected static final int TOTAL_BUST = 21;
   /** A value from which a player can never win. Prevents infinite rolling.
    */
   protected static final int TOTAL_MAX = 30;
   /** The integer value for the roll choice for a player.
    */
   protected static final int ROLL = 1;
   /** The integer value for the re-roll choice for a player.
    */
   protected static final int RE_ROLL = 2;
   /** The integer value for the add choice for a player.
    */
   protected static final int ADD = 3;
   /** The integer value for the take-away choice for a player.
    */
   protected static final int TAKE_AWAY = 4;
   /** The player object representing the player character.
   */
   protected final Player pc;
   /** The enemy object representing the enemy being fought.
    */
   protected final Enemy enemy;
   /** The players current roll total.
    */
   protected int playerRoll;
   /** The players last current roll value.
    */
   protected int lastRoll;
   /** The players current choice value.
    */
   protected int playerChoice;
   /** The outcome value of the combat.
    */
   protected int outcome;
    /** Creates a new combat class with the player and the passed enemy.
     * @param player the players character object
     * @param newEnemy the enemy object the player should play against
     */
   public Combat(final Player player, final Enemy newEnemy) {
       pc = player;
       enemy = newEnemy;
       playerRoll = 0;
       playerChoice = 1;
       outcome = 0;
   }

    /** Starts the combat cycle and returns outcome when finished.
     * @return the integer outcome value of the combat
     */
   public int startCombat() {
       System.out.println("You have begun a game of Baldur's Bones.");
       System.out.println("Your current stats are as follows.");
       pc.getStats();
       Scanner scan = new Scanner(System.in);
       System.out.println("Enter to continue");
       scan.nextLine();
       startRoll();
       playerChoose();
       finishGame();
       return outcome;
   }

    /** Sets the players roll to its starting value and informs them of their rolls.
     */
   protected void startRoll() {
       playerRoll = 0;
       Random rand = new Random();
       System.out.println("To start the game you roll:");
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerRoll += lastRoll;
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerRoll += lastRoll;
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerRoll += lastRoll;
   }

    /** Creates loop to allow player to take game actions until they quit or cannot win.
     */
   protected void playerChoose() {
       Scanner scan = new Scanner(System.in);
       while (playerChoice != 0 && playerRoll < TOTAL_MAX) {
           System.out.println();
           if (playerRoll > TOTAL_BUST) {
               System.out.println("Your current roll is over 21. "
                       + "You will lose if you do not use a cheat to reduce your total.");
           }
           System.out.println("Your current roll total is: " + playerRoll);
           System.out.println("Your last roll was: " + lastRoll);
           System.out.println("Select the move you want to make");
           System.out.println("1 - Roll, 2 - ReRoll");
            if (pc.getLevel() > 1) {
                System.out.println("3 - Add, 4 - Take Away");
            }
            System.out.println("0 - Hold");
            playerChoice = scan.nextInt();
            if (playerChoice != 0) {
                playerChoiceDirector();
            }
       }
       System.out.println("Your ending rolled total was " + playerRoll + ".");
       if (playerRoll > TOTAL_BUST) {
           playerBust();
       }
   }
    private void playerBust() {
       System.out.println("You ended the round with a roll higher than 21.");
       System.out.println("You will lose the current round.");
       playerRoll = 0;
    }
    /** Calls the game action method based on the player choice value.
     */
   protected void playerChoiceDirector() {
       if (playerChoice == ROLL) {
           playerRoll();
       } else if (playerChoice == RE_ROLL) {
           playerReRoll();
       } else if (playerChoice == ADD && pc.getLevel() > 1) {
           playerAdd();
       } else if (playerChoice == TAKE_AWAY && pc.getLevel() > 1) {
           playerTakeAway();
       } else if (playerChoice != 0) {
           System.out.println("\n That is an invalid choice! \n");
       }
   }

    /** Gets the enemy roll and compares it to the player, then sets the outcome.
     */
   protected void finishGame() {
       outcome = enemy.compareRoll(playerRoll);
   }

    /** Makes a new roll and adds it to the players total.
     */
   protected void playerRoll() {
       Random rand = new Random();
       lastRoll = rand.nextInt(1, ROLL_MAX);
       playerRoll += lastRoll;
       System.out.println("You rolled a " + lastRoll + ".");
   }

    /** Removes the last roll from player total and add a new roll to the player total.
     */
   protected void playerReRoll() {
       if (pc.useReRoll()) {
           System.out.println("You removed " + lastRoll + ".");
           playerRoll -= lastRoll;
           Random rand = new Random();
           lastRoll = rand.nextInt(1, ROLL_MAX);
           playerRoll += lastRoll;
           System.out.println("You rolled a " + lastRoll + ".");
       }
   }

    /** Adds 1 to the players total.
     */
    protected void playerAdd() {
        if (pc.useAdd()) {
            playerRoll += 1;
            System.out.println("You added 1 to your total roll.");
        }
   }

    /** Take 1 away from the players total.
     */
   protected void playerTakeAway() {
       if (pc.useTakeAway()) {
           playerRoll -= 1;
           System.out.println("You removed 1 from your total roll.");
       }
   }
}
