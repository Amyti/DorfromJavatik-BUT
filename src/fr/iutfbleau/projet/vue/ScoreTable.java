package vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ScoreManager;
import java.awt.*;
import java.util.List;

public class ScoreTable extends JFrame {

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
