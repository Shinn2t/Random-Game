public class Mage extends GameCharacter{
	 public Mage(String name, int level) {
	        super(name, level);
	    }

	    @Override
	    public String attack() {
	        return name + " casts Fireball!";
	    }

	    @Override
	    public String specialMove() {
	        return name + " uses Lightning Storm!";
	    }

}
