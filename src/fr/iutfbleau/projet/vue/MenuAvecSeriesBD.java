package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Serie;
import model.SerieBD;
import vue.ScoreTable;
import vue.Jeu;
import vue.MyButton;
import vue.BackgroundPanel;

/**
 * La classe MenuAvecSeriesBD représente le menu principal du jeu, permettant de sélectionner une série,
 * de démarrer le jeu, d'afficher les scores, ou de quitter.
 */
public class MenuAvecSeriesBD extends JFrame {

    private JComboBox<Serie> comboBoxSeries;
    private MyButton boutonJouer;
    private MyButton boutonQuitter;
    private MyButton boutonScores;
    private MyButton boutonCommentJouer;

    /**
     * Constructeur de la classe MenuAvecSeriesBD.
     * Initialise et configure l'interface utilisateur du menu principal.
     */
    public MenuAvecSeriesBD() {
        setTitle("Menu");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        JLabel logoLabel = new JLabel(new ImageIcon("../res/logo.png"));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel labelChoisirSerie = new JLabel("Choisir une série :");
        labelChoisirSerie.setOpaque(true);
        labelChoisirSerie.setBackground(new Color(139, 69, 19));
        labelChoisirSerie.setForeground(Color.WHITE);
        labelChoisirSerie.setFont(new Font("Arial", Font.BOLD, 16));
        labelChoisirSerie.setHorizontalAlignment(SwingConstants.CENTER);
        labelChoisirSerie.setPreferredSize(new Dimension(200, 30));
        labelChoisirSerie.setBorder(BorderFactory.createLineBorder(new Color(92, 51, 23), 2));

        comboBoxSeries = new JComboBox<>();
        chargerSeries();

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(labelChoisirSerie, gbc);

        gbc.gridy++;
        centerPanel.add(comboBoxSeries, gbc);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(139, 69, 19, 200));
        leftPanel.setPreferredSize(new Dimension(240, 600));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        boutonJouer = setupButton("JOUER");
        boutonQuitter = setupButton("QUITTER");
        boutonScores = setupButton("TABLEAU DES SCORES");

        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(boutonJouer, gbc);

        gbc.gridy++;
        leftPanel.add(boutonScores, gbc);

        gbc.gridy++;
        leftPanel.add(boutonQuitter, gbc);

        backgroundPanel.add(leftPanel, BorderLayout.WEST);

        boutonJouer.addActionListener(new JouerAction());
        boutonQuitter.addActionListener(new QuitterAction());
        boutonScores.addActionListener(new AfficherScoresAction());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false); 
        rightPanel.setPreferredSize(new Dimension(240, 600));

        boutonCommentJouer = setupButton("COMMENT JOUER");
        boutonCommentJouer.setPreferredSize(new Dimension(200, 50));
        rightPanel.add(boutonCommentJouer, BorderLayout.SOUTH);

        backgroundPanel.add(rightPanel, BorderLayout.EAST);

        boutonCommentJouer.addActionListener(new CommentJouerAction());

        setVisible(true);
    }

    /**
     * Configure un bouton personnalisé avec un style et des couleurs spécifiques.
     *
     * @param text Le texte à afficher sur le bouton.
     * @return Le bouton personnalisé configuré.
     */
    private MyButton setupButton(String text) {
        MyButton button = new MyButton();
        button.setText(text);
        button.setRadius(15);
        button.setPreferredSize(new Dimension(200, 50));
        button.setColor(new Color(139, 69, 19));
        button.setColorOver(new Color(160, 82, 45));
        button.setColorClick(new Color(101, 67, 33));
        button.setBorderColor(new Color(92, 51, 23));
        button.setForeground(Color.WHITE);
        return button;
    }

    /**
     * Charge les séries de tuiles depuis la base de données et les ajoute au comboBoxSeries.
     */
    private void chargerSeries() {
        SerieBD serieDAO = new SerieBD();
        List<Serie> seriesList = serieDAO.getSeries();

        for (Serie serie : seriesList) {
            comboBoxSeries.addItem(serie);
        }
    }

    /**
     * Action pour démarrer le jeu avec la série sélectionnée.
     */
    private class JouerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Serie selectedSerie = (Serie) comboBoxSeries.getSelectedItem();
            if (selectedSerie != null) {
                int seriesId = selectedSerie.getId();
                new Jeu(seriesId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(MenuAvecSeriesBD.this, "Veuillez sélectionner une série.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Action pour quitter l'application.
     */
    private class QuitterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Action pour afficher le tableau des scores de la série sélectionnée.
     */
    private class AfficherScoresAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Serie selectedSerie = (Serie) comboBoxSeries.getSelectedItem();
            if (selectedSerie != null) {
                int seriesId = selectedSerie.getId();
                new ScoreTable(seriesId);
            } else {
                JOptionPane.showMessageDialog(MenuAvecSeriesBD.this, "Veuillez sélectionner une série.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Action pour afficher les instructions de jeu.
     */
    private class CommentJouerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(MenuAvecSeriesBD.this,
                "1. Pour lancer le jeu, sélectionnez d'abord une série, puis cliquez sur le bouton JOUER dans le menu à gauche.\n" +
                "2. Pour faire pivoter les tuiles, utilisez les flèches directionnelles directement sur votre clavier.\n" +
                "3. Vous pouvez maintenant commencer à jouer !",
                "Comment Jouer", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
