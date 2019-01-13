package dataClasses;

public class Transaction {
	int idTransaction;
	int idCrediteur;
	int idDebiteur;
	String numeroCompteCrediteur;
	String numeroCompteDebiteur;
	float valeur;
	
	public Transaction(int idTransaction, int idCrediteur, int idDebiteur, String numeroCompteCrediteur,
			String numeroCompteDebiteur, float valeur) {
		this.idTransaction = idTransaction;
		this.idCrediteur = idCrediteur;
		this.idDebiteur = idDebiteur;
		this.numeroCompteCrediteur = numeroCompteCrediteur;
		this.numeroCompteDebiteur = numeroCompteDebiteur;
		this.valeur = valeur;
	}
	
	public Transaction(String numeroCompteCrediteur,
			String numeroCompteDebiteur, float valeur) {
		this.numeroCompteCrediteur = numeroCompteCrediteur;
		this.numeroCompteDebiteur = numeroCompteDebiteur;
		this.valeur = valeur;
	}

}
