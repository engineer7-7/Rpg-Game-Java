import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Grid {
    private final char[][] grid;
    private final int rows;
    private final int cols;
    private int marketRow;
    private int marketCol;
    private int lakeRow;
    private int lakeCol;
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        initializeGrid();
        initializeMarketPosition();
    }
    public void initializeGrid() {
        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = random.nextBoolean() ? '-' : '.';
            }
        }
        // Create 20 random monster
        int x, y, i = 0;
        while (i < 30) {
            x = random.nextInt(50);
            y = random.nextInt(50);
            if (isValidPosition(x, y) && grid[x][y] != 'M') {
                grid[x][y] = 'M';
                i++;
            }
        }
    }
    public void displayMap(Hero hero) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (hero.getRow() == row && hero.getCol() == col) {
                    System.out.print("H");
                } else if (marketRow == row && marketCol == col) {
                    System.out.print("A");
                }
                else if (lakeRow == row && lakeCol == col) {
                    System.out.print("L");
                }else {
                    System.out.print(grid[row][col] + " ");
                }
            }
            System.out.println();
        }
    }
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void checkCommonSquareInteraction(Hero hero, Battle battle, List<Monster> monsters) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        if (isValidPosition(heroRow, heroCol) && grid[heroRow][heroCol] == 'M') {
            updateMonsterLevels(hero.getLevel());
            List<Monster> monstersInSameLevel = new ArrayList<>();
            for (Monster monster : monsters) {
                if (monster.getLevel() == hero.getLevel()) {
                    monstersInSameLevel.add(monster);
                }
            }
            if (!monstersInSameLevel.isEmpty()) {
                battle.startBattle();
            } else {
                System.out.println("List is empty! ");
            }
        }
    }
    public boolean canMoveUp(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();

        return isValidPosition(heroRow - 1, heroCol) && grid[heroRow - 1][heroCol] != 'N';
    }
    public boolean canMoveDown(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        return isValidPosition(heroRow + 1, heroCol) && grid[heroRow + 1][heroCol] != 'N';
    }
    public boolean canMoveLeft(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        return isValidPosition(heroRow, heroCol - 1) && grid[heroRow][heroCol - 1] != 'N';
    }
    public boolean canMoveRight(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        return isValidPosition(heroRow, heroCol + 1) && grid[heroRow][heroCol + 1] != 'N';
    }
    public void quitGame() {
        System.out.println("Quitting the game. Goodbye!");
        System.exit(0);
    }
    public void moveHeroToRandomPosition(Hero hero) {
        Random random = new Random();
        int newRow = random.nextInt(rows);
        int newCol = random.nextInt(cols);
        if (isValidPosition(newRow, newCol) && grid[newRow][newCol] != 'N') {
            int oldRow = hero.getRow();
            int oldCol = hero.getCol();
            grid[oldRow][oldCol] = '.';
            hero.setRow(newRow);
            hero.setCol(newCol);
            grid[newRow][newCol] = 'H';
        }
    }
    public void moveHeroUp(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();

        if (isValidPosition(heroRow - 1, heroCol) && grid[heroRow - 1][heroCol] != 'N') {
            hero.setRow(heroRow - 1);
        }
    }
    public void moveHeroDown(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        if (isValidPosition(heroRow + 1, heroCol) && grid[heroRow + 1][heroCol] != 'N') {
            hero.setRow(heroRow + 1);
        }
    }
    public void moveHeroLeft(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        if (isValidPosition(heroRow, heroCol - 1) && grid[heroRow][heroCol - 1] != 'N') {
            hero.setCol(heroCol - 1);
        }
    }
    public void moveHeroRight(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();

        if (isValidPosition(heroRow, heroCol + 1) && grid[heroRow][heroCol + 1] != 'N') {
            hero.setCol(heroCol + 1);
        }
    }
    public void initializeMarketPosition() {
        Random random = new Random();
        this.marketRow = random.nextInt(rows);
        this.marketCol = random.nextInt(cols);
        while (grid[this.marketRow][this.marketCol] == 'M' || grid[this.marketRow][this.marketCol] == 'H') {
            this.marketRow = random.nextInt(rows);
            this.marketCol = random.nextInt(cols);
        }
        grid[this.marketRow][this.marketCol] = 'A'; // a = market
    }
    public int getMarketRow() {
        return marketRow;
    }
    public int getMarketCol() {
        return marketCol;
    }
    public boolean canHeroAccessLake(Hero hero) {
        int heroRow = hero.getRow();
        int heroCol = hero.getCol();
        return heroRow != lakeRow || heroCol != lakeCol;
    }
    public void updateMonsterLevels(int newHeroLevel) {
        for (Monster monster : Main.monsters) {
            monster.setLevel(newHeroLevel);
        }
    }
}
