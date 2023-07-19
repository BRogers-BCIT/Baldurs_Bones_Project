package baldursbones.bb;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Player Object.
 * Controller: Location Menu / Game Combat.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class Player {

    // Constant: Starting value for the Player's Health stat.
    private static final int START_HEALTH = 3;

    // Constant: Starting value for the Player's Experience stat.
    private static final int START_EXP = 0;

    // Constant: Starting value for the Player's Level stat.
    private static final int START_LEVEL = 1;

    // Constant: Starting value for the Player's Re-Roll Ability.
    private static final int START_RE_ROLL = 1;

    // Constant: Starting value for the Player's Add Ability.
    private static final int START_ADD = 0;

    // Constant: Starting value for the Player's Take-Away Ability.
    private static final int START_TAKEAWAY = 0;

    // Constant: Starting value for the Player X,Y Location tuple.
    private static final int[] START_LOCATION = {6, 0};

    // Constant: Integer value represented in Level 1.
    private static final int LEVEL_1 = 1;

    // Constant: Integer value represented in Level 1.
    private static final int LEVEL_2 = 2;

    // Constant: Integer value represented in Level 1.
    private static final int LEVEL_3 = 3;

    // Constant: Experience value need to Level Up at Level 1.
    private static final int LEVEL_1_EXP_THRESHOLD = 3;

    // Constant: Experience value need to Level Up at Level 2.
    private static final int LEVEL_2_EXP_THRESHOLD = 5;

    // Constant: Integer value represented in winning a battle.
    private static final int WIN_BATTLE = 1;

    // Constant: Integer value represented in losing a battle.
    private static final int LOSE_BATTLE = -1;

    // Constant: Integer value represented in losing to a boss.
    private static final int LOSE_TO_BOSS = -2;

    // Constant: Integer value representing the number of lines to print for the Player's Stats.
    private static final int NUMBER_OF_STATS = 6;

    // Variable: An integer that represents the outcome of the last fight the Player object was in.
    private int lastOutcome;

    // Variable: A string that represents the characters name in game.
    private String name;

    // Variable: The recorded value of the Player's Health stat.
    private int statHealth;

    // Variable: The recorded value of the Player's Experience stat.
    private int statExp;

    // Variable: The recorded value of the Player's Level stat.
    private int statLevel;

    // Variable: The recorded value of the Player's Re-Roll Ability uses.
    private int abilityReRoll;

    // Variable: The recorded value of the Player's Add Ability uses.
    private int abilityAdd;

    // Variable: The recorded value of the Player's Take-Away Ability uses.
    private int abilityTakeAway;

    //Variable: The recorded value of the X,Y coordinates of the Player's position.
    private int[] location;

    // Text file: Contains all dialogue to be printed by the Player class.
    private final File playerText = new File("src/main/resources/baldursbones/bb/PlayerText.txt");

    /**
     * Initializes a new Player character and sets their Stats, Abilities, and Location to the game start values.
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
     * Gets and returns the current X,Y coordinates of the Player character Location.
     *
     * @return An integer tuple representing the Player's X,Y coordinates
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * Gets and returns the current value of the Player characters Health stat.
     *
     * @return An integer value representing the current value of the Player characters Health stat
     */
    public int getStatHealth() {
        return statHealth;
    }

    /**
     * Gets the current value of the Player characters Level stat.
     *
     * @return An integer value representing the current value of the Player characters Level stat
     */
    public int getStatLevel() {
        return statLevel;
    }

    /**
     * Get and return the string value of the Player characters name.
     *
     * @return A string that represents the Player characters name
     */
    public String getName() {
        return name;
    }

    /**
     * Get and return the outcome value of the Player's last fight.
     *
     * @return An integer that represents the outcome of the Player objects last fight
     */
    public int getLastOutcome() {
        return lastOutcome;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Add" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityAdd() {
        return abilityAdd;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Take-Away" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityTakeAway() {
        return abilityTakeAway;
    }

    /**
     * Get and return the integer value of the number of available uses of the "Re-Roll" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityReRoll() {
        return abilityReRoll;
    }

    /**
     * Sets the Player's X,Y coordinates to a new value.
     *
     * @param newLocation An integer tuple representing the new X,Y coordinates
     */
    public void setLocation(final int[] newLocation) {
        location = newLocation;
    }

    /**
     * Set the Player characters name to a new string value.
     *
     * @param name A string to change the Player characters name to
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set the Player outcome name to a new int value.
     *
     * @param lastOutcome An integer to change the Player's outcome value to
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
        // Set the name of the character based on the saved name field of the Player object.
        nameTitle.setText(name);
        // Set the description of the character based on the Player's Level field.
        if (statLevel == 1) {
            descriptionTitle.setText("Deck Hand");
        } else if (statLevel == 2) {
            descriptionTitle.setText("Skipper");
        } else {
            descriptionTitle.setText("First Mate");
        }
        // Display the title for the Stats and abilities using the Player's name.
        statsTitle.setText(name + "'s Current Stats: ");
        abilitiesTitle.setText(name + "'s Ability Uses:");
    }

    // Update the Stats labels on the Player Info menu with the correct information.
    private void updateStats(final Label health, final Label level, final Label exp) {
        // Display the information for the Player's Stats using the values of the fields within the Player object.
        health.setText("Credibility: " + statHealth);
        level.setText("Renown: " + statLevel);
        exp.setText("Reputation: " + statExp);
    }

    // Update the Ability labels on the Player Info menu with the correct information.
    private void updateAbilities(final Label add, final Label takeAway, final Label reRoll) {
        // Display the information for the Player's abilities using the values of the fields within the Player object.
        add.setText("Add Uses: " + abilityAdd);
        takeAway.setText("Take Away Uses: " + abilityTakeAway);
        reRoll.setText("Re-Roll Uses: " + abilityReRoll);
    }

    /**
     * Takes an outcome value and calls the appropriate method to handle the outcome.
     *
     * @param descriptionArea The text area to display the result of the combat in
     * @param outcome An integer value representing the outcome of the combat
     */
    public void finishBattle(final int outcome, final TextArea descriptionArea) {
        if (outcome == LOSE_BATTLE || outcome == LOSE_TO_BOSS) {
            // If: outcome matches a "lose" outcome then call the loseBattle method.
            loseBattle(outcome, descriptionArea);
        } else if (outcome == WIN_BATTLE) {
            // Else: outcome matches a "win" outcome then call the winBattle method.
            winBattle(descriptionArea);
        }
    }

    // Whenever the Player's Level increases increase the appropriate Stats.
    // Also display a message for leveling up and a message based on the new Level.
    private void levelUp(final TextArea descriptionArea) {
        // Increase the Player's Stats.
        statLevel += 1;
        statHealth += 1;
        abilityReRoll += 1;
        abilityAdd += 1;
        abilityTakeAway += 1;
        // Try to read the Level Up description from the Player text file.
        try {
            // Create a new scanner for the text file and Level Up the first section.
            Scanner fileReader = new Scanner(playerText);
            // Display the Level Up message and the message related to the Level.
            descriptionArea.appendText(fileReader.nextLine());
            if (statLevel == LEVEL_2) {
                // Display the Level 2 message.
                descriptionArea.appendText(fileReader.nextLine());
            }
            if (statLevel == LEVEL_3) {
                // Skip the Level 2 message and display the Level 3 message.
                fileReader.nextLine();
                descriptionArea.appendText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    // Add 1 to Player Experience, check if a Level Up is needed and call one if it is.
    // Then display an appropriate method based on the Player Level.
    private void winBattle(final TextArea descriptionArea) {
        // Increase Experience and check for Level Up.
        statExp += 1;
        // Try to read the win-battle description from the Player text file.
        try {
            // Create a new scanner for the text file and print the first section.
            Scanner fileReader = new Scanner(playerText);
            fileReader.nextLine();
            fileReader.nextLine();
            fileReader.nextLine();
            // Display the description for winning a combat.
            descriptionArea.setText(fileReader.nextLine());
            if ((statLevel == LEVEL_1 && statExp == LEVEL_1_EXP_THRESHOLD)
                    || (statLevel == LEVEL_2 && statExp == LEVEL_2_EXP_THRESHOLD)) {
                statExp = 0;
                levelUp(descriptionArea);
                // If the Player is Level 3 tell them to fight the boss.
            } else if (statLevel == LEVEL_3 && statExp >= 1) {
                descriptionArea.appendText(fileReader.nextLine());
                // If not leveling up or Level 3 then print Stats and Experience needed to level up.
            } else {
                descriptionArea.appendText("Level: " + statLevel);
                descriptionArea.appendText("\nExperience: " + statExp);
                descriptionArea.appendText("\nYou need " + ((2 * statLevel + 1) - statExp)
                        + " experience to level up.");
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    // Remove 1 from Player Health or 2 if they lost to a boss and check if the Player is still alive.
    // If the Player has not lost the game then display the lose battle message or lose to boss message.
    private void loseBattle(final int outcome, final TextArea descriptionArea) {
        // Try to read the lose-battle description from the Player text file.
        try {
            // Create a new scanner for the text file and skip the first section.
            Scanner fileReader = new Scanner(playerText);
            for (int i = 1; i < NUMBER_OF_STATS; i++) {
                fileReader.nextLine();
            }
            // Display the lose battle description.
            if (outcome == LOSE_BATTLE) {
                statHealth -= 1;
                if (statHealth >= 1) {
                    descriptionArea.setText(fileReader.nextLine());
                    descriptionArea.appendText(statHealth + " remaining.");
                }
                // Display the lose to boss description.
            } else if (outcome == LOSE_TO_BOSS) {
                statHealth -= 2;
                if (statHealth >= 1) {
                    fileReader.nextLine();
                    descriptionArea.setText(fileReader.nextLine());
                    descriptionArea.appendText(statHealth + " remaining.");
                }
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the text file.
            throw new RuntimeException(e);
        }
    }

    /**
     * Use a charge of Re-Roll Ability if the Player has a use available and inform the Player of the remaining uses.
     * If the Player has no uses remaining then inform the Player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the Ability use in
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useReRoll(final TextArea descriptionArea) {
        // If a use is available take one away and tell the Player the remaining uses.
        if (abilityReRoll >= 1) {
            abilityReRoll -= 1;
            descriptionArea.setText(abilityTakeAway + " uses remaining of Re-Roll.");
            return true;
            // If no uses are available inform the Player.
        } else {
            descriptionArea.setText("Cannot use Re-Roll. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Add Ability if the Player has a use available and inform the Player of the remaining uses.
     * If the Player has no uses remaining then inform the Player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the Ability use in
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useAdd(final TextArea descriptionArea) {
        // If a use is available take one away and tell the Player the remaining uses.
        if (abilityAdd >= 1) {
            abilityAdd -= 1;
            descriptionArea.setText(abilityAdd + " uses remaining of Add 1.");
            return true;
            // If no uses are available inform the Player.
        } else {
            descriptionArea.setText("Cannot use Add. No uses.");
            return false;
        }
    }

    /**
     * Use a charge of Take Away Ability if the Player has a use available and inform the Player of the remaining uses.
     * If the Player has no uses remaining then inform the Player and do nothing instead.
     *
     * @param descriptionArea The text area to display the result of the Ability use in
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useTakeAway(final TextArea descriptionArea) {
        // If a use is available take one away and tell the Player the remaining uses.
        if (abilityTakeAway >= 1) {
            abilityTakeAway -= 1;
            descriptionArea.setText(abilityTakeAway + " uses remaining of Take-Away.");
            return true;
            // If no uses are available inform the Player.
        } else {
            descriptionArea.setText("Cannot use Take-Away. No uses.");
            return false;
        }
    }
}
