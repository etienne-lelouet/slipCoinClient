package dataClasses;

public class Compte {
	String numeroCompte;
	float solde;
	int idUser;
	
	public Compte(String numeroCompte, float solde, int idUser) {
		super();
		this.numeroCompte = numeroCompte;
		this.solde = solde;
		this.idUser = idUser;
	}
	public Compte(String numeroCompte, float solde) {
		super();
		this.numeroCompte = numeroCompte;
		this.solde = solde;
	}
}
