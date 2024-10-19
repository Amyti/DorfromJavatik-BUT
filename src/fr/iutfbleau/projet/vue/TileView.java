package vue;

import model.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class TileView extends JPanel implements MouseListener {
    
    private Tile tile;
    private Random random;
    private Dimension hexagonSize;
    private List<Point> position; 
    private final BasicStroke fixedStroke; 
    
    public TileView(Tile tile) {
        this.tile = tile;
        this.random = new Random();
        this.hexagonSize = new Dimension(400, 400);
        this.position = new ArrayList<>();
        this.fixedStroke = new BasicStroke(3); 
        setPreferredSize(hexagonSize);
        this.addMouseListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // ça rend niquel
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Point position : this.position) {
            int centerX = position.x;
            int centerY = position.y;
            int largeRadius = 100;
            int smallRadius = 20;

            Shape largeHexagon = createHexagon(centerX, centerY, largeRadius);

            g2d.setStroke(fixedStroke);  
            g2d.setColor(Color.BLACK);
            g2d.draw(largeHexagon);  

            /*
                GRILLE HEXAGONAL DU DEMONNNNNNNNNNNNS (j'ai pas dormis)
            */ 
            int rows = 6;
            int cols = 6;
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

            g2d.setStroke(fixedStroke);  
            g2d.setColor(Color.BLACK);
            g2d.draw(largeHexagon);
        }
    }

    
    private Shape createHexagon(int centerX, int centerY, int radius) {
        Path2D hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            if (i == 0) {
                hexagon.moveTo(x, y);
            } else {
                hexagon.lineTo(x, y);
            }
        }
        hexagon.closePath();
        return hexagon;
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
                return getFixedShade(index, new Color(0, 100, 0), new Color(34, 139, 34), new Color(85, 107, 47)); 
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

    @Override
    public void mouseClicked(MouseEvent e) {
        position.add(e.getPoint());
        repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
