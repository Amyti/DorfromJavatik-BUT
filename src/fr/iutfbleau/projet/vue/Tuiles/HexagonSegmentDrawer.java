package fr.iutfbleau.projet.vue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

/**
 * La classe HexagonSegmentDrawer est responsable de dessiner un segment d'un hexagone.
 */
public class HexagonSegmentDrawer {

    private Graphics2D g2d;
    private int centerX;
    private int centerY;
    private int smallRadius;
    private Color color;
    private int index;

    /**
     * Constructeur de la classe HexagonSegmentDrawer.
     *
     * @param g2d       L'objet Graphics2D utilisé pour dessiner.
     * @param centerX   La coordonnée X du centre de l'hexagone.
     * @param centerY   La coordonnée Y du centre de l'hexagone.
     * @param smallRadius Le rayon de l'hexagone intérieur.
     * @param color     La couleur du segment.
     * @param index     L'index du segment (0 à 5).
     */
    public HexagonSegmentDrawer(Graphics2D g2d, int centerX, int centerY, int smallRadius, Color color, int index) {
        this.g2d = g2d;
        this.centerX = centerX;
        this.centerY = centerY;
        this.smallRadius = smallRadius;
        this.color = color;
        this.index = index;
    }

    /**
     * Dessine le segment de l'hexagone.
     */
    public void drawSegment() {
        double angle1 = index * Math.PI / 3;
        double angle2 = (index + 1) * Math.PI / 3;

        int x1 = centerX + (int) (Math.cos(angle1) * smallRadius);
        int y1 = centerY + (int) (Math.sin(angle1) * smallRadius);
        int x2 = centerX + (int) (Math.cos(angle2) * smallRadius);
        int y2 = centerY + (int) (Math.sin(angle2) * smallRadius);

        Path2D segment = new Path2D.Double();
        segment.moveTo(centerX, centerY);
        segment.lineTo(x1, y1);
        segment.lineTo(x2, y2);
        segment.closePath();

        g2d.setColor(color);
        g2d.fill(segment);
        g2d.draw(segment);
    }
}
