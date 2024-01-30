package Classes;

public class CritereAnneeProd implements Critere{
		private int _AnneeCritere;
		public CritereAnneeProd(int CritA)
		{
			_AnneeCritere=CritA;
		}
		public boolean estSatisfaitPar(Voiture v) {
			if(v.get_Annee_Production()<=_AnneeCritere) return true;
			return false;
		}
}
