package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String serveur, bdd, user, mdp;

    public Database(String serveur, String bdd, String user, String mdp) {
    	System.out.println("test");
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
        String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        System.out.println(url);
        return DriverManager.getConnection(url, this.user, this.mdp);
    }
}
