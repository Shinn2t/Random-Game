public class Warrior extends GameCharacter{
	
	 public Warrior(String name, int level) {
	        super(name, level);
	    }

	    @Override
	    public String attack() {
	        return name + " swings a mighty sword!";
	    }

	    @Override
	    public String specialMove() {
	        return name + " uses Shield Bash!";
	    }

}
