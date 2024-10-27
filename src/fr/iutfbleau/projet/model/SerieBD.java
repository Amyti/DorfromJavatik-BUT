package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SerieBD {


    public SerieBD() {
    }

    public List<Serie> getSeries() {
        List<Serie> seriesList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(
            "jdbc:mariadb://dwarves.iut-fbleau.fr/kabbouri",
            "kabbouri", "061005");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM series")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                seriesList.add(new Serie(id, name));
            }

        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
        return seriesList;
    }
}
