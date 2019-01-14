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
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Profil extends JFrame {

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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profil frame = new Profil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
	public static void sleep(long millisec) {
		try { Thread.sleep(millisec); } catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Profil(TCPClient tp, int ii) {
		this.tcpclient=tp;
		this.id=ii;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		NetBuffer MessRecuProfil ;
		
		while ((MessRecuProfil= this.tcpclient.getNewMessage()) ==null) {
			 try { Thread.sleep(1); } catch (InterruptedException e1) {
                    e1.printStackTrace();
            }
		}
		
		
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(80, 53, 56, 16);
		contentPane.add(lblNom);
		
		JTextArea nom = new JTextArea();
		nom.setBounds(186, 50, 176, 19);
		nom.setText(MessRecuProfil.readString());
		contentPane.add(nom);
		
		JLabel lblPrenom = new JLabel("Pr\u00E9nom");
		lblPrenom.setBounds(80, 99, 56, 16);
		contentPane.add(lblPrenom);
		
		JTextArea prenom = new JTextArea();
		prenom.setBounds(186, 96, 176, 19);
		prenom.setText(MessRecuProfil.readString());
		contentPane.add(prenom);
		
		JLabel lblDate = new JLabel("Date de Naissance");
		lblDate.setBounds(44, 141, 116, 16);
		contentPane.add(lblDate);
		
		JTextArea date = new JTextArea();
		date.setBounds(186, 138, 176, 19);
		date.setText(MessRecuProfil.readString());
		contentPane.add(date);
		
		JLabel lblSolde = new JLabel("Solde");
		lblSolde.setBounds(80, 186, 56, 16);
		contentPane.add(lblSolde);
		
		JTextArea solde = new JTextArea();
		solde.setBounds(186, 183, 176, 19);
		solde.setText(Double.toString(MessRecuProfil.readDouble()));
		contentPane.add(solde);
		
		
		JFrame tmp=this;
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(12, 15, 97, 25);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tmp.dispose();
				Home h=new Home(getTcpclient(),getId());
				h.setVisible(true);
				
			}
		});
		
	}
}
