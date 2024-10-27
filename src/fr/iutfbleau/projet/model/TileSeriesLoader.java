package fr.iutfbleau.projet.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe TileSeriesLoader est responsable de charger une série de tuiles depuis
 * une base de données en fonction de l'identifiant d'une série spécifique.
 */
public class TileSeriesLoader {

    private static final String DB_URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/kabbouri";
    private static final String DB_USER = "kabbouri";
    private static final String DB_PASS = "061005";

    /**
     * Charge une série de tuiles depuis la base de données en fonction de l'ID de la série.
     * Pour chaque tuile, crée un objet {@link Tile} avec les types de terrains et le ratio de séparation.
     *
     * @param seriesId L'ID de la série dont les tuiles doivent être chargées.
     * @return Une liste de {@link Tile} représentant les tuiles de la série.
     */
    public static List<Tile> loadSeries(int seriesId) {
        List<Tile> tiles = new ArrayList<>();
        
        String query = """
            SELECT t.id, tt1.name AS terrain1_name, tt2.name AS terrain2_name, t.split_ratio
            FROM tiles t
            INNER JOIN series_tiles st ON st.tile_id = t.id
            LEFT JOIN terrain_types tt1 ON t.terrain1_id = tt1.id
            LEFT JOIN terrain_types tt2 ON t.terrain2_id = tt2.id
            WHERE st.series_id = ?
        """;
    
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seriesId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String terrain1Name = rs.getString("terrain1_name");
                String terrain2Name = rs.getString("terrain2_name"); 
                double splitRatio = rs.getDouble("split_ratio");

                Tile.TerrainType terrain1 = Tile.TerrainType.valueOf(terrain1Name.toUpperCase());
                Tile tile;

                if (terrain2Name == null) {
                    tile = new Tile(terrain1);
                } else {
                    Tile.TerrainType terrain2 = Tile.TerrainType.valueOf(terrain2Name.toUpperCase());
                    tile = new Tile(terrain1, terrain2, splitRatio);
                }

                tiles.add(tile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiles;
    }
}
