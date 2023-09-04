public class Paladin extends Hero{

    public Paladin(String name, int level, int healthPower, int magicPower, int money, int experience , int maxHealthPower , int maxMagicPower , int defence , int row , int col) {
        super(name, level, healthPower, magicPower, 10, 10, 5, money, experience , maxHealthPower , maxMagicPower , defence , row ,col);
        strength += 5;
        dexterity += 5;
    }
}
