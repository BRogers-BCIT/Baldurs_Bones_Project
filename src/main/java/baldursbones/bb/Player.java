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

    // Constant: Starting value for the Player's Health Stat.
    private static final int START_HEALTH = 3;

    // Constant: Starting value for the Player's Experience Stat.
    private static final int START_EXP = 0;

    // Constant: Starting value for the Player's Level Stat.
    private static final int START_LEVEL = 1;

    // Constant: Starting value for the Player's Re-Roll Ability.
    private static final int START_RE_ROLL = 1;

    // Constant: Starting value for the Player's Add Ability.
    private static final int START_ADD = 0;

    // Constant: Starting value for the Player's Take-Away Ability.
    private static final int START_TAKEAWAY = 0;

    // Constant: Starting value for the X & Y Coordinates of the Player.
    private static final int[] START_LOCATION = {6, 0};

    // Constant: Integer value for checking if Player Level is Level One.
    private static final int LEVEL_1 = 1;

    // Constant: Integer value for checking if Player Level is Level Two.
    private static final int LEVEL_2 = 2;

    // Constant: Integer value for checking if Player Level is Level Three.
    private static final int LEVEL_3 = 3;

    // Constant: Experience value need to Level Up at Level One.
    private static final int LEVEL_1_EXP_THRESHOLD = 3;

    // Constant: Experience value need to Level Up at Level Two.
    private static final int LEVEL_2_EXP_THRESHOLD = 5;

    // Constant: Integer Outcome Value that indicates the Player won the last Combat.
    private static final int WIN_COMBAT = 1;

    // Constant: Integer Outcome Value that indicates the Player lost the last Combat.
    private static final int LOSE_COMBAT = -1;

    // Constant: Integer Outcome Value that indicates the Player won the last Combat to a Boss Enemy.
    private static final int LOSE_TO_BOSS = -2;

    // Constant: The number of lines to skip in the Player Text File to find the Level Up text.
    private static final int SKIP_TO_LEVEL_UP = 4;

    // Variable: An integer that represents the Outcome Value of the last Combat for the Player object.
    private int lastOutcome;

    // Variable: A string that represents the Characters Name.
    private String name;

    // Variable: The current value of the Player's Health stat.
    private int statHealth;

    // Variable: The current value of the Player's Experience stat.
    private int statExp;

    // Variable: The current value of the Player's Level stat.
    private int statLevel;

    // Variable: The current number of available uses for the Player's Re-Roll Ability.
    private int abilityReRoll;

    // Variable: The current number of available uses for the Player's Add Ability.
    private int abilityAdd;

    // Variable: The current number of available uses for the Player's Take-Away Ability.
    private int abilityTakeAway;

    //Variable: The current value of the X & Y Coordinates for the Player's position.
    private int[] location;

    // Text file: The Text File object with the Text to display for the Player.
    private final File playerText = new File("src/main/resources/baldursbones/bb/PlayerText.txt");

    /**
     * Initializes a new Player character and sets all fields to their Game Start values.
     * Stats, Abilities, and Location values.
     */
    public Player() {
        // Set Stat Values.
        statHealth = START_HEALTH;
        statExp = START_EXP;
        statLevel = START_LEVEL;
        // Set Ability Values.
        abilityReRoll = START_RE_ROLL;
        abilityAdd = START_ADD;
        abilityTakeAway = START_TAKEAWAY;
        // Set Location Value.
        location = START_LOCATION;
    }

    /**
     * Returns the current X & Y Coordinates for the Player's current Location.
     *
     * @return A tuple representing the X & Y Coordinates of the Players Location
     */
    public int[] getLocation() {
        return location;
    }

    /**
     * Returns the current value of the Player's Health stat.
     *
     * @return An integer value representing the current Player Health stat
     */
    public int getStatHealth() {
        return statHealth;
    }

    /**
     * Returns the current value of the Player's Level stat.
     *
     * @return An integer value representing the current Player Level stat
     */
    public int getStatLevel() {
        return statLevel;
    }

    /**
     * Return the string value for the Player's Name.
     *
     * @return A string that represents the Player's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the Outcome Value of the last Combat for the Player.
     *
     * @return An integer that represents the Outcome Value of the last Combat
     */
    public int getLastOutcome() {
        return lastOutcome;
    }

    /**
     * Return an integer value for the number of available uses of the "Add" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityAdd() {
        return abilityAdd;
    }

    /**
     * Return an integer value for the number of available uses of the "Take-Away" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityTakeAway() {
        return abilityTakeAway;
    }

    /**
     * Return an integer value of the number of available uses of the "Re-Roll" Ability.
     *
     * @return An integer that represents the number of available uses of the Ability
     */
    public int getAbilityReRoll() {
        return abilityReRoll;
    }

    /**
     * Sets the Player's X & Y Coordinates to a new Location Value.
     *
     * @param newLocation The updated X & Y Coordinates of the new Player Location
     */
    public void setLocation(final int[] newLocation) {
        location = newLocation;
    }

    /**
     * Set the Player's Name to a new string value.
     *
     * @param name A string representing the new Player Name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set the Outcome Value to the Outcome of the Last Combat.
     *
     * @param lastOutcome The Outcome Value of the Combat
     */
    public void setLastOutcome(final int lastOutcome) {
        this.lastOutcome = lastOutcome;
    }

    /**
     * Create a Label object for each display element in the Player Info Scene by looking up the CSS ID's.
     * Pass the Label objects to helper Methods to update the Labels with the Player Variable information.
     *
     * @param characterInfoMenu The Grid Pane object containing the Labels to be updated
     */
    public void displayStats(final GridPane characterInfoMenu) {
        // Get the assosiated label for each part of the character info menu.
        // Title Labels.
        Label characterName = (Label) characterInfoMenu.lookup("#CharacterName");
        Label characterDescription = (Label) characterInfoMenu.lookup("#CharacterDescription");
        Label characterNameStats = (Label) characterInfoMenu.lookup("#StatsTitle");
        Label characterNameAbilities = (Label) characterInfoMenu.lookup("#AbilitiesTitle");
        // Stats Labels.
        Label characterHealth = (Label) characterInfoMenu.lookup("#PlayerHealth");
        Label characterLevel = (Label) characterInfoMenu.lookup("#PlayerLevel");
        Label characterExp = (Label) characterInfoMenu.lookup("#PlayerExp");
        /// Abilities Labels.
        Label characterAdd = (Label) characterInfoMenu.lookup("#PlayerAdd");
        Label characterTakeAway = (Label) characterInfoMenu.lookup("#PlayerTakeAway");
        Label characterReRoll = (Label) characterInfoMenu.lookup("#PlayerReRoll");
        // Helper functions to populate Labels with Player information.
        updateTitles(characterName, characterDescription, characterNameStats, characterNameAbilities);
        updateStats(characterHealth, characterLevel, characterExp);
        updateAbilities(characterAdd, characterTakeAway, characterReRoll);
    }

    // Update the Title Labels on the Player Info menu with the correct information.
    private void updateTitles(final Label nameTitle, final Label descriptionTitle,
                              final Label statsTitle, final Label abilitiesTitle) {
        // Set the name of the character to the Character Name field.
        nameTitle.setText(name);
        // Set the description of the character based on the Player's Level field.
        if (statLevel == 1) {
            descriptionTitle.setText("Deck Hand");
        } else if (statLevel == 2) {
            descriptionTitle.setText("Skipper");
        } else {
            descriptionTitle.setText("First Mate");
        }
        // Display a Title for the Stats section and Abilities section using the Player's Name field.
        statsTitle.setText(name + "'s Current Stats: ");
        abilitiesTitle.setText(name + "'s Ability Uses:");
    }

    // Update the Stats Labels on the Player Info menu with the correct information.
    private void updateStats(final Label health, final Label level, final Label exp) {
        // Display the information for the Player's Stats using values from the Player fields.
        health.setText("Credibility: " + statHealth);
        level.setText("Renown: " + statLevel);
        exp.setText("Reputation: " + statExp);
    }

    // Update the Abilities Labels on the Player Info menu with the correct information.
    private void updateAbilities(final Label add, final Label takeAway, final Label reRoll) {
        // Display the information for the Player's Abilities using values from the Player fields.
        add.setText("Add Uses: " + abilityAdd);
        takeAway.setText("Take Away Uses: " + abilityTakeAway);
        reRoll.setText("Re-Roll Uses: " + abilityReRoll);
    }

    /**
     * Use the Last Outcome value to determine if the Player won or lost the Combat and call the correct Method.
     *
     * @param descriptionArea A Text Area to display the Player Combat Results in.
     */
    public void finishCombat(final TextArea descriptionArea) {
        if (lastOutcome == LOSE_COMBAT || lastOutcome == LOSE_TO_BOSS) {
            // If: The outcome matches a "loss" Outcome Value, then call the "Lose Combat" method.
            loseCombat(lastOutcome, descriptionArea);
        } else if (lastOutcome == WIN_COMBAT) {
            // Else: The outcome matches a "win" Outcome Value, then call the "Win Combat" method.
            winCombat(descriptionArea);
        }
    }

    // Preform the appropriate actions and display the appropriate Player text for winning a Combat.
    private void winCombat(final TextArea descriptionArea) {
        // Increase Experience Stat for the Player.
        statExp += 1;
        // Try to read the "Win Combat" description from the Player Text File.
        try {
            // Create a new scanner for the Text File and display the Win Combat text.
            Scanner fileReader = new Scanner(playerText);
            descriptionArea.setText(fileReader.nextLine() + "\n");
            // Check if the Player Exp matches the Level Up threshold for their Level.
            if ((statLevel == LEVEL_1 && statExp == LEVEL_1_EXP_THRESHOLD)
                    || (statLevel == LEVEL_2 && statExp == LEVEL_2_EXP_THRESHOLD)) {
                // If the Exp Matches the threshold then reset the Player Exp and Level Up.
                statExp = 0;
                levelUp(descriptionArea);
            } else if (statLevel == LEVEL_3 && statExp >= 1) {
                // Else: If the Player is Level 3 print the Fight Boss text.
                descriptionArea.appendText(fileReader.nextLine());
            } else {
                // Else: Print the current Stats for the Player.
                descriptionArea.appendText("Level: " + statLevel);
                descriptionArea.appendText("\nExperience: " + statExp);
                descriptionArea.appendText("\nHealth: " + statHealth);
                // Print the amount of Exp needed for the Player to Level Up.
                descriptionArea.appendText("\nYou need " + ((2 * statLevel + 1) - statExp)
                        + " experience to level up.");
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    // Increase Player Stats and Display Level Up description.
    private void levelUp(final TextArea descriptionArea) {
        // Increase the Player's Stats.
        statLevel += 1;
        statHealth += 1;
        abilityReRoll += 1;
        abilityAdd += 1;
        abilityTakeAway += 1;
        // Try to read the Level Up description from the Player Text File.
        try {
            // Create a new scanner for the Text File and skip the Combat Outcome descriptions.
            Scanner fileReader = new Scanner(playerText);
            for (int combatTextLines = 0; combatTextLines < SKIP_TO_LEVEL_UP; combatTextLines++) {
                fileReader.nextLine();
            }
            // Display the Level Up description.
            descriptionArea.appendText(fileReader.nextLine() + "\n");
            if (statLevel == LEVEL_2) {
                // If: Player is Level 2 then display the Level 2 description.
                descriptionArea.appendText(fileReader.nextLine());
            }
            if (statLevel == LEVEL_3) {
                // If: Player is Level 2 then display the Level 3 description.
                fileReader.nextLine();
                descriptionArea.appendText(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    // Preform the appropriate actions and display the appropriate Player text for losing a Combat.
    private void loseCombat(final int outcome, final TextArea descriptionArea) {
        // Try to read the "Lose Combat" description from the Player Text File.
        try {
            // Create a new scanner for the Text File and skip the "Win Combat" section.
            Scanner fileReader = new Scanner(playerText);
            fileReader.nextLine();
            fileReader.nextLine();
            if (outcome == LOSE_COMBAT) {
                // If: Player lost a normal Combat.
                // Lower Player Health Stat by 1.
                statHealth -= 1;
                // If the Player Health is greater than 0, display lose Combat description.
                if (statHealth >= 1) {
                    descriptionArea.setText(fileReader.nextLine());
                    descriptionArea.appendText("\n" + statHealth + " Health remaining.");
                }
            } else if (outcome == LOSE_TO_BOSS) {
                // Else: Player lost a Combat to Boss
                // Lower Player Health Stat by 2.
                statHealth -= 1;
                // If the Player Health is greater than 0, display lose Combat to Boss description.
                if (statHealth >= 1) {
                    fileReader.nextLine();
                    descriptionArea.setText(fileReader.nextLine());
                    descriptionArea.appendText("\n" + statHealth + " Health remaining.");
                }
            }
        } catch (FileNotFoundException e) {
            // Catch any errors with reading the Text File.
            throw new RuntimeException(e);
        }
    }

    /**
     * If: the Player has a use available, use a charge of the "Re-Roll" Ability and display the remaining uses.
     * Else If: the Player has no uses remaining then inform the Player and do nothing.
     *
     * @param descriptionArea The Text Area to display the result of the Ability Use
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useReRoll(final TextArea descriptionArea) {
        // If: There are available uses, decrement the uses counter and display the remaining uses.
        if (abilityReRoll >= 1) {
            abilityReRoll -= 1;
            descriptionArea.setText("Re-Roll Ability Used. ");
            descriptionArea.setText(abilityTakeAway + " uses remaining of Re-Roll.\n");
            return true;
            // Else: Display that there are no remaining uses.
        } else {
            descriptionArea.setText("Cannot use Re-Roll. No uses.");
            return false;
        }
    }

    /**
     * If: the Player has a use available, use a charge of the "Add" Ability and display the remaining uses.
     * Else If: the Player has no uses remaining then inform the Player and do nothing.
     *
     * @param descriptionArea The Text Area to display the result of the Ability Use
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useAdd(final TextArea descriptionArea) {
        // If: There are available uses, decrement the uses counter and display the remaining uses.
        if (abilityAdd >= 1) {
            abilityAdd -= 1;
            descriptionArea.setText("Add Ability Used. ");
            descriptionArea.setText(abilityAdd + " uses remaining of Add 1.\n");
            return true;
            // Else: Display that there are no remaining uses.
        } else {
            descriptionArea.setText("Cannot use Add. No uses.");
            return false;
        }
    }

    /**
     * If: the Player has a use available, use a charge of the "Take-Away" Ability and display the remaining uses.
     * Else If: the Player has no uses remaining then inform the Player and do nothing.
     *
     * @param descriptionArea The Text Area to display the result of the Ability Use
     * @return A boolean value indicating if the Ability could be used
     */
    public boolean useTakeAway(final TextArea descriptionArea) {
        // If: There are available uses, decrement the uses counter and display the remaining uses.
        if (abilityTakeAway >= 1) {
            abilityTakeAway -= 1;
            descriptionArea.setText("Take-Away Ability Used. ");
            descriptionArea.setText(abilityTakeAway + " uses remaining of Take-Away.\n");
            return true;
            // Else: Display that there are no remaining uses.
        } else {
            descriptionArea.setText("Cannot use Take-Away. No uses.");
            return false;
        }
    }
}
