/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slipcoinserver;
import java.util.ArrayList;
import modele.*;
/**
 *
 * @author etien
 */
public class SlipCoinServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database db = new Database("163.172.49.216", "slipCoin", "wef", "ppe2018wef");
        System.out.println(Modele.Connexion(db, "login", "login"));
        ArrayList<String> lesEntreprises = Modele.selectAllEntreprises(db);
        for (String entreprise : lesEntreprises) {
            System.out.println(entreprise);
        }
    }
    
}
