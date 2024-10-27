package fr.iutfbleau.projet;


import model.Tile;
import vue.Jeu;
import javax.swing.*;
import vue.MenuAvecSeriesBD;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuAvecSeriesBD::new);
    }
}
