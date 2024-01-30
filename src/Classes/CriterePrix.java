package Classes;

public class CriterePrix implements Critere{
	private int _PrixCrit;
	public CriterePrix(int px)
	{
		_PrixCrit=px;
	}
	public boolean estSatisfaitPar(Voiture v) {
		if(v.get_Prix_Loc() < _PrixCrit) return true;
		return false;
	}
}
