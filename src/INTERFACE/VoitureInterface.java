package INTERFACE;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Classes.Agence;
import Classes.Civilite;
import Classes.Client;
import Classes.ClientLoueurExc;
import Classes.ClientLoueurExc2;
import Classes.Critere;
import Classes.CritereAnneeProd;
import Classes.CritereMarque;
import Classes.CriterePrix;
import Classes.InterCritere;
import Classes.Voiture;
import Classes.VoitureExisteExc;
import Classes.VoitureLouee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.*;

public class VoitureInterface extends JPanel {
	JTable _Table;
	static DefaultTableModel _TableModel;
	private Iterator<Voiture> IT;
	private Iterator<Voiture> IterFiltre;
	TreeSet<String> Marque;
	JComboBox<String> comboBox;
	JTextField annee =null;
	JTextField Prix =null;
	JButton ValiderCritere;
	private JButton boutonLouer;
	static Map<String, Voiture> voitures = new TreeMap<>();
	static Map<String, Client> clients = new TreeMap<>();
	static TreeSet<String> Vlouee;
	private JButton Ajouter;
	private JButton Supprimer;
	private JTextField anneeAjout;
	private JTextField PrixAjout;
	private JTextField modelAjout;
	private JTextField marqueAjout;

	VoitureInterface(Agence ag) {
		super();
		comboBox = new JComboBox<String>();
		String[] col = { "Marque", "Modele", "Année de production", "Prix" };
		_TableModel = new DefaultTableModel(col, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		IT = ag.get_LesVoitures().iterator();
		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			if (!Home2.monagence.VoitureLouee(V)) {
				voitures.put(V.toString(), V);
			}
			Object[] obj = { V.get_Marque(), V.get_Modele(), V.get_Annee_Production(), V.get_Prix_Loc() };
			_TableModel.addRow(obj);
		}
		Iterator<Client> iterClients = Agence._LesClients.iterator();
		while (iterClients.hasNext()) {
			Client C = iterClients.next();
			clients.put(C.toString(), C);
		}

		_Table = new JTable(_TableModel);
		ValiderCritere = new JButton("Filtrer");
		Ajouter = new JButton("Ajouter");
		boutonLouer = new JButton("louer");
		Supprimer = new JButton("Supprimer");

		JPanel panel = new JPanel(new BorderLayout());

		JPanel panelBoutons = new JPanel();
		panelBoutons.add(ValiderCritere);
		panelBoutons.add(Ajouter);
		panelBoutons.add(boutonLouer);
		panelBoutons.add(Supprimer);

		panel.add(panelBoutons, BorderLayout.NORTH);
		_Table = new JTable(_TableModel);

		panel.add(new JScrollPane(_Table), BorderLayout.CENTER);

		add(panel);

		_Table.setDefaultRenderer(Object.class, new Personnaliser_Lignes());

		ValiderCritere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					afficherBoiteDialogue(new JFrame());
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}
			}
		});
		Supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					afficherBoiteDialogue4(new JFrame());
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}
			}
		});

		Ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					afficherBoiteDialogue3(new JFrame());

				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}

			}
		});

		boutonLouer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					afficherBoiteDialogue2(new JFrame());
				} catch (ClientLoueurExc2 e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}
			}

		});
	}

	public void Modifier_Affichage(Iterator<Voiture> it) {
		_TableModel.setRowCount(0);
		Vlouee = new TreeSet<>();
		String[] col = { "Marque", "Modele", "Année de production", "Prix" };
		for (int row = 0; row < _TableModel.getRowCount(); row++) {
			_TableModel.removeRow(row);
		}
		Iterator<Voiture> i = it;
		while (i.hasNext()) {
			Voiture V = (Voiture) i.next();
			if (!Home2.monagence.VoitureLouee(V))
				Vlouee.add(V.get_Marque());
			Object[] obj = { V.get_Marque(), V.get_Modele(), V.get_Annee_Production(), V.get_Prix_Loc() };
			_TableModel.addRow(obj);

		}
		_Table = new JTable(_TableModel);
	}

	private class Personnaliser_Lignes extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);

			Voiture V;
			String mq, mod;
			int ann, px;
			mq = (String) _TableModel.getValueAt(row, 0);
			mod = (String) _TableModel.getValueAt(row, 1);
			ann = (int) _TableModel.getValueAt(row, 2);
			px = (int) _TableModel.getValueAt(row, 3);
			V = new Voiture(mq, mod, ann, px);
			if (Home2.monagence.VoitureLouee(V)) {
				cellComponent.setBackground(Color.orange); // Changer la couleur de fond de la ligne
				cellComponent.setForeground(Color.BLACK);
			} else {

				cellComponent.setBackground(Color.green);
				cellComponent.setForeground(Color.BLACK);
			}

			return cellComponent;
		}
	}

	private void afficherBoiteDialogue4(JFrame parentFrame) {
		JPanel panel = new JPanel(new GridLayout(3, 3));
		JLabel labelVoitures = new JLabel("Voitures");
		JComboBox<String> comboVoitures = new JComboBox<String>();

		IT = Home2.monagence.get_LesVoitures().iterator();
		voitures = new TreeMap<>();
		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			voitures.put(V.toString(), V);
		}
		if (IterFiltre != null)

		{
			voitures = new TreeMap<>();
			while (IterFiltre.hasNext()) {
				Voiture V = IterFiltre.next();
				voitures.put(V.toString(), V);
			}
		}
		Set<String> keys = voitures.keySet();
		Iterator<String> iterVoitures = keys.iterator();
		while (iterVoitures.hasNext()) {
			comboVoitures.addItem((String) iterVoitures.next());
		}

		panel.add(labelVoitures);
		panel.add(comboVoitures);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Supprimer une voiture",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			Voiture V = null;

			String selection = (String) comboVoitures.getSelectedItem();

			iterVoitures = keys.iterator();
			while (iterVoitures.hasNext()) {
				String str = (String) iterVoitures.next();
				if (str.equals(selection)) {
					V = (Voiture) voitures.get(str);
					break;
				}

			}
			Agence._LesVoitures.remove(V);
			Modifier_Affichage(Agence._LesVoitures.iterator());
		}
	}

	private void afficherBoiteDialogue3(JFrame parentFrame) {
		JPanel panel2 = new JPanel(new GridLayout(10, 2));
		panel2.add(new JLabel("Marque : "));
		marqueAjout = new JTextField("");
		panel2.add(marqueAjout);

		panel2.add(new JLabel("Modele : "));
		modelAjout = new JTextField("");
		panel2.add(modelAjout);

		panel2.add(new JLabel("Annee Production : "));
		anneeAjout = new JTextField("");
		panel2.add(anneeAjout);

		panel2.add(new JLabel("Prix : "));
		PrixAjout = new JTextField("");
		panel2.add(PrixAjout);

		add(panel2);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel2, "Ajouter une voiture",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String mq, mod;
			int an = 0, px = 0;
			if (!(marqueAjout.getText().equals("")) && !(modelAjout.getText().equals(""))
					&& !(PrixAjout.getText().equals("")) && !(anneeAjout.getText().equals(""))) {
				mq = marqueAjout.getText();
				mod = modelAjout.getText();

				an = Integer.parseInt(anneeAjout.getText());

				px = Integer.parseInt(PrixAjout.getText());

				try {
					Home2.monagence.Ajouter_Une_Voiture2(mq, mod, an, px);
					JOptionPane.showMessageDialog(null, "Voiture ajoutee avec succées!");

					Object[] obj = { mq, mod, an, px };
					VoitureInterface._TableModel.addRow(obj);
					marqueAjout.setText("");
					modelAjout.setText("");
					anneeAjout.setText("");
					PrixAjout.setText("");
				} catch (VoitureExisteExc e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			} else
				JOptionPane.showMessageDialog(null, "IL faut remplir tous les champs!!");

		}

	}

	private void afficherBoiteDialogue(JFrame parentFrame) {
		IterFiltre = null;
		JPanel panel = new JPanel(new GridLayout(20, 2));

		panel.add(new JLabel("Marque : "));
		comboBox = new JComboBox<>();
		comboBox.addItem((String) "Tous");
		IT = Home2.monagence.get_LesVoitures().iterator();
		Marque = new TreeSet<>();

		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			Marque.add(V.get_Marque());
		}
		Iterator<?> iterator = Marque.iterator();
		while (iterator.hasNext()) {
			comboBox.addItem((String) iterator.next());
		}
		panel.add(comboBox);

		panel.add(new JLabel("Annee Production : "));
		annee = new JTextField("0");
		panel.add(annee);

		panel.add(new JLabel("Prix : "));
		Prix = new JTextField("0");
		panel.add(Prix);

		add(panel);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Informations de location",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			appliquerFiltre();
		}

	}

	private void appliquerFiltre() {

		Critere Mq, ann, px;
		Home2.Ic = new InterCritere();
		if (!Prix.getText().equals("0")  && !Prix.getText().isEmpty() &&Prix.getText()!=null) {
			px = new CriterePrix(Integer.parseInt(Prix.getText()));
			Home2.Ic.addCritere(px);
		}
		if (!annee.getText().equals("0") && !annee.getText().isEmpty()  &&annee.getText()!=null) {
			ann = new CritereAnneeProd(Integer.parseInt(annee.getText()));
			Home2.Ic.addCritere(ann);
		}
		if (!comboBox.getSelectedItem().equals("Tous")) {
			String c = (String) comboBox.getSelectedItem();
			System.out.println(c);
			Mq = new CritereMarque(c);
			Home2.Ic.addCritere(Mq);
		}
		try {
			IterFiltre = Home2.monagence.Selection_InterCrit(Home2.Ic);
			IT = Home2.monagence.Selection_InterCrit(Home2.Ic);
		} catch (VoitureExisteExc e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		Modifier_Affichage(IT);
	}

	private void afficherBoiteDialogue2(JFrame parentFrame) throws ClientLoueurExc2 {
		JPanel panel = new JPanel(new GridLayout(10, 3));
		JLabel labelVoitures = new JLabel("Voitures");
		JComboBox<String> comboVoitures = new JComboBox<String>();
		JLabel labelClients = new JLabel("Clients");
		JComboBox<String> comboClients = new JComboBox<String>();

		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			if (!Home2.monagence.VoitureLouee(V))
				voitures.put(V.toString(), V);
		}
		if (IterFiltre != null) {
			voitures = new TreeMap<>();
			while (IterFiltre.hasNext()) {
				Voiture V = IterFiltre.next();
				if (!Home2.monagence.VoitureLouee(V))
					voitures.put(V.toString(), V);
			}
		}
		Set<String> keys = voitures.keySet();
		Iterator<String> iterVoitures = keys.iterator();
		while (iterVoitures.hasNext()) {
			comboVoitures.addItem((String) iterVoitures.next());
		}

		Iterator<?> iterCleints = Agence._LesClients.iterator();
		while (iterCleints.hasNext()) {
			Client C = (Client) iterCleints.next();
			if (!Home2.monagence.estLoueur(C))
				comboClients.addItem(C.toString());
		}

		panel.add(labelVoitures);
		panel.add(comboVoitures);

		panel.add(labelClients);
		panel.add(comboClients);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Informations de location",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			Voiture V = null;
			Client C = null;

			String selection = (String) comboVoitures.getSelectedItem();

			iterVoitures = keys.iterator();
			while (iterVoitures.hasNext()) {
				String str = (String) iterVoitures.next();
				if (str.equals(selection)) {
					V = (Voiture) voitures.get(str);
					break;
				}

			}

			String selectedClient = (String) comboClients.getSelectedItem();
			Set<String> keys2 = clients.keySet();
			iterCleints = keys2.iterator();
			while (iterCleints.hasNext()) {
				String str = (String) iterCleints.next();
				if (str.equals(selectedClient)) {
					C = (Client) clients.get(str);
					break;
				}

			}

			if (Home2.monagence.estLoueur(C))
				throw new ClientLoueurExc2("Le client loue deja une voiture!!");
			try {
				Home2.monagence.LoueVoiture(C, V);
			
				Object[] obj = { V.get_Marque(), V.get_Modele(), V.get_Annee_Production(), V.get_Prix_Loc(),
						C.get_Nom(), C.get_Prenom(), C.get_CIN(), C.get_Civilite() };
				LocationInterface._LTableModel.addRow(obj);
				if(IterFiltre != null)
				{
						comboBox.setSelectedItem("Tous");
				appliquerFiltre();
				}
			
				JOptionPane.showMessageDialog(null, "Voiture : " + V + " est louée avec succés ");
			} catch (VoitureExisteExc e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (VoitureLouee e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
}
