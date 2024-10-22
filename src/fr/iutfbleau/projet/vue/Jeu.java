package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import model.*;
import model.Tile.TerrainType;

public class Jeu extends MouseAdapter {
    
    private JFrame frame;
    private TileView tileView;
    private JScrollPane scrollPane;
    private Point lastMousePosition;
    
    public Jeu() {
        frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        
        Tile tile = new Tile(getRandomTerrainType());
        tileView = new TileView(tile);
        
        tileView.setPreferredSize(new Dimension(2500, 2500));
        
        scrollPane = new JScrollPane(tileView);
        scrollPane.setPreferredSize(new Dimension(1080, 720));
        
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        scrollPane.setDoubleBuffered(true);
        tileView.addMouseListener(this);
        tileView.addMouseMotionListener(this);
        
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        SwingUtilities.invokeLater(this::centerViewOnTile);
    }
    
    private TerrainType getRandomTerrainType() {
        TerrainType[] types = TerrainType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }
    
    
    private void centerViewOnTile() {
        JViewport viewport = scrollPane.getViewport();
        
        viewport.setViewPosition(new Point(1200-540, 1200-360));
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        lastMousePosition = SwingUtilities.convertPoint(tileView, e.getPoint(), scrollPane.getViewport());
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        JViewport viewport = (JViewport) scrollPane.getViewport();
        Point newMousePosition = SwingUtilities.convertPoint(tileView, e.getPoint(), scrollPane.getViewport());
        int deltaX = lastMousePosition.x - newMousePosition.x;
        int deltaY = lastMousePosition.y - newMousePosition.y;
        Point viewPosition = viewport.getViewPosition();
        int newViewX = viewPosition.x + deltaX;
        int newViewY = viewPosition.y + deltaY;
        int maxViewX = tileView.getWidth() - viewport.getWidth();
        int maxViewY = tileView.getHeight() - viewport.getHeight();
        
        if (newViewX < 0) {
            newViewX = 0;
        } else if (newViewX > maxViewX) {
            newViewX = maxViewX;
        }
        if (newViewY < 0) {
            newViewY = 0;
        } else if (newViewY > maxViewY) {
            newViewY = maxViewY;
        }
        
        viewport.setViewPosition(new Point(newViewX, newViewY));
        lastMousePosition = newMousePosition;
    }
}
