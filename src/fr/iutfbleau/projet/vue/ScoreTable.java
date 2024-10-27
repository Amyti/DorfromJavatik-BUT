package fr.iutfbleau.projet.vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import fr.iutfbleau.projet.model.ScoreManager;
import java.awt.*;
import java.util.List;

/**
 * La classe ScoreTable affiche un tableau des scores pour une série spécifique.
 * Elle récupère les scores à partir du `ScoreManager` et les présente dans une fenêtre avec une table.
 */
public class ScoreTable extends JFrame {

    /**
     * Constructeur de la classe ScoreTable.
     * Initialise et affiche une fenêtre avec un tableau des scores pour la série spécifiée.
     *
     * @param seriesId L'identifiant de la série pour laquelle afficher les scores.
     */
    public ScoreTable(int seriesId) {
        setTitle("Tableau des Scores - Série " + seriesId);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Integer> scores = ScoreManager.getScoresBySeries(seriesId);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Score");

        for (int score : scores) {
            model.addRow(new Object[]{score});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
