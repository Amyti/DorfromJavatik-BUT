package vue;

import model.Tile;
import controller.TileController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TileView extends JPanel implements MouseListener, MouseMotionListener {
    
    private List<Point> positions;
    private TileController tileController;
    private List<Point> positionsDisponibles = new ArrayList<>();
    private Point hoveredHexagon = null;
    private Map<Point, Tile> tuilesPlacees = new HashMap<>();
    private List<Tile> listeTuilesGenerees = new ArrayList<>();
    
    public TileView() {
        this.positions = new ArrayList<>();
        setPreferredSize(new Dimension(2500, 2500));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.tileController = new TileController(this);
        
        listeTuilesGenerees = Tile.series1();
        Tile premiereTuile = listeTuilesGenerees.get(0);
        
        
        
        Point pointInitial = new Point(1200, 1200);
        ajouterTuile(pointInitial, premiereTuile);
        ajouterPosition(pointInitial);
        mettreAJourPositionsDisponibles();
    }
    
    public void ajouterTuile(Point point, Tile tile) {
        tuilesPlacees.put(point, tile);
        positions.add(point);
    }
    
    public Tile getTileAtPosition(Point point) {
        return tuilesPlacees.get(point);
    }
    
    public void ajouterPosition(Point point) {
        positions.add(point);
    }
    
    public List<Point> positionAdjacentes(Point centreTuile) {
        List<Point> positionsAdjacentes = new ArrayList<>();
        int decalageHorizontal = 150;
        int decalageVertical = 85;
        int decalageVerticalComplet = 170;
        
        positionsAdjacentes.add(new Point(centreTuile.x, centreTuile.y - decalageVerticalComplet));
        positionsAdjacentes.add(new Point(centreTuile.x, centreTuile.y + decalageVerticalComplet));
        positionsAdjacentes.add(new Point(centreTuile.x + decalageHorizontal, centreTuile.y - decalageVertical));
        positionsAdjacentes.add(new Point(centreTuile.x + decalageHorizontal, centreTuile.y + decalageVertical));
        positionsAdjacentes.add(new Point(centreTuile.x - decalageHorizontal, centreTuile.y - decalageVertical));
        positionsAdjacentes.add(new Point(centreTuile.x - decalageHorizontal, centreTuile.y + decalageVertical));
        
        return positionsAdjacentes;
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
            List<Point> nouvellesPositions = tileController.calculerPositionsDisponibles(position, positionsDisponibles);
            for (Point nouvellePosition : nouvellesPositions) {
                boolean superpose = false;
                for (Point positionPrise : positions) {
                    if (nouvellePosition.equals(positionPrise)) {
                        superpose = true;
                        break;
                    }
                    if (nouvellePosition.x <= 0 || nouvellePosition.x > 2400 || nouvellePosition.y < 95 || nouvellePosition.y > 2390) {
                        superpose = true;
                        break;
                    }
                }
                if (!superpose) {
                    positionsDisponibles.add(nouvellePosition);
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (Point position : this.positions) {
            Tile tileAtPosition = tuilesPlacees.get(position);
            
            if (tileAtPosition != null) {
                Shape largeHexagon = HexagonUtils.createHexagon(position.x, position.y, 100);
                DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, position.x, position.y, 6, 6, 20, largeHexagon, tileAtPosition);
                grille.GrilleHexagonal();
            }
        }
        
        g2d.setStroke(new BasicStroke(4));
        for (Point position : positionsDisponibles) {
            int dispoRadius = 50;
            Shape hexagon;
            
            if (position.equals(hoveredHexagon)) {
                dispoRadius = 75;
                hexagon = HexagonUtils.createHexagon(position.x, position.y, dispoRadius);
                g2d.setColor(new Color(255, 0, 0, 50));
                g2d.setStroke(new BasicStroke(6));
            } else {
                dispoRadius = 50;
                hexagon = HexagonUtils.createHexagon(position.x, position.y, dispoRadius);
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.setStroke(new BasicStroke(3));
            }
            g2d.draw(hexagon);
        }
    }
    
    public Tile getProchaineTuile() {
        if (!listeTuilesGenerees.isEmpty()) {
            return listeTuilesGenerees.get(0);    
        }
        return null;
    }
    
    public void retirerProchaineTuile() {
        if (!listeTuilesGenerees.isEmpty()) {
            listeTuilesGenerees.remove(0); 
        }
    }
    
    
    public List<Point> getPositionsDisponibles() {
        return positionsDisponibles;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        Point hexagoneSurvole = tileController.getCentreHexagoneClique(x, y, positionsDisponibles);
        
        if (hexagoneSurvole != null && !hexagoneSurvole.equals(hoveredHexagon)) {
            hoveredHexagon = hexagoneSurvole;
            repaint();
        } else if (hexagoneSurvole == null && hoveredHexagon != null) {
            hoveredHexagon = null;
            repaint();
        }
    }
    /*NE SERT A RIEN HAHAH 
    private Tile.TerrainType getRandomTerrainType() {
    Tile.TerrainType[] types = Tile.TerrainType.values();
    Random random = new Random();
    return types[random.nextInt(types.length)];
    }
    */
    
    public List<Point> getPositions() {
        return positions;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        Point centreHexagone = tileController.getCentreHexagoneClique(x, y, positionsDisponibles);
        
        if (centreHexagone != null && !listeTuilesGenerees.isEmpty()) {
            Tile tuileAPlacer = listeTuilesGenerees.remove(0);
            tileController.PlacerTuile(centreHexagone.x, centreHexagone.y, tuileAPlacer); 
            positionsDisponibles.remove(centreHexagone);
            mettreAJourPositionsDisponibles(); 
            listeTuilesGenerees.add(tuileAPlacer);
            repaint();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
}
