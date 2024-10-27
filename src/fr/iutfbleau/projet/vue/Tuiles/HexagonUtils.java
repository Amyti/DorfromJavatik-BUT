package vue;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * La classe HexagonUtils fournit des méthodes utilitaires pour créer et manipuler des formes hexagonales.
 */
public class HexagonUtils {

    /**
     * Crée un hexagone centré aux coordonnées spécifiées avec un rayon donné.
     *
     * @param centerX La coordonnée X du centre de l'hexagone.
     * @param centerY La coordonnée Y du centre de l'hexagone.
     * @param radius Le rayon de l'hexagone.
     * @return Un objet Shape représentant l'hexagone.
     */
    public static Shape createHexagon(int centerX, int centerY, int radius) {
        Path2D hexagon = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            double x = centerX + Math.cos(angle) * radius;
            double y = centerY + Math.sin(angle) * radius;
            if (i == 0) {
                hexagon.moveTo(Math.round(x), Math.round(y));
            } else {
                hexagon.lineTo(Math.round(x), Math.round(y));
            }
        }
        hexagon.closePath();
        return hexagon;
    }
}
