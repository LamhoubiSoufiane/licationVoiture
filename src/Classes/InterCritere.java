package Classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class InterCritere {
	ArrayList<Critere> _LesCriteres;

	public InterCritere() {
		Scanner sc = new Scanner(System.in);
		Critere c;
		String crit;
		int critpxA;
		int choix;
		_LesCriteres = new ArrayList<>();
		/*do {
			System.out.println("1-Critere marque");
			System.out.println("2-Critere prix");
			System.out.println("3-Critere annee production");
			System.out.println("4-FIN");
			choix = sc.nextInt();
			switch (choix) {
			case 1:
				System.out.println("Entrer le critere marque : ");
				crit = sc.next();
				c = new CritereMarque(crit);
				this.addCritere(c);
				break;
			case 2:
				System.out.println("Entrer le critere prix : ");
				critpxA = sc.nextInt();
				c = new CriterePrix(critpxA);
				this.addCritere(c);
				break;
			case 3:
				System.out.println("Entrer le critere annee : ");
				critpxA = sc.nextInt();
				c = new CriterePrix(critpxA);
				this.addCritere(c);
				break;
			case 4:
				System.out.println("FIN");
				break;
			}
		} while (choix != 4);*/

	}

	public void addCritere(Critere c) {
		_LesCriteres.add(c);
	}

	public boolean estSatisfaitPar(Voiture v) {
		Iterator<Critere> it = _LesCriteres.iterator();
		while (it.hasNext()) {
			Critere c = it.next();
			if (!c.estSatisfaitPar(v))
				return false;
		}
		return true;
	}

}
