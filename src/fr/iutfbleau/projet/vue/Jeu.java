package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.*;

public class Jeu extends MouseAdapter {  
    private JFrame frame;
    private JScrollPane scrollPane;
    private JPanel panneau;
    private Point initialClick;

    public Jeu() {
        // Création du panneau pour contenir les tuiles
        panneau = new JPanel();
        panneau.setPreferredSize(new Dimension(2000, 2000));  // Taille du panneau

        // Création de la première tuile
        Tile tile = new Tile(Tile.TerrainType.FORET);
        TileView tileView = new TileView(tile);

        panneau.add(tileView);

        scrollPane = new JScrollPane(panneau); 
        scrollPane.setPreferredSize(new Dimension(1080, 720)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane); 
        frame.pack(); 
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        panneau.addMouseListener(this);
        panneau.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        initialClick = e.getPoint();  
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        JViewport viewport = scrollPane.getViewport(); 
        Point viewPosition = viewport.getViewPosition(); 

        int xMoved = e.getX() - initialClick.x;
        int yMoved = e.getY() - initialClick.y;

        int newX = viewPosition.x - xMoved;
        int newY = viewPosition.y - yMoved;

        viewport.setViewPosition(new Point(newX, newY));
    }
}
