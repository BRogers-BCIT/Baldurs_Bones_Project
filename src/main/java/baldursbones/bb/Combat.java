package baldursbones.bb;

import java.util.Random;
import java.util.Scanner;

/** Combat.
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Combat {

   /** Constant: The maximum (exclusive) value for a roll. Limit rolls to 1 - 6.
    */
   protected static final int ROLL_MAX = 7;

   /** Constant: The highest valid value in a round of combat. Exceeding Total_Bust will result in the player losing.
    */
   protected static final int TOTAL_BUST = 21;


   /** Constant: a ceiling value to stop player from rolling forever (No use of abilities can result in valid total).
    */
   protected static final int TOTAL_MAX = 30;

   /** Constant: The value used to indicate a player choice of adding a new roll.
    */
   protected static final int ROLL = 1;

   /** Constant: The value used to indicate a player choice of holding their current total.
    */
   protected static final int HOLD = 2;

   /** Constant: The value used to indicate a player choice of adding one to their total.
    */
   protected static final int ADD = 3;

   /** Constant: The value used to indicate a player choice of subtracting one from their total.
    */
   protected static final int TAKE_AWAY = 4;

    /** Constant: The value used to indicate a player choice of re-rolling their last roll.
     */
    protected static final int RE_ROLL = 5;

   /** Object: The player object representing current the player character.
   */
   protected final Player pc;

   /** Object: The enemy object representing the current enemy being fought.
    */
   protected final Enemy enemy;

   /** Variable: The players current roll total.
    */
   protected int playerTotal;

   /** Variable: The value of the players last die rolled by the player.
    */
   protected int lastRoll;

   /** The value that records what the player choose to do.
    */
   protected int playerChoice;

   /** The value used to record the outcome of the combat (-1 = player loses, 1 = player wins).
    */
   protected int outcome;

   /** Create a new combat and sets initial values.
    * Takes a player character object and a new enemy object.
    * @param player the current player character object
    * @param newEnemy a new enemy object for the player to play against
    */
   public Combat(final Player player, final Enemy newEnemy) {
       pc = player;
       enemy = newEnemy;
       playerTotal = 0;
       playerChoice = 1;
       outcome = 0;
   }

    /** Print the player stats, get the starting rolls and call the game choice loop.
     * When the loop ends call the combat end method and return the outcome.
     * @return an integer value used to represent the outcome of the combat
     */
   public int startCombat() {
       // Print current player stats and prompt player to continue.
       Scanner scan = new Scanner(System.in);
       System.out.println("Begin Combat.");
       System.out.println("Current Stats: ");
       pc.getStats();
       System.out.println("Enter to continue");
       scan.nextLine();

       // Get the starting rolls for the player and call the game choice loop.
       startRoll();
       playerChoose();

       // Get the outcome of the combat and return it.
       finishGame();
       return outcome;
   }

    /** Sets the players roll to its starting value and informs them of their rolls.
     */
   protected void startRoll() {
       // Set the starting total to 0 and print the players starting rolls.
       playerTotal = 0;
       System.out.println("Starting rolls:");
       Random rand = new Random();

       // Generate the first starting roll, print it and add it to the total.
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerTotal += lastRoll;

       // Generate the second starting roll, print it and add it to the total.
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerTotal += lastRoll;

       // Generate the final starting roll, print it, add it to the total, and save it as the last player roll.
       lastRoll = rand.nextInt(1, ROLL_MAX);
       System.out.println(lastRoll);
       playerTotal += lastRoll;
   }

    /** Creates loop to allow player to take game actions until they quit or cannot win.
     */
   protected void playerChoose() {
       Scanner scan = new Scanner(System.in);
       // Loop through player options while player total is not above max roll and last choice was not hold total.
       while (playerChoice != 2 && playerTotal < TOTAL_MAX) {
           if (playerTotal > TOTAL_BUST) {
               System.out.println("Current roll exceeds 21.");
           }

           // Print current combat values
           System.out.println("Current total: " + playerTotal);
           System.out.println("Last Roll: " + lastRoll);
           System.out.println("Select move: ");

           // Print player choices
           System.out.println("1 - Roll, 2 - Hold");
           if (pc.getLevel() > 1) {
                System.out.println("3 - Add, 4 - Take Away, 5 - Re-Roll");
           }

           // Record player choices and call response printer.
           playerChoice = scan.nextInt();
           if (playerChoice != HOLD) {
               playerChoiceDirector();
           }
       }

       // Print the players final total and check if they lose to bust.
       System.out.println("Your end total is " + playerTotal + ".");
       if (playerTotal > TOTAL_BUST) {
           playerBust();
       }
   }

   // If the player ended with a roll > 21, they automatically lose (set total to 0).
   private void playerBust() {
      System.out.println("Ended with total greater than 21. You will lose.");
      playerTotal = 0;
   }

   /** Call a game action method based on the player choice value.
    * If there is not a valid choice inform player and return.
    */
   protected void playerChoiceDirector() {
       // Call method based on player choice:
       // 1 = Add Roll, 2 = Hold (N/A), 3 = Add to total, 4 = Subtract from total, 5 = Re-Roll last roll.
       if (playerChoice == ROLL) {
           playerRoll();
       } else if (playerChoice == ADD && pc.getLevel() > 1) {
           playerAdd();
       } else if (playerChoice == TAKE_AWAY && pc.getLevel() > 1) {
           playerTakeAway();
       } else if (playerChoice == RE_ROLL && pc.getLevel() > 1) {
           playerReRoll();

       // If no valid option was chosen print warning and return.
       } else if (playerChoice != 0) {
           System.out.println("Invalid choice.");
       }
   }

    /** Call the getRoll method from the enemy to get enemy roll.
     * Compare against player roll and set the outcome (true = player wins, false = player loses)
     */
   protected void finishGame() {
       outcome = enemy.compareRoll(playerTotal);
   }

    /** Generate a new roll value between 1-6.
     * Add it to total, set last roll to its value and inform player of roll.
     */
   protected void playerRoll() {
       Random rand = new Random();
       lastRoll = rand.nextInt(1, ROLL_MAX);
       playerTotal += lastRoll;
       System.out.println("Rolled: " + lastRoll + ".");
   }

    /** Subtract the last roll value from player total and add a new roll to the player total.
     */
   protected void playerReRoll() {
       // Call use Re-Roll method to check if the ability can be used.
       if (pc.useReRoll()) {
           // Remove the last roll from the total.
           System.out.println("Removed: " + lastRoll + ".");
           playerTotal -= lastRoll;
           // Generate new roll, add it to total, and record it as the last roll.
           Random rand = new Random();
           lastRoll = rand.nextInt(1, ROLL_MAX);
           playerTotal += lastRoll;
           System.out.println("Added: " + lastRoll + ".");
       }
   }

    /** Add one to the current total value.
     */
    protected void playerAdd() {
        if (pc.useAdd()) {
            playerTotal += 1;
            System.out.println("Added 1.");
        }
   }

    /** Subtract one from the current total value.
     */
   protected void playerTakeAway() {
       if (pc.useTakeAway()) {
           playerTotal -= 1;
           System.out.println("Subtracted 1.");
       }
   }
}
