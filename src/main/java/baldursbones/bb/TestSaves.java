package baldursbones.bb;

public class TestSaves {

    private String saveName;

    private String characterName;

    private String saveTime;

    public TestSaves(String saveName, String characterName, String saveTime) {
        this.saveName = saveName;
        this.characterName = characterName;
        this.saveTime = saveTime;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }
}
