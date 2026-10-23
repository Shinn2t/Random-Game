import java.util.ArrayList;

public class CharacterManager {
   
    private ArrayList<GameCharacter> characters;

    public CharacterManager() {
        characters = new ArrayList<>();
    }

    public void addCharacter(GameCharacter character) {
        characters.add(character);
    }

    public ArrayList<GameCharacter> getCharacters() {
        return characters;
    }

    public GameCharacter searchCharacter(String name) {

        for (GameCharacter c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }

        return null;
    }

    public boolean removeCharacter(String name) {

        GameCharacter c = searchCharacter(name);

        if (c != null) {
            characters.remove(c);
            return true;
        }

        return false;
    }

    public boolean updateCharacter(String oldName, String newName) {

        GameCharacter c = searchCharacter(oldName);

        if (c != null) {
            c.setName(newName);
            return true;
        }

        return false;
    }
}

