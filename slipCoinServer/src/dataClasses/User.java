package dataClasses;

public class User {
	private String username;
	private int idUser;
	private int type; //1 = personne, 2 = entreprise

	public User(int idUser, String username, int type) {
		this.username = username;
		this.idUser = idUser;
		this.type = type;
	}	

	public User(String username, int idUser, int type) {
		this.username = username;
		this.idUser = idUser;
		this.type = type;
	}	
	public User(String username, int type) {
		this.username = username;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	
}
