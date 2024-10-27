package fr.iutfbleau.projet.vue;

import javax.swing.*;
import java.awt.*;

/**
 * La classe BackgroundPanel représente un panneau avec une image de fond.
 * L'image de fond est chargée à partir d'un fichier lors de l'initialisation.
 */
public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    /**
     * Constructeur de la classe BackgroundPanel.
     * Charge une image de fond depuis le chemin spécifié.
     */
    public BackgroundPanel() {
        try {
            backgroundImage = new ImageIcon("../res/dor.png").getImage();
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image de fond : " + e.getMessage());
        }
    }

    /**
     * Redessine le composant avec l'image de fond.
     *
     * @param g Le contexte graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
