package baldursbones.bb;

/**
 * Save File Format.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SaveFile {
    // Name of the save file.
    private String fileName;

    // Name of the saved character.
    private String characterName;

    // Time the file was saved.
    private String saveTime;

    // Name of the associated game data file.
    private String dataFile;

    /**
     * Creates a save file object that contains all the values needed to display and open a save file.
     *
     * @param fileName      the name of the save file object
     * @param characterName the character name being played in the save file
     * @param saveTime      the time that the save file was created / updated
     * @param dataFile      the name of the file with the character data
     */
    public SaveFile(final String fileName, final String characterName, final String saveTime, final String dataFile) {
        this.fileName = fileName;
        this.characterName = characterName;
        this.saveTime = saveTime;
        this.dataFile = dataFile;
    }

    /**
     * Returns a string with the file name value of the save file object.
     *
     * @return a string with the file name value of this save file object
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the file name property of this save file object to a passed string.
     *
     * @param fileName the new string to set the file name value to
     */
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns a string with the character name value of the save file object.
     *
     * @return a string with the character name value of this save file object
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Sets the value of the character name property of this save file object to a passed string.
     *
     * @param characterName the new string to set the character name value to
     */
    public void setCharacterName(final String characterName) {
        this.characterName = characterName;
    }

    /**
     * Returns a string with the save time value of the save file object.
     *
     * @return a string with the save time value of this save file object
     */
    public String getSaveTime() {
        return saveTime;
    }

    /**
     * Sets the value of the save time property of this save file object to a passed string.
     *
     * @param saveTime the new string to set the save time value to
     */
    public void setSaveTime(final String saveTime) {
        this.saveTime = saveTime;
    }

    /**
     * Returns a string with the datafile value of the save file object.
     *
     * @return a string with the data file value of this save file object
     */
    public String getDataFile() {
        return dataFile;
    }

    /**
     * Sets the value of the data file property of this save file object to a passed string.
     *
     * @param dataFile the new string to set the data file value to
     */
    public void setDataFile(final String dataFile) {
        this.dataFile = dataFile;
    }
}
