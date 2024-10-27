package fr.iutfbleau.projet.vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import fr.iutfbleau.projet.model.Serie;
import fr.iutfbleau.projet.model.SerieBD;
import fr.iutfbleau.projet.vue.MyButton;
import fr.iutfbleau.projet.vue.BackgroundPanel;
import fr.iutfbleau.projet.vue.actions.JouerAction;
import fr.iutfbleau.projet.vue.actions.QuitterAction;
import fr.iutfbleau.projet.vue.actions.AfficherScoresAction;
import fr.iutfbleau.projet.vue.actions.CommentJouerAction;

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

        // Ajout des action listeners avec les classes externes
        boutonJouer.addActionListener(new JouerAction(this));
        boutonQuitter.addActionListener(new QuitterAction());
        boutonScores.addActionListener(new AfficherScoresAction(this));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(240, 600));

        boutonCommentJouer = setupButton("COMMENT JOUER");
        boutonCommentJouer.setPreferredSize(new Dimension(200, 50));
        rightPanel.add(boutonCommentJouer, BorderLayout.SOUTH);

        backgroundPanel.add(rightPanel, BorderLayout.EAST);

        boutonCommentJouer.addActionListener(new CommentJouerAction(this));

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
     * Getter pour le JComboBox des séries.
     *
     * @return Le JComboBox contenant les séries.
     */
    public JComboBox<Serie> getComboBoxSeries() {
        return comboBoxSeries;
    }
}
