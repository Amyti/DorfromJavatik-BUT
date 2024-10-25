package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import controller.TileController;

public class Jeu extends MouseAdapter implements KeyListener {

    private JFrame frame;
    private TileView tileView;
    private PreviewPanel previewPanel;
    private JScrollPane scrollPane;
    private Point lastMousePosition;
    private JlabelPerso score;
    private TileController tileController;

    public Jeu() {
        frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        tileView = new TileView();
        tileController = new TileController(tileView);

        tileView.setPreferredSize(new Dimension(2500, 2500));

        Tile prochaineTuile = tileView.getProchaineTuile();
        previewPanel = new PreviewPanel(prochaineTuile);
        previewPanel.setBackground(Color.WHITE);

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
        JPanel sideBar = new JPanel(new GridBagLayout());  
        score = new JlabelPerso("Score : 0", 25);
        JlabelPerso previsualisationLabel = new JlabelPerso("Prochaine tuile", 10);

        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.weightx = 0.75; 
        gbc.weighty = 1.0;  
        gbc.fill = GridBagConstraints.BOTH; 
        mainScreen.add(scrollPane, gbc);

        sideBar.setBackground(Color.WHITE);
        sideBar.setPreferredSize(new Dimension(270, 720));  
        sideBar.setMinimumSize(new Dimension(270, 720));   
        sideBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.NORTH; 
        gbc.insets = new Insets(10, 10, 10, 10);  
        sideBar.add(score, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.05; 
        sideBar.add(previsualisationLabel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        sideBar.add(previewPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25; 
        gbc.fill = GridBagConstraints.BOTH;
        mainScreen.add(sideBar, gbc);

        frame.add(mainScreen);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addKeyListener(this);
        frame.setFocusable(true); 

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

    @Override
    public void mouseClicked(MouseEvent e) {
        tileView.mouseClicked(e);
        previewPanel.setProchaineTuile(tileView.getProchaineTuile());
        int scoreFinal = tileController.getScore();
        score.setText("Score : " + scoreFinal);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            previewPanel.rotateTile(); 
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            previewPanel.rotateTile(); 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
