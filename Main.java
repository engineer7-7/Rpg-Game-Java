import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static List<Hero> heroes = new ArrayList<>();
    public static List<Monster> monsters = new ArrayList<>();
    private static Scanner scanner;

    public static void main(String[] args) {
        Market market = new Market();
        scanner = new Scanner(System.in);

        Grid grid = new Grid(50, 50);
        initializeParty(grid);
        initializeMonsters();
        grid.initializeGrid();

        Battle battle = new Battle(heroes, monsters, grid);
        Hero selectedHero = heroes.get(0);
        gameLoop(grid, battle, selectedHero, market);

    }

    public static void gameLoop(Grid grid, Battle battle, Hero selectedHero, Market market) {
        boolean gameRunning = true;

        while (gameRunning) {
            grid.displayMap(selectedHero);
            System.out.println("Choose a direction to move (up/down/left/right) , START to check hero's inventory or quit to exit:");
            String direction = scanner.nextLine().toLowerCase();
            System.out.println(direction);
            int originalRow = selectedHero.getRow();
            int originalCol = selectedHero.getCol();

            switch (direction) {
                case "quit" -> {
                    grid.quitGame();
                    gameRunning = false;
                }
                case "u" -> {
                    if (grid.canMoveUp(selectedHero)) {
                        grid.moveHeroUp(selectedHero);
                    } else {
                        System.out.println("Cannot move up in this direction.");
                    }
                }
                case "d" -> {
                    if (grid.canMoveDown(selectedHero)) {
                        grid.moveHeroDown(selectedHero);
                    } else {
                        System.out.println("Cannot move down in this direction.");
                    }
                }
                case "l" -> {
                    if (grid.canMoveLeft(selectedHero)) {
                        grid.moveHeroLeft(selectedHero);
                    } else {
                        System.out.println("Cannot move left in this direction.");
                    }
                }
                case "r" -> {
                    if (grid.canMoveRight(selectedHero)) {
                        grid.moveHeroRight(selectedHero);
                    } else {
                        System.out.println("Cannot move right in this direction.");
                    }
                }
                case "s"-> selectedHero.displayStats();
                default -> System.out.println("Invalid direction.");
            }
            if (!grid.canHeroAccessLake(selectedHero)) {
                System.out.println("You cannot proceed here. It's a lake!");
                selectedHero.setRow(originalRow);
                selectedHero.setCol(originalCol);
            }
            if (grid.getMarketRow() == selectedHero.getRow() && grid.getMarketCol() == selectedHero.getCol()) {
                market.handleHeroMarketActions(selectedHero);
            }
            grid.checkCommonSquareInteraction(selectedHero, battle, monsters);
        }
    }
    public static void initializeParty(Grid grid) {
        System.out.println("Welcome to the party creation!");
        System.out.println("How many heroes do you want to create (1-3)?");
        int numberOfHeroes = scanner.nextInt();
        scanner.nextLine();
        while (!(numberOfHeroes >= 1 && numberOfHeroes <= 3)) {
            System.out.println("Invalid number. Choose among 1-3! ");
            numberOfHeroes = scanner.nextInt();
            scanner.nextLine();
        }
        for (int i = 1; i <= numberOfHeroes; i++) {
            System.out.println("Choose a hero: Paladin, Sorcerer, Warrior");
            String heroChoice = scanner.nextLine();
            while (!(heroChoice.equalsIgnoreCase("Paladin") || heroChoice.equalsIgnoreCase("Sorcerer")
                    || heroChoice.equalsIgnoreCase("Warrior"))) {
                System.out.println("Invalid choice of hero. Choose among Sorcerer - Warrior - Paladin ");
                heroChoice = scanner.nextLine();
            }
            System.out.println("Choose your hero's name for hero " + i);
            String heroName = scanner.nextLine();
            if (heroChoice.equalsIgnoreCase("Sorcerer")) {
                heroes.add(new Sorcerer(heroName, 1, 50, 50, 100, 0, 100, 100, 10, 20, 20));
            } else if (heroChoice.equalsIgnoreCase("Paladin")) {
                heroes.add(new Paladin(heroName, 1, 50, 40, 100, 0, 100, 100, 10, 20, 20));
            } else if (heroChoice.equalsIgnoreCase("Warrior")) {
                heroes.add(new Warrior(heroName, 1, 50, 50, 100, 0, 100, 100, 10, 29, 20));
            }
            grid.moveHeroToRandomPosition(heroes.get(i - 1));
        }
        System.out.println("You have created the following heroes:");
        for (Hero newHeroes : heroes) {
            System.out.println(newHeroes.getName());
        }
    }
    public static void initializeMonsters() {
        monsters.add(new Dragon("Char", 1, 50 ,10, 10, 10, 100, 8));
        monsters.add(new Exoskeleton("Exo", 1, 50, 10, 5, 5, 100, 8));
        monsters.add(new Spirit("Ghost", 1, 50, 10, 7, 7, 100, 5));
    }
}