package vue;

import model.Tile;
import controller.TileController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class TileView extends JPanel implements MouseListener {

    private Tile tile;
    private List<Point> positions;  
    private final BasicStroke fixedStroke;
    private TileController tileController;

    public TileView(Tile tile) {
        this.tile = tile;
        this.positions = new ArrayList<>();
        this.fixedStroke = new BasicStroke(3);
        setPreferredSize(new Dimension(400, 400));
        this.addMouseListener(this);
        this.positions.add(new Point(490,310)); 
        this.tileController = new TileController(this);
    }

    public void ajouterPosition(Point point) {
        positions.add(point);
    }

    public Point getLastTilePosition() {
        if (!positions.isEmpty()) {
            return positions.get(positions.size() - 1);
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Point position : this.positions) {
            int centerX = position.x;
            int centerY = position.y;
            int largeRadius = 100;
            int smallRadius = 20;
            int rows = 6;
            int cols = 6;

            Shape largeHexagon = HexagonUtils.createHexagon(centerX, centerY, largeRadius);

            DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, centerX, centerY, rows, cols, smallRadius, largeHexagon, tile);
            grille.GrilleHexagonal();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        tileController.PlacerTuile(x, y); 
    }

    // Méthodes inutilisées du MouseListener
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
