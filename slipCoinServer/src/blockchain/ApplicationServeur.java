package blockchain;

import java.util.ArrayList;

import blockchain.ApplicationServeur.CustomServerClient;
import network.buffers.NetBuffer;
import network.tcp.TCPClient;
import network.tcp.TCPServer;


public class ApplicationServeur implements Runnable {
	
	/** Ecrire un message sur la console (+ rapide à écrire !)
	 * @param infoMessage message à écrire
	 */
	public static void log(String infoMessage) {
		synchronized(WriteOnConsoleClass.LOCK) { System.out.println("ApplicationServeur : " + infoMessage); } //System.out.flush();
		//System.out.println("ApplicationServeur : " + infoMessage);
	}

	public static int nextClientID = 1;
	
	class CustomServerClient {
		public TCPClient tcpSock;
		private final int ID;
		private boolean estAuthentifie = false;
		private String nomDeCompte, motDePasse;
		
		public boolean estActuellementAuthentifie() {
			return estAuthentifie;
		}
		public void vientDEtreAuthentifie(String arg_compte, String arg_pass) {
			nomDeCompte = arg_compte;
			motDePasse = arg_pass;
			estAuthentifie = true;
		}
		
		public CustomServerClient(TCPClient arg_tcpSock) {
			tcpSock = arg_tcpSock;
			ID = ApplicationServeur.nextClientID;
			ApplicationServeur.nextClientID++;
		}
	}
	
	public ArrayList<CustomServerClient> serverClientList = new ArrayList<CustomServerClient>();
	
	public boolean checkUserCredentials(String userName, String userPass) {
		
		return true;
	}
	

	public static void sleep(long millisec) {
		try { Thread.sleep(millisec); } catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	TCPServer server;
	
	@Override
	// Equivalent de la méthode :
	// public static void main(String[] args)
	// sur une réelle appication serveur.
	public void run() {
		
		
		int tcpPort = 12345;
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
				CustomServerClient servClient = new CustomServerClient(newTCPClient);
				serverClientList.add(servClient);
				
				/*
				System.out.println("Serveur : nouveau client - Liste des clients :");
				for (int i = 0; i < serverClientList.size(); i++) {
					System.out.println(serverClientList.get(i).ID);
				}*/
			}
			
			// Suppression des clients qui ne sont plus connectés
			int clientIndex = 0;
			while (clientIndex < serverClientList.size()) {
				CustomServerClient servClient = serverClientList.get(clientIndex);
				if ( ! servClient.tcpSock.isConnected() )  {
					boolean criticalErrorOccured = servClient.tcpSock.criticalErrorOccured();
					if (criticalErrorOccured) {
						log("Erreur critique sur un client, déconnexion : " + servClient.tcpSock.getCriticalErrorMessage());
					}
					servClient.tcpSock.stop(); // facultatif
					serverClientList.remove(clientIndex);
					System.out.println("Serveur : Déconnexion du client : " + servClient.ID);
					
				} else
					clientIndex++;
			}
			
			// Ecouter ce que les clients demandent
			for (clientIndex = 0; clientIndex < serverClientList.size(); clientIndex++) {
				CustomServerClient servClient = serverClientList.get(clientIndex);
				NetBuffer newMessage = servClient.tcpSock.getNewMessage();
				if (newMessage != null) {
					log("Nouveau message reçu de " + servClient.ID);
					if (! newMessage.currentData_isInt()) {
						log("ERREUR : message mal formatté.");
						// Je ne réponds rien
						//servClient.tcpSock.sendMessage(replyMessage);
					} else {
						int messageType = newMessage.readInteger();
						
						// Authentification
						if (messageType == 1) {
							String nomCompte = newMessage.readString();
							String motDePasse = newMessage.readString();
							if (checkUserCredentials(nomCompte, motDePasse)) {
								servClient.vientDEtreAuthentifie(nomCompte, motDePasse);
								// Réuss
								NetBuffer reply = new NetBuffer();
								reply.writeInt(1);
								reply.writeBool(true);
								reply.writeString("Bienvenue " + nomCompte);
								servClient.tcpSock.sendMessage(reply);
							} else {
								NetBuffer reply = new NetBuffer();
								reply.writeInt(1);
								reply.writeBool(false);
								reply.writeString("Echec de la connexion : mot de passe ou nom de compte invalide.");
								servClient.tcpSock.sendMessage(reply);
							}
						}

						// Demander son ID
						if (messageType == 2) {
							NetBuffer reply = new NetBuffer();
							reply.writeInt(2);
							reply.writeBool(servClient.estActuellementAuthentifie());
							if (servClient.estActuellementAuthentifie()) {
								reply.writeInt(servClient.ID);
							}
							servClient.tcpSock.sendMessage(reply);
						}

						// Demander son nom (amnésie power...)
						if (messageType == 3) {
							NetBuffer reply = new NetBuffer();
							reply.writeInt(3);
							reply.writeBool(servClient.estActuellementAuthentifie());
							if (servClient.estActuellementAuthentifie()) {
								reply.writeString(servClient.nomDeCompte);
							}
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
	public static void main(String[] args) {
		

		ApplicationServeur applicationServ = new ApplicationServeur();
	}
	
}


//Juste pour avoir un println thread-safe
class WriteOnConsoleClass {
	static public final Object LOCK = new Object();
}
