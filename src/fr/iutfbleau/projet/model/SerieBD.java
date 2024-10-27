package fr.iutfbleau.projet.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe SerieBD gère l'interaction avec la base de données pour récupérer les informations
 * des séries et les convertir en objets de type {@link Serie}.
 */
public class SerieBD {

    /**
     * Constructeur par défaut de la classe SerieBD.
     */
    public SerieBD() {
    }

    /**
     * Récupère la liste de toutes les séries depuis la base de données.
     * Chaque série est convertie en un objet {@link Serie}.
     *
     * @return Une liste de {@link Serie} représentant toutes les séries dans la base de données.
     */
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
