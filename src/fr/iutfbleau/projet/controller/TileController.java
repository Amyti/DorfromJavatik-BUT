package fr.iutfbleau.projet.controller;

import fr.iutfbleau.projet.model.*;
import fr.iutfbleau.projet.model.Tile.TerrainType;
import fr.iutfbleau.projet.vue.*;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;   
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * La classe TileController gère la logique de placement des tuiles, le calcul des scores,
 * et les actions lors de la fin de partie dans une grille de tuiles hexagonales.
 */
public class TileController {

    private TileView tileView;
    public int score = 0;
    private int tilesPlacedCount = 0; 
    private final int MAX_TILES = 50;

    /**
     * Constructeur de la classe TileController.
     * @param tileView La vue des tuiles, utilisée pour mettre à jour l'affichage et les positions disponibles.
     */
    public TileController(TileView tileView) {
        this.tileView = tileView;
    }

    /**
     * Place une tuile à une position spécifique et met à jour le score si des critères sont remplis.
     * @param x La coordonnée X où placer la tuile.
     * @param y La coordonnée Y où placer la tuile.
     * @param tuileAPlacer La tuile à placer.
     */
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

    /**
     * Signale la fin de la partie en calculant le score final et en appelant la méthode de fin de jeu.
     */
    public void signalerFinDeJeu() {
        int ScoreFinal = calculerScore();
        tileView.finDeJeu(ScoreFinal); 
    }

    /**
     * Calcule les points pour chaque tuile placée en comparant ses côtés avec les tuiles adjacentes.
     * @param positionNouvelleTuile La position de la nouvelle tuile.
     * @param nouvelleTuile La nouvelle tuile à laquelle attribuer des points.
     */
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

    /**
     * Compare les côtés de deux tuiles pour voir si leurs terrains sont compatibles.
     * @param tuile1 La première tuile.
     * @param tuile2 La deuxième tuile.
     * @param posTuile1 La position de la première tuile.
     * @param posTuile2 La position de la deuxième tuile.
     * @return true si les côtés sont compatibles, sinon false.
     */
    private boolean comparerCotes(Tile tuile1, Tile tuile2, Point posTuile1, Point posTuile2) {
        int coteTuile1 = obtenirCote(posTuile1, posTuile2);
        int coteOpposeTuile2 = (coteTuile1 + 3) % 6;

        TerrainType terrainCoteTuile1 = obtenirTerrainCote(tuile1, coteTuile1);
        TerrainType terrainCoteTuile2 = obtenirTerrainCote(tuile2, coteOpposeTuile2);

        return terrainCoteTuile1 != null && terrainCoteTuile1.equals(terrainCoteTuile2);
    }

    /**
     * Compare les terrains étendus de deux tuiles et attribue des points si compatibles.
     * @param tuile1 La première tuile.
     * @param tuile2 La deuxième tuile.
     * @return true si un point a été gagné, sinon false.
     */
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

    /**
     * Calcule le côté de la tuile en fonction des coordonnées de deux points.
     * @param posTuile1 Position de la première tuile.
     * @param posTuile2 Position de la deuxième tuile.
     * @return Le côté de la tuile sous forme d'entier.
     */
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

    /**
     * Obtient le terrain d'un côté spécifique d'une tuile en prenant en compte la rotation.
     * @param tuile La tuile pour laquelle obtenir le terrain.
     * @param cote Le côté de la tuile.
     * @return Le terrain du côté demandé.
     */
    private TerrainType obtenirTerrainCote(Tile tuile, int cote) {
        int rotation = (int) tuile.getRotationAngle();
        int coteAvecRotation = (cote + rotation / 60) % 6;

        if (tuile.hasTwoTerrains()) {
            return (coteAvecRotation < 3) ? tuile.getTerrain1() : tuile.getTerrain2();
        }
        return tuile.getTerrain1();
    }

    /**
     * Retourne le centre de l'hexagone sous le clic si trouvé dans les positions disponibles.
     * @param x La coordonnée X du clic.
     * @param y La coordonnée Y du clic.
     * @param positionsDisponibles Les positions disponibles pour le placement.
     * @return Le centre de l'hexagone sous le clic ou null si aucun hexagone trouvé.
     */
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

    /**
     * Obtient la dernière position de la tuile placée dans la vue des tuiles.
     * @return La position de la dernière tuile placée.
     */
    public Point getLastTileCenter() {
        return tileView.getLastTilePosition();
    }

    /**
     * Vérifie si une tuile est déjà présente dans une liste de positions prises.
     * @param position La position de la tuile à vérifier.
     * @param positionsPrises La liste des positions déjà prises.
     * @return true si la tuile est déjà présente, sinon false.
     */
    public boolean tuileDejaPresente(Point position, List<Point> positionsPrises) {
        for (Point prise : positionsPrises) {
            if (position.equals(prise)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcule le score en fonction de l'agencement des tuiles.
     * @return Le score calculé.
     */
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

    /**
     * Ajoute une tuile aux poches de terrains en regroupant les tuiles adjacentes.
     * @param terrain Le type de terrain de la tuile.
     * @param position La position de la tuile.
     * @param pochesParTerrain Les poches de terrains.
     */
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

    /**
     * Retourne le score actuel.
     * @return Le score actuel.
     */
    public int getScore() {
        System.out.println("Instance TileController dans getScore : " + this);
        System.out.println("Score actuel dans getScore() : " + score);
        return score;
    }

    /**
     * Calcule les positions disponibles pour le placement d'une tuile autour d'une tuile existante.
     * @param centreTuile La position centrale de la tuile.
     * @param positionsPrises Les positions déjà prises.
     * @return Une liste des positions disponibles pour le placement.
     */
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

    /**
     * Obtient un type de terrain aléatoire parmi les types de terrains disponibles.
     * @return Un type de terrain aléatoire.
     */
    private TerrainType getRandomTerrainType() {
        TerrainType[] types = TerrainType.values();
        Random random = new Random();
        return types[random.nextInt(types.length)];
    }
}
