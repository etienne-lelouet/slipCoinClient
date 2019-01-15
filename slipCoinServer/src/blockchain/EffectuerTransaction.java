package blockchain;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import network.buffers.NetBuffer;
import network.tcp.TCPClient;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class EffectuerTransaction extends JFrame {

	private JPanel contentPane;
	TCPClient tcpclient;
	int id;

	public TCPClient getTcpclient() {
		return tcpclient;
	}

	public void setTcpclient(TCPClient tcpclient) {
		this.tcpclient = tcpclient;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static void sleep(long millisec) {
		try {
			Thread.sleep(millisec);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try {
		 * EffectuerTransaction frame = new EffectuerTransaction();
		 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
		 */
	}

	/**
	 * Create the frame.
	 */
	public EffectuerTransaction(TCPClient tp, int ii) {
		this.tcpclient = tp;
		this.id = ii;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel compte_credit = new JLabel("Compte Cr\u00E9dit");
		compte_credit.setBounds(58, 76, 97, 16);
		contentPane.add(compte_credit);

		JTextArea numCompte = new JTextArea();
		numCompte.setBounds(176, 73, 190, 22);
		contentPane.add(numCompte);

		JLabel Montant = new JLabel("Montant");
		Montant.setBounds(71, 136, 77, 16);
		contentPane.add(Montant);

		JTextArea valueMontant = new JTextArea();
		valueMontant.setBounds(176, 133, 190, 22);
		contentPane.add(valueMontant);

		JButton btnValide = new JButton("Valide");
		btnValide.setBounds(172, 194, 97, 25);
		contentPane.add(btnValide);
		JFrame tmp=this;
		btnValide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String compteCredit = numCompte.getText();
				double m = Double.parseDouble(valueMontant.getText());
				NetBuffer demandeTransaction = new NetBuffer();
				demandeTransaction.writeInt(4);
				demandeTransaction.writeInt(getId());
				demandeTransaction.writeString(compteCredit);
				demandeTransaction.writeDouble(m);
				TCPClient tmp_client=getTcpclient();
				tmp_client.sendMessage(demandeTransaction);
				sleep(1);
				NetBuffer mess_recu;
				while ((mess_recu= tmp_client.getNewMessage()) ==null) {
					 try { Thread.sleep(1); } catch (InterruptedException e1) {
		                    e1.printStackTrace();
		            }
				}
				boolean recu=mess_recu.readBoolean();
				if (recu) 	JOptionPane.showMessageDialog(null, "Transaction succesful !!");
				else JOptionPane.showMessageDialog(null, "Transaction unsuccesful !!");
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(12, 13, 97, 25);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tmp.dispose();
				Home h = new Home(getTcpclient(), getId());
				h.setVisible(true);

			}
		});
	}
}
