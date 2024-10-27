package fr.iutfbleau.projet.vue;

import fr.iutfbleau.projet.model.Tile;
import fr.iutfbleau.projet.controller.TileController;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fr.iutfbleau.projet.model.ScoreManager;
import javax.swing.SwingUtilities; 
import java.util.Map;
import javax.swing.JOptionPane;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;
import java.util.Objects; 

/**
 * La classe TileView représente la vue principale pour afficher et interagir avec les tuiles du jeu.
 * Elle gère l'affichage graphique des tuiles, les événements de souris pour le placement de tuiles,
 * et la gestion des positions disponibles pour les nouvelles tuiles.
 */
public class TileView extends JPanel implements MouseListener, MouseMotionListener {

    private List<Point> positions;
    private TileController tileController;
    private List<Point> positionsDisponibles = new ArrayList<>();
    private Point hoveredHexagon = null;
    private Map<Point, Tile> tuilesPlacees = new HashMap<>();
    private List<Tile> listeTuilesGenerees;
    private int seriesId;

    /**
     * Constructeur de la classe TileView.
     *
     * @param tiles La liste initiale de tuiles à placer.
     * @param seriesId L'identifiant de la série de tuiles pour la gestion des scores.
     */
    public TileView(List<Tile> tiles, int seriesId) {
        this.seriesId = seriesId;
        this.listeTuilesGenerees = new ArrayList<>(tiles); 
        this.positions = new ArrayList<>();
        setPreferredSize(new Dimension(2500, 2500));
        setBackground(new Color(160, 82, 45));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.tileController = new TileController(this);

        Tile premiereTuile = listeTuilesGenerees.get(0);
        Point pointInitial = new Point(1200, 1200);
        ajouterTuile(pointInitial, premiereTuile);
        ajouterPosition(pointInitial);
        mettreAJourPositionsDisponibles();
    }

    /**
     * Obtient la liste des positions des tuiles placées.
     *
     * @return Une liste de points représentant les positions des tuiles placées.
     */
    public List<Point> getPositions() {
        return positions;
    }

    /**
     * Obtient la prochaine tuile à placer.
     *
     * @return La prochaine tuile à placer, ou null si aucune tuile n'est disponible.
     */
    public Tile getProchaineTuile() {
        return listeTuilesGenerees.isEmpty() ? null : listeTuilesGenerees.get(0);
    }

    /**
     * Retire la prochaine tuile de la liste des tuiles à placer.
     */
    public void retirerProchaineTuile() {
        if (!listeTuilesGenerees.isEmpty()) {
            listeTuilesGenerees.remove(0);
        }
    }

    /**
     * Ajoute une tuile à la position spécifiée.
     *
     * @param point La position où ajouter la tuile.
     * @param tile La tuile à ajouter.
     */
    public void ajouterTuile(Point point, Tile tile) {
        tuilesPlacees.put(point, tile);
        positions.add(point);
    }

    /**
     * Obtient la tuile placée à une position spécifique.
     *
     * @param point La position de la tuile à obtenir.
     * @return La tuile à la position spécifiée, ou null si aucune tuile n'est présente.
     */
    public Tile getTileAtPosition(Point point) {
        return tuilesPlacees.get(point);
    }

    /**
     * Ajoute une position à la liste des positions de tuiles.
     *
     * @param point La position à ajouter.
     */
    public void ajouterPosition(Point point) {
        positions.add(point);
    }

    /**
     * Calcule les positions adjacentes autour d'une tuile donnée.
     *
     * @param centreTuile Le centre de la tuile pour laquelle trouver les positions adjacentes.
     * @return Une liste de points représentant les positions adjacentes.
     */
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

    /**
     * Récupère la dernière position de tuile placée.
     *
     * @return Le dernier point ajouté, ou null si aucune position n'est disponible.
     */
    public Point getLastTilePosition() {
        return positions.isEmpty() ? null : positions.get(positions.size() - 1);
    }

    /**
     * Met à jour les positions disponibles pour le placement des nouvelles tuiles.
     */
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

    /**
     * Obtient le score actuel en appelant le contrôleur de tuiles.
     *
     * @return Le score calculé pour le jeu en cours.
     */
    public int getScore() {
        return tileController.calculerScore();
    }

    /**
     * Gère la fin de la partie en sauvegardant le score et en offrant des options pour recommencer ou quitter.
     *
     * @param scoreFinal Le score final atteint à la fin du jeu.
     */
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

    /**
     * Redessine le composant en affichant les tuiles et les positions disponibles.
     *
     * @param g Le contexte graphique utilisé pour le dessin.
     */
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
