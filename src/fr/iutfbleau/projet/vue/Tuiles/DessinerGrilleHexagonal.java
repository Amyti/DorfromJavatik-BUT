package vue;

import model.Tile;
import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random; 
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
                double apothemeAngle = Math.PI / 2; 
    
                for (int row = -rows; row <= rows; row++) {
                    for (int col = -cols; col <= cols; col++) {
                        int offsetX = col * (int) (smallRadius * 1.5);
                        int offsetY = row * (int) (Math.sqrt(3) * smallRadius) + ((col % 2 == 0) ? 0 : (int) (Math.sqrt(3) * smallRadius / 2));
                        Shape smallHexagon = HexagonUtils.createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);
                        Shape intersection = getIntersection(largeHexagon, smallHexagon);
                        if (intersection != null) {
                            boolean isInTerrain1;
                            double angle = Math.atan2(centerY + offsetY - centerY, centerX + offsetX - centerX);
                            if (terrain1OnLeftOrTop) {
                                isInTerrain1 = angle < apothemeAngle;
                            } else {
                                isInTerrain1 = angle > apothemeAngle;
                            }
                            if (isInTerrain1) {
                                g2d.setColor(getFixedTerrainColor(tile.getTerrain1(), centerX + offsetX, centerY + offsetY));
                            } else {
                                g2d.setColor(getFixedTerrainColor(tile.getTerrain2(), centerX + offsetX, centerY + offsetY));
                            }
                            g2d.fill(intersection);
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1));
                            g2d.draw(intersection);
                        }
                    }
                }
            } else {
                double dividingLine = centerY;  
                for (int row = -rows; row <= rows; row++) {
                    for (int col = -cols; col <= cols; col++) {
                        int offsetX = col * (int) (smallRadius * 1.5);
                        int offsetY = row * (int) (Math.sqrt(3) * smallRadius) + ((col % 2 == 0) ? 0 : (int) (Math.sqrt(3) * smallRadius / 2));
                        Shape smallHexagon = HexagonUtils.createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);
                        Shape intersection = getIntersection(largeHexagon, smallHexagon);
                        if (intersection != null) {
                            boolean isInTerrain1 = (centerY + offsetY) < dividingLine;
                            if (isInTerrain1) {
                                g2d.setColor(getFixedTerrainColor(tile.getTerrain1(), centerX + offsetX, centerY + offsetY));
                            } else {
                                g2d.setColor(getFixedTerrainColor(tile.getTerrain2(), centerX + offsetX, centerY + offsetY));
                            }
                            g2d.fill(intersection);
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1));
                            g2d.draw(intersection);
                        }
                    }
                }
            }
        } else {
            g2d.setColor(getFixedTerrainColor(tile.getTerrain1(), centerX, centerY));
            g2d.fill(largeHexagon);
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(largeHexagon);
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
            return getFixedShade(index, new Color(0, 0, 255), new Color(100, 149, 237), new Color(135, 206, 250));
            case CHAMP:
            return getFixedShade(index, new Color(255, 255, 0), new Color(240, 230, 140), new Color(255, 223, 0));
            case PRE:
            return getFixedShade(index, new Color(0, 128, 0), new Color(144, 238, 144), new Color(34, 139, 34));
            case FORET:
            return getFixedShade(index, new Color(0, 100, 0), new Color(01, 139, 34), new Color(02, 180, 67));
            case MONTAGNE:
            return getFixedShade(index, new Color(169, 169, 169), new Color(128, 128, 128), new Color(105, 105, 105));
            default:
            return Color.WHITE;
        }
    }
    
    private Color getFixedShade(int index, Color color1, Color color2, Color color3) {
        switch (index) {
            case 0:
            return color1;
            case 1:
            return color2;
            case 2:
            return color3;
            default:
            return color1;
        }
    }
}
