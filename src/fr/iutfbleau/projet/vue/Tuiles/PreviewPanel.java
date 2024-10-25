package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import model.Tile;

public class PreviewPanel extends JPanel {
    private Tile prochaineTuile;
    private double rotationAngle = 0; 
    
    public PreviewPanel(Tile prochaineTuile) {
        this.prochaineTuile = prochaineTuile;
        setPreferredSize(new Dimension(150, 150)); 
    }
    
    public void setProchaineTuile(Tile tuile) {
        this.prochaineTuile = tuile;
        repaint(); 
    }
    public double getRotationAngle() {
        return rotationAngle;
    }
    
    public void rotateTile() {
        rotationAngle += 60;
        if (prochaineTuile != null) {
            prochaineTuile.setRotationAngle(rotationAngle % 360);
        }
        repaint();
    }
    
    
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int hexagonRadius = 50;
        
        Shape hexagon = HexagonUtils.createHexagon(centerX, centerY, hexagonRadius);
        
        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotationAngle), centerX, centerY); 
        g2d.setTransform(transform);
        
        g2d.setColor(Color.BLACK);
        g2d.draw(hexagon);
        
        DessinerGrilleHexagonal grille = new DessinerGrilleHexagonal(g2d, centerX, centerY, 6, 6, 10, hexagon, prochaineTuile);
        grille.GrilleHexagonal();
        
        g2d.setTransform(oldTransform);
    }
}
