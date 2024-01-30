package Classes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws VoitureExisteExc, VoitureLouee {
		Agence ag = null;
		FileReader myFile = null;
		Scanner scanner = new Scanner(System.in);
		String marq, mod;
		Voiture V = null;
		InterCritere ic = null;
		Client c = null;
		Iterator<Voiture> it;
		int voit;
		int cmpt = 1;
		int ann, prx;
		try {
			myFile = new FileReader("VoituresLoc.dat");
			ag = new Agence(myFile);
		} catch (FileNotFoundException e) {
			System.out.println("je peut pas lire depuis VoituresLoc.dat !!");
			ag = new Agence();
		}
		int choix;
		do {
			System.out.println("1-Ajouter une voiture");
			System.out.println("2-Afficher tous les voitures");
			System.out.println("3-Sauvegarder");
			System.out.println("4-Afficher selection par critere.");
			// System.out.println("5-Creer un critere intersection.");
			System.out.println("6-Afficher voitures satisfaisant critInter.");
			System.out.println("7-Louer une voiture");
			System.out.println("8-Afficher les voitures louee");
			System.out.println("9-Rendre une voiture");
			System.out.println("10-Quitter");
			choix = scanner.nextInt();
			switch (choix) {
			case 1:
				try {
					ag.Ajouter_Une_Voiture();
				} catch (InputMismatchException e) {
					System.out.println("erreur d'entree des donnees");
				} catch (VoitureExisteExc e) {
					System.out.println(e.getMessage());
				}

				break;
			case 2:
				ag.Afficher_Tous_LesVoitures();
				break;
			case 3:
				ag.sauvegarder();
				break;
			case 4:
				try {
					ag.afficheSelection();
				} catch (InputMismatchException e) {
					System.out.println("Donnee invalide");
				}
				break;
			case 5:

				break;
			case 6:
				try {
					ic = new InterCritere();
					ag.Afficher_Voitures_CritInter(ic);
				} catch (InputMismatchException e) {
					System.out.println("Donnee invalide!!");
				}
				break;
			case 7:
				try {
					cmpt = 1;
					Client C = new Client();
					if (ag.estLoueur(C))
						throw new ClientLoueurExc("Le client loue deja une voiture!!");
					ic = new InterCritere();
					it = ag.Selection_InterCrit(ic);
					int i = 1;
					System.out.println("Les voitures disponibles dans l agence : ");
					while (it.hasNext()) {
						V = (Voiture) it.next();
						System.out.println((i++) + "---" + V);
					}
					voit = scanner.nextInt();
					// V=ag.VoitureChoisit(it2,voit);
					it = ag.Selection_InterCrit(ic);
					Voiture v2;
					while (it.hasNext()) {
						// System.out.println("je suis dans la boucle iterator");
						v2 = (Voiture) it.next();
						if (cmpt == voit)
							V = v2;
						cmpt++;
					}
					System.out.println("-------------------------");
					System.out.println("Voiture choisit : ");
					System.out.println(V);
					if (V != null) {
						try 
						{
							ag.LoueVoiture(C, V);
						}catch (VoitureExisteExc e) {
							System.out.println(e.getMessage());
						}catch (VoitureLouee e) {
							System.out.println(e.getMessage());
						}
					}

				} catch (ClientLoueurExc e) {
					System.out.println(e.getMessage());
				} catch (VoitureExisteExc e) {
					System.out.println(e.getMessage());
				}
				break;
			case 8:
				it = ag.LesVoituresLouees();
				while (it.hasNext()) {
					V = (Voiture) it.next();
					System.out.println("--------------------------------");
					System.out.println(V);
					System.out.println("-------------------------------------- Louée par le client : ----");
					c = ag.VoitureLoueeC(V);
					System.out.println(c);
				}
				break;
			case 9:
				Client cli = new Client();
				ag.rendVoiture(cli);
				break;
			case 10:
				System.out.println("FIN PROGRAMME!!");
				break;
			}
		} while (choix != 10);
	}

}
