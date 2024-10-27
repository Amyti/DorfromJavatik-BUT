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

public class MenuAvecSeriesBD extends JFrame {

    private JComboBox<Serie> comboBoxSeries;
    private MyButton boutonJouer;   
    private MyButton boutonQuitter;
    private MyButton boutonScores;

    public MenuAvecSeriesBD() {
        setTitle("Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        boutonJouer = new MyButton();
        boutonJouer.setText("Jouer");
        boutonJouer.setRadius(15);
        boutonJouer.setPreferredSize(new Dimension(200, 50)); 
        boutonJouer.setColor(new Color(139, 69, 19)); 
        boutonJouer.setColorOver(new Color(160, 82, 45)); 
        boutonJouer.setColorClick(new Color(101, 67, 33)); 
        boutonJouer.setBorderColor(new Color(92, 51, 23)); 
        boutonJouer.setForeground(Color.WHITE);

        boutonQuitter = new MyButton();
        boutonQuitter.setText("Quitter");
        boutonQuitter.setRadius(15);
        boutonQuitter.setPreferredSize(new Dimension(200, 50));
        boutonQuitter.setColor(new Color(139, 69, 19)); 
        boutonQuitter.setColorOver(new Color(160, 82, 45)); 
        boutonQuitter.setColorClick(new Color(101, 67, 33)); 
        boutonQuitter.setBorderColor(new Color(92, 51, 23)); 
        boutonQuitter.setForeground(Color.WHITE);

        boutonScores = new MyButton();
        boutonScores.setText("Tableau des Scores");
        boutonScores.setRadius(15);
        boutonScores.setPreferredSize(new Dimension(200, 50));
        boutonScores.setColor(new Color(139, 69, 19));
        boutonScores.setColorOver(new Color(160, 82, 45)); 
        boutonScores.setColorClick(new Color(101, 67, 33)); 
        boutonScores.setBorderColor(new Color(92, 51, 23)); 
        boutonScores.setForeground(Color.WHITE);

        comboBoxSeries = new JComboBox<>();
        chargerSeries();

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Choisir une série :"), gbc);

        gbc.gridy++;
        centerPanel.add(comboBoxSeries, gbc);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false); 
        rightPanel.setOpaque(true); 
        
        rightPanel.setBackground(new Color(139, 69, 19, 140)); 

        rightPanel.setPreferredSize(new Dimension(240, 600));

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(boutonJouer, gbc);

        gbc.gridy++;
        rightPanel.add(boutonScores, gbc);

        gbc.gridy++;
        rightPanel.add(boutonQuitter, gbc);

        backgroundPanel.add(rightPanel, BorderLayout.EAST);

        boutonJouer.addActionListener(new JouerAction());
        boutonQuitter.addActionListener(new QuitterAction());
        boutonScores.addActionListener(new AfficherScoresAction());

        setVisible(true);
    }

    private void chargerSeries() {
        SerieBD serieDAO = new SerieBD();
        List<Serie> seriesList = serieDAO.getSeries();

        for (Serie serie : seriesList) {
            comboBoxSeries.addItem(serie);
        }
    }

    private class JouerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Serie selectedSerie = (Serie) comboBoxSeries.getSelectedItem();
            if (selectedSerie != null) {
                int seriesId = selectedSerie.getId();
                new Jeu(seriesId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(MenuAvecSeriesBD.this, "Veuillez sélectionner une série.");
            }
        }
    }

    private class QuitterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class AfficherScoresAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Serie selectedSerie = (Serie) comboBoxSeries.getSelectedItem();
            if (selectedSerie != null) {
                int seriesId = selectedSerie.getId();
                new ScoreTable(seriesId);
            } else {
                JOptionPane.showMessageDialog(MenuAvecSeriesBD.this, "Veuillez sélectionner une série.");
            }
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon("../res/dor.png").getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    
}
