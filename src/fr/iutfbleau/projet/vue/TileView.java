package view;

import model.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Area;
import java.util.Random;

public class TileView extends JPanel {

    private Tile tile;
    private Random random;
    private Dimension hexagonSize;

    public TileView(Tile tile) {
        this.tile = tile;
        this.random = new Random();
        this.hexagonSize = new Dimension(400, 400);
        setPreferredSize(hexagonSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = hexagonSize.width / 2;
        int centerY = hexagonSize.height / 2;
        int largeRadius = 100;
        int smallRadius = 20; 

        Shape largeHexagon = createHexagon(centerX, centerY, largeRadius);

        int rows = 6;
        int cols = 6;
        for (int row = -rows; row <= rows; row++) {
            for (int col = -cols; col <= cols; col++) {
                int offsetX = (int) (col * smallRadius * 1.5);
                int offsetY = (int) ((row * Math.sqrt(3) * smallRadius) + (col % 2 == 0 ? 0 : (Math.sqrt(3) * smallRadius) / 2));

                Shape smallHexagon = createHexagon(centerX + offsetX, centerY + offsetY, smallRadius);
                
                Shape intersection = getIntersection(largeHexagon, smallHexagon);
                
                if (intersection != null) {
                    g2d.setColor(getRandomTerrainColor(tile.getTerrain1()));
                    g2d.fill(intersection);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(intersection);
                }
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(largeHexagon);

        g2d.setColor(Color.RED);
        g2d.drawString("Texte autour de la tuile", 50, 50);
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

    private Color getRandomTerrainColor(Tile.TerrainType terrain) {
        switch (terrain) {
            case MER:
                return getRandomShade(new Color(0, 0, 255), new Color(100, 149, 237), new Color(135, 206, 250));
            case CHAMP:
                return getRandomShade(new Color(255, 255, 0), new Color(240, 230, 140), new Color(255, 223, 0));
            case PRE:
                return getRandomShade(new Color(0, 128, 0), new Color(144, 238, 144), new Color(34, 139, 34));
            case FORET:
                return getRandomShade(new Color(0, 100, 0), new Color(34, 139, 34), new Color(85, 107, 47));
            case MONTAGNE:
                return getRandomShade(new Color(169, 169, 169), new Color(128, 128, 128), new Color(105, 105, 105));
            default:
                return Color.WHITE;
        }
    }

    private Color getRandomShade(Color color1, Color color2, Color color3) {
        int choice = random.nextInt(3);
        switch (choice) {
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

    public static void main(String[] args) {
        Tile tile = new Tile(Tile.TerrainType.MER);
        TileView tileView = new TileView(tile);

        JFrame frame = new JFrame("Tile View Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tileView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
