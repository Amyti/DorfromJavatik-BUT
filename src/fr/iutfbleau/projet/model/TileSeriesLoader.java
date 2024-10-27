package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TileSeriesLoader {
    private static final String DB_URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/kabbouri";
    private static final String DB_USER = "kabbouri";
    private static final String DB_PASS = "061005";

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
