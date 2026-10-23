public abstract class GameCharacter {

    protected String name;
    protected int level;
    protected int health = 100;

    public GameCharacter(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void takeDamage(int damage) {
        health -= damage;

        if (health < 0) {
            health = 0;
        }
    }

    public void heal(int amount) {
        health += amount;

        if (health > 100) {
            health = 100;
        }
    }

    public abstract String attack();

    public abstract String specialMove();

    @Override
    public String toString() {
        return "Name: " + name
                + "\nType: " + getClass().getSimpleName()
                + "\nLevel: " + level
                + "\nHealth: " + health + "/100";
    }
}