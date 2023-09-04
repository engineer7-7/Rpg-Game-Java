import java.util.*;

public class Market {
    private final List<Spell> spellList;
    private final List<Armor> armorList;
    private final List<Weapon> weaponList;
    private final List<Potion> potionList;

    public Market() {
        Random random = new Random();
        spellList = new ArrayList<>();
        armorList = new ArrayList<>();
        weaponList = new ArrayList<>();
        potionList = new ArrayList<>();
        spellList.add(new IceSpell("IceBall", 10, 1, 20));
        spellList.add(new FireSpell("FireBall", 15, 1, 15));
        spellList.add(new LightingSpell("LightingBall", 20, 1, 20));
        String[] weaponTypes = {"Sword", "Axe", "Bow", "Staff", "Dagger"};
        for (int i = 0; i < 5; i++) {
            String weaponType = weaponTypes[i];
            int damage = random.nextInt(20) + 10;
            weaponList.add(new Weapon(weaponType + " " , random.nextInt(50) + 10, random.nextInt(5) + 1, damage));
        }

        String[] potionTypes = {"Health", "Mana", "Strength", "Speed", "Defense"};
        for (int i = 0; i < 5; i++) {
            String potionType = potionTypes[i];
            potionList.add(new Potion(potionType + " Potion " + i, random.nextInt(20) + 5, random.nextInt(5) + 1, potionType));
        }

        String[] armorTypes = {"Helmet", "Chest", "Legs", "Gloves", "Boots"};
        for (int i = 0; i < 5; i++) {
            String armorType = armorTypes[i];
            armorList.add(new Armor(armorType, random.nextInt(20) + 1, random.nextInt(10) + 1, random.nextInt(30) + 1));
        }
    }

