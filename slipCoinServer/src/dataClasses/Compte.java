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
	public String getNumeroCompte() {
		return numeroCompte;
	}
	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
}
