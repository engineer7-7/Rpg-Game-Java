public class Weapon extends Item{

    private final int damage;

    public Weapon(String name, int price, int requiredLevel, int damage) {
        super(name, price);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }


}
