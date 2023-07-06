package baldursbones.bb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Player.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Player {

    // Constant: Starting value for the players health stat.
    private static final int START_HEALTH = 3;

    // Constant: Starting value for the players experience stat.
    private static final int START_EXP = 0;

    // Constant: Starting value for the players level stat.
    private static final int START_LEVEL = 1;

    // Constant: Starting value for the players re-roll ability.
    private static final int START_RE_ROLL = 1;

    // Constant: Starting value for the players add ability.
    private static final int START_ADD = 0;

    // Constant: Starting value for the players take-away ability.
    private static final int START_TAKEAWAY = 0;

    // Constant: Starting value for the players X,Y location tuple.
    private static final int[] START_LOCATION = {6, 0};

    // Constant: Integer value represented in level 1.
    private static final int LEVEL_1 = 1;

    // Constant: Integer value represented in level 1.
    private static final int LEVEL_2 = 2;

    // Constant: Integer value represented in level 1.
    private static final int LEVEL_3 = 3;

    // Constant: Experience value need to level up at level 1.
    private static final int LEVEL_1_EXP_THRESHOLD = 3;

    // Constant: Experience value need to level up at level 2.
    private static final int LEVEL_2_EXP_THRESHOLD = 5;

    // Constant: Integer value represented in winning a battle.
    private static final int WIN_BATTLE = 1;

    // Constant: Integer value represented in losing a battle.
    private static final int LOSE_BATTLE = -1;

    // Constant: Integer value represented in losing to a boss.
    private static final int LOSE_TO_BOSS = -2;

    /**
     * Variable: The recorded value of the players health stat.
     */
    protected int health;

    /**
     * Variable: The recorded value of the players experience stat.
     */
    protected int exp;

    /**
     * Variable: The recorded value of the players level stat.
     */
    protected int level;

    /**
     * Variable: The recorded value of the players re-roll ability uses.
     */
    protected int abilityReRoll;

    /**
     * Variable: The recorded value of the players add ability uses.
     */
    protected int abilityAdd;

    /**
     * Variable: The recorded value of the players take-away ability uses.
     */
    protected int abilityTakeAway;

    /**
     * Variable: The recorded value of the X,Y coordinates of the players position.
     */
    protected int[] location;

    // Text file: contains all dialogue to be printed by the player class.
    private final File playerText = new File("src/main/resources/baldursbones/bb/PlayerText.txt");

    /**
     * Initializes a new player character and sets their stats, abilities, and location to the starting values.
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

    /**
     * Gets and returns the current X,Y coordinates of the player character location.
     *
     * @return an integer tuple representing the players X,Y coordinates
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * Sets the players X,Y coordinates to a new value.
     *
     * @param newLocation an integer tuple representing the new X,Y coordinates
     */
    public void setLocation(final int[] newLocation) {
        location = newLocation;
    }

    /**
     * Gets the current value of the player characters level stat.
     *
     * @return an integer value representing the current value of the player characters level stat.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the current value of the player characters health stat.
     *
     * @return an integer value representing the current value of the player characters health stat.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Prints the current value of the players stats and available uses of abilities.
     */
    public void getStats() {
        System.out.println("Health: " + health);
        System.out.println("Level: " + level);
        System.out.println("Exp: " + exp);
        System.out.println("Re-Roll uses: " + abilityReRoll);
        if (level >= 2) {
            System.out.println("Add uses: " + abilityAdd);
            System.out.println("Take-away uses: " + abilityTakeAway);
        }
    }

    /**
     * Takes an outcome value and calls the appropriate method to handle the outcome.
     *
     * @param outcome an integer value representing the outcome of the combat
     */
    public void finishBattle(final int outcome) {
        // If outcome matches a "lose" outcome then call the loseBattle method.
        if (outcome == LOSE_BATTLE || outcome == LOSE_TO_BOSS) {
            loseBattle(outcome);

            // If outcome matches a "win" outcome then call the winBattle method.
        } else if (outcome == WIN_BATTLE) {
            winBattle();
        }

        // After the outcome check if the health of the player is not 0 and prompt user to continue.
        if (health > 0) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter to continue");
            scan.nextLine();
        }
    }

    /**
     * Increase the players stats and print the message associated with their new level.
     *
     * @throws RuntimeException if text file cannot be found
     */
    private void levelUp() {
        // Increase the players stats.
        level += 1;
        health += 1;
        abilityReRoll += 1;
        abilityAdd += 1;
        abilityTakeAway += 1;

        try {
            // Create a new scanner for the text file and level up the first section.
            Scanner fileReader = new Scanner(playerText);
            // Print the level up message and the message related to the level.
            System.out.println(fileReader.nextLine());
            if (level == LEVEL_2) {
                System.out.println(fileReader.nextLine());
            }
            if (level == LEVEL_3) {
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }

        // Print the players new stats for them.
        getStats();
    }

    /**
     * Add 1 to player exp, check if a level up is needed and call one if it is.
     * Otherwise, inform player to fight boss if they are level 3 or tell them experience needed to level up.
     *
     * @throws RuntimeException if text file cannot be found
     */
    private void winBattle() {
        // Increase experience and check for level up.
        exp += 1;
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(playerText);
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            System.out.println(fileReader.nextLine());
            if ((level == LEVEL_1 && exp == LEVEL_1_EXP_THRESHOLD)
                    || (level == LEVEL_2 && exp == LEVEL_2_EXP_THRESHOLD)) {
                exp = 0;
                levelUp();
                // If the player is level 3 tell them to fight the boss.
            } else if (level == LEVEL_3 && exp >= 1) {
                System.out.println(fileReader.nextLine());
                // If not leveling up or level 3 then print stats and experience needed to level up.
            } else {
                System.out.println("Level: " + level);
                System.out.println("Experience: " + exp);
                System.out.println("You need " + ((2 * level + 1) - exp) + " experience to level up.");
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    private void loseBattle(final int outcome) {
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(playerText);
            for (int i = 1; i < 6; i++) {
                fileReader.nextLine();
            }
            // Lose battle.
            if (outcome == LOSE_BATTLE) {
                health -= 1;
                if (health >= 1) {
                    System.out.println(fileReader.nextLine());
                    System.out.println(health + " remaining.");
                }
                // Lose to boss.
            } else if (outcome == LOSE_TO_BOSS) {
                health -= 2;
                if (health >= 1) {
                    fileReader.nextLine();
                    System.out.println(fileReader.nextLine());
                    System.out.println(health + " remaining.");
                }
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Use a charge of Re-Roll cheat if the player has a use available.
     * If the player has no uses remaining then inform the player.
     *
     * @return a boolean indication if the ability could be used.
     */
    public boolean useReRoll() {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityReRoll >= 1) {
            abilityReRoll -= 1;
            System.out.println(abilityTakeAway + " uses remaining of Re-Roll.");
            return true;
            // If no uses are available inform the player.
        } else {
            System.out.println("Cannot use Re-Roll. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Add cheat if the player has a use available.
     * If the player has no uses remaining then inform the player.
     *
     * @return a boolean indication if the ability could be used.
     */
    public boolean useAdd() {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityAdd >= 1) {
            abilityAdd -= 1;
            System.out.println(abilityAdd + " uses remaining of Add 1.");
            return true;
            // If no uses are available inform the player.
        } else {
            System.out.println("Cannot use Add. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Take Away cheat if the player has a use available.
     * If the player has no uses remaining then inform the player.
     *
     * @return a boolean indication if the ability could be used.
     */
    public boolean useTakeAway() {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityTakeAway >= 1) {
            abilityTakeAway -= 1;
            System.out.println(abilityTakeAway + " uses remaining of Take-Away.");
            return true;
            // If no uses are available inform the player.
        } else {
            System.out.println("Cannot use Take-Away. No uses.");
            return false;
        }
    }
}
