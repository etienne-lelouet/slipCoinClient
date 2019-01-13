package server;

import java.util.ArrayList;

import dataClasses.User;
import modele.Modele;
import network.buffers.NetBuffer;
import network.tcp.TCPClient;
import network.tcp.TCPServer;
import modele.Database;
import server.ServerClient;

public class ApplicationServeur {
    public int tcpPort;
    public Database db;

    /** Ecrire un message sur la console (+ rapide à écrire !)
     * @param infoMessage message à écrire
     */
    public static void log(String infoMessage) {
            synchronized(WriteOnConsoleClass.LOCK) { System.out.println("ApplicationServeur : " + infoMessage); } //System.out.flush();
            //System.out.println("ApplicationServeur : " + infoMessage);
    }

    public static int nextClientID = 1;

    public ArrayList<ServerClient> serverClientList = new ArrayList<ServerClient>();

    public static void sleep(long millisec) {
            try { Thread.sleep(millisec); } catch (InterruptedException e1) {
                    e1.printStackTrace();
            }
    }

    TCPServer server;

    public ApplicationServeur (int tcpPort, Database db) {
        this.tcpPort = tcpPort;
        this.db = db;
    }
    
    public void start() {
        server = new TCPServer(tcpPort);
        if (server.isListening()) {
                log("Le serveur écoute sur le port " + tcpPort);
        } else {
                server.stop();
                return;
        }

        // Boucle du serveur
        while (server.isListening()) {
            // Accepter de nouveaux clients (asynchrone)
            TCPClient newTCPClient = server.accept(); // non bloquant
            if (newTCPClient != null) {
                // Nouveau client accepté !
                // Je crée le client du serveur
                ServerClient client = new ServerClient(newTCPClient);
                serverClientList.add(client);

                /*
                System.out.println("Serveur : nouveau client - Liste des clients :");
                for (int i = 0; i < serverClientList.size(); i++) {
                        System.out.println(serverClientList.get(i).ID);
                }*/
            }

            // Suppression des clients qui ne sont plus connectés
            int clientIndex = 0;
            while (clientIndex < serverClientList.size()) {
                ServerClient servClient = serverClientList.get(clientIndex);
                if ( ! servClient.tcpSock.isConnected() )  {
                    boolean criticalErrorOccured = servClient.tcpSock.criticalErrorOccured();
                    if (criticalErrorOccured) {
                            log("Erreur critique sur un client, déconnexion : " + servClient.tcpSock.getCriticalErrorMessage());
                    }
                    servClient.tcpSock.stop(); // facultatif
                    serverClientList.remove(clientIndex);
                } else
                    clientIndex++;
            }

            // Ecouter ce que les clients demandent
            for (clientIndex = 0; clientIndex < serverClientList.size(); clientIndex++) {
                    ServerClient servClient = serverClientList.get(clientIndex);
                    NetBuffer newMessage = servClient.tcpSock.getNewMessage();
                    if (newMessage != null) {
                        if (! newMessage.currentData_isInt()) {
                                log("ERREUR : message mal formatt�.");
                        } else {
                            int messageType = newMessage.readInteger();
                            NetBuffer reply = new NetBuffer();
                            switch(messageType){
                                case '1':
                                    String username = newMessage.readString();
                                    String password = newMessage.readString();
                                    User user = Modele.Connexion(db, username, password);
                                    
                                    if (user != null) { 
                                        reply.writeInt(1);
                                        reply.writeBool(true);
                                        reply.writeString("Bienvenue " + username);
                                        servClient.tcpSock.sendMessage(reply);  
                                    } else {
                                        reply.writeInt(1);
                                        reply.writeBool(false);
                                        reply.writeString("couple login/mot de passe inconnu");
                                        servClient.tcpSock.sendMessage(reply);
                                    }
                                    break;
                                case '2':
                                    break;
                                default:
                                    reply.writeInt(9);
                                    reply.writeString("error");
                                    reply.writeString("Id Du message invalide");
                                    servClient.tcpSock.sendMessage(reply);
                            }

                        }
                    }
            }
            sleep(1); // 1ms entre chaque itération, minimum
        }

    }
	
	public void forceStop() {
		if (server == null) return;
		server.stop();
	}
	
}

class WriteOnConsoleClass {
	static public final Object LOCK = new Object();
}