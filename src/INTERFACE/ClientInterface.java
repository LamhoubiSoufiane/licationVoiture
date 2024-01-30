package INTERFACE;

import Classes.*;

import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

public class ClientInterface extends JPanel {
	JPanel _Cpanel;
	Agence monagence;
	JTable _CTable;
	static DefaultTableModel _CTableModel;
	JButton btnAjouter, btnSupprimer, btnModifier;
	Iterator<Client> IT;

	ClientInterface(Agence ag) {
		super();
		this.monagence = ag;
		this.setLayout(new BorderLayout());

		_Cpanel = new JPanel();
		String[] col = { "Nom", "Prenom", "CIN", "Civilité" };
		_CTableModel = new DefaultTableModel(col, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		IT = ag.get_LesClients().iterator();
		while (IT.hasNext()) {
			Client c = (Client) IT.next();
			Object[] obj = { c.get_Nom(), c.get_Prenom(), c.get_CIN(), c.get_Civilite() };
			_CTableModel.addRow(obj);
		}
		_CTable = new JTable(_CTableModel);
		add(_CTable, BorderLayout.NORTH);

		this.add(_Cpanel);

		JPanel boutonsPanel = new JPanel();
		btnAjouter = new JButton("Ajouter");
		btnSupprimer = new JButton("Supprimer");
		btnModifier = new JButton("Modifier");

		boutonsPanel.add(btnAjouter);
		boutonsPanel.add(btnSupprimer);
		boutonsPanel.add(btnModifier);
		this.add(boutonsPanel);
		btnAjouter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					afficherBoiteDialogue(new JFrame());
				} catch (ClientLoueurExc2 e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}
			}

		});
		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					afficherBoiteDialogue2(new JFrame());
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "veuillez verifier les formats des champs !!");
				}
			}

		});
	}

	private static void afficherBoiteDialogue(JFrame parentFrame) throws ClientLoueurExc2 {
		JPanel panel = new JPanel(new GridLayout(4, 3));
		JLabel labelNom = new JLabel("Nom : ");
		JTextField textNom = new JTextField();
		JLabel labelPrenom = new JLabel("Prénom : ");
		JTextField textPrenom = new JTextField();
		JLabel labelCin = new JLabel("CIN : ");
		JTextField textCin = new JTextField();
		JLabel labelCivilite = new JLabel("Civilité : ");
		JComboBox<String> comboCivilite = new JComboBox<String>();
		comboCivilite.addItem("M.");
		comboCivilite.addItem("Mlle");
		comboCivilite.addItem("Mme");

		panel.add(labelNom);
		panel.add(textNom);
		panel.add(labelPrenom);
		panel.add(textPrenom);
		panel.add(labelCin);
		panel.add(textCin);
		panel.add(labelCivilite);
		panel.add(comboCivilite);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Ajouter un client",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String nom, prenom, cin;
			Civilite civilite;
			if (comboCivilite.getSelectedItem().equals("M."))
				civilite = Civilite.PREMIER;
			else if (comboCivilite.getSelectedItem().equals("Mlle"))
				civilite = Civilite.DEUXIEME;
			else
				civilite = Civilite.TROISIEME;
			nom = textNom.getText();
			prenom = textPrenom.getText();
			cin = textCin.getText();
			Agence._LesClients.add(new Client(nom, prenom, cin, civilite));
			JOptionPane.showMessageDialog(null, "Client est ajouté avec succés ");
			Object[] obj = { nom, prenom, cin, comboCivilite.getSelectedItem() };
			_CTableModel.addRow(obj);

		}
	}

	public void Modifier_Affichage() {
		String[] col = { "Nom", "Prenom", "CIN", "Civilité" };

		_CTableModel.setRowCount(0);
		for (int row = 0; row < _CTableModel.getRowCount(); row++) {
			_CTableModel.removeRow(row);
		}
		IT = Home2.monagence.get_LesClients().iterator();
		while (IT.hasNext()) {
			Client c = (Client) IT.next();
			Object[] obj = { c.get_Nom(), c.get_Prenom(), c.get_CIN(), c.get_Civilite() };
			_CTableModel.addRow(obj);
		}
		_CTable = new JTable(_CTableModel);
	}

	private void afficherBoiteDialogue2(JFrame parentFrame) {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		JLabel labelClients = new JLabel("Clients");
		JComboBox<String> comboClients = new JComboBox<String>();
		Iterator<?> iterCleints = Agence._LesClients.iterator();
		while (iterCleints.hasNext()) {
			Client C = (Client) iterCleints.next();
			comboClients.addItem(C.toString());
		}

		panel.add(labelClients);
		panel.add(comboClients);

		int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Supprimer un client",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			Client C = null;
			String selectedClient = (String) comboClients.getSelectedItem();
			Set<String> keys2 = VoitureInterface.clients.keySet();
			iterCleints = keys2.iterator();
			while (iterCleints.hasNext()) {
				String str = (String) iterCleints.next();
				if (str.equals(selectedClient)) {
					C = (Client) VoitureInterface.clients.get(str);
					break;
				}
			}
			if (C != null) {
				if (Home2.monagence.estLoueur(C))
					JOptionPane.showMessageDialog(null, "Client est loueur !! ");
				else {
					if (Home2.monagence.supprimerClient(C))
					{
						JOptionPane.showMessageDialog(null, "Client est supprimé avec succés ");
						Modifier_Affichage();
					}

						
					
					else
						JOptionPane.showMessageDialog(null, "il y a un problème ");
				}

			} else
				JOptionPane.showMessageDialog(null, "il y a un problème ");

		}
	}
}
