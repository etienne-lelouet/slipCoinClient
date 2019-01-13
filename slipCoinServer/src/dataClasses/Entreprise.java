package dataClasses;

public class Entreprise {
	private String nom;
	private String description;
	private String produits;
	private String position;
	private String numeroCompte;

	public Entreprise(String nom, String description, String produits,
			String position) {
		this.nom = nom;
		this.description = description;
		this.produits = produits;
		this.position = position;
	}

	public Entreprise(String nom, String description, String produits,
			String position, String numeroCompte) {
		this.nom = nom;
		this.description = description;
		this.produits = produits;
		this.position = position;
		this.numeroCompte = numeroCompte;
	}
	
}
