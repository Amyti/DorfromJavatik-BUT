package controller;

import model.*;
import model.Tile.TerrainType;
import vue.*;


import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;   

public class TileController {
    private TileView tileView;
    public int score = 0;
    
    public TileController(TileView tileView) {
        this.tileView = tileView;
    }
    
    public void PlacerTuile(int x, int y, Tile tuileAPlacer) {
        Point point = new Point(x, y);
        tileView.ajouterTuile(point, tuileAPlacer);
        tileView.ajouterPosition(point);
        ajoutpoints(point, tuileAPlacer);
        tileView.mettreAJourPositionsDisponibles();
        tileView.repaint();
        
        System.out.println("Centre de la tuile placée : (" + point.x + ", " + point.y + ")");
    }
    
    private void ajoutpoints(Point positionNouvelleTuile, Tile nouvelleTuile) {
        List<Point> positionsAdjacentes = tileView.positionadjacentes(positionNouvelleTuile);
        for (Point posAdj : positionsAdjacentes) {
            Tile tuileAdjacente = tileView.getTileAtPosition(posAdj);
            
            if (tuileAdjacente != null) {
                boolean pointGagné = comparerTerrainsEtendus(nouvelleTuile, tuileAdjacente);
                if (pointGagné) {
                    System.out.println("Score actuel : " + score);
                }
            }
        }
    }
    
    private boolean comparerTerrainsEtendus(Tile tuile1, Tile tuile2) {
        boolean pointGagné = false;
        
        if (tuile1.getTerrain1() == tuile2.getTerrain1() || tuile1.getTerrain1() == tuile2.getTerrain2()) {
            score++;
            pointGagné = true;
        }
        
        if (tuile1.getTerrain2() != null && (tuile1.getTerrain2() == tuile2.getTerrain1() || tuile1.getTerrain2() == tuile2.getTerrain2())) {
            score++;
            pointGagné = true;
        }
        
        return pointGagné;
    }
    
    public int getScore() {
        return score;
    }
    
    public Point getCentreHexagoneClique(int x, int y, List<Point> positionsDisponibles) {
        int dispoRadius = 50;
        
        for (Point position : positionsDisponibles) {
            Shape hexagon = HexagonUtils.createHexagon(position.x, position.y, dispoRadius);
            
            if (hexagon.contains(x, y)) {
                return position;
            }
        }
        return null; 
    }
    
    public Point getLastTileCenter() {
        return tileView.getLastTilePosition();
    }
    
    public boolean tuileDejaPresente(Point position, List<Point> positionsPrises) {
        for (Point prise : positionsPrises) {
            if (position.equals(prise)) {
                return true;
            }
        }
        return false;
    }
    
    
    /*public Point verifPoint(Point autreTuiles, int x, int y){
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
    }*/
    
    
    
    public List<Point> calculerPositionsDisponibles(Point centreTuile, List<Point> positionsPrises) {
        List<Point> positionsDisponibles = new ArrayList<>();
        List<Point> positionFiltree = new ArrayList<>();
        
        int horizontalOffset = 150;
        int verticalOffset = 85;
        int verticalFullOffset = 170;
        
        positionsDisponibles.add(new Point(centreTuile.x, centreTuile.y - verticalFullOffset));
        positionsDisponibles.add(new Point(centreTuile.x, centreTuile.y + verticalFullOffset));
        positionsDisponibles.add(new Point(centreTuile.x + horizontalOffset, centreTuile.y - verticalOffset));
        positionsDisponibles.add(new Point(centreTuile.x + horizontalOffset, centreTuile.y + verticalOffset));
        positionsDisponibles.add(new Point(centreTuile.x - horizontalOffset, centreTuile.y - verticalOffset));
        positionsDisponibles.add(new Point(centreTuile.x - horizontalOffset, centreTuile.y + verticalOffset));
        
        for(Point position : positionsDisponibles){
            if(tuileDejaPresente(position, positionsPrises) == false){
                positionFiltree.add(position);
            }
        }
        
        return positionFiltree;
    }
    
    private TerrainType getRandomTerrainType() {
        TerrainType[] types = TerrainType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }
    
    
}
