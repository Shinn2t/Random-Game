public class Archer extends GameCharacter{
      
	public Archer(String name, int level) {
        super(name, level);
    }

    @Override
    public String attack() {
        return name + " shoots an arrow!";
    }

    @Override
    public String specialMove() {
        return name + " uses Rain of Arrows!";
    }
}
