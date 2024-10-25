package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tile {
    public enum TerrainType {
        MER, CHAMP, PRE, FORET, MONTAGNE
    }
    
    private TerrainType terrain1;
    private TerrainType terrain2;
    private boolean hasTwoTerrains;
    private boolean isTerrain1OnTopOrLeft;
    private double terrain1Ratio;
    private double rotationAngle;
    
    public Tile(TerrainType terrain1) {
        this.terrain1 = terrain1;
        this.hasTwoTerrains = false;
        this.terrain1Ratio = 1.0; 
        this.rotationAngle = 0;
        
    }
    
    public Tile(TerrainType terrain1, TerrainType terrain2) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.hasTwoTerrains = true;
        this.terrain1Ratio = 0.5;
        this.rotationAngle = new Random().nextInt(6) * 60; 
        
    }
    
    public Tile(TerrainType terrain1, TerrainType terrain2, double terrain1Ratio) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.hasTwoTerrains = true;
        this.terrain1Ratio = terrain1Ratio;
    }
    
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
    }
    
    public double getRotationAngle() {
        return rotationAngle;
    }
    
    public boolean hasTwoTerrains() {
        return this.hasTwoTerrains;
    }
    
    public boolean isTerrain1OnTopOrLeft() {
        return this.isTerrain1OnTopOrLeft;
    }
    
    public TerrainType getTerrain1() {
        return terrain1;
    }
    
    public TerrainType getTerrain2() {
        return hasTwoTerrains ? terrain2 : null;
        
    }
    
    public double getTerrain1Ratio() {
        return terrain1Ratio;
    }
    
    
    public static List<Tile> series1() {
        List<Tile> serieTuiles = new ArrayList<>();
        
        TerrainType[][] terrains = {
            {TerrainType.CHAMP, TerrainType.FORET},
            {TerrainType.MER, TerrainType.MONTAGNE},
            {TerrainType.PRE, TerrainType.CHAMP},
            {TerrainType.FORET, TerrainType.PRE},
            {TerrainType.MONTAGNE, TerrainType.MER},
            {TerrainType.MER, TerrainType.MER},
            {TerrainType.MER, TerrainType.MONTAGNE},
            {TerrainType.PRE, TerrainType.CHAMP},
            {TerrainType.FORET, TerrainType.PRE},
            {TerrainType.MONTAGNE, TerrainType.MER},
            {TerrainType.CHAMP, TerrainType.FORET},
            {TerrainType.MER, TerrainType.MONTAGNE},
            {TerrainType.PRE, TerrainType.CHAMP},
            {TerrainType.FORET, TerrainType.PRE},
            {TerrainType.MONTAGNE, TerrainType.MER},
            {TerrainType.MER, TerrainType.MER},
            
            
        };
        
        double[] ratios = {0.2, 0.5, 0.8, 0.2, 0.5,1.0, 0.5, 0.8, 0.2, 0.5,1.0, 0.5, 0.8, 0.2,0.5,0.1};
        
        /*/ le ration c'est pour savoir le type de tuile yen n'a 4 :
        1.0 : c'est pour une tuile completeeee
        0.5 : c'est une tuile qui fait moutte moutte 
        0.2 et 0.8 : c'est pour 20% une couleurs et 80% pour le reste 
        */
        
        for (int i = 0; i < terrains.length ; i++) {
            Tile tuile;
            if (ratios[i] == 1.0) {
                tuile = new Tile(terrains[i][0], terrains[i][1], ratios[1]); 
            } else {
                tuile = new Tile(terrains[i][0], terrains[i][1], ratios[i]); 
            }
            serieTuiles.add(tuile);
        }
        
        return serieTuiles;
    }
}
