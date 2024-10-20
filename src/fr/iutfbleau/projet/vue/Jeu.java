import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import vue.*;
import model.*;
import controller.*;;

public class Jeu {
    private JPanel panneau;

    public Jeu() {
        panneau = new JPanel();
        Tile tile = new Tile(Tile.TerrainType.FORET);
        TileView tileView = new TileView(tile);

        JFrame frame = new JFrame("Deformantique");
        panneau.setSize(2000,2000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panneau.add(tileView);
        frame.add(panneau);
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
