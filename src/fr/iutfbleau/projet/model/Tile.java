package model;


public class Tile {
    public enum TerrainType {
        MER, CHAMP, PRE, FORET, MONTAGNE
    }

    private TerrainType terrain1;
    private TerrainType terrain2;
    private boolean isDualTerrain;

    public Tile(TerrainType terrain) {
        this.terrain1 = terrain;
        this.isDualTerrain = false;
    }

    public Tile(TerrainType terrain1, TerrainType terrain2) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.isDualTerrain = true;
    }

    public TerrainType getTerrain1() {
        return terrain1;
    }

    public TerrainType getTerrain2() {
        return isDualTerrain ? terrain2 : null;
    }

    public boolean isDualTerrain() {
        return isDualTerrain;
    }

}