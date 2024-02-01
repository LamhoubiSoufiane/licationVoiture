package INTERFACE;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Classes.*;

public class LocationInterface extends JPanel {
	private JButton RendreVoit;
	private JComboBox<String> comboBox;
	JComboBox<String> Cli;
	private Iterator<Client> Clients;
	Iterator<Voiture> IT;
	JTable _LTable;
	static DefaultTableModel _LTableModel;
	static Map<String, Client> clients = new TreeMap<>();

	LocationInterface(Agence ag) {
		super();

		String[] col = { "Marque", "Modele", "Année de production", "Prix", "Nom", "Prenom", "CIN", "Civilite" };
		_LTableModel = new DefaultTableModel(col, 0);
		IT = ag.LesVoituresLouees();
		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			Client C = Home2.monagence.VoitureLoueeC(V);
			Object[] obj = { V.get_Marque(), V.get_Modele(), V.get_Annee_Production(), V.get_Prix_Loc(), C.get_Nom(),
					C.get_Prenom(), C.get_CIN(), C.get_Civilite() };
			_LTableModel.addRow(obj);
		}
		JPanel mainPanel = new JPanel(new BorderLayout());

		_LTable = new JTable(_LTableModel);
		mainPanel.add(new JScrollPane(_LTable), BorderLayout.CENTER);
		comboBox = new JComboBox<>();
		RendreVoit = new JButton("Rendre une voiture");
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(comboBox);
		panel.add(RendreVoit);
		mainPanel.add(panel, BorderLayout.SOUTH);

		add(mainPanel);

		comboBox.addItem((String) "Tous");

		Clients = Home2.monagence.LesClientsLoueur();
		while (Clients.hasNext()) {
			Client C = (Client) Clients.next();
			clients.put(C.toString(), C);
			comboBox.addItem((String) C.toString());
		}

		updateComboBox();

		RendreVoit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iterator<String> iterCleints;
				Client c = null;
				if (!comboBox.getSelectedItem().equals("Tous")) {
					String selectedClient = (String) comboBox.getSelectedItem();
					Set<String> keys2 = clients.keySet();
					iterCleints = keys2.iterator();
					while (iterCleints.hasNext()) {
						String str = (String) iterCleints.next();
						if (str.equals(selectedClient)) {
							c = (Client) clients.get(str);
							break;
						}
					}
					Home2.monagence.rendVoiture(c);
					JOptionPane.showMessageDialog(null, "Le client" + c.get_Nom() + "a rendu la voiture");

					updateComboBox();
				}
				ModifierAffichage();
			}
		});

	}

	private void updateComboBox() {
		comboBox.removeAllItems();
		comboBox.addItem((String) "Tous");

		Clients = Home2.monagence.LesClientsLoueur();
		while (Clients.hasNext()) {
			comboBox.addItem((String) Clients.next().toString());
		}
	}

	public void ModifierAffichage() {
		_LTableModel.setRowCount(0);
		String[] col = { "Marque", "Modele", "Année de production", "Prix", "Nom", "Prenom", "CIN", "Civilite" };
		for (int row = 0; row < _LTableModel.getRowCount(); row++) {
			_LTableModel.removeRow(row);
		}
		IT = Home2.monagence.LesVoituresLouees();
		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			Client C = Home2.monagence.VoitureLoueeC(V);
			Object[] obj = { V.get_Marque(), V.get_Modele(), V.get_Annee_Production(), V.get_Prix_Loc(), C.get_Nom(),
					C.get_Prenom(), C.get_CIN(), C.get_Civilite() };
			_LTableModel.addRow(obj);
		}
		_LTable = new JTable(_LTableModel);
	}

	public void Rendre_Voiture_Int() {
		Cli = new JComboBox<>();
		Cli.addItem((String) "Rien");
		Iterator<Client> it = Home2.monagence.LesClientsLoueur();
		while (it.hasNext()) {
			Cli.addItem((String) it.next().get_Nom());
		}
		this.add(Cli);
	}
}
