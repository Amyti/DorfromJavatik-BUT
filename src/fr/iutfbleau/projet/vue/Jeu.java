package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.*;

public class Jeu extends MouseAdapter {  
    private JFrame frame;

    public Jeu() {
       

        Tile tile = new Tile(Tile.TerrainType.FORET);
        TileView tileView = new TileView(tile);


        frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tileView); 
        frame.pack(); 
        frame.setSize(1080,720);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

    }

}
