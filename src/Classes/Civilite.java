package Classes;

public enum Civilite {
	PREMIER("M."),DEUXIEME("Mlle"),TROISIEME("Mme");
	public final String valeur;
	Civilite(String val) {
		this.valeur=val;
	}
}
