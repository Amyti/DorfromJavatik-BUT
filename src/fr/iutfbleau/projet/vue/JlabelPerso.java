package vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * La classe JlabelPerso est une sous-classe de JLabel avec un style personnalisé.
 * Elle utilise une police personnalisée et permet de spécifier la taille du texte.
 */
public class JlabelPerso extends JLabel {

    /**
     * Constructeur de la classe JlabelPerso.
     * Charge et applique une police personnalisée au texte.
     *
     * @param text Le texte à afficher dans le JLabel.
     * @param size La taille de la police pour le texte.
     */
    public JlabelPerso(String text, int size) {
        super(text);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("../res/Zorque.otf"));
            customFont = customFont.deriveFont(Font.BOLD, size);
            setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        setForeground(Color.BLACK);
    }
}
