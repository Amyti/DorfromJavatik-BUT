package fr.iutfbleau.projet.vue.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;

/**
 * Action pour afficher les instructions de jeu.
 */
public class CommentJouerAction implements ActionListener {

    private MenuAvecSeriesBD menu;

    public CommentJouerAction(MenuAvecSeriesBD menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(menu,
            "1. Pour lancer le jeu, sélectionnez d'abord une série, puis cliquez sur le bouton JOUER dans le menu à gauche.\n" +
            "2. Pour faire pivoter les tuiles, utilisez les flèches directionnelles directement sur votre clavier.\n" +
            "3. Vous pouvez maintenant commencer à jouer !",
            "Comment Jouer", JOptionPane.INFORMATION_MESSAGE);
    }
}
