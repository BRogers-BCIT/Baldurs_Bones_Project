package baldursbones.bb;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

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

    // Constant: Integer value representing the number of lines to print for the players stats.
    private static final int NUMBER_OF_STATS = 6;

    // Variable: An integer that represents the outcome of the last fight the player object was in.
    private int lastOutcome;

    // Variable: A string that represents the characters name in game.
    private String name;

    // Variable: The recorded value of the players health stat.
    private int statHealth;

    // Variable: The recorded value of the players experience stat.
    private int statExp;

    // Variable: The recorded value of the players level stat.
    private int statLevel;

    // Variable: The recorded value of the players re-roll ability uses.
    private int abilityReRoll;

    // Variable: The recorded value of the players add ability uses.
    private int abilityAdd;

    // Variable: The recorded value of the players take-away ability uses.
    private int abilityTakeAway;

    //Variable: The recorded value of the X,Y coordinates of the players position.
    private int[] location;

    // Text file: Contains all dialogue to be printed by the player class.
    private final File playerText = new File("src/main/resources/baldursbones/bb/PlayerText.txt");

    /**
     * Initializes a new player character and sets their stats, abilities, and location to the game start values.
     */
    public Player() {
        statHealth = START_HEALTH;
        statExp = START_EXP;
        statLevel = START_LEVEL;
        abilityReRoll = START_RE_ROLL;
        abilityAdd = START_ADD;
        abilityTakeAway = START_TAKEAWAY;
        location = START_LOCATION;
    }

    /**
     * Gets and returns the current X,Y coordinates of the player character location.
     *
     * @return An integer tuple representing the players X,Y coordinates
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * Gets and returns the current value of the player characters health stat.
     *
     * @return An integer value representing the current value of the player characters health stat.
     */
    public int getStatHealth() {
        return statHealth;
    }

    /**
     * Gets the current value of the player characters level stat.
     *
     * @return An integer value representing the current value of the player characters level stat.
     */
    public int getStatLevel() {
        return statLevel;
    }

    /**
     * Get and return the string value of the player characters name.
     *
     * @return A string that represents the player characters name
     */
    public String getName() {
        return name;
    }

    /**
     * Get and return the outcome value of the player's last fight.
     *
     * @return An integer that represents the outcome of the player objects last fight
     */
    public int getLastOutcome() {
        return lastOutcome;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Add" ability.
     *
     * @return An integer that represents the number of available uses of the ability
     */
    public int getAbilityAdd() {
        return abilityAdd;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Take-Away" ability.
     *
     * @return An integer that represents the number of available uses of the ability
     */
    public int getAbilityTakeAway() {
        return abilityTakeAway;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Re-Roll" ability.
     *
     * @return An integer that represents the number of available uses of the ability
     */
    public int getAbilityReRoll() {
        return abilityReRoll;
    }

    /**
     * Sets the players X,Y coordinates to a new value.
     *
     * @param newLocation An integer tuple representing the new X,Y coordinates
     */
    public void setLocation(final int[] newLocation) {
        location = newLocation;
    }

    /**
     * Set the player characters name to a new string value.
     *
     * @param name A string to change the player characters name too.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set the player outcome name to a new int value.
     *
     * @param lastOutcome An integer to change the player's outcome value to.
     */
    public void setLastOutcome(final int lastOutcome) {
        this.lastOutcome = lastOutcome;
    }


    /**
     * Finds the character info labels from the grid pane and calls helper functions to populate the Player Info menu.
     *
     * @param characterInfoMenu The grid pane object containing the labels to be updated
     */
    public void displayStats(final GridPane characterInfoMenu) {
        // Get the assosiated label for each part of the character info menu.
        Label characterName = (Label) characterInfoMenu.lookup("#CharacterNameTitle");
        Label characterDescription = (Label) characterInfoMenu.lookup("#CharacterDescriptionTitle");
        Label characterNameStats = (Label) characterInfoMenu.lookup("#CharacterDisplayNameStats");
        Label characterNameAbilities = (Label) characterInfoMenu.lookup("#CharacterDisplayNameAbilities");
        Label characterHealth = (Label) characterInfoMenu.lookup("#CharacterDisplayHealth");
        Label characterLevel = (Label) characterInfoMenu.lookup("#CharacterDisplayLevel");
        Label characterExp = (Label) characterInfoMenu.lookup("#CharacterDisplayExp");
        Label characterAdd = (Label) characterInfoMenu.lookup("#CharacterDisplayAdd");
        Label characterTakeAway = (Label) characterInfoMenu.lookup("#CharacterDisplayTakeAway");
        Label characterReRoll = (Label) characterInfoMenu.lookup("#CharacterDisplayReRoll");
        // Call helper functions to populate the menu.
        updateTitles(characterName, characterDescription, characterNameStats, characterNameAbilities);
        updateStats(characterHealth, characterLevel, characterExp);
        updateAbilities(characterAdd, characterTakeAway, characterReRoll);
    }

    // Update the title labels on the Player Info menu with the correct information.
    private void updateTitles(final Label nameTitle, final Label descriptionTitle,
                              final Label statsTitle, final Label abilitiesTitle) {
        // Set the name of the character based on the saved name field of the player object.
        nameTitle.setText(name);
        // Set the description of the character based on the player's level field.
        if (statLevel == 1) {
            descriptionTitle.setText("Deck Hand");
        } else if (statLevel == 2) {
            descriptionTitle.setText("Skipper");
        } else {
            descriptionTitle.setText("First Mate");
        }
        // Display the title for the stats and abilities using the players name.
        statsTitle.setText(name + "'s Current Stats: ");
        abilitiesTitle.setText(name + "'s Ability Uses:");
    }

    // Update the stats labels on the Player Info menu with the correct information.
    private void updateStats(final Label health, final Label level, final Label exp) {
        // Display the information for the players stats using the values of the fields within the player object.
        health.setText("Credibility: " + statHealth);
        level.setText("Renown: " + statLevel);
        exp.setText("Reputation: " + statExp);
    }

    // Update the ability labels on the Player Info menu with the correct information.
    private void updateAbilities(final Label add, final Label takeAway, final Label reRoll) {
        // Display the information for the players abilities using the values of the fields within the player object.
        add.setText("Add Uses: " + abilityAdd);
        takeAway.setText("Take Away Uses: " + abilityTakeAway);
        reRoll.setText("Re-Roll Uses: " + abilityReRoll);
    }

    /**
     * Takes an outcome value and calls the appropriate method to handle the outcome.
     *
     * @param outcome An integer value representing the outcome of the combat
     */
    public void finishBattle(final int outcome) {
        if (outcome == LOSE_BATTLE || outcome == LOSE_TO_BOSS) {
            // If: outcome matches a "lose" outcome then call the loseBattle method.
            loseBattle(outcome);
        } else if (outcome == WIN_BATTLE) {
            // Else: outcome matches a "win" outcome then call the winBattle method.
            winBattle();
        }
    }

    /**
     * Increase the players stats and print the message associated with their new level.
     *
     * @throws RuntimeException If text file cannot be found
     */
    private void levelUp() {
        // Increase the players stats.
        statLevel += 1;
        statHealth += 1;
        abilityReRoll += 1;
        abilityAdd += 1;
        abilityTakeAway += 1;
        // Try to read the level-up text from the Player text file.
        try {
            // Create a new scanner for the text file and level up the first section.
            Scanner fileReader = new Scanner(playerText);
            // Print the level up message and the message related to the level.
            System.out.println(fileReader.nextLine());
            if (statLevel == LEVEL_2) {
                System.out.println(fileReader.nextLine());
            }
            if (statLevel == LEVEL_3) {
                fileReader.nextLine();
                System.out.println(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Add 1 to player exp, check if a level up is needed and call one if it is.
     * Otherwise, inform player to fight boss if they are level 3 or tell them experience needed to level up.
     *
     * @throws RuntimeException if text file cannot be found
     */
    private void winBattle() {
        // Increase experience and check for level up.
        statExp += 1;
        // Try to read the win-battle text from the Player text file.
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(playerText);
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            System.out.println(fileReader.nextLine());
            if ((statLevel == LEVEL_1 && statExp == LEVEL_1_EXP_THRESHOLD)
                    || (statLevel == LEVEL_2 && statExp == LEVEL_2_EXP_THRESHOLD)) {
                statExp = 0;
                levelUp();
                // If the player is level 3 tell them to fight the boss.
            } else if (statLevel == LEVEL_3 && statExp >= 1) {
                System.out.println(fileReader.nextLine());
                // If not leveling up or level 3 then print stats and experience needed to level up.
            } else {
                System.out.println("Level: " + statLevel);
                System.out.println("Experience: " + statExp);
                System.out.println("You need " + ((2 * statLevel + 1) - statExp) + " experience to level up.");
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    private void loseBattle(final int outcome) {
        // Try to read the lose-battle text from the Player text file.
        try {
            // Create a new scanner for the text file and skip the first section.
            Scanner fileReader = new Scanner(playerText);
            for (int i = 1; i < NUMBER_OF_STATS; i++) {
                fileReader.nextLine();
            }
            // Lose battle.
            if (outcome == LOSE_BATTLE) {
                statHealth -= 1;
                if (statHealth >= 1) {
                    System.out.println(fileReader.nextLine());
                    System.out.println(statHealth + " remaining.");
                }
                // Lose to boss.
            } else if (outcome == LOSE_TO_BOSS) {
                statHealth -= 2;
                if (statHealth >= 1) {
                    fileReader.nextLine();
                    System.out.println(fileReader.nextLine());
                    System.out.println(statHealth + " remaining.");
                }
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Use a charge of Re-Roll ability if the player has a use available and inform the player of the remaining uses.
     * If the player has no uses remaining then inform the player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the ability use in
     * @return A boolean indication if the ability could be used.
     */
    public boolean useReRoll(final TextArea descriptionArea) {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityReRoll >= 1) {
            abilityReRoll -= 1;
            descriptionArea.setText(abilityTakeAway + " uses remaining of Re-Roll.");
            return true;
            // If no uses are available inform the player.
        } else {
            descriptionArea.setText("Cannot use Re-Roll. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Add ability if the player has a use available and inform the player of the remaining uses.
     * If the player has no uses remaining then inform the player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the ability use in
     * @return A boolean indication if the ability could be used.
     */
    public boolean useAdd(final TextArea descriptionArea) {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityAdd >= 1) {
            abilityAdd -= 1;
            descriptionArea.setText(abilityAdd + " uses remaining of Add 1.");
            return true;
            // If no uses are available inform the player.
        } else {
            descriptionArea.setText("Cannot use Add. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Take Away ability if the player has a use available and inform the player of the remaining uses.
     * If the player has no uses remaining then inform the player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the ability use in
     * @return A boolean indication if the ability could be used.
     */
    public boolean useTakeAway(final TextArea descriptionArea) {
        // If a use is available take one away and tell the player the remaining uses.
        if (abilityTakeAway >= 1) {
            abilityTakeAway -= 1;
            descriptionArea.setText(abilityTakeAway + " uses remaining of Take-Away.");
            return true;
            // If no uses are available inform the player.
        } else {
            descriptionArea.setText("Cannot use Take-Away. No uses.");
            return false;
        }
    }
}
