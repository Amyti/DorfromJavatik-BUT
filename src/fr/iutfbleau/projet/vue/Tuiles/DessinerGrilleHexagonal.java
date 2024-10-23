package vue;

import model.Tile;
import java.awt.*;
import java.awt.geom.Area;

public class DessinerGrilleHexagonal {

    private Graphics2D g2d;
    private int centerX, centerY, rows, cols, smallRadius;
    private Shape largeHexagon;
    private Tile tile;

    public DessinerGrilleHexagonal(Graphics2D g2d, int centerX, int centerY, int rows, int cols, int smallRadius, Shape largeHexagon, Tile tile) {
        this.g2d = g2d;
        this.centerX = centerX;
        this.centerY = centerY;
        this.rows = rows;
        this.cols = cols;
        this.smallRadius = smallRadius;
        this.largeHexagon = largeHexagon;
        this.tile = tile;
    }

    public void GrilleHexagonal() {
        if (tile.hasTwoTerrains()) {
            boolean terrain1OnLeftOrTop = tile.isTerrain1OnTopOrLeft();
            double terrain1Ratio = tile.getTerrain1Ratio();

            if (terrain1Ratio != 0.5) {
                double apothemeAngle = 3.9 / 2;

                for (int row = -rows; row <= rows; row++) {
                    for (int col = -cols; col <= cols; col++) {
                        drawHexagon(row, col, apothemeAngle, terrain1OnLeftOrTop);
                    }
                }
            } else {
                double dividingLine = centerY;
                for (int row = -rows; row <= rows; row++) {
                    for (int col = -cols; col <= cols; col++) {
                        drawHexagonSimple(row, col, dividingLine);
                    }
                }
            }
        } else {
            g2d.setColor(getFixedTerrainColor(tile.getTerrain1(), centerX, centerY));
            g2d.fill(largeHexagon);
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(largeHexagon);
    }

    private void drawHexagon(int row, int col, double apothemeAngle, boolean terrain1OnLeftOrTop) {
        int offsetX = col * (int) (smallRadius * 1.5);
        int offsetY = row * (int) (Math.sqrt(3) * smallRadius) + ((col % 2 == 0) ? 0 : (int) (Math.sqrt(3) * smallRadius / 2));
        Shape smallHexagon = HexagonUtils.createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);
        Shape intersection = getIntersection(largeHexagon, smallHexagon);
        if (intersection != null) {
            boolean isInTerrain1;
            double angle = Math.atan2(centerY + offsetY - centerY, centerX + offsetX - centerX);
            isInTerrain1 = terrain1OnLeftOrTop ? angle < apothemeAngle : angle > apothemeAngle;

            g2d.setColor(getFixedTerrainColor(isInTerrain1 ? tile.getTerrain1() : tile.getTerrain2(), centerX + offsetX, centerY + offsetY));
            g2d.fill(intersection);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(0));
            g2d.draw(intersection);
        }
    }

    private void drawHexagonSimple(int row, int col, double dividingLine) {
        int offsetX = col * (int) (smallRadius * 1.5);
        int offsetY = row * (int) (Math.sqrt(3) * smallRadius) + ((col % 2 == 0) ? 0 : (int) (Math.sqrt(3) * smallRadius / 2));
        Shape smallHexagon = HexagonUtils.createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);
        Shape intersection = getIntersection(largeHexagon, smallHexagon);
        if (intersection != null) {
            boolean isInTerrain1 = (centerY + offsetY) < dividingLine;
            g2d.setColor(getFixedTerrainColor(isInTerrain1 ? tile.getTerrain1() : tile.getTerrain2(), centerX + offsetX, centerY + offsetY));
            g2d.fill(intersection);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(0));
            g2d.draw(intersection);
        }
    }

    private Shape getIntersection(Shape shape1, Shape shape2) {
        Area area1 = new Area(shape1);
        Area area2 = new Area(shape2);
        area1.intersect(area2);
        return area1.isEmpty() ? null : area1;
    }

    private Color getFixedTerrainColor(Tile.TerrainType terrain, int x, int y) {
        int index = (Math.abs(x + y) % 3);
        switch (terrain) {
            case MER:
                return getFixedShade(index, new Color(66, 112, 182), new Color(111, 168, 238), new Color(88, 142, 212));
            case CHAMP:
                return getFixedShade(index, new Color(144, 197, 129), new Color(164, 217, 148), new Color(125, 178, 109));
            case PRE:
                return getFixedShade(index, new Color(231, 224, 83), new Color(245, 238, 88), new Color(213, 205, 70));
            case FORET:
                return getFixedShade(index, new Color(64, 118, 75), new Color(80, 138, 95), new Color(50, 98, 56));
            case MONTAGNE:
                return getFixedShade(index, new Color(100, 100, 100), new Color(120, 120, 120), new Color(140, 140, 140));
            default:
                return Color.WHITE;
        }
    }

    private Color getFixedShade(int index, Color color1, Color color2, Color color3) {
        return switch (index) {
            case 0 -> color1;
            case 1 -> color2;
            case 2 -> color3;
            default -> color1;
        };
    }
}
