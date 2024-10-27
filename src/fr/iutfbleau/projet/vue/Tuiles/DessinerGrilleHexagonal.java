package fr.iutfbleau.projet.vue;

import fr.iutfbleau.projet.model.Tile;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * La classe DessinerGrilleHexagonal est responsable de dessiner une grille hexagonale
 * avec un remplissage coloré représentant les terrains d'une tuile. Elle gère les tuiles
 * ayant un ou deux types de terrains et ajuste les couleurs en fonction du ratio de séparation.
 */
public class DessinerGrilleHexagonal {

    private Graphics2D g2d;
    private int centerX, centerY, smallRadius;
    private Shape largeHexagon;
    private Tile tile;

    /**
     * Constructeur de la classe DessinerGrilleHexagonal.
     *
     * @param g2d L'objet Graphics2D utilisé pour dessiner.
     * @param centerX La coordonnée X du centre de l'hexagone.
     * @param centerY La coordonnée Y du centre de l'hexagone.
     * @param smallRadius Le rayon de l'hexagone intérieur.
     * @param largeHexagon La forme de l'hexagone extérieur.
     * @param tile La tuile contenant les informations de terrain à dessiner.
     */
    public DessinerGrilleHexagonal(Graphics2D g2d, int centerX, int centerY, int smallRadius, Shape largeHexagon, Tile tile) {
        this.g2d = g2d;
        this.centerX = centerX;
        this.centerY = centerY;
        this.smallRadius = smallRadius;
        this.largeHexagon = largeHexagon;
        this.tile = tile;
    }

    /**
     * Dessine l'hexagone de la tuile. S'il y a deux terrains, l'hexagone est divisé
     * en fonction du ratio de séparation. Sinon, il est rempli d'une seule couleur.
     */
    public void GrilleHexagonal() {
        double splitRatio = tile.getSplitRatio();

        if (tile.hasTwoTerrains()) {
            drawSplitHexagonWithRatio(splitRatio);
        } else {
            drawFullHexagon(getTerrainColor(tile.getTerrain1()));
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(largeHexagon);
    }

    /**
     * Dessine un hexagone divisé en segments avec des couleurs différentes
     * en fonction du ratio de séparation entre les deux types de terrains.
     *
     * @param ratio Le ratio de séparation entre les deux terrains de la tuile.
     */
    private void drawSplitHexagonWithRatio(double ratio) {
        Color terrain1Color = getTerrainColor(tile.getTerrain1());
        Color terrain2Color = getTerrainColor(tile.getTerrain2());

        int numSegments = (int) Math.round(6 * ratio);

        for (int i = 0; i < 6; i++) {
            double angle1 = i * Math.PI / 3; 
            double angle2 = (i + 1) * Math.PI / 3; 

            int x1 = centerX + (int) (Math.cos(angle1) * smallRadius);
            int y1 = centerY + (int) (Math.sin(angle1) * smallRadius);
            int x2 = centerX + (int) (Math.cos(angle2) * smallRadius);
            int y2 = centerY + (int) (Math.sin(angle2) * smallRadius);

            Path2D segment = new Path2D.Double();
            segment.moveTo(centerX, centerY);
            segment.lineTo(x1, y1);
            segment.lineTo(x2, y2);
            segment.closePath();

            g2d.setColor(i < numSegments ? terrain1Color : terrain2Color);
            g2d.fill(segment);
            g2d.draw(segment);
        }
    }

    /**
     * Dessine un hexagone entièrement rempli d'une seule couleur.
     *
     * @param terrainColor La couleur représentant le type de terrain.
     */
    private void drawFullHexagon(Color terrainColor) {
        g2d.setColor(terrainColor);
        g2d.fill(largeHexagon);
    }

    /**
     * Récupère la couleur associée à un type de terrain spécifique.
     *
     * @param terrain Le type de terrain pour lequel obtenir la couleur.
     * @return La couleur correspondant au terrain spécifié.
     */
    private Color getTerrainColor(Tile.TerrainType terrain) {
        switch (terrain) {
            case MER:
                return new Color(66, 112, 182);
            case CHAMP:
                return new Color(144, 197, 129);
            case PRE:
                return new Color(231, 224, 83);
            case FORET:
                return new Color(64, 118, 75);
            case MONTAGNE:
                return new Color(100, 100, 100);
            default:
                return Color.WHITE;
        }
    }
}
