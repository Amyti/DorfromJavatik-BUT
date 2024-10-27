package fr.iutfbleau.projet.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe CustomDialog permet d'afficher une boîte de dialogue personnalisée
 * avec un message, un titre et un bouton OK pour fermer la boîte de dialogue.
 */
public class CustomDialog {

    /**
     * Affiche une boîte de dialogue personnalisée avec un message et un titre spécifiés.
     *
     * @param parent  Le composant parent de la boîte de dialogue (peut être null).
     * @param message Le message à afficher dans la boîte de dialogue.
     * @param title   Le titre de la boîte de dialogue.
     */
    public static void showCustomMessage(Component parent, String message, String title) {
        JDialog dialog = new JDialog((JFrame) null, title, true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(139, 69, 19, 140)); 
        messagePanel.setLayout(new GridBagLayout());

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messagePanel.add(messageLabel);

        dialog.add(messagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        MyButton okButton = new MyButton();
        okButton.setText("OK");
        okButton.setRadius(15);
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setColor(new Color(139, 69, 19));
        okButton.setColorOver(new Color(160, 82, 45));
        okButton.setColorClick(new Color(101, 67, 33));
        okButton.setBorderColor(new Color(92, 51, 23));
        okButton.setForeground(Color.WHITE);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.getContentPane().setBackground(new Color(139, 69, 19, 140));
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(92, 51, 23), 3));

        dialog.setVisible(true);
    }
}
