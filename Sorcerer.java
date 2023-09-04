public class Sorcerer extends Hero{

    public Sorcerer(String name, int level, int healthPower, int magicPower, int money, int experience , int maxHealthPower , int maxMagicPower , int defence , int row , int col) {
        super(name, level, healthPower, magicPower, 5, 10, 10, money, experience , maxHealthPower , maxMagicPower , defence ,row , col);
        dexterity += 5;
        agility += 5;
    }
}
