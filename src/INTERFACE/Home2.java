package INTERFACE;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import Classes.Agence;
import Classes.Client;
import Classes.Critere;
import Classes.CritereAnneeProd;
import Classes.CritereMarque;
import Classes.CriterePrix;
import Classes.InterCritere;
import Classes.Voiture;
import Classes.VoitureExisteExc;

public class Home2 extends JFrame implements ActionListener {

	public static JPanel monpanel;
	private JPanel panelButton;
	private JButton ButtVoiture;
	private JButton ButtClient;
	private JButton ButtLocation;
	private CardLayout CardLayout;
	private JPanel CardPanel;
	private VoitureInterface pVoit;
	private ClientInterface pCli;
	private LocationInterface pLoc;
	public static Agence monagence;
	private Iterator<Voiture> IT;
	public static TreeSet<String> Marque;

	private JButton Sauvegarder;

	private JButton VoitureLouee;
	private JButton LoueeVoit;


	public static InterCritere Ic;

	Home2(Agence ag) {
		super("Location");
		monagence = ag;

		IT = monagence.get_LesVoitures().iterator();
		Marque = new TreeSet<>();

		while (IT.hasNext()) {
			Voiture V = (Voiture) IT.next();
			Marque.add(V.get_Marque());
		}

		monpanel = new JPanel(new BorderLayout());

		panelButton = new JPanel(new GridLayout(1, 3)); // Change to GridLayout with 1 row and 3 columns

		ButtVoiture = new JButton("Voiture");
		ButtClient = new JButton("Client");
		ButtLocation = new JButton("Location");

		ButtVoiture.addActionListener(this);
		ButtClient.addActionListener(this);
		ButtLocation.addActionListener(this);

		panelButton.add(ButtVoiture);
		panelButton.add(ButtClient);
		panelButton.add(ButtLocation);

		monpanel.add(panelButton, BorderLayout.NORTH); // Add to the top of the panel

		/**********************************************/
		CardLayout = new CardLayout();
		CardPanel = new JPanel(CardLayout);

		pVoit = new VoitureInterface(ag);
		pCli = new ClientInterface(ag);
		pLoc = new LocationInterface(ag);

		CardPanel.add(pVoit, "Voiture");
		CardPanel.add(pCli, "Client");
		CardPanel.add(pLoc, "Locations");
		ButtVoiture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout.show(CardPanel, "Voiture");
			}
		});

		ButtClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout.show(CardPanel, "Client");
			}
		});

		ButtLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout.show(CardPanel, "Locations");
			}
		});

		monpanel.add(CardPanel, BorderLayout.CENTER); // Add to the center of the panel

		JPanel panelSauvgarder = new JPanel(new GridLayout(1, 2)); // Change to GridLayout with 1 row and 2 columns
		Sauvegarder = new JButton("Sauvegarder");
		Sauvegarder.addActionListener(this);
		panelSauvgarder.add(Sauvegarder);
		

		monpanel.add(panelSauvgarder, BorderLayout.SOUTH); // Add to the bottom of the panel
		ButtVoiture.setPreferredSize(new Dimension(300, 40));
		ButtClient.setPreferredSize(new Dimension(300, 40));
		ButtLocation.setPreferredSize(new Dimension(300, 40));
		Sauvegarder.setPreferredSize(new Dimension(1000, 40));
		

		this.add(monpanel);

		this.addWindowListener(new Fermer());

		this.setSize(1000, 600);
		this.setVisible(true);

	}

	class Fermer extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			monagence.sauvegarder();
			System.exit(0);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.Sauvegarder) {
			monagence.sauvegarder();
		}
	}

}
