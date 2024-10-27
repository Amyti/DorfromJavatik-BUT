package fr.iutfbleau.projet.vue.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import fr.iutfbleau.projet.vue.MenuAvecSeriesBD;
import fr.iutfbleau.projet.model.Serie;
import fr.iutfbleau.projet.vue.ScoreTable;

/**
 * Action pour afficher le tableau des scores de la série sélectionnée.
 */
public class AfficherScoresAction implements ActionListener {

    private MenuAvecSeriesBD menu;

    public AfficherScoresAction(MenuAvecSeriesBD menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Serie selectedSerie = (Serie) menu.getComboBoxSeries().getSelectedItem();
        if (selectedSerie != null) {
            int seriesId = selectedSerie.getId();
            new ScoreTable(seriesId);
        } else {
            JOptionPane.showMessageDialog(menu, "Veuillez sélectionner une série.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
