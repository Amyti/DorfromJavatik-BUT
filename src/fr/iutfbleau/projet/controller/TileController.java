package controller;

import model.*;
import model.Tile.TerrainType;
import vue.*;


import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;   
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class TileController {
    private TileView tileView;
    public int score = 0;
    private int tilesPlacedCount = 0; 
    private final int MAX_TILES = 50;

    
    public TileController(TileView tileView) {
        this.tileView = tileView;
    }
    
    public void PlacerTuile(int x, int y, Tile tuileAPlacer) {
        if (tilesPlacedCount >= MAX_TILES) {
            signalerFinDeJeu();
        }

        Point point = new Point(x, y);
        tileView.ajouterTuile(point, tuileAPlacer);
        tileView.ajouterPosition(point);
        ajoutPoints(point, tuileAPlacer); 
        tilesPlacedCount++;
        tileView.mettreAJourPositionsDisponibles();
        tileView.repaint();
        
        
    }


    public void signalerFinDeJeu() {
        int ScoreFinal = calculerScore();
        tileView.finDeJeu(ScoreFinal); 
    }
    
    private void ajoutPoints(Point positionNouvelleTuile, Tile nouvelleTuile) {
        List<Point> positionsAdjacentes = tileView.positionAdjacentes(positionNouvelleTuile);
        for (Point posAdj : positionsAdjacentes) {
            Tile tuileAdjacente = tileView.getTileAtPosition(posAdj);
            
            if (tuileAdjacente != null) {
                if (comparerCotes(nouvelleTuile, tuileAdjacente, positionNouvelleTuile, posAdj)) {
                    score++;
                }
            }
        }
    }

    private boolean comparerCotes(Tile tuile1, Tile tuile2, Point posTuile1, Point posTuile2) {
        int coteTuile1 = obtenirCote(posTuile1, posTuile2);
        int coteOpposeTuile2 = (coteTuile1 + 3) % 6;

        TerrainType terrainCoteTuile1 = obtenirTerrainCote(tuile1, coteTuile1);
        TerrainType terrainCoteTuile2 = obtenirTerrainCote(tuile2, coteOpposeTuile2);

        return terrainCoteTuile1 != null && terrainCoteTuile1.equals(terrainCoteTuile2);
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

    private int obtenirCote(Point posTuile1, Point posTuile2) {
        int dx = posTuile2.x - posTuile1.x;
        int dy = posTuile2.y - posTuile1.y;
        
        if (dx == 0 && dy < 0) return 0; 
        if (dx > 0 && dy < 0) return 1; 
        if (dx > 0 && dy == 0) return 2;
        if (dx == 0 && dy > 0) return 3; 
        if (dx < 0 && dy > 0) return 4; 
        if (dx < 0 && dy == 0) return 5; 
        return -1;
    }

    private TerrainType obtenirTerrainCote(Tile tuile, int cote) {
        int rotation = (int) tuile.getRotationAngle();
        int coteAvecRotation = (cote + rotation / 60) % 6;

        if (tuile.hasTwoTerrains()) {
            return (coteAvecRotation < 3) ? tuile.getTerrain1() : tuile.getTerrain2();
        }

        return tuile.getTerrain1();
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

    public int calculerScore() {
        Map<Tile.TerrainType, List<Set<Point>>> pochesParTerrain = new HashMap<>();
        
        for (Point position : tileView.getPositions()) {
            Tile tile = tileView.getTileAtPosition(position);
            if (tile != null) {
                ajouterTuileAuxPoches(tile.getTerrain1(), position, pochesParTerrain);
                if (tile.hasTwoTerrains()) {
                    ajouterTuileAuxPoches(tile.getTerrain2(), position, pochesParTerrain);
                }
            }
        }

        int scoreTotal = 0;
        for (List<Set<Point>> poches : pochesParTerrain.values()) {
            for (Set<Point> poche : poches) {
                int taillePoche = poche.size();
                scoreTotal += taillePoche * taillePoche; 
            }
        }

        return scoreTotal;
    }

    private void ajouterTuileAuxPoches(Tile.TerrainType terrain, Point position, Map<Tile.TerrainType, List<Set<Point>>> pochesParTerrain) {
        List<Set<Point>> poches = pochesParTerrain.computeIfAbsent(terrain, k -> new ArrayList<>());

        Set<Point> pocheTrouvee = null;
        List<Set<Point>> pochesAAjouter = new ArrayList<>();

        for (Set<Point> poche : poches) {
            for (Point adj : tileView.positionAdjacentes(position)) {
                if (poche.contains(adj)) {
                    if (pocheTrouvee == null) {
                        pocheTrouvee = poche;
                        pocheTrouvee.add(position);
                    } else {
                        pocheTrouvee.addAll(poche);
                        pochesAAjouter.add(poche);
                    }
                    break;
                }
            }
        }

        if (pocheTrouvee == null) {
            Set<Point> nouvellePoche = new HashSet<>();
            nouvellePoche.add(position);
            poches.add(nouvellePoche);
        }

        poches.removeAll(pochesAAjouter);
    }
    
    public int getScore() {
        System.out.println("Instance TileController dans getScore : " + this);
        System.out.println("Score actuel dans getScore() : " + score);
        return score;
    }

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
            if(!tuileDejaPresente(position, positionsPrises)){
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
