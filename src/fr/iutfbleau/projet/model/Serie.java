package fr.iutfbleau.projet.model;

/**
 * La classe Serie représente une série avec un identifiant et un nom.
 * Elle est utilisée pour identifier et manipuler des séries spécifiques.
 */
public class Serie {

    private int id;
    private String name;

    /**
     * Constructeur de la classe Serie.
     *
     * @param id L'identifiant unique de la série.
     * @param name Le nom de la série.
     */
    public Serie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Récupère l'identifiant de la série.
     *
     * @return L'identifiant de la série.
     */
    public int getId() {
        return id;
    }

    /**
     * Récupère le nom de la série.
     *
     * @return Le nom de la série.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le nom de la série sous forme de chaîne de caractères.
     *
     * @return Le nom de la série.
     */
    @Override
    public String toString() {
        return name;
    }
}
