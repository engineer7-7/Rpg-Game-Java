public class Warrior extends Hero{

    public Warrior(String name, int level, int healthPower, int magicPower, int money, int experience , int maxHealthPower , int maxMagicPower , int defence , int row , int col) {
        super(name, level, healthPower, magicPower, 10, 5, 10, money, experience , maxHealthPower , maxMagicPower , defence , row , col);
        strength += 5;
        agility += 5;
    }
}
