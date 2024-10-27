package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 * La classe MyButton est un bouton personnalisé avec des couleurs et des coins arrondis,
 * qui réagit aux événements de la souris pour changer de couleur selon son état (survol, clic, etc.).
 */
public class MyButton extends JButton {

    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    /**
     * Constructeur de la classe MyButton.
     * Initialise les couleurs par défaut pour le bouton et ajoute des écouteurs d'événements de souris.
     */
    public MyButton() {
        setColor(Color.WHITE);
        colorOver = new Color(179, 250, 160);
        colorClick = new Color(152, 184, 144);
        borderColor = new Color(30, 136, 56);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setBackground(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                setBackground(over ? colorOver : color);
            }
        });
    }

    /**
     * Indique si la souris est actuellement au-dessus du bouton.
     *
     * @return true si la souris est sur le bouton, sinon false.
     */
    public boolean isOver() {
        return over;
    }

    /**
     * Définit si la souris est au-dessus du bouton.
     *
     * @param over true si la souris est sur le bouton, sinon false.
     */
    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * Obtient la couleur de fond par défaut du bouton.
     *
     * @return La couleur de fond par défaut.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Définit la couleur de fond par défaut du bouton.
     *
     * @param color La couleur de fond à appliquer.
     */
    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    /**
     * Obtient la couleur du bouton lorsque la souris le survole.
     *
     * @return La couleur de survol du bouton.
     */
    public Color getColorOver() {
        return colorOver;
    }

    /**
     * Définit la couleur du bouton lorsque la souris le survole.
     *
     * @param colorOver La couleur de survol à appliquer.
     */
    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    /**
     * Obtient la couleur du bouton lorsqu'il est cliqué.
     *
     * @return La couleur de clic du bouton.
     */
    public Color getColorClick() {
        return colorClick;
    }

    /**
     * Définit la couleur du bouton lorsqu'il est cliqué.
     *
     * @param colorClick La couleur de clic à appliquer.
     */
    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    /**
     * Obtient la couleur de bordure du bouton.
     *
     * @return La couleur de bordure.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Définit la couleur de bordure du bouton.
     *
     * @param borderColor La couleur de bordure à appliquer.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Obtient le rayon de courbure des coins du bouton.
     *
     * @return Le rayon des coins arrondis.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Définit le rayon de courbure des coins du bouton.
     *
     * @param radius Le rayon des coins arrondis.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Redessine le composant pour appliquer les styles personnalisés.
     * Affiche le bouton avec des coins arrondis et des couleurs spécifiques en fonction de son état.
     *
     * @param grphcs Le contexte graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(grphcs);
    }
}
