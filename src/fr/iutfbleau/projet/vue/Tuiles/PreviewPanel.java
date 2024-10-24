package vue;

import javax.swing.*;
import java.awt.*;
import model.Tile;
import vue.HexagonUtils;
import vue.DessinerGrilleHexagonal;

public class PreviewPanel extends JPanel {
    private Tile prochaineTuile;

    public PreviewPanel(Tile prochaineTuile) {
        this.prochaineTuile = prochaineTuile;
        setPreferredSize(new Dimension(150, 150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;
        int radius = 50;

        if (prochaineTuile != null) {
            Shape hexagon = HexagonUtils.createHexagon(centreX, centreY, radius);
            g2d.setColor(Color.BLACK);
            g2d.draw(hexagon);

            DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, centreX, centreY, 6, 6, 10, hexagon, prochaineTuile);
            grille.GrilleHexagonal();
        }
    }

    public void setProchaineTuile(Tile prochaineTuile) {
        this.prochaineTuile = prochaineTuile;
        repaint(); 
    }
}

