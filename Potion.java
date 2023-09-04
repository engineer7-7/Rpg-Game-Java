public class Potion extends Item{

    private final String potionChoice;

    public Potion(String name, int price, int requiredLevel, String potionChoice) {
        super(name, price);
        this.potionChoice = potionChoice;
    }
    public String getPotionChoice() {
        return potionChoice;
    }

}
