import java.util.ArrayList;
import java.util.List;

public class Hero extends Living {
    private int defense;
    private Armor equippedArmor;
    private Weapon equippedWeapon;

    public List<Armor> ownedArmors;
    public List<Weapon> ownedWeapons;

    private int row;
    private int col;
    private final int maxHealthPower;
    private int baseDamage;
    private Weapon weapon;
    private int baseDefense;
    private int damage;

    protected List<Spell> spellInventory;
    protected List<Item> inventory;
    protected List<Monster> monsters;
    protected int magicPower;
    protected int strength;
    protected int dexterity;
    protected int agility;
    private int money;
    private int experience;

    public Hero(String name, int level, int healthPower, int magicPower, int strength, int dexterity, int agility,
                int money, int experience, int defense, int maxHealthPower, int maxMagicPower, int row, int col) {
        super(name, level, healthPower);
        this.magicPower = magicPower;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.money = money;
        this.experience = experience;
        this.inventory = new ArrayList<>();
        this.spellInventory = new ArrayList<>();
        this.defense = defense;
        this.maxHealthPower = maxHealthPower;
        this.row = row;
        this.col = col;
        this.monsters = new ArrayList<>();
        this.ownedArmors = new ArrayList<>();
        this.ownedWeapons = new ArrayList<>();
    }

    public int getMagicPower() {
        return magicPower;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getAgility() {
        return agility;
    }

    public int getMoney() {
        return money;
    }

    public int getExperience() {
        return experience;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public List<Spell> getSpellInventory() {
        return spellInventory;
    }


    public void levelUp(Grid grid) {
        if (experience >= 30) {
            level++;
            strength += 10;
            agility += 10;
            dexterity += 10;
            healthPower += 5;
            magicPower += 5;

            experience -= 30;
            grid.updateMonsterLevels(level);

            int spiritNewLevel = level;
            int dragonNewLevel = level;
            int exoskeletonNewLevel = level;
            System.out.println("Hero has leveled up!");
            System.out.println("Hero's new level: " + level);
            System.out.println("New strength: " + strength);
            System.out.println("New agility: " + agility);
            System.out.println("Spirit new level: " + spiritNewLevel);
            System.out.println("Dragon new level: " + dragonNewLevel);
            System.out.println("Exoskeleton new level: " + exoskeletonNewLevel);
        } else {
            System.out.println("Not enough XP for level up!");
        }
    }




    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void decreaseMoney(int amount) {
        this.money = getMoney() - amount;
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    public void increaseHeroMoney(int amount) {
        this.money = getMoney() + amount;
    }

    public void addSpellToInventory(Spell spell) {
        spellInventory.add(spell);
    }

    public void removeSpellFromInventory(Spell spell) {
        spellInventory.remove(spell);
    }

    public void displayStats() {
        System.out.println("Name: " + name);
        System.out.println("Level: " + level);
        System.out.println("Health Power: " + healthPower);
        System.out.println("Magic Power: " + magicPower);
        System.out.println("Strength: " + strength);
        System.out.println("Dexterity: " + dexterity);
        System.out.println("Agility: " + agility);
        System.out.println("Money: " + money);
        System.out.println("Experience: " + experience);
        System.out.println("Inventory: " + inventory);
        System.out.println("Spell Inventory: " + spellInventory);

    }

    public void usePotion(Potion potion) {
        String potionType = potion.getPotionChoice();

        switch (potionType) {
            case "Health" -> increaseHealthPower(20);
            case "Mana" -> increaseMagicPower(15);
            case "Strength" -> increaseStrength(10);
            case "Agility" -> increaseAgility(10);
            case "Dexterity" -> increaseDexterity(8);
            case "Defense" -> increaseDefense(10);
        }
        removeItemFromInventory(potion);
    }
    public void increaseHealthPower(int amount) {
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            if (healthPower + amount > getMaxHealthPower()) {
                setHealthPower(getMaxHealthPower());
            } else {
                setHealthPower(healthPower + amount);
                System.out.println("Health increased by " + amount + " points.");
                System.out.println("Hero's new healthPower: " + getHealthPower());
            }
        }
    }
    public void increaseMagicPower(int amount) {
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            int newMagicPower = magicPower + amount;
            System.out.println("Hero's new magicPower is: " + newMagicPower);

        } else {
            System.out.println("No valid hero! ");
        }
    }
    public void increaseStrength(int amount) {
        System.out.println(strength);
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            setStrength(strength + amount);
            System.out.println("Strength increased by " + amount + " points.");
            System.out.println("Hero's new strength: " + getStrength());

        } else {
            System.out.println("No valid hero! ");
        }
    }

    public void increaseDefense(int amount) {
        System.out.println(defense);
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            setDefence(defense + amount);
            System.out.println("Defence increased by " + amount + "points. ");
            System.out.println("Hero's new defence: " + getDefense());

        } else {
            System.out.println("No valid hero! ");
        }
    }
    public void increaseAgility(int amount) {
        System.out.println(agility);
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            setAgility(agility + amount);
            System.out.println("Agility increased by " + amount + " points.");
            System.out.println("Hero's new agility: " + getAgility());

        } else {
            System.out.println("No valid hero! ");
        }
    }
    public void increaseDexterity(int amount) {
        System.out.println(dexterity);
        if (this instanceof Paladin || this instanceof Sorcerer || this instanceof Warrior) {
            setDexterity(dexterity + amount);
            System.out.println("Dexterity increased by " + amount + " points.");
            System.out.println("Hero's new dexterity: " + getDexterity());

        } else {
            System.out.println("No valid hero! ");
        }
    }
    public int getDefense() {
        return defense;
    }
    public void takeDamage(int damage) {
        healthPower -= damage;
        if (healthPower < 0) {
            healthPower = 0;
        }
        System.out.println(name + " took " + damage + " damage!");
    }
    public int getMaxHealthPower() {
        return maxHealthPower;
    }
    public void setHealthPower(int healthPower) {
        this.healthPower = healthPower;
    }
    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.damage = this.baseDamage + weapon.getDamage();
        System.out.println("Equipped " + weapon.getName() + " weapon.");
    }
    public void equipArmor(Armor armor) {
        this.defense = this.baseDefense + armor.getDefense();
        System.out.println("Equipped " + armor.getName() + " armor.");
    }
    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }
    public void setCol(int i) {
        col = i;
    }
    public void setRow(int i) {
        row = i;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setDefence(int defence) {
        this.defense = defense;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public Weapon getEquippedWeapon(){
        return weapon;
    }
    public Armor getEquippedArmor() {
        return equippedArmor;
    }
    public void addOwnedArmor(Armor armor) {
        ownedArmors.add(armor);
    }
    public List<Armor> getOwnedArmors() {
        return ownedArmors;
    }
    public void addOwnedWeapon(Weapon weapon){
        ownedWeapons.add(weapon);

    }
    public List<Weapon> getOwnedWeapons(){
        return ownedWeapons;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }
}
