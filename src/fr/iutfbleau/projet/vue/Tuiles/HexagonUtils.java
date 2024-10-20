package vue;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class HexagonUtils {

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
