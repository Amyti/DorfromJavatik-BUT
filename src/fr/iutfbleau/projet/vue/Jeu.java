package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.*;
import vue.JlabelPerso;

public class Jeu extends MouseAdapter {

    private JFrame frame;
    private TileView tileView;
    private JScrollPane scrollPane;
    private Point lastMousePosition;

    public Jeu() {
        LineBorder border = new LineBorder(Color.BLACK, 5);

        frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        tileView = new TileView();
        tileView.setPreferredSize(new Dimension(2500, 2500));

        scrollPane = new JScrollPane(tileView);
        scrollPane.setPreferredSize(new Dimension(1080, 720));
        scrollPane.setMinimumSize(new Dimension(540, 540));  

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setDoubleBuffered(true);
        tileView.addMouseListener(this);
        tileView.addMouseMotionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel mainScreen = new JPanel(new GridBagLayout());
        JPanel sideBar = new JPanel();
        
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.weightx = 0.75; 
        gbc.weighty = 1.0;  
        gbc.fill = GridBagConstraints.BOTH; 
        mainScreen.add(scrollPane, gbc);

        sideBar.setBackground(Color.WHITE);
        sideBar.setPreferredSize(new Dimension(270, 720));  
        sideBar.setMinimumSize(new Dimension(270, 720));   
        
        gbc.gridx = 1; 
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; 
        sideBar.setBorder(border);

        JlabelPerso score = new JlabelPerso("Score : ");
        sideBar.add(score);

        mainScreen.add(sideBar, gbc);

        frame.add(mainScreen);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        SwingUtilities.invokeLater(this::centerViewOnTile);
    }

    private void centerViewOnTile() {
        JViewport viewport = scrollPane.getViewport();

        viewport.setViewPosition(new Point(1200 - scrollPane.getWidth() / 2, 1200 - scrollPane.getHeight() / 2));
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
