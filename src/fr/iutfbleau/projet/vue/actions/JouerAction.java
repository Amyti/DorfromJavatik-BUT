package fr.iutfbleau.projet.vue.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;
import fr.iutfbleau.projet.model.Serie;
import fr.iutfbleau.projet.vue.Jeu;

/**
 * Action pour démarrer le jeu avec la série sélectionnée.
 */
public class JouerAction implements ActionListener {

    private MenuAvecSeriesBD menu;

    public JouerAction(MenuAvecSeriesBD menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Serie selectedSerie = (Serie) menu.getComboBoxSeries().getSelectedItem();
        if (selectedSerie != null) {
            int seriesId = selectedSerie.getId();
            new Jeu(seriesId);
            menu.dispose();
        } else {
            JOptionPane.showMessageDialog(menu, "Veuillez sélectionner une série.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
