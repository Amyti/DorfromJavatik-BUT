package vue;

import model.Tile;
import controller.TileController;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import vue.MenuAvecSeriesBD;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.ScoreManager;
import javax.swing.SwingUtilities; 
import java.util.Map;
import javax.swing.JOptionPane;

import java.util.Objects; 

public class TileView extends JPanel implements MouseListener, MouseMotionListener {

    private List<Point> positions;
    private TileController tileController;
    private List<Point> positionsDisponibles = new ArrayList<>();
    private Point hoveredHexagon = null;
    private Map<Point, Tile> tuilesPlacees = new HashMap<>();
    private List<Tile> listeTuilesGenerees;
    private int seriesId;

    
    public TileView(List<Tile> tiles, int seriesId) {
        this.seriesId = seriesId;
        this.listeTuilesGenerees = new ArrayList<>(tiles); 
        this.positions = new ArrayList<>();
        setPreferredSize(new Dimension(2500, 2500));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.tileController = new TileController(this);

        Tile premiereTuile = listeTuilesGenerees.get(0);
        Point pointInitial = new Point(1200, 1200);
        ajouterTuile(pointInitial, premiereTuile);
        ajouterPosition(pointInitial);
        mettreAJourPositionsDisponibles();
    }

    public List<Point> getPositions() {
        return positions;
    }

    public Tile getProchaineTuile() {
        return listeTuilesGenerees.isEmpty() ? null : listeTuilesGenerees.get(0);
    }

    public void retirerProchaineTuile() {
        if (!listeTuilesGenerees.isEmpty()) {
            listeTuilesGenerees.remove(0);
        }
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
        return positions.isEmpty() ? null : positions.get(positions.size() - 1);
    }

    public void mettreAJourPositionsDisponibles() {
        positionsDisponibles.clear();
        for (Point position : positions) {
            List<Point> nouvellesPositions = tileController.calculerPositionsDisponibles(position, positionsDisponibles);
            for (Point nouvellePosition : nouvellesPositions) {
                boolean superpose = false;
                for (Point positionPrise : positions) {
                    if (nouvellePosition.equals(positionPrise) ||
                        nouvellePosition.x <= 0 || nouvellePosition.x > 2400 || nouvellePosition.y < 95 || nouvellePosition.y > 2390) {
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

    public int getScore() {
        return tileController.calculerScore();
    }

    public void finDeJeu(int scoreFinal) {
        ScoreManager.saveScore(scoreFinal, seriesId);

        int response = JOptionPane.showOptionDialog(
            this,
            "Félicitations ! Vous avez terminé le jeu.\nScore final : " + scoreFinal,
            "Fin de Jeu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Recommencer", "Quitter"},
            "Recommencer"
        );

        if (response == 0) {
            SwingUtilities.getWindowAncestor(this).dispose(); 
            new MenuAvecSeriesBD(); 
        } else {
            System.exit(0); 
        }
    }


    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Point position : this.positions) {
            Tile tileAtPosition = tuilesPlacees.get(position);

            if (tileAtPosition != null) {
                Shape largeHexagon = HexagonUtils.createHexagon(position.x, position.y, 100);
                AffineTransform oldTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(tileAtPosition.getRotationAngle()), position.x, position.y);
                DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, position.x, position.y, 100, largeHexagon, tileAtPosition);
                grille.GrilleHexagonal();
                g2d.setTransform(oldTransform);
            }
        }

        g2d.setStroke(new BasicStroke(4));
        for (Point position : positionsDisponibles) {
            int dispoRadius = position.equals(hoveredHexagon) ? 75 : 50;
            Shape hexagon = HexagonUtils.createHexagon(position.x, position.y, dispoRadius);
            g2d.setColor(position.equals(hoveredHexagon) ? new Color(255, 0, 0, 50) : new Color(0, 0, 0, 50));
            g2d.setStroke(new BasicStroke(position.equals(hoveredHexagon) ? 6 : 3));
            g2d.draw(hexagon);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point hexagoneSurvole = tileController.getCentreHexagoneClique(e.getX(), e.getY(), positionsDisponibles);
        if (!Objects.equals(hexagoneSurvole, hoveredHexagon)) {
            hoveredHexagon = hexagoneSurvole;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point centreHexagone = tileController.getCentreHexagoneClique(e.getX(), e.getY(), positionsDisponibles);
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
