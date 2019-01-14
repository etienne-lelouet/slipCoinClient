package blockchain;

public class Compte {
	private int id;
	private String numero_compte;
	private float solde;
	
	public Compte(int id, String numero_compte, float solde) {
		super();
		this.id = id;
		this.numero_compte = numero_compte;
		this.solde = solde;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero_compte() {
		return numero_compte;
	}
	public void setNumero_compte(String numero_compte) {
		this.numero_compte = numero_compte;
	}
	public float getSolde() {
		return solde;
	}
	public void setSolde(float solde) {
		this.solde = solde;
	}
	
}
