package vue;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class JlabelPerso extends JLabel {

    public JlabelPerso(String text) {
        super(text);

        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("../res/Zorque.otf"));
            customFont = customFont.deriveFont(Font.BOLD, 25);
            setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        setForeground(Color.BLACK);
    }
}
