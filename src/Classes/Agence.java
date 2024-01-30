package Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Agence {
	public static ArrayList<Voiture> _LesVoitures;
	// private Hashtable<Client, Voiture> _Locations;
	public static Map<Client, Voiture> _Locations;
	public static TreeSet<Client> _LesClients;

	public Agence() {
		_LesVoitures = new ArrayList<>();
		// _Locations = new Hashtable<>();
		_Locations = new TreeMap<>();
		_LesClients = new TreeSet<>();
	}

	public Agence(FileReader file) {
		Voiture v = null;
		String Line;
		Client C = null;
		String nom = null, pren = null, cin = null, civ = null;
		BufferedReader br = null;
		Civilite cv;
		try {
			br = new BufferedReader(file);

			try {
				// _Locations = new Hashtable<>();
				_Locations = new TreeMap<>();
				_LesVoitures = new ArrayList<>();
				// _LesClients=new TreeSet<>();
				while ((Line = br.readLine()) != null) {
					StringTokenizer tokens = new StringTokenizer(Line, " ");
					try {
						v = new Voiture("", "", 0, 0);
						v.set_Marque(tokens.nextToken());
						v.set_Modele(tokens.nextToken());
						v.set_Annee_Production(Integer.parseInt(tokens.nextToken()));
						v.set_Prix_Loc(Integer.parseInt(tokens.nextToken()));
						if ((nom = tokens.nextToken()) != null) {
							pren = tokens.nextToken();
							cin = tokens.nextToken();
							civ = tokens.nextToken();
							if (civ.equals(Civilite.PREMIER.valeur))
								cv = Civilite.PREMIER;
							else if (civ.equals(Civilite.DEUXIEME.valeur))
								cv = Civilite.DEUXIEME;
							else
								cv = Civilite.TROISIEME;
							C = new Client(nom, pren, cin, cv);
							_Locations.put(C, v);
							// _LesClients.add(C);
						}
					} catch (NumberFormatException e) {
						System.out.println("probleme dans le transfere des string !!");
					} catch (NoSuchElementException e) {
					}
					_LesVoitures.add(v);
				}

			} catch (IOException e) {

			} finally {
				br.close();
			}
		} catch (IOException e) {
			System.out.println("je peut pas lire depuis Chambre.dat !!");
		}
		try {
			br = new BufferedReader(new FileReader("Clients.dat"));

			try {
				_LesClients = new TreeSet<>();
				while ((Line = br.readLine()) != null) {
					StringTokenizer tokens = new StringTokenizer(Line, " ");
					try {
						nom = tokens.nextToken();
						pren = tokens.nextToken();
						cin = tokens.nextToken();
						civ = tokens.nextToken();
						if (civ.equals(Civilite.PREMIER.valeur))
							cv = Civilite.PREMIER;
						else if (civ.equals(Civilite.DEUXIEME.valeur))
							cv = Civilite.DEUXIEME;
						else
							cv = Civilite.TROISIEME;
						C = new Client(nom, pren, cin, cv);
						_LesClients.add(C);
					} catch (NumberFormatException e) {
						System.out.println("probleme dans le transfere des string !!");
					} catch (NoSuchElementException e) {
					}
				}

			} catch (IOException e) {

			} finally {
				br.close();
			}
		} catch (IOException e) {
			System.out.println("je peut pas lire depuis clients.dat !!");
		}
	}

	public ArrayList<Voiture> get_LesVoitures() {
		return _LesVoitures;
	}

	public TreeSet<Client> get_LesClients() {
		return _LesClients;
	}

	public void Afficher_Tous_LesVoitures() {
		Iterator it = null;
		it = this.get_LesVoitures().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

	public void Ajouter_Une_Voiture() throws VoitureExisteExc {
		String mar, mod;
		int ann, prix;
		int eq = 0;
		Voiture v, vv;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Entrer la marque de la voiture : ");
		mar = scanner.nextLine();
		System.out.println("Entrer le modele de la voiture : ");
		mod = scanner.nextLine();
		System.out.println("Entrer l'année de production : ");
		ann = scanner.nextInt();
		System.out.println("Entrer le prix de location : ");
		prix = scanner.nextInt();
		v = new Voiture(mar, mod, ann, prix);
		Iterator<Voiture> it = _LesVoitures.iterator();
		if (!it.hasNext())
			_LesVoitures.add(v);
		while (it.hasNext()) {
			vv = (Voiture) it.next();
			if (vv.equals(v))
				throw new VoitureExisteExc("La voiture existe deja");

		}
		_LesVoitures.add(v);

	}

	public void Ajouter_Une_Voiture2(String mar, String mod, int ann, int prix) throws VoitureExisteExc {
		int eq = 0;
		Voiture v, vv;
		v = new Voiture(mar, mod, ann, prix);
		Iterator<Voiture> it = _LesVoitures.iterator();
		if (!it.hasNext())
			_LesVoitures.add(v);
		while (it.hasNext()) {
			vv = (Voiture) it.next();
			if (vv.equals(v))
				throw new VoitureExisteExc("La voiture existe deja");
		}
		_LesVoitures.add(v);
	}

	public void sauvegarder() {
		BufferedWriter bw = null;

		Iterator<Voiture> it = _LesVoitures.iterator();
		try {
			bw = new BufferedWriter(new FileWriter("VoituresLoc.dat"));
			try {
				while (it.hasNext()) {
					Voiture v = it.next();
					bw.write(v.get_Marque());
					bw.write(" ");
					bw.write(v.get_Modele());
					bw.write(" ");
					bw.write(Integer.toString(v.get_Annee_Production()));
					bw.write(" ");
					bw.write(Integer.toString(v.get_Prix_Loc()));
					bw.write(" ");
					Client c = VoitureLoueeC(v);
					if (c != null) {
						bw.write(c.get_Nom());
						bw.write(" ");
						bw.write(c.get_Prenom());
						bw.write(" ");
						bw.write(c.get_CIN());
						bw.write(" ");
						bw.write(c.get_Civilite());
						bw.write(" ");
						_LesClients.add(c);
					}
					bw.newLine();
				}
			} catch (IOException e) {
				System.out.println("je peut pas écrire dans le fichier !!");
			} finally {
				bw.close();
			}

		} catch (IOException e) {
			System.out.println("je peut pas écrire dans le fichier !!");
		}
		Iterator<Client> itc = _LesClients.iterator();
		try {
			bw = new BufferedWriter(new FileWriter("Clients.dat"));
			try {
				while (itc.hasNext()) {
					Client c = itc.next();
					if (c != null) {
						bw.write(c.get_Nom());
						bw.write(" ");
						bw.write(c.get_Prenom());
						bw.write(" ");
						bw.write(c.get_CIN());
						bw.write(" ");
						bw.write(c.get_Civilite());
						bw.write(" ");
					}
					bw.newLine();
				}
			} catch (IOException e) {
				System.out.println("je peut pas écrire dans le fichier clients!!");
			} finally {
				bw.close();
			}

		} catch (IOException e) {
			System.out.println("je peut pas écrire dans le fichier clients!!");
		}

	}

	public boolean supprimerClient(Client c) {
		
		if (_LesClients.remove(c))
			return true;
		return false;

	}

	public Iterator<Voiture> selectionne(Critere c) throws VoitureExisteExc {
		Iterator<Voiture> it = _LesVoitures.iterator();
		Iterator<Voiture> resultats = null;
		Voiture v;
		ArrayList<Voiture> VoitsSatisf = new ArrayList<>();
		while (it.hasNext()) {
			v = it.next();
			if (c.estSatisfaitPar(v))
				VoitsSatisf.add(v);
		}
		resultats = VoitsSatisf.iterator();
		if (!resultats.hasNext())
			throw new VoitureExisteExc("Aucune voiture ne satsifait le critère!!");
		return resultats;
	}

	public Iterator<Voiture> Selection_InterCrit(InterCritere ic) throws VoitureExisteExc {
		Iterator<Voiture> it = _LesVoitures.iterator();
		Iterator<Voiture> resultats = null;
		ArrayList<Voiture> VoitsSatisf = new ArrayList<>();
		Voiture V;
		while (it.hasNext()) {
			V = it.next();
			if (ic.estSatisfaitPar(V))
				VoitsSatisf.add(V);
		}
		resultats = VoitsSatisf.iterator();
		if (!resultats.hasNext())
			throw new VoitureExisteExc("Aucune voiture ne satsifait les critères!!");
		return resultats;
	}

	public void afficheSelection() {
		int c;
		Scanner sc = new Scanner(System.in);
		Iterator<Voiture> it;
		Voiture V;
		String crit;
		Critere cc;
		int px;
		do {
			System.out.println("1-Critere marque.");
			System.out.println("2-Critere prix.");
			System.out.println("3-Fin critere");
			c = sc.nextInt();
			switch (c) {
			case 1:
				System.out.println("Entrer le critere marque : ");
				crit = sc.next();
				cc = new CritereMarque(crit);
				try {
					it = selectionne(cc);
					while (it.hasNext()) {
						V = it.next();
						System.out.println(V);
					}
				} catch (VoitureExisteExc e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println("Entrer le critere prix : ");
				px = sc.nextInt();
				cc = new CriterePrix(px);
				try {
					it = selectionne(cc);
					while (it.hasNext()) {
						V = it.next();
						System.out.println(V);
					}
				} catch (VoitureExisteExc e) {
					System.out.println(e.getMessage());
				}

				break;
			case 3:
				System.out.println("Fin critere");
				break;
			default:
				System.out.println("Erreur du choix!!");
				break;
			}

		} while (c != 3);
	}

	public void Afficher_Voitures_CritInter(InterCritere ic) {
		Iterator<Voiture> it = _LesVoitures.iterator();
		Voiture V;
		while (it.hasNext()) {
			V = (Voiture) it.next();
			if (ic.estSatisfaitPar(V))
				System.out.println(V);
		}
	}

	public boolean ExisteVoiture(Voiture v) {
		Iterator<Voiture> it = _LesVoitures.iterator();
		Voiture vv;
		while (it.hasNext()) {
			vv = it.next();
			if (vv.equals(v))
				return true;
		}
		return false;
	}

	public Iterator<Voiture> Chercher_ParMarque(String Mq) {
		Iterator<Voiture> iter = this._LesVoitures.iterator();
		ArrayList<Voiture> result = new ArrayList<Voiture>();
		while (iter.hasNext()) {
			Voiture V = (Voiture) iter.next();
			if (V.get_Marque().equals(Mq))
				result.add(V);
		}
		return result.iterator();
	}

	public Iterator<Voiture> Chercher_ParMarque_Modele(String Mq, String Mo) {
		Iterator<Voiture> iter = this._LesVoitures.iterator();
		ArrayList<Voiture> result = new ArrayList<Voiture>();
		while (iter.hasNext()) {
			Voiture V = (Voiture) iter.next();
			if ((V.get_Marque().equals(Mq)) && (V.get_Modele().equals(Mo)))
				result.add(V);
		}
		return result.iterator();
	}

	public Iterator<Voiture> Chercher_ParMarque_Modele_Annee(String Mq, String Mo, int ann) {
		Iterator<Voiture> iter = this._LesVoitures.iterator();
		ArrayList<Voiture> result = new ArrayList<Voiture>();
		while (iter.hasNext()) {
			Voiture V = (Voiture) iter.next();
			if ((V.get_Marque().equals(Mq)) && (V.get_Modele().equals(Mo)) && (V.get_Annee_Production() == ann))
				result.add(V);
		}
		return result.iterator();
	}

	public boolean VoitureLouee(Voiture v) {
		if (this._Locations.isEmpty())
			return false;
		// Enumeration<Client> keys = _Locations.keys();
		Set<Client> keys = _Locations.keySet();
		Voiture V;
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client c = (Client) it.next();
			V = (Voiture) _Locations.get(c);
			if (V.equals(v))
				return true;
		}
		/*
		 * while (keys.hasMoreElements()) { Client c = (Client) keys.nextElement(); V =
		 * (Voiture) _Locations.get(c); if (V.equals(v)) return true; }
		 */
		return false;
	}

	public Client VoitureLoueeC(Voiture v) {
		if (this._Locations.isEmpty())
			return null;
		/*
		 * Enumeration<Client> keys = _Locations.keys(); Voiture V; while
		 * (keys.hasMoreElements()) { Client c = (Client) keys.nextElement(); V =
		 * (Voiture) _Locations.get(c); if (V.equals(v)) return c; }
		 */
		Set<Client> keys = _Locations.keySet();
		Voiture V;
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client c = (Client) it.next();
			V = (Voiture) _Locations.get(c);
			if (V.equals(v))
				return c;
		}
		return null;
	}

	public void Afficher_VoitureLouee() {
		Set<Client> keys = _Locations.keySet();
		Voiture V;
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client c = (Client) it.next();
			V = (Voiture) _Locations.get(c);
			System.out.println(V);
			System.out.println("---- Louée par le client : -----");
			System.out.println(c);
			System.out.println("----------------------------------");
		}
		/*
		 * Enumeration<Client> keys = _Locations.keys(); Voiture V; while
		 * (keys.hasMoreElements()) { Client c = (Client) keys.nextElement(); V =
		 * (Voiture) _Locations.get(c); System.out.println(V);
		 * System.out.println("---- Louée par le client : -----");
		 * System.out.println(c);
		 * System.out.println("----------------------------------"); }
		 */
	}

	public void LoueVoiture(Client c, Voiture v) throws VoitureExisteExc, VoitureLouee {
		if (!ExisteVoiture(v))
			throw new VoitureExisteExc("La voiture n existe pas dans l'agence!!");
		else if (VoitureLouee(v))
			throw new VoitureLouee("la voiture n est pas disponible pour le moment(Elle est louee)!!");
		else {
			_Locations.put(c, v);
			System.out.println("Voiture louee !!");
		}

	}

	public Voiture VoitureChoisit(Iterator<Voiture> it, int choix) {
		Voiture v;
		int cmpt = 1;
		while (it.hasNext()) {
			System.out.println("je suis dans la boucle iterator");
			v = (Voiture) it.next();
			System.out.println(v);
			if (cmpt == choix)
				return v;
			cmpt++;
		}
		return null;
	}

	public boolean estLoueur(Client c) {
		Set<Client> keys = _Locations.keySet();
		Voiture V;
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client cc = (Client) it.next();
			if (cc.equals(c))
				return true;
		}
		return false;
	}

	public void rendVoiture(Client client) {

		if (_Locations.containsKey(client)) {
			_Locations.remove(client);
			System.out.println("Voiture rendu!!");
		}

	}

	public Iterator<Voiture> LesVoituresLouees() {
		Set<Client> keys = _Locations.keySet();
		Voiture V;
		ArrayList<Voiture> Voits = new ArrayList<>();
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client c = (Client) it.next();
			V = (Voiture) _Locations.get(c);
			Voits.add(V);
		}

		return Voits.iterator();
	}

	public Iterator<Client> LesClientsLoueur() {
		Set<Client> keys = _Locations.keySet();
		return keys.iterator();
	}

	public Client Chercher_Client_Loueur(String cin) {
		Client c = null;
		Set<Client> keys = _Locations.keySet();
		Iterator<Client> it = keys.iterator();
		while (it.hasNext()) {
			Client cc = (Client) it.next();
			if (cc.get_CIN().equals(cin))
				return cc;
		}
		return c;
	}
}
