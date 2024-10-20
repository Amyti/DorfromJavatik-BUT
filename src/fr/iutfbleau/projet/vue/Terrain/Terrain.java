package vue;

import model.Tile;
import vue.Tuiles.*;

public class Terrain {

    private Tile[][] grid; 
    private final int cols = 8;
    private final int rows = 6; 

    public Terrain() {
        grid = new Tile[rows][cols];
        initGrille();
    }

    private void initGrille() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isCenter(row, col)) {
                    grid[row][col] = new Tile(Tile.TerrainType.PRE);
                } else {
                    grid[row][col] = new Tile(Tile.TerrainType.CHAMP); 
                }
            }
        }
    }

    public void dessinerGrille(){
        for (int row = -rows; row <= rows; row++) {
            for (int col = -cols; col <= cols; col++) {
                int offsetX = col * (int) (smallRadius * 1.5);
                int offsetY = row * (int) (Math.sqrt(3) * smallRadius) + ((col % 2 == 0) ? 0 : (int) (Math.sqrt(3) * smallRadius / 2));

                Shape smallHexagon = createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);

                Shape intersection = getIntersection(largeHexagon, smallHexagon);

                if (intersection != null) {
                    g2d.setColor(getFixedTerrainColor(tile.getTerrain1(), centerX + offsetX, centerY + offsetY));
                    g2d.fill(intersection);

                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));  
                    g2d.draw(intersection);
                }
            }
        }
    }

    private boolean isCenter(int row, int col) {
        int centerRow = rows / 2;
        int centerCol = cols / 2;

        return (row >= centerRow - 1 && row <= centerRow + 1) &&
               (col >= centerCol - 1 && col <= centerCol + 1);
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return grid[row][col];
        }
        return null; 
    }

    public Tile[][] getGrille() {
        return grid;
    }
}
