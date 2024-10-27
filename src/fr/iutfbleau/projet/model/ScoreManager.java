package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static final String DB_URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/kabbouri";
    private static final String DB_USER = "kabbouri";
    private static final String DB_PASS = "061005";

    public static void saveScore(int score, int seriesId) {
        String query = "INSERT INTO scores (score, series_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, score);
            stmt.setInt(2, seriesId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getScoresBySeries(int seriesId) {
        List<Integer> scores = new ArrayList<>();
        String query = "SELECT score FROM scores WHERE series_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, seriesId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                scores.add(rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
