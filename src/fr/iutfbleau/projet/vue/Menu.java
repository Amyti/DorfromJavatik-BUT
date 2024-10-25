package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;

public class Menu extends JFrame{
    

    public Menu(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        ImageIcon image = new ImageIcon("../res/dorfromantik.png");
        add(new JLabel(image));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }
}
