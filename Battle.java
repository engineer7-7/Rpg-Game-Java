import java.util.*;

public class Battle {

    private final List<Hero> heroes;
    private final List<Monster> monsters;
    private final Grid grid;

    public Battle(List<Hero> heroes, List<Monster> monsters, Grid grid) {
        this.heroes = heroes;
        this.monsters = monsters;
        this.grid = grid;
    }
    public void startBattle() {
        Random random = new Random();
        int numMonsters = countAliveMonsters();
        Monster targetMonster = monsters.get(random.nextInt(numMonsters));
        int round = 1;
        boolean isBattleOver = false;
        while (!isBattleOver) {
            for (Hero hero : heroes) {
                if (hero.getHealthPower() > 0) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("======= Round " + round + " ======== ");
                    System.out.println("Hero's health: " + hero.getHealthPower() +"\n" + "Hero's magicPower: "
                            + hero.magicPower
                            +"\n" + "Monster's health: " + targetMonster.getHealthPower());
                    System.out.println("What action would you like to take for " + hero.getName() + "?");
                    System.out.println("1. Attack");
                    System.out.println("2. Cast Spell");
                    System.out.println("3. Use Item");
                    System.out.println("4. Change Equipment");
                    System.out.println("Enter 'Q' to quit.");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("Q")) {
                        System.out.println("Exiting hero actions for " + hero.getName());
                        System.out.println("Hero lost 10 $ for this action! ");
                        hero.setMoney(hero.getMoney() - 10);
                        return;
                    }
                    try {
                        int actionChoice = Integer.parseInt(input);
                        if (actionChoice < 1 || actionChoice > 4) {
                            System.out.println("Please enter a number among 1-4! ");
                        } else {
                            switch (actionChoice) {
                                case 1 -> {
                                    attackMonster(hero, targetMonster);
                                    if (hero.getHealthPower() <= 0) {
                                        System.out.println(hero.getName() + " has been defeated!");
                                        penaltyHeroes();
                                    }
                                }
                                case 2 -> castSpell(hero, targetMonster);
                                case 3 -> useItem(hero);
                                case 4 -> changeEquipment(hero);
                                default -> System.out.println("Invalid action choice.");
                            }
                            if (targetMonster.getHealthPower() > 0) {
                                monstersTurn(targetMonster, hero);
                                round++;
                                hero.setHealthPower(hero.getHealthPower() + 15);
                                targetMonster.setHealthPower(targetMonster.getHealthPower() + 15);
                                hero.setMagicPower(hero.getMagicPower() + 10);
                                System.out.println("Hero's health: " + hero.getHealthPower()
                                +"\n" + "Monster's health: " + targetMonster.getHealthPower());
                            } else {
                                System.out.println("Heroes have won !");
                                System.out.println(targetMonster.getName() + " has been defeated!");
                                int updatedXp = hero.getExperience() + 10;
                                hero.setExperience(updatedXp);
                                System.out.println(hero.getName() + " has won the battle!");
                                int updatedMoney = hero.getMoney() + 10;
                                System.out.println(hero.getName() + " new money: " + updatedMoney);
                                hero.levelUp(grid);
                                targetMonster.setHealthPower(50);
                                isBattleOver = true;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number or 'Q' to quit.");
                    }
                }
            }
            if (heroesAreDead()) {
                System.out.println("Monsters have won! ");
                penaltyHeroes();
                targetMonster.setHealthPower(50);
                isBattleOver = true;
            }
        }
        targetMonster.setHealthPower(50);
    }
    public boolean heroesAreDead() {
        for (Hero hero : heroes) {
            if (hero.getHealthPower() > 0) {
                return false;
            }
        }
        return true;
    }
    public void castSpell(Hero hero, Monster targetMonster) {
        if (hero.getSpellInventory().size() == 0) {
            applySpell(hero, null, targetMonster);
            return;
        }
        System.out.println("Which spell do you want to use? ");
        for (int i = 0; i < hero.getSpellInventory().size(); i++) {
            Spell spell = hero.getSpellInventory().get(i);
            System.out.println((i + 1) + ". " + spell.getName());
        }
        Scanner scanner = new Scanner(System.in);
        int spellChoice = scanner.nextInt();
        scanner.nextLine();
        if (spellChoice >= 1 && spellChoice <= hero.getSpellInventory().size()) {
            Spell chosenSpell = hero.getSpellInventory().get(spellChoice - 1);
            applySpell(hero, chosenSpell, targetMonster);
            hero.getSpellInventory().remove(chosenSpell);
        } else {
            System.out.println("Invalid spell choice.");
        }
    }
    public void useItem(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        if (hero.getInventory().isEmpty()) {
            System.out.println("Hero's inventory is empty. ");
            System.out.println("Skipping Hero's turn. ");
        } else {
            System.out.println("Available potions:");
            for (int i = 0; i < hero.getInventory().size(); i++) {
                Item item = hero.getInventory().get(i);
                if (item instanceof Potion potion) {
                    System.out.println((i + 1) + ". " + potion.getName());
                } else {
                    System.out.println("Item not potion! ");
                }
            }
            System.out.println("Choose a potion to use:");
            int potionChoice = scanner.nextInt();
            scanner.nextLine();
            if (potionChoice >= 1 && potionChoice <= hero.getInventory().size()) {
                Potion chosenPotion = (Potion) hero.getInventory().get(potionChoice - 1);
                hero.usePotion(chosenPotion);
                System.out.println(hero.getName() + " used " + chosenPotion.getName() + " potion.");
            } else {
                System.out.println("Invalid potion choice.");
            }
        }
    }
    public int calculateDamage(int attackerStrength, int defenderDefense) {
        int damageReduction = defenderDefense / 2;
        return Math.max(0, attackerStrength - damageReduction);
    }
    public void applySpell(Hero hero, Spell spell, Monster targetMonster) {
        if (spell == null) {
            System.out.println("No spells available! ");
            System.out.println("Skipping hero's turn ");
        } else if (spell instanceof IceSpell) {
            iceSpellChoice(hero, targetMonster, spell);
        } else if (spell instanceof FireSpell) {
            fireSpellChoice(hero, targetMonster, spell);
        } else if (spell instanceof LightingSpell) {
            lightingSpellChoice(hero, targetMonster, spell);
        } else {
            System.out.println("Invalid spell choice. ");
        }
    }
    public int countAliveMonsters() {
        int count = 0;
        for (Monster monster : monsters) {
            if (monster.getHealthPower() > 0) {
                count++;
            }
        }
        return count;
    }
    public void monstersTurn(Monster attackingMonster, Hero targetHero) {
        if (attackingMonster != null && targetHero != null) {
            attack(attackingMonster, targetHero);
        }
    }
    public void attack(Monster monster, Hero hero) {
        Random random = new Random();
        int minDamage = monster.getStrength() * 2;
        int maxDamage = monster.getStrength() * 3;
        int damageDealt = minDamage + random.nextInt(maxDamage - minDamage + 1);
        int defense = hero.getDefense();
        int damageReduction = defense / 2;
        int finalDamage = Math.max(0, damageDealt - damageReduction);
        int randomDamage = random.nextInt(31) + 10;
        finalDamage = Math.min(finalDamage, randomDamage);
        hero.takeDamage(finalDamage);
        System.out.println(monster.getName() + " attacked " + hero.getName() + " for " + finalDamage + " damage!");
    }
    public void penaltyHeroes() {
        int moneyLost = 0;
        for (Hero hero : heroes) {
            moneyLost = hero.getMoney() / 2;
            hero.decreaseMoney(moneyLost);
            hero.setHealthPower(50);
        }
        System.out.println("Heroes have been penalized. They lost " + moneyLost + " money.");
    }
    public void changeEquipment(Hero hero) {
        Scanner scanner = new Scanner(System.in);
        if (hero.getInventory().isEmpty()) {
            System.out.println("Hero's inventory is empty!");
            System.out.println("Skipping hero's turn.");
        } else {
            int equipmentChoice;
            while (true) {
                System.out.println("Choose equipment type to change:");
                System.out.println("1. Armor");
                System.out.println("2. Weapon");
                equipmentChoice = scanner.nextInt();
                scanner.nextLine();

                if (equipmentChoice == 1 || equipmentChoice == 2) {
                    break;
                } else {
                    System.out.println("Invalid equipment choice.");
                }
            }
            switch (equipmentChoice) {
                case 1 -> changeArmor(hero);
                case 2 -> changeWeapon(hero);
            }
        }
    }
    public void changeWeapon(Hero hero) {
        Weapon equippedWeapon = hero.getEquippedWeapon();
        if (equippedWeapon != null) {
            System.out.println(hero.getName() + " equipped weapon " + equippedWeapon.getName() + " - Attack: " + equippedWeapon.getDamage());
        } else {
            System.out.println(hero.getName() + " does not have equipped weapon");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Owned weapons:");
        List<Weapon> ownedWeapons = hero.getOwnedWeapons();

        for (int i = 0; i < ownedWeapons.size(); i++) {
            Weapon weapon = ownedWeapons.get(i);
            System.out.println((i + 1) + ". " + weapon.getName() + " - Attack: " + weapon.getDamage());
        }
        System.out.println("Choose an weapon to equip (or enter 0 to cancel): ");
        int weaponChoice = scanner.nextInt();
        scanner.nextLine();
        if (weaponChoice == 0) {
            System.out.println("Canceled armor change.");
        } else if (weaponChoice >= 1 && weaponChoice <= ownedWeapons.size()) {
            Weapon chosenWeapon = ownedWeapons.get(weaponChoice - 1);
            hero.equipWeapon(chosenWeapon);
            System.out.println(hero.getName() + " equipped with weapon: " + chosenWeapon.getName());
        } else {
            System.out.println("Invalid weapon choice.");
        }
    }
    public void changeArmor(Hero hero) {
        Armor equippedArmor = hero.getEquippedArmor();
        if (equippedArmor != null) {
            System.out.println(hero.getName() + " equipped armor " + equippedArmor.getName() + " - Defence: " + equippedArmor.getDefense());
        } else {
            System.out.println(hero.getName() + " does not have equipped armor");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Owned armors:");
        List<Armor> ownedArmors = hero.getOwnedArmors();
        for (int i = 0; i < ownedArmors.size(); i++) {
            Armor armor = ownedArmors.get(i);
            System.out.println((i + 1) + ". " + armor.getName() + " - Defence: " + armor.getDefense());
        }
        System.out.println("Choose an armor to equip (or enter 0 to cancel): ");
        int armorChoice = scanner.nextInt();
        scanner.nextLine();
        if (armorChoice == 0) {
            System.out.println("Canceled armor change.");
        } else if (armorChoice >= 1 && armorChoice <= ownedArmors.size()) {
            Armor chosenArmor = ownedArmors.get(armorChoice - 1);
            hero.equipArmor(chosenArmor);
            int heroNewDefence = hero.getDefense() + chosenArmor.getDefense();
            System.out.println(hero.getName() + " equipped with armor: " + chosenArmor.getName());
            System.out.println("Hero's new defence: " + heroNewDefence);
        } else {
            System.out.println("Invalid armor choice.");
        }
    }
    public void attackMonster(Hero hero, Monster monster) {
        Weapon equippedWeapon = hero.getEquippedWeapon();
        String[] weaponTypes = {"Sword", "Axe", "Bow", "Staff", "Dagger"};
        int[] weaponDamages = {20, 25, 15, 18, 12};
        int weaponIndex = -1;
        for (int i = 0; i < weaponTypes.length; i++) {
            if (equippedWeapon != null && equippedWeapon.getName().contains(weaponTypes[i])) {
                weaponIndex = i;
                break;
            }
        }
        if (weaponIndex != -1) {
            int heroDamage = calculateDamage(hero.getStrength() + weaponDamages[weaponIndex], monster.getDefense());
            monster.takeDamage(heroDamage);
            System.out.println(hero.getName() + " attacked " + monster.getName() + " with a " + equippedWeapon.getName() + " for " + heroDamage + " damage!");
        } else {
            int heroHandDamage = calculateDamage(hero.getStrength(), monster.getDefense());
            monster.takeDamage(heroHandDamage);
            System.out.println(hero.getName() + " attacked " + monster.getName() + " for " + heroHandDamage + " damage ");
        }
    }
    public void iceSpellChoice(Hero hero, Monster targetMonster, Spell spell) {
        if (hero.getMagicPower() > 10) {
            calculateSpellDamage(hero, targetMonster, spell);
            targetMonster.setDamageRange(Math.max(targetMonster.getDamageRange() - 2, 0));
            int newMagicPower = hero.getMagicPower() - 5;
            System.out.println("Hero's new magic power is: " + newMagicPower);
        } else {
            System.out.println("Not enough magic power for spell attack! ");
        }
    }
    public void fireSpellChoice(Hero hero, Monster targetMonster, Spell spell) {
        if (hero.getMagicPower() > 10) {
            calculateSpellDamage(hero, targetMonster, spell);
            targetMonster.setDefence(Math.max(targetMonster.getDefense() - 2, 0));
            int newMagicPower = hero.getMagicPower() - 5;
            System.out.println("Hero's new magic power is: " + newMagicPower);
        } else {
            System.out.println("Not enough magic power for spell attack! ");
        }
    }
    public void lightingSpellChoice(Hero hero, Monster targetMonster, Spell spell) {
        if (hero.getMagicPower() > 10) {
            calculateSpellDamage(hero, targetMonster, spell);
            targetMonster.setDodgeChance(targetMonster.getDodgeChance() - 2 > 0 ? targetMonster.getDodgeChance() - 2 : 0);
            int newMagicPower = hero.getMagicPower() - 5;
            System.out.println("Hero's new magic power is: " + newMagicPower);
        } else {
            System.out.println("Not enough magic power for spell attack! ");
        }
    }
    public void calculateSpellDamage(Hero hero, Monster targetMonster, Spell spell) {
        int spellDamage = spell.getDamage() - targetMonster.getDefense();
        System.out.println(targetMonster.getName() + " received " + spellDamage + " damage from " + hero.getName());
    }
}