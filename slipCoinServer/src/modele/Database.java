package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String serveur, bdd, user, mdp;

    public Database(String serveur, String bdd, String user, String mdp) {
        this.serveur = serveur;
        this.bdd = bdd;
        this.user = user;
        this.mdp = mdp;
    }

    public void chargerPilote() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException exp) {
            System.out.println("Erreur de chargement du pilote JDBC");
        }
    }

    public Connection connect() throws SQLException {
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd;
        return DriverManager.getConnection(url, this.user, this.mdp);
    }
}
