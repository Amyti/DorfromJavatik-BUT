package controller;

import model.Tile;
import vue.TileView;

import java.awt.*;

public class TileController {
    private TileView tileView;
    private int rayon = 50; 

    public TileController(TileView tileView) {
        this.tileView = tileView;
    }

    public void PlacerTuile(int x, int y) {
        Point autreTuiles = getLastTileCenter(); 

        if (autreTuiles == null) {
            Point point = new Point(x, y);
            tileView.ajouterPosition(point);
            tileView.repaint();
            return;
        }

        Point point = verifPoint(autreTuiles, x, y);
    

        tileView.ajouterPosition(point);
        tileView.repaint();

        System.out.println("Centre de la tuile placée : (" + point.x + ", " + point.y + ")");
    }

    public Point getLastTileCenter() {
        return tileView.getLastTilePosition();
    }


    public Point verifPoint(Point autreTuiles, int x, int y){
        Point point;
        if (x > autreTuiles.x - 25 && x < autreTuiles.x + 25) {
            if (y > autreTuiles.y) {
                point = new Point(autreTuiles.x, autreTuiles.y + 170);
            } else {
                point = new Point(autreTuiles.x, autreTuiles.y - 170);
            }
        }
        else if (x > autreTuiles.x) {
            if (y > autreTuiles.y) {
                point = new Point(autreTuiles.x + 150, autreTuiles.y + 85);
            } else {
                point = new Point(autreTuiles.x + 150 , autreTuiles.y - 85);
            }
        } else if(x < autreTuiles.x){
            if (y > autreTuiles.y) {
                point = new Point(autreTuiles.x - 150, autreTuiles.y + 85);
            } else {
                point = new Point(autreTuiles.x - 150, autreTuiles.y - 85);
            }
        }else{
            point = new Point(autreTuiles.x, autreTuiles.y);
        }

        return point;
    }


}
