package vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class JLabelPerso extends JLabel {

    public JLabelPerso(String text) {
        super(text);

        try {
            Font policeJeu = Font.createFont(Font.TRUETYPE_FONT, new File("../../../../../res/BelinskyB.otf"));
            policeJeu = policeJeu.deriveFont(Font.BOLD, 14);  

            setFont(policeJeu);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); 
        }

        setForeground(Color.BLUE);
    }
}
