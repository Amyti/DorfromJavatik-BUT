package model;

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

    public Tile(TerrainType terrain1) {
        this.terrain1 = terrain1;
        this.hasTwoTerrains = false;
        this.terrain1Ratio = 1.0; 
    }


    public Tile(TerrainType terrain1, TerrainType terrain2) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.hasTwoTerrains = true;
        this.isTerrain1OnTopOrLeft = new Random().nextBoolean();
        
        Random random = new Random();
        double[] possibleRatios = {1.0, 0.5, 0.2, 0.8}; 
        this.terrain1Ratio = possibleRatios[random.nextInt(possibleRatios.length)];
    }

    public TerrainType getTerrain1() {
        return terrain1;
    }

    public TerrainType getTerrain2() {
        return hasTwoTerrains ? terrain2 : null;
    }

    public boolean hasTwoTerrains() {
        return this.hasTwoTerrains;
    }

    public boolean isTerrain1OnTopOrLeft() {
        return isTerrain1OnTopOrLeft;
    }

    public double getTerrain1Ratio() {
        return terrain1Ratio;
    }
}
