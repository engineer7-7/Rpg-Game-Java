public class Armor extends Item{

    private final int defense;

    public Armor(String name, int price, int requiredLevel, int defense) {
        super(name, price);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }
}

