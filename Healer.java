public class Healer extends GameCharacter{
 
	public Healer(String name, int level) {
        super(name, level);
    }

    @Override
    public String attack() {
        return name + " throws holy energy!";
    }

    @Override
    public String specialMove() {
        return name + " uses Divine Healing!";
    }
}
