package fr.iutfbleau.projet.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import fr.iutfbleau.projet.model.Tile;

/**
 * La classe PreviewPanel représente un panneau de prévisualisation d'une tuile,
 * affichant la tuile actuelle avec un angle de rotation configurable.
 */
public class PreviewPanel extends JPanel {

    private Tile prochaineTuile;
    private double rotationAngle = 0;

    /**
     * Constructeur pour créer un panneau de prévisualisation avec une tuile donnée.
     *
     * @param prochaineTuile La prochaine tuile à afficher dans le panneau de prévisualisation.
     */
    public PreviewPanel(Tile prochaineTuile) {
        this.prochaineTuile = prochaineTuile;
        setPreferredSize(new Dimension(150, 150));
    }

    /**
     * Définit la prochaine tuile à afficher dans la prévisualisation et réinitialise l'angle de rotation.
     *
     * @param tuile La tuile à afficher dans la prévisualisation.
     */
    public void setProchaineTuile(Tile tuile) {
        this.prochaineTuile = tuile;
        rotationAngle = 0;
        repaint();
    }

    /**
     * Obtient l'angle de rotation actuel de la tuile.
     *
     * @return L'angle de rotation actuel en degrés.
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Fait pivoter la tuile de l'angle spécifié.
     *
     * @param rotation L'angle de rotation supplémentaire en degrés.
     */
    public void rotateTile(int rotation) {
        rotationAngle += rotation;
        if (prochaineTuile != null) {
            prochaineTuile.setRotationAngle(rotationAngle % 360);
        }
        repaint();
    }

    /**
     * Redessine le panneau de prévisualisation avec la tuile actuelle, en appliquant l'angle de rotation.
     *
     * @param g Le contexte graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (prochaineTuile == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int hexagonRadius = 100;

        Shape hexagon = HexagonUtils.createHexagon(centerX, centerY, hexagonRadius);

        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotationAngle), centerX, centerY);
        g2d.setTransform(transform);

        g2d.setColor(Color.BLACK);
        g2d.draw(hexagon);

        DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, centerX, centerY, hexagonRadius, hexagon, prochaineTuile);
        grille.GrilleHexagonal();

        g2d.setTransform(oldTransform);
    }
}
