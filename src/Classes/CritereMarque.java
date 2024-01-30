package Classes;

public class CritereMarque implements Critere{
	private String _MarqueCritere;
	public CritereMarque(String CritM)
	{
		_MarqueCritere=CritM;
	}
	public boolean estSatisfaitPar(Voiture v) {
		if(v.get_Marque().toLowerCase().equals(_MarqueCritere.toLowerCase())) return true;
		return false;
	}
}
