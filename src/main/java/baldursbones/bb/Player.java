package baldursbones.bb;

import java.util.Scanner;

/** Player.
 * @author Braden Rogers
 * @version 2023-TermProject
 */
public class Player {
    private static final int START_HEALTH = 3;
    private static final int START_EXP = 0;
    private static final int START_LEVEL = 1;
    private static final int START_RE_ROLL = 1;
    private static final int START_ADD = 0;
    private static final int START_TAKEAWAY = 0;
    private static final int[] START_LOCATION = {6, 0};

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_1_EXP_THRESHOLD = 3;
    private static final int LEVEL_2_EXP_THRESHOLD = 5;
    private static final int WIN_BATTLE = 1;
    private static final int LOSE_BATTLE = -1;
    private static final int LOSE_TO_BOSS = -2;
    /** Player health value.
     */
    protected int health;
    /** Player experience value.
     */
    protected int exp;
    /** Player level value.
     */
    protected int level;
    /** Player Re-Roll uses value.
     */
    protected int abilityReRoll;
    /** Player Add uses value.
     */
    protected int abilityAdd;
    /** Player Take Away uses value.
     */
    protected int abilityTakeAway;
    /** Player location tuple.
     */
    protected int[] location;

    /** Initializes a new player character and sets their stats to the starting values.
     */
    public Player() {
        health = START_HEALTH;
        exp = START_EXP;
        level = START_LEVEL;
        abilityReRoll = START_RE_ROLL;
        abilityAdd = START_ADD;
        abilityTakeAway = START_TAKEAWAY;
        location = START_LOCATION;
    }

    /** Gets the current location tuple of the player character.
     * @return an integer tuple of the player location
     */
    public int[] getLocation() {
        return location;
    }

    /** Sets the players location tuple to a new value.
     * @param newLocation an integer tuple to set the players location to
     */
    public void setLocation(final int[] newLocation) {
        location = newLocation;
    }

    /** Gets the current level value of the player character.
     * @return an integer of the players current level value
     */
    public int getLevel() {
        return level;
    }
    /** Gets the current health value of the player character.
     * @return an integer of the players current health value
     */
    public int getHealth() {
        return health;
    }

    /** Prints the current value of the players stats.
     */
    public void getStats() {
        System.out.println("Your current credibility is: " + health);
        System.out.println("Your current reputation is: " + level);
        System.out.println("Your current renown is: " + exp);
        System.out.println("The number of remaining uses of the Re-Roll cheat is: " + abilityReRoll);
        if (level >= 2) {
            System.out.println("The number of remaining uses of the "
                    + "Add 1 to total cheat is: " + abilityAdd);
            System.out.println("The number of remaining uses of the "
                    + "Take Away 1 from total cheat is: " + abilityTakeAway);
        }
    }

    /** Calls the appropriate method to handle the outcome of a battle.
     * @param outcome the outcome value of the battle
     */
    public void finishBattle(final int outcome) {
        if (outcome == LOSE_BATTLE || outcome == LOSE_TO_BOSS) {
            loseBattle(outcome);
        } else if (outcome == WIN_BATTLE) {
            winBattle();
        }
        if (health > 0) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue");
            scan.nextLine();
        }
    }

    /** Update players stats and informs them they have level up.
     */
    private void levelUp() {
        level += 1;
        health += 1;
        abilityReRoll += 1;
        abilityAdd += 1;
        abilityTakeAway += 1;
        System.out.println("Congratulations! You have increased your reputation. Your are now level " + level + ".");
        if (level == LEVEL_2) {
            System.out.println("You have gained the ability to use your reputation stone "
                    + "to add or subtract one from your total roll");
            System.out.println("You can use this ability along with your re-roll "
                    + "to increase your chances of winning.\n");
        }
        if (level == LEVEL_3) {
            System.out.println("You now have enough of a reputation to fight the legendary adventurer Volo."
                    + " He resides in a tavern to the North East.\n");
        }
        getStats();
    }

    /** Add 1 to player exp, level up if needed, inform player to fight boss if they are level 3.
     */
    private void winBattle() {
        exp += 1;
        System.out.println();
        if ((level == LEVEL_1 && exp == LEVEL_1_EXP_THRESHOLD) || (level == LEVEL_2 && exp == LEVEL_2_EXP_THRESHOLD)) {
            exp = 0;
            levelUp();
        } else if (level == LEVEL_3 && exp >= 1) {
            System.out.println("You currently have enough of a reputation to fight the legendary adventurer Volo.");
        } else {
            System.out.println("Your current reputation is: " + level);
            System.out.println("Your current renown is: " + exp);
            System.out.println("You need " + ((2 * level + 1) - exp)
                    + " more reputation to increase increase your renown.");
        }
    }
    private void loseBattle(final int outcome) {
        System.out.println("\n");
        if (outcome == LOSE_BATTLE) {
            health -= 1;
            if (health >= 1) {
                System.out.println("As a result of losing your game you have lost credibility.");
                System.out.println("If you lose " + health + " more times you will be too disgraced to continue.");
            }
        } else if (outcome == LOSE_TO_BOSS) {
            health -= 2;
            if (health >= 1) {
                System.out.println("As a result of losing your game with the adventurer Volo "
                        + "you have lost credibility.");
                System.out.println("If you lose " + health
                        + " more credibility you will be too disgraced to continue.");
                System.out.println("\nYou must let others talk to Volo for the moment");
                System.out.println("You must leave the room and return.");
            }
        }
    }

    /** Use a charge of Re-Roll cheat if the player can or inform the player they have no more uses.
     * @return a boolean indication if the ability could be used.
     */
    public boolean useReRoll() {
        if (abilityReRoll >= 1) {
            abilityReRoll -= 1;
            System.out.println("You have " + abilityTakeAway + " remaining uses of your Re-Roll cheat.");
            return true;
        } else {
            System.out.println("You do not have any uses of the Re-Roll cheat.");
            return false;
        }
    }
    /** Use a charge of Add cheat if the player can or inform the player they have no more uses.
     * @return a boolean indication if the ability could be used.
     */
    public boolean useAdd() {
        if (abilityAdd >= 1) {
            abilityAdd -= 1;
            System.out.println("You have " + abilityAdd + " remaining uses of the Add 1 to total cheat.");
            return true;
        } else {
            System.out.println("You do not have any uses of the Add 1 to total cheat.");
            return false;
        }
    }
    /** Use a charge of Take Away cheat if the player can or inform the player they have no more uses.
     * @return a boolean indication if the ability could be used.
     */
    public boolean useTakeAway() {
        if (abilityTakeAway >= 1) {
            abilityTakeAway -= 1;
            System.out.println("You have " + abilityTakeAway + " remaining uses of Take-Away 1 from total cheat.");
            return true;
        } else {
            System.out.println("You do not have any uses of the Take-Away 1 from total cheat.");
            return false;
        }
    }
}
