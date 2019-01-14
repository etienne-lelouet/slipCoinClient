/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import modele.Database;
import server.ApplicationServeur;

/**
 *
 * @author etien
 */
public class Main {
    public static Database db = new Database("163.172.49.216", "slipCoin", "wef", "ppe2018wef");
    public static int port = 6969;
    
    public static void main (String[] args) {
    	ApplicationServeur serveur = new ApplicationServeur(port, db);
    	serveur.start();
    }
}
