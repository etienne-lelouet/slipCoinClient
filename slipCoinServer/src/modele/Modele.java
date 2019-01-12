package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.Connection;
import main.Main;

public class Modele {

    public static String Connexion(Database database, String username, String password) {
        int idUser = 0;
        String requete = "SELECT t1.idUser, t1.username, t1.password"
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
                return "zbebman frere ça marche pas, pas de res";
            }
            if (unRes.next()) {
                idUser = unRes.getInt("idUser");

                unRes.close();
                unStat.close();
                connexion.close();

                return Integer.toString(idUser);
            }
        } catch (SQLException exp) {
            System.out.println(requete);
            System.out.println(exp);
                return "zbebman frere ça marche pas";
        }
        return "zbebman frere ça marche pas";
    }

//    //Modele des patients
    public static ArrayList<String> selectAllEntreprises(Database database) {
        ArrayList<String> lesEntreprises = new ArrayList<String>();
        String requete = " SELECT t1.*, t2.numeroCompte FROM entreprise t1, compte t2 WHERE t1.idUser = t2.idUser";
        try {
            Connection connexion = database.connect();
            PreparedStatement unStat = connexion.prepareStatement(requete);
            ResultSet unRes = unStat.executeQuery();
            while (unRes.next()) {
                String nom = unRes.getString("nom");
                String numeroCompte = unRes.getString("numeroCompte");
                lesEntreprises.add(nom + " " + numeroCompte);
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
//
//    public static ArrayList<Commentaire> selectAllMessages(Patient unPatient) {
//        ArrayList<Commentaire> lesCommentaires = new ArrayList<Commentaire>();
//        String requete = "SELECT t1.* , t2.nom, t2.prenom"
//                + " FROM donneesmedicales t1, personne t2"
//                + " WHERE idPatient = ? AND t1.idMedecin = t2.idPersonne";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
//            unStat.setInt(1, unPatient.getIdPatient());
//            ResultSet unRes = unStat.executeQuery();
//            while (unRes.next()) {
//                int idDonneesMedicales = unRes.getInt("idDonneesMedicales");
//                int idPatient = unRes.getInt("idPatient");
//                int idMedecin = unRes.getInt("idMedecin");
//                String nom = unRes.getString("nom");
//                String prenom = unRes.getString("prenom");
//                String contenu = unRes.getString("contenu");
//                Commentaire unCommentaire = new Commentaire(idDonneesMedicales, idPatient, idMedecin, contenu, nom, prenom);
//                lesCommentaires.add(unCommentaire);
//            }
//            unStat.close();
//            unRes.close();
//            uneBdd.seDeConnceter();
//        } catch (SQLException exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return lesCommentaires;
//    }
//
//    public static Patient getPatientById(int id) {
//        Patient unPatient = new Patient();
//        String requete = "SELECT t1.*, t2.* FROM personne t1, donneesbiologiques t2 WHERE t1.idPersonne = ? AND t2.idPersonne = t1.idPersonne";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
//            unStat.setInt(1, id);
//            ResultSet unRes = unStat.executeQuery();
//            while (unRes.next()) {
//                int idPatient = unRes.getInt("idPersonne");
//                int taille = unRes.getInt("Taille");
//                String etat_civil = unRes.getString("etat_civil");
//                String nom = unRes.getString("nom");
//                String prenom = unRes.getString("prenom");
//                String date_naissance = unRes.getString("date_naissance");
//                String adresse = unRes.getString("adresse");
//                String adressecomp = unRes.getString("adressecomp");
//                String code_postal = unRes.getString("code_postal");
//                String Ville = unRes.getString("Ville");
//                String telephone = unRes.getString("telephone");
//                String email = unRes.getString("email");
//                String urlphoto = unRes.getString("urlphoto");
//                String GroupeSanguin = unRes.getString("GroupeSanguin");
//                unPatient = new Patient(idPatient, taille, nom, prenom, etat_civil, date_naissance, adresse, adressecomp, code_postal, Ville, email, telephone, urlphoto, GroupeSanguin);
//            }
//        } catch (SQLException e) {
//            System.out.println(requete);
//            System.out.println(e);
//        }
//        return unPatient;
//    }
//
//    public static int insertCommentaire(Commentaire unCommentaire) {
//        int id = 0;
//        String requete = "INSERT INTO donneesmedicales VALUES (null, ?, ?, ?);";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, unCommentaire.getContenu());
//            ps.setInt(2, unCommentaire.getIdPatient());
//            ps.setInt(3, unCommentaire.getIdMedecin());
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//
//            if (rs.next()) {
//                id = rs.getInt(1);
//            }
//            ps.close();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return id;
//    }
//
//    public static boolean verifyAuthorization(int idMessage, int idMedecin) {
//        String requete = "SELECT * FROM donneesmedicales WHERE idDonneesMedicales = ? AND idMedecin = ?;";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setInt(1, idMessage);
//            ps.setInt(2, idMedecin);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                return true;
//            }
//            uneBdd.seDeConnceter();
//        } catch (SQLException exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return false;
//    }
//
//    public static void deleteCommentaire(Commentaire unCommentaire) {
//        String requete = "DELETE FROM donneesmedicales where idDonneesMedicales = ?;";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setInt(1, unCommentaire.getIdCommentaire());
//            ps.executeUpdate();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//    }
//
//    public static void updateCommentaire(Commentaire unCommentaire) {
//        String requete = "UPDATE donneesmedicales "
//                + " SET contenu = ? where idDonneesMedicales = ?;";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setString(1, unCommentaire.getContenu());
//            ps.setInt(2, unCommentaire.getIdCommentaire());
//            ps.executeUpdate();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//    }
//
//    public static void insertOrdonnance(Ordonnance uneOrdonnance) {
//        int id = 0;
//        String requete = "INSERT INTO ordonnance VALUES (null, null, ?, ?, ?);";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setString(1, uneOrdonnance.getContenu());
//            ps.setInt(2, uneOrdonnance.getIdPatient());
//            ps.setInt(3, uneOrdonnance.getIdMedecin());
//            ps.executeUpdate();
//            ps.close();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//    }
//
//    public static ArrayList<Prescription> selectAllPrescriptions(Patient unPatient) {
//        ArrayList<Prescription> lesPrescriptions = new ArrayList<Prescription>();
//        String requete = "SELECT t1.*,t2.Nom as nomMedicament, t3.nom as nomMedecin, t3.prenom as prenomMedecin"
//                + " FROM prescrire t1, medicament t2, personne t3"
//                + " WHERE t1.idPatient = ? AND t1.idMedicament = t2.idMedicament AND t1.idMedecin = t3.idPersonne";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
//            unStat.setInt(1, unPatient.getIdPatient());
//            ResultSet unRes = unStat.executeQuery();
//            while (unRes.next()) {
//                int idPrescription = unRes.getInt("idPrescription");
//                String nomMedecin = unRes.getString("nomMedecin");
//                String prenomMedecin = unRes.getString("prenomMedecin");
//                String nomMedicament = unRes.getString("nomMedicament");
//                String dateDebut = unRes.getString("DateDebut");
//                String dateFin = unRes.getString("DateFin");
//                String commentaire = unRes.getString("commentaire");
//
//                Prescription unePrescription = new Prescription(idPrescription, nomMedecin, prenomMedecin, nomMedicament, dateDebut, dateFin, commentaire);
//                lesPrescriptions.add(unePrescription);
//            }
//            unStat.close();
//            unRes.close();
//            uneBdd.seDeConnceter();
//        } catch (SQLException exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return lesPrescriptions;
//    }
//
//    public static ArrayList<Medicament> getAllMedicaments() {
//        ArrayList<Medicament> lesMedicaments = new ArrayList<Medicament>();
//        String requete = "SELECT * FROM medicament";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement unStat = uneBdd.getMaConnexion().prepareStatement(requete);
//            ResultSet unRes = unStat.executeQuery();
//            while (unRes.next()) {
//                int idMedicament = unRes.getInt("idMedicament");
//                String nomMedicament = unRes.getString("Nom");
//
//                Medicament unMedicament = new Medicament(idMedicament, nomMedicament);
//                lesMedicaments.add(unMedicament);
//            }
//            unStat.close();
//            unRes.close();
//            uneBdd.seDeConnceter();
//        } catch (SQLException exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return lesMedicaments;
//    }
//
//    public static void insertPrescription(Prescription unePrescription) {
//        System.out.println("pat" + unePrescription.getIdPatient());
//        System.out.println("med" + unePrescription.getIdMedecin());
//        int id = 0;
//        String requete = "INSERT INTO prescrire VALUES (null, ?, ?, ?, ?, ?, ?);";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setInt(1, unePrescription.getIdMedicament());
//            ps.setInt(2, unePrescription.getIdMedecin());
//            ps.setInt(3, unePrescription.getIdPatient());
//            ps.setString(4, unePrescription.getDateDebut());
//            ps.setString(5, unePrescription.getDateFin());
//            ps.setString(6, unePrescription.getCommentaire());
//            ps.executeUpdate();
//            ps.close();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//    }
//
//    public static void updatePrescription(int idPrescription, Prescription unePrescription) {
//        String requete = "UPDATE prescrire "
//                + " SET DateDebut = ?, DateFin = ?, Commentaire = ? where idPrescription = ?;";
//        Bdd uneBdd = new Bdd();
//        try {
//            System.out.println(unePrescription.getCommentaire());
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setString(1, unePrescription.getDateDebut());
//            ps.setString(2, unePrescription.getDateFin());
//            ps.setString(3, unePrescription.getCommentaire());
//            ps.setInt(4, idPrescription);
//
//            ps.executeUpdate();
//            uneBdd.seDeConnceter();
//        } catch (Exception exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//    }
//
//    public static boolean verifyAuthorizationPrescription(int idPrescription, int idMedecin) {
//        String requete = "SELECT * FROM prescrire WHERE idPrescription = ? AND idMedecin = ?;";
//        Bdd uneBdd = new Bdd();
//        try {
//            uneBdd.seConnecter();
//            PreparedStatement ps = uneBdd.getMaConnexion().prepareStatement(requete);
//            ps.setInt(1, idPrescription);
//            ps.setInt(2, idMedecin);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                return true;
//            }
//            uneBdd.seDeConnceter();
//        } catch (SQLException exp) {
//            System.out.println("Erreur : " + requete);
//            System.out.println("Erreur : " + exp);
//        }
//        return false;
//    }

    public static String cleanString(String string) {
        string = string.replaceAll("'", "\'");
        return string;
    }

}
