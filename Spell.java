public class Spell {
    private final String name;
    private final int price;
    private final int requiredLevel;
    private final int damage;
    public Spell(String name, int price, int requiredLevel,  int damage) {
        this.name = name;
        this.price = price;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getDamage() {
        return damage;
    }
    public int getRequiredLevel() {
        return requiredLevel;
    }
    @Override
    public String toString() {
        return getName() + getPrice();
    }

}
