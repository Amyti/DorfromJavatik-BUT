package fr.iutfbleau.projet;


import model.Tile;
import vue.TileView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Tile tile = new Tile(Tile.TerrainType.FORET);
        TileView tileView = new TileView(tile);

        JFrame frame = new JFrame("Deformantique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tileView);
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
