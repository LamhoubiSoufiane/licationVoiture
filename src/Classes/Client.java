package Classes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Client implements Comparable<Client> {
	private String _Nom;
	private String _Prenom;
	private String _CIN;
	private Civilite _Civilite;

	public Client(String nm, String pr, String cn, Civilite civ) {
		this._Nom = nm;
		this._Prenom = pr;
		this._CIN = cn;
		this._Civilite = civ;
	}

	Client() {
		Scanner sc = new Scanner(System.in);
		int choix;
		boolean verif = true;
		try {
			System.out.println("Entrer le nom du client : ");
			this._Nom = sc.next();
			System.out.println("Entrer le prenom du client : ");
			this._Prenom = sc.next();
			System.out.println("Entrer le CIN du client : ");
			this._CIN = sc.next();
			System.out.println("Choisisser la civilite du client: ");
			do {
				System.out.println("1-M.");
				System.out.println("2-Mlle");
				System.out.println("3-Mme");
				choix = sc.nextInt();
				switch (choix) {
				case 1:
					this._Civilite = Civilite.PREMIER;
					verif = false;
					break;
				case 2:
					this._Civilite = Civilite.DEUXIEME;
					verif = false;
					break;
				case 3:
					this._Civilite = Civilite.TROISIEME;
					verif = false;
					break;
				default:
					System.out.println("Erreur du choix!!");
					break;
				}
			} while (verif);
		} catch (InputMismatchException e) {
			System.out.println("Il faut entrer que des chaines de caracts");
		}
	}

	public int compareTo(Client otherClient) {
		return this._CIN.compareToIgnoreCase(otherClient.get_CIN());
	}

	public String get_Nom() {
		return _Nom;
	}

	public void set_Nom(String _Nom) {
		this._Nom = _Nom;
	}

	public String get_Prenom() {
		return _Prenom;
	}

	public void set_Prenom(String _Prenom) {
		this._Prenom = _Prenom;
	}

	public String get_CIN() {
		return _CIN;
	}

	public void set_CIN(String _CIN) {
		this._CIN = _CIN;
	}

	public String get_Civilite() {
		return _Civilite.valeur;
	}

	public void set_Civilite(Civilite _Civilite) {
		this._Civilite = _Civilite;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || this.getClass() != o.getClass())
			return false;
		Client c = (Client) o;
		if ((this.get_Nom().toLowerCase().equals(c.get_Nom().toLowerCase()))
				&& (this.get_Prenom().toLowerCase().equals(c.get_Prenom().toLowerCase()))
				&& (this.get_CIN().equals(c.get_CIN())) && (this.get_Civilite().equals(c.get_Civilite())))
			return true;
		return false;
	}

	public String toString() {
		return this.get_Civilite() + " " + this.get_Nom() + " " + this.get_Prenom() + " " + this.get_CIN();
	}
}
