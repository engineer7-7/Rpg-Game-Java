
public class Monster extends Living{

    private int damageRange;
    private int strength;
    private int defense;
    private double dodgeChance;
    protected int experienceReward;
    protected int moneyReward;
    private int maxHealthPower;
    private int row;
    private int col;
    private int initialHealth;
    private int damageRangeRounds;

    public Monster(String name, int level, int healthPower, int damageRange, int defense, double dodgeChance , int strength , int maxHealthPower) {
        super(name , level , healthPower);
        this.damageRange = damageRange;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        this.strength = strength;
        this.maxHealthPower = maxHealthPower;
        this.initialHealth = maxHealthPower;
    }

    public void displayStats() {
        System.out.println("Monster: " + name);
        System.out.println("Level: " + level);
        System.out.println("Health: " + healthPower);
        System.out.println("Damage Range: " + damageRange);
        System.out.println("Defense: " + defense);
        System.out.println("Dodge Chance: " + dodgeChance);
    }

    public void takeDamage(int damage) {
        healthPower -= damage;
        if (healthPower < 0) {
            healthPower = 0;
        }
        System.out.println(name + " took " + damage + " damage!");
    }

    public int getDefense() {
        return defense;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public int getStrength() {
        return strength;
    }

    public int getMaxHealthPower() {
        return maxHealthPower;
    }

    public void increaseHealthPower(int amount) {
        healthPower += amount;
        if (healthPower > maxHealthPower) {
            healthPower = maxHealthPower;
        }
    }

    public void setHealthPower(int healthPower){
        this.healthPower = healthPower;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void monsterLevelUp(){
        level+= 1;
        strength += 5;
        defense += 10;
        System.out.println("Monster new level: " + level);

    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getInitialHealth() {
        return initialHealth;
    }

    public void reduceDamageRange(int rounds) {
        this.damageRangeRounds = rounds;
    }

    public void reduceDefense(int amount) {
        defense -= amount;
        if (defense < 0) {
            defense = 0;
        }
    }


    public void setDamageRange(int damageRange){
        this.damageRange = damageRange;
    }
    public int getDamageRange(){
        return damageRange;
    }

    public void setDefence(int defence) {
        this.defense = defence;
    }

    public void setDodgeChance(double dodgeChance){
        this.dodgeChance = dodgeChance;

    }

    public double getDodgeChance() {
        return dodgeChance;
    }
}
