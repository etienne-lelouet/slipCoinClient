package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dataClasses.Compte;
import dataClasses.Entreprise;
import dataClasses.Personne;
import dataClasses.User;

import java.sql.PreparedStatement;
import java.sql.Connection;
import main.Main;

public class Modele {
	/**
	 *
	 * retourne un objet de type User avec les données de la database si les identifiants sont corrects
	 * retourne un User vide si les identifiants sont incorrects, 
	 */
    public static User Connexion(Database database, String username, String password) {
        String requete = "SELECT t1.idUser, t1.username, t1.userType"
                + " FROM users t1"
                + " WHERE username = ? AND password = ?";
        
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setString(1, username);
            unStat.setString(2, password);
            ResultSet unRes = unStat.executeQuery();
            unRes.last();
            int rows = unRes.getRow();
            unRes.beforeFirst();
            if (rows != 1) {
                return null;
            }
            if (unRes.next()) {
                User user = new User(unRes.getString("username"), unRes.getInt("idUser"), unRes.getInt("userType"));
                unRes.close();
                unStat.close();
                connexion.close();
                return user;
            }
        } catch (SQLException exp) {
            System.out.println(requete);
            System.out.println(exp);
        }
		return null;
    }
    
    public static Personne SelectionnerPersonne(Database database, int idPersonne) {
        String requete = "SELECT t1.*"
                + " FROM personne t1"
                + " WHERE idUser = ?";
        
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setInt(1, idPersonne);
            ResultSet unRes = unStat.executeQuery();
            unRes.last();
            int rows = unRes.getRow();
            unRes.beforeFirst();
            if (rows != 1) {
                return null;
            }
            if (unRes.next()) {
                Personne personne = new Personne(unRes.getString("nom"), unRes.getString("prenom"), unRes.getString("dateNaissance"));
                unRes.close();
                unStat.close();
                connexion.close();
                return personne;
            }
        } catch (SQLException exp) {
            System.out.println(requete);
            System.out.println(exp);
        }
		return null;
    }
    
    public static Entreprise SelectionnerEntreprise(Database database, int idEntreprise) {
        String requete = "SELECT t1.*"
                + " FROM entreprise t1, compte t2"
                + " WHERE t1.idUser = ?";
        
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setInt(1, idEntreprise);
            ResultSet unRes = unStat.executeQuery();
            unRes.last();
            int rows = unRes.getRow();
            unRes.beforeFirst();
            if (rows != 1) {
                return null;
            }
            if (unRes.next()) {
                Entreprise entreprise = new Entreprise(unRes.getString("nom"), unRes.getString("description"), 
                		unRes.getString("produits"), unRes.getString("position"));
                unRes.close();
                unStat.close();
                connexion.close();
                return entreprise;
            }
        } catch (SQLException exp) {
            System.out.println(requete);
            System.out.println(exp);
        }
		return null;
    }

	/**
	 *
	 * retourne la liste des entreprise, avec leur numero de compte pour faire des transactions
	 */

    public static ArrayList<Entreprise> selectAllEntreprises(Database database) {
        ArrayList<Entreprise> lesEntreprises = new ArrayList<Entreprise>();
        String requete = " SELECT t1.*, t2.numeroCompte FROM entreprise t1, compte t2 WHERE t1.idUser = t2.idUser";
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            ResultSet unRes = unStat.executeQuery();
            while (unRes.next()) {
                String nom = unRes.getString("nom");
                String description = unRes.getString("description");
                String produits = unRes.getString("produits");
                String position = unRes.getString("position");
                String numeroCompte = unRes.getString("numeroCompte");
                lesEntreprises.add(new Entreprise(nom, description, produits, position, numeroCompte));
            }
            unStat.close();
            unRes.close();
            connexion.close();
        } catch (SQLException exp) {
            System.out.println("Erreur : " + requete);
            System.out.println("Erreur : " + exp);
        }
        return lesEntreprises;
    }

    public static int insertPersonne(Database database, String username, String password, String nom, String prenom, 
    		String date_naissance, String numeroCompte) {
        String requete = "call insertPersonne(?, ?, ?, ?, ?, ?)";
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setString(1, username);
            unStat.setString(2, password);
            unStat.setString(3, nom);
            unStat.setString(4, prenom);
            unStat.setString(5, date_naissance);
            unStat.setString(6, numeroCompte);
            ResultSet unRes = unStat.executeQuery();
            while (unRes.next()) {
                int id = unRes.getInt("id");
            }
            unStat.close();
            unRes.close();
            connexion.close();
        } catch (SQLException exp) {
            System.out.println("Erreur : " + requete);
            System.out.println("Erreur : " + exp);
            return 0;
        }
        return 1;
    }

    public static int insertEntreprise(Database database, String username, String password, String nom, String description, 
    		String produits, String position, String numeroCompte) {
        String requete = "call insertPersonne(?, ?, ?, ?, ?, ?)";
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setString(1, username);
            unStat.setString(2, password);
            unStat.setString(3, nom);
            unStat.setString(4, description);
            unStat.setString(5, produits);
            unStat.setString(6, position);
            unStat.setString(7, numeroCompte);
            ResultSet unRes = unStat.executeQuery();
            int id = 0;
            while (unRes.next()) {
                id = unRes.getInt("id");
            }
            unStat.close();
            unRes.close();
            connexion.close();
            return id;
        } catch (SQLException exp) {
            System.out.println("Erreur : " + requete);
            System.out.println("Erreur : " + exp);
            return 0;
        }
    }
	public static boolean effectuerTransaction(Database database, String numeroCompteDebiteur, String numeroCompteCrediteur, 
			float montant){
        String requete = "call transaction(?, ?, ?)";
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setString(1, numeroCompteDebiteur);
            unStat.setString(2, numeroCompteCrediteur);
            unStat.setFloat(3, montant);
            ResultSet unRes = unStat.executeQuery();
            unStat.close();
            unRes.close();
            connexion.close();
            return true;
        } catch (SQLException exp) {
            System.out.println("Erreur : " + requete);
            System.out.println("Erreur : " + exp);
            return false;
        }
    }
	
    public static Compte SelectionnerCompte(Database database, int idUser) {
        String requete = "SELECT t1.*"
                + " FROM compte t1"
                + " WHERE idUser = ?";
        
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            unStat.setInt(1, idUser);
            ResultSet unRes = unStat.executeQuery();
            unRes.last();
            int rows = unRes.getRow();
            unRes.beforeFirst();
            if (rows != 1) {
                return null;
            }
            if (unRes.next()) {
                Compte compte = new Compte(unRes.getString("numeroCompte"), unRes.getFloat("solde"), unRes.getInt("idUser"));
                unRes.close();
                unStat.close();
                connexion.close();
                return compte;
            }
        } catch (SQLException exp) {
            System.out.println(requete);
            System.out.println(exp);
        }
		return null;
    }

    public static String cleanString(String string) {
        string = string.replaceAll("'", "\'");
        return string;
    }

}
