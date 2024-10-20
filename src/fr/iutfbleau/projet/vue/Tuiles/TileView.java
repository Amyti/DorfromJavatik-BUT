package vue;

import model.Tile;
import controller.TileController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileView extends JPanel implements MouseListener {
    
    private Tile tile;
    private List<Point> positions;  
    private final BasicStroke fixedStroke;
    private TileController tileController;
    private List<Point> positionsDisponibles = new ArrayList<>(); 
    private Map<Point, Tile> tuilesPlacees = new HashMap<>();
    
    
    public void ajouterTuile(Point point, Tile tile) {
        tuilesPlacees.put(point, tile);
        positions.add(point); 
    }
    
    public TileView(Tile tile) {
        this.tile = tile;
        this.positions = new ArrayList<>();
        this.fixedStroke = new BasicStroke(3);
        setPreferredSize(new Dimension(400, 400));
        this.addMouseListener(this);
        this.positions.add(new Point(490,310)); 
        this.tileController = new TileController(this);
        
        Tile tuileInitiale = new Tile(Tile.TerrainType.PRE);  // Vous pouvez choisir un type de terrain ici
        Point positionInitiale = new Point(490, 310);  // Position centrale ou définie
        ajouterTuile(positionInitiale, tuileInitiale);
        mettreAJourPositionsDisponibles();
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
    
    
    
    public void mettreAJourPositionsDisponibles() {
        positionsDisponibles.clear();
        for (Point position : positions) {
            positionsDisponibles.addAll(tileController.calculerPositionsDisponibles(position, positionsDisponibles));
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (Point position : tuilesPlacees.keySet()) {
            Tile tuile = tuilesPlacees.get(position);
            int centerX = position.x;
            int centerY = position.y;
            int largeRadius = 100;
            
            int smallRadius = 20;
            int rows = 6;
            int cols = 6;
            
            Shape largeHexagon = HexagonUtils.createHexagon(centerX, centerY, largeRadius);
            DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, centerX, centerY, rows, cols, smallRadius, largeHexagon, tuile);
            grille.GrilleHexagonal();
        }
        
        g2d.setColor(new Color(0, 0, 0, 50)); 
        g2d.setStroke(new BasicStroke(2));
        for (Point position : positionsDisponibles) {
            int dispoRadius = 50;
            Shape hexagon = HexagonUtils.createHexagon(position.x, position.y, dispoRadius);
            g2d.draw(hexagon);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        Point centreHexagone = tileController.getCentreHexagoneClique(x, y, positionsDisponibles);
        
        if (centreHexagone != null) {
            tileController.PlacerTuile(centreHexagone.x, centreHexagone.y);
            mettreAJourPositionsDisponibles(); 
            repaint();
        }
    }
    
    
    public void setTile(Tile tile) {
        this.tile = tile;
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