    public void handleHeroMarketActions(Hero hero) {
        Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Welcome to the market! ");
                System.out.println("What would you like to do for hero " + hero.getName() + "?");
                System.out.println("1. Buy items");
                System.out.println("2. Buy spells");
                System.out.println("3. Sell items ");
                System.out.println("4. Sell spells ");
                System.out.println("5. Exit");
                int choice;
                String choiceInput = scanner.nextLine();
                try {
                    choice = Integer.parseInt(choiceInput);
                    if (choice < 1 || choice > 5) {
                        System.out.println("Please enter a number among 1-5! ");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. ");
                    continue;
                }

                switch (choice) {
                    case 1 -> buyItems(hero);
                    case 2 -> buySpells(hero);
                    case 3 -> {
                        if (hero.getInventory().isEmpty()) {
                            System.out.println("Hero doesn't have any items to sell.");
                        } else {
                            System.out.println("Available items for selling:");
                            int index = 1;
                            for (Item item : hero.getInventory()) {
                                System.out.println(index + ". " + item.getName() + " - Price: " + item.getPrice());
                                index++;
                            }
                            int itemSellChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (itemSellChoice >= 1 && itemSellChoice <= hero.getInventory().size()) {
                                Item itemToSell = hero.getInventory().get(itemSellChoice - 1);
                                sellItems(hero, itemToSell);
                            } else {
                                System.out.println("Invalid item choice.");
                            }
                        }
                    }
                        case 4 -> {
                            if (hero.getSpellInventory().isEmpty()) {
                                System.out.println("Hero doesn't have any spells to sell.");
                            } else {
                                System.out.println("Available spells for selling:");
                                int index = 1;
                                for (Spell spell : hero.getSpellInventory()) {
                                    System.out.println(index + ". " + spell.getName() + " - Price: " + spell.getPrice());
                                    index++;
                                }
                                int spellSellChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (spellSellChoice >= 1 && spellSellChoice <= hero.getSpellInventory().size()) {
                                    Spell spellToSell = hero.getSpellInventory().get(spellSellChoice - 1);
                                    sellSpell(hero, spellToSell);
                                } else {
                                    System.out.println("Invalid spell choice.");
                                }
                            }
                        }

                        case 5 -> {
                        System.out.println("Exiting hero actions for " + hero.getName());
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }

        }
    public void buyItems(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the type of item you want to buy:");
        System.out.println("1. Armor");
        System.out.println("2. Weapon");
        System.out.println("3. Potion");

        int itemChoice = scanner.nextInt();
        scanner.nextLine();

        switch (itemChoice) {
            case 1 -> {
                if (armorList.isEmpty()) {
                    System.out.println("No armors available for purchase.");
                } else {
                    System.out.println("Available armors for purchase:");
                    for (int i = 0; i < armorList.size(); i++) {
                        Armor armor = armorList.get(i);
                        System.out.println((i + 1) + ". " + armor.getName() + " - Price: " + armor.getPrice());
                    }
                    int armorChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (armorChoice >= 1 && armorChoice <= armorList.size()) {
                        Armor chosenArmor = armorList.get(armorChoice - 1);
                        if (hero.getMoney() >= chosenArmor.getPrice()) {
                            hero.addItemToInventory(chosenArmor);
                            hero.decreaseMoney(chosenArmor.getPrice());
                            armorList.remove(chosenArmor);
                            hero.addOwnedArmor(chosenArmor);
                            System.out.println("You bought " + chosenArmor.getName() + "for " + chosenArmor.getPrice());
                            System.out.println("Your remaining money: " + hero.getMoney());
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                    } else {
                        System.out.println("Invalid armor choice.");
                    }
                    if (armorList.isEmpty()) {
                        System.out.println("No armors left to purchase.");
                    }
                }
            }

            case 2 -> {
                if (weaponList.isEmpty()) {
                    System.out.println("No weapons available for purchase.");
                } else {
                    System.out.println("Available weapons for purchase:");
                    for (int i = 0; i < weaponList.size(); i++) {
                        Weapon weapon = weaponList.get(i);
                        System.out.println((i + 1) + ". " + weapon.getName() + " - Price: " + weapon.getPrice());
                    }
                    int weaponChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (weaponChoice >= 1 && weaponChoice <= weaponList.size()) {
                        Weapon chosenWeapon = weaponList.get(weaponChoice - 1);
                        if (hero.getMoney() >= chosenWeapon.getPrice()) {
                            hero.addItemToInventory(chosenWeapon);
                            hero.addOwnedWeapon(chosenWeapon);
                            hero.decreaseMoney(chosenWeapon.getPrice());
                            weaponList.remove(chosenWeapon);
                            hero.equipWeapon(chosenWeapon);
                            System.out.println("You bought " + chosenWeapon.getName());
                            System.out.println("Your remaining money: " + hero.getMoney());
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                    } else {
                        System.out.println("Invalid weapon choice.");
                    }

                    if (weaponList.isEmpty()) {
                        System.out.println("No weapons left to purchase.");
                    }
                }
            }

            case 3 -> {
                if (potionList.isEmpty()) {
                    System.out.println("No potions available for purchase.");
                } else {
                    System.out.println("Available potions for purchase:");
                    for (int i = 0; i < potionList.size(); i++) {
                        Potion potion = potionList.get(i);
                        System.out.println((i + 1) + ". " + potion.getName() + " - Price: " + potion.getPrice());
                    }
                    int potionChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (potionChoice >= 1 && potionChoice <= potionList.size()) {
                        Potion chosenPotion = potionList.get(potionChoice - 1);
                        if (hero.getMoney() >= chosenPotion.getPrice()) {
                            hero.addItemToInventory(chosenPotion);
                            hero.decreaseMoney(chosenPotion.getPrice());
                            potionList.remove(chosenPotion);
                            System.out.println("You bought " + chosenPotion.getName());
                            System.out.println("Your remaining money: " + hero.getMoney());
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                    } else {
                        System.out.println("Invalid potion choice.");
                    }
                    if (potionList.isEmpty()) {
                        System.out.println("No potions left to purchase.");
                    }
                }
            }

            default -> System.out.println("Invalid item type choice.");
        }
    }




    public void buySpells(Hero hero) {
        Scanner scanner = new Scanner(System.in);

        if (spellList.isEmpty()) {
            System.out.println("There are no spells available in the market.");
            return;
        }

        System.out.println("Available spells for purchase:");
        for (int i = 0; i < spellList.size(); i++) {
            Spell spell = spellList.get(i);
            System.out.println((i + 1) + ". " + spell.getName() + " - Price: " + spell.getPrice());
        }

        int spellChoice = scanner.nextInt();
        scanner.nextLine();

        if (spellChoice >= 1 && spellChoice <= spellList.size()) {
            Spell chosenSpell = spellList.get(spellChoice - 1);

            if (hero.getMoney() >= chosenSpell.getPrice()) {
                if (chosenSpell.getRequiredLevel() <= hero.getLevel()) {
                    hero.addSpellToInventory(chosenSpell);
                    hero.decreaseMoney(chosenSpell.getPrice());
                    spellList.remove(chosenSpell);
                    System.out.println("You bought " + chosenSpell.getName());
                    System.out.println("Your remaining money: " + hero.getMoney());
                    System.out.println("Spell added to your inventory:");
                    System.out.println(chosenSpell);
                } else {
                    System.out.println("You don't have the required level to buy this spell.");
                }
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Invalid spell choice.");
        }
    }

    public void sellSpell(Hero hero, Spell spell) {
        if (hero.getSpellInventory().contains(spell)) {
            hero.removeSpellFromInventory(spell);
            hero.increaseHeroMoney(spell.getPrice() / 2);
            spellList.add(spell);
            System.out.println("You sold " + spell.getName() + " spell.");
            System.out.println("Your remaining money: " + hero.getMoney());

        } else {
            System.out.println("Hero doesn't have this spell in inventory.");
        }
    }
    public void sellItems(Hero hero, Item item) {
        if (hero.getInventory().contains(item)) {
            hero.removeItemFromInventory(item);
            hero.increaseHeroMoney(item.getPrice() / 2);

            if (item instanceof Armor) {
                armorList.add((Armor) item);
                System.out.println("You sold " + item.getName());
            } else if (item instanceof Weapon) {
                weaponList.add((Weapon) item);
                System.out.println("You sold " + item.getName());
            } else if (item instanceof Potion) {
                potionList.add((Potion) item);
                System.out.println("You sold " + item.getName());
            } else {
                System.out.println("Invalid item type.");
            }

            System.out.println("Your remaining money: " + hero.getMoney());
        } else {
            System.out.println("Hero doesn't have this item in inventory.");
        }
    }



}
