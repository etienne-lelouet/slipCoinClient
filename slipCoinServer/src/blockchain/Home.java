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

import javax.swing.JButton;

public class Home extends JFrame {

	private JPanel contentPane;
	public TCPClient tcpclient;
	int id;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	public Home(TCPClient t,int ii) {
		this.tcpclient=t;
		this.id=ii;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConsulterProfil = new JButton("Consulter profil");
		btnConsulterProfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NetBuffer demandeConsulterMessage = new NetBuffer();
				demandeConsulterMessage.writeInt(2);
				demandeConsulterMessage.writeInt(getId());
				TCPClient tmp_client=getTcpclient();
				tmp_client.sendMessage(demandeConsulterMessage);
				
			}
		});
		btnConsulterProfil.setBounds(60, 34, 190, 25);
		contentPane.add(btnConsulterProfil);
		
		JButton btnConsulterDesTransactions = new JButton("Consulter des transactions");
		btnConsulterDesTransactions.setBounds(60, 108, 190, 25);
		contentPane.add(btnConsulterDesTransactions);
		
		JButton btnEffectuerUneTransaction = new JButton("Effectuer une transaction");
		btnEffectuerUneTransaction.setBounds(60, 177, 190, 25);
		contentPane.add(btnEffectuerUneTransaction);
		
	}

}
