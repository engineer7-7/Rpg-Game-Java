public class Living {

    protected String name;
    protected int level;
    protected int healthPower;

    public Living(String name, int level, int healthPower) {
        this.name = name;
        this.level = level;
        this.healthPower = healthPower;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealthPower() {
        return healthPower;
    }

    public void reduceHealth(int amount) {
        healthPower -= amount;
        if (healthPower <= 0) {
            healthPower = 0;
            System.out.println(name + " fainted.");
        }
    }
}
