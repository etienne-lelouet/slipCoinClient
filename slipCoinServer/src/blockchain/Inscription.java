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

public class Inscription extends JFrame {

	private JPanel contentPane;
	TCPClient tcpclient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscription frame = new Inscription();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
	public String generate(int length)
	{
		    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres dont tu ne veux pas
		    String pass = "";
		    for(int x=0;x<length;x++)
		    {
		       int i = (int)Math.floor(Math.random() * 36); // Si tu supprimes des lettres tu diminues ce nb
		       pass += chars.charAt(i);
		    }
		    System.out.println(pass);
		    return pass;
	}
	
	
	public static void sleep(long millisec) {
		try { Thread.sleep(millisec); } catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public TCPClient getTcpclient() {
		return tcpclient;
	}


	public void setTcpclient(TCPClient tcpclient) {
		this.tcpclient = tcpclient;
	}


	/**
	 * Create the frame.
	 */
	public Inscription(TCPClient tp) {
		JFrame tmp=this;
		this.tcpclient=tp;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCrerUnCompte = new JLabel("Cr\u00E9er un Compte");
		lblCrerUnCompte.setBounds(201, 24, 138, 31);
		contentPane.add(lblCrerUnCompte);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(55, 83, 82, 16);
		contentPane.add(lblNewLabel);
		
		JTextArea user = new JTextArea();
		user.setBounds(179, 80, 149, 22);
		contentPane.add(user);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(55, 128, 56, 16);
		contentPane.add(lblPassword);
		
		JTextArea password = new JTextArea();
		password.setBounds(179, 125, 149, 22);
		contentPane.add(password);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(55, 177, 56, 16);
		contentPane.add(lblNom);
		
		JTextArea nom = new JTextArea();
		nom.setBounds(179, 174, 149, 22);
		contentPane.add(nom);
		
		JLabel lblPrnom = new JLabel("Pr\u00E9nom");
		lblPrnom.setBounds(55, 221, 56, 16);
		contentPane.add(lblPrnom);
		
		JTextArea prenom = new JTextArea();
		prenom.setBounds(179, 218, 149, 22);
		contentPane.add(prenom);
		
		JLabel lblDateDeNaissance = new JLabel("Date de naissance");
		lblDateDeNaissance.setBounds(55, 269, 131, 16);
		contentPane.add(lblDateDeNaissance);
		
		JTextArea date = new JTextArea();
		date.setBounds(179, 266, 149, 22);
		contentPane.add(date);
		
		JButton btnValide = new JButton("Valide");
		btnValide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NetBuffer demandeInscrire = new NetBuffer();
				String username=user.getText();
				String pass=password.getText();
				String name=nom.getText();
				String prename=prenom.getText();
				String date_naissance=date.getText();
				String numCompte=generate(12);
				demandeInscrire.writeInt(0);
				demandeInscrire.writeString(username);
				demandeInscrire.writeString(pass);
				demandeInscrire.writeString(name);
				demandeInscrire.writeString(prename);
				demandeInscrire.writeString(date_naissance);
				demandeInscrire.writeString(numCompte);
			
				TCPClient tmp_client=getTcpclient();
				tmp_client.sendMessage(demandeInscrire);
				sleep(1);
				NetBuffer mess_recu;
				while ((mess_recu= tmp_client.getNewMessage()) ==null) {
					 try { Thread.sleep(1); } catch (InterruptedException e1) {
		                    e1.printStackTrace();
		            }
				}
				boolean res=mess_recu.readBoolean();
				if (res) 	JOptionPane.showMessageDialog(null, "Inscription succesful !!");
				else JOptionPane.showMessageDialog(null, "Inscription unsuccesful !!");
			}
		});
		btnValide.setBounds(219, 315, 97, 25);
		contentPane.add(btnValide);
		
		JButton btnConnecte = new JButton("Connecte");
		btnConnecte.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tmp.dispose();
				Connecte c=new Connecte(getTcpclient());
				c.setVisible(true);
				
			}
		});
		btnConnecte.setBounds(422, 13, 97, 25);
		contentPane.add(btnConnecte);
	}

}
