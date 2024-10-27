package fr.iutfbleau.projet.model;

import java.util.Random;

/**
 * La classe Tile représente une tuile avec des types de terrains et des propriétés telles que
 * l'angle de rotation, le ratio de séparation et la disposition des terrains.
 */
public class Tile {

    /**
     * Enumération des différents types de terrains possibles pour une tuile.
     */
    public enum TerrainType {
        MER, CHAMP, PRE, FORET, MONTAGNE
    }

    private TerrainType terrain1;
    private TerrainType terrain2;
    private boolean hasTwoTerrains;
    private boolean isTerrain1OnTopOrLeft;
    private double splitRatio;
    private double rotationAngle;

    // Instance statique de Random pour éviter les instanciations répétées
    private static final Random RANDOM = new Random();

    /**
     * Constructeur pour créer une tuile avec un seul type de terrain.
     *
     * @param terrain1 Le type de terrain unique de la tuile.
     */
    public Tile(TerrainType terrain1) {
        this.terrain1 = terrain1;
        this.hasTwoTerrains = false;
        this.splitRatio = 1.0;
        this.isTerrain1OnTopOrLeft = true;
        this.rotationAngle = 0;
    }

    /**
     * Constructeur pour créer une tuile avec deux types de terrains.
     *
     * @param terrain1 Le premier type de terrain.
     * @param terrain2 Le deuxième type de terrain.
     * @param splitRatio Le ratio de séparation entre les deux terrains.
     */
    public Tile(TerrainType terrain1, TerrainType terrain2, double splitRatio) {
        this.terrain1 = terrain1;
        this.terrain2 = terrain2;
        this.hasTwoTerrains = true;
        this.splitRatio = splitRatio;
        this.isTerrain1OnTopOrLeft = RANDOM.nextBoolean();
        this.rotationAngle = RANDOM.nextInt(6) * 60;
    }

    /**
     * Récupère le ratio de séparation entre les deux terrains de la tuile.
     *
     * @return Le ratio de séparation.
     */
    public double getSplitRatio() {
        return this.splitRatio;
    }

    /**
     * Définit l'angle de rotation de la tuile.
     *
     * @param angle L'angle de rotation en degrés.
     */
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
    }

    /**
     * Récupère l'angle de rotation de la tuile.
     *
     * @return L'angle de rotation de la tuile en degrés.
     */
    public double getRotationAngle() {
        return rotationAngle;
    }

    /**
     * Récupère le premier type de terrain de la tuile.
     *
     * @return Le premier terrain de la tuile.
     */
    public TerrainType getTerrain1() {
        return terrain1;
    }

    /**
     * Récupère le deuxième type de terrain de la tuile s'il existe.
     *
     * @return Le deuxième terrain de la tuile ou null si la tuile n'a qu'un seul terrain.
     */
    public TerrainType getTerrain2() {
        return hasTwoTerrains ? terrain2 : null;
    }

    /**
     * Vérifie si la tuile possède deux types de terrains.
     *
     * @return true si la tuile a deux terrains, sinon false.
     */
    public boolean hasTwoTerrains() {
        return this.hasTwoTerrains;
    }

    /**
     * Vérifie si le premier terrain est positionné en haut ou à gauche de la tuile.
     *
     * @return true si le terrain1 est en haut ou à gauche, sinon false.
     */
    public boolean isTerrain1OnTopOrLeft() {
        return isTerrain1OnTopOrLeft;
    }
}
