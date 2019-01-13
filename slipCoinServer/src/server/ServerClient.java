package server;

import network.tcp.TCPClient;

/**
 *
 * @author etienne
 */
public class ServerClient {
    public TCPClient tcpSock;
    private int ID;
    private boolean estAuthentifie = false;
    private String nomDeCompte, motDePasse;

    public ServerClient(TCPClient arg_tcpSock) {
        tcpSock = arg_tcpSock;
    }

    public boolean estActuellementAuthentifie() {
        return estAuthentifie;
    }
    public void vientDEtreAuthentifie(String arg_compte, String arg_pass) {
        nomDeCompte = arg_compte;
        motDePasse = arg_pass;
        estAuthentifie = true;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID= ID;
    }
}