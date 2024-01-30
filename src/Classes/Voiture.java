package Classes;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Voiture {
	private String _Marque;
	private String _Modele;
	private int _Annee_Production;
	private int _Prix_Loc;

	public Voiture(String marque, String modele, int date, int prix) {
		_Marque = marque;
		_Modele = modele;
		_Annee_Production = date;
		_Prix_Loc = prix;
	}

	public String get_Marque() {
		return _Marque;
	}

	public void set_Marque(String _Marque) {
		this._Marque = _Marque;
	}

	public String get_Modele() {
		return _Modele;
	}

	public void set_Modele(String _Modele) {
		this._Modele = _Modele;
	}

	public int get_Annee_Production() {
		return _Annee_Production;
	}
	public void set_Annee_Production(int _Annee_Production) {
		this._Annee_Production = _Annee_Production;
	}

	public int get_Prix_Loc() {
		return _Prix_Loc;
	}

	public void set_Prix_Loc(int _Prix_Loc) {
		this._Prix_Loc = _Prix_Loc;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || this.getClass() != o.getClass())
			return false;
		Voiture v = (Voiture) o;
		if ((get_Annee_Production() == v.get_Annee_Production()) && (get_Prix_Loc() == v.get_Prix_Loc())
				&& (get_Modele().equals(v.get_Modele()) ) && get_Marque().equals(v.get_Marque()))
			return true;
		return false;
	}

	public String toString() {
		return get_Marque() + "  Modele:" + get_Modele() + "  Prix: " + get_Prix_Loc();
	}
}
