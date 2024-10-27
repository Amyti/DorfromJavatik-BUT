package fr.iutfbleau.projet;

import fr.iutfbleau.projet.model.Tile;
import fr.iutfbleau.projet.vue.Jeu;
import javax.swing.*;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;

/**
 * La classe Main est le point d'entrée de l'application.
 * Elle lance le menu principal du jeu en initialisant une instance de `MenuAvecSeriesBD`.
 */
public class Main {

    /**
     * La méthode main est le point de départ de l'exécution de l'application.
     * Elle crée et affiche le menu principal.
     *
     * @param args Les arguments de ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        new MenuAvecSeriesBD();
    }
}
