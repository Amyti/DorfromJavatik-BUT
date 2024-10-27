package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Serie;
import model.SerieBD;
import java.util.List;

public class MenuAvecSeriesBD extends JFrame {

    private JComboBox<Serie> comboBoxSeries;

    public MenuAvecSeriesBD() {
        setTitle("Menu avec Fond et Séries");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton boutonJouer = new JButton("Jouer");
        JButton boutonQuitter = new JButton("Quitter");

        comboBoxSeries = new JComboBox<>();
        chargerSeries();

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(boutonJouer, gbc);

        gbc.gridy++;
        backgroundPanel.add(new JLabel("Choisir une série :"), gbc);

        gbc.gridy++;
        backgroundPanel.add(comboBoxSeries, gbc);

        gbc.gridy++;
        backgroundPanel.add(boutonQuitter, gbc);

        boutonJouer.addActionListener(new BoutonJouerAction());
        boutonQuitter.addActionListener(e -> System.exit(0));

        add(backgroundPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void chargerSeries() {
        SerieBD serieDAO = new SerieBD();
        List<Serie> seriesList = serieDAO.getSeries();
        
        for (Serie serie : seriesList) {
            comboBoxSeries.addItem(serie);
        }
    }

    private class BoutonJouerAction implements ActionListener {
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

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon("../res/dorfromantik.avif").getImage();
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
