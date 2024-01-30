package Classes;

public interface Critere {
	/**
	* @param v la voiture dont on teste la confirmité
	* @return true si et seulement si la voiture est conforme au
	* critère (on dit que v satisfait le critère)
	*/
	boolean estSatisfaitPar(Voiture v);
}
