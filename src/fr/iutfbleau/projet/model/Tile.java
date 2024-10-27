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
    private double splitRatio;
    private double rotationAngle;

    public Tile(TerrainType terrain1) {
        this.terrain1 = terrain1;
        this.hasTwoTerrains = false;
        this.splitRatio = 1.0; 
        this.isTerrain1OnTopOrLeft = true; 
        this.rotationAngle = 0;
    }

    public Tile(TerrainType terrain1, TerrainType terrain2, double splitRatio) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.hasTwoTerrains = true;
        this.splitRatio = splitRatio;
        this.isTerrain1OnTopOrLeft = new Random().nextBoolean(); 
        this.rotationAngle = new Random().nextInt(6) * 60;
    }

    public double getSplitRatio() {
        return this.splitRatio;
    }

    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
    }

    public double getRotationAngle() {
        return rotationAngle;
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
}
