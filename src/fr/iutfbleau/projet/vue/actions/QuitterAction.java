package fr.iutfbleau.projet.vue.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action pour quitter l'application.
 */
public class QuitterAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
