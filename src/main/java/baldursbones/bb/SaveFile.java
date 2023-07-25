package baldursbones.bb;

/**
 * Save File Format.
 *
 * @author Braden Rogers
 * @version Baldur's Bones v1.1
 */
public class SaveFile {
    // Name of the save file.
    private final String fileName;

    // Name of the saved character.
    private final String characterName;

    // Time the file was saved.
    private final String saveTime;

    // Name of the associated game data file.
    private final String infoFile;

    /**
     * Creates a Save File object that with the Save File Info and the Path of the Save Info File.
     *
     * @param fileName      The Name of the Save File object
     * @param characterName The Character Name assosiated with the Save File
     * @param saveTime      The time that the Save File was created / updated
     * @param infoFile      The Path of the Info File associated with the Save File
     */
    public SaveFile(final String fileName, final String characterName, final String saveTime, final String infoFile) {
        this.fileName = fileName;
        this.characterName = characterName;
        this.saveTime = saveTime;
        this.infoFile = infoFile;
    }

    /**
     * Returns a string with the File Name value of the Save File object.
     * Used to find the Save File data in the Master Save Directory / Display Info in the Saved Games Table.
     *
     * @return A string representing the File Name value of this Save File object
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns a string with the Character Name value associated with the Save File object.
     * Used to display Save Information in the Saved Games Table.
     *
     * @return A string representing the Character Name value assosiated with this Save File object
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Returns a string with the Save Time value of the Save File object.
     * Used to display Save Information in the Saved Games Table.
     *
     * @return A string representing the Save Time value assosiated with this Save File object
     */
    public String getSaveTime() {
        return saveTime;
    }

    /**
     * Returns a string with the Path of the Info File assosiated with the Save File.
     * Used to find the assosiated Info File when Loading or Deleting a Save / Display Info in the Saved Games Table.
     *
     * @return A string representing the Path of the Info File of the Save File object
     */
    public String getInfoFile() {
        return infoFile;
    }
}
