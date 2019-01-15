package blockchain;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import network.buffers.*;
import network.tcp.*;

public class Connecte extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 457064726799775087L;
	private JPanel contentPane;
	private JPasswordField password;
	public TCPClient tcpclient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Connecte frame = new Connecte();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	boolean check_login(String user, String pass) {
		BufferedReader br = null;
        String line1,line2 = null;
		 File f=new File("E://workspace//blockchain//src//resources"+"//sources_compte.properties");     
	     
	      
	       
	       try {
	    	    br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	           
	            for (line1 = br.readLine(); line1 != null; line1 = br.readLine()) {
	            	
	            	String[] tmp=line1.split("=");
	            	String name = tmp[1];
	            	line1=br.readLine();
	            	tmp=line1.split("=");
	            	String pw=tmp[1];
	            	if (name.toLowerCase().equals(user)&&pw.toLowerCase().equals(pass)) return true;
	            	
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		
	
		return false;
		
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
	public Connecte() {
		int tcpPort=6969;
		this.tcpclient=new TCPClient("localhost", tcpPort);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Connexion");
		lblNewLabel.setBounds(190, 39, 87, 28);
		
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("User");
		lblUsername.setBounds(81, 125, 87, 22);
		contentPane.add(lblUsername);
		
		JTextPane user = new JTextPane();
		user.setBounds(190, 125, 138, 22);
		contentPane.add(user);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 165, 87, 22);
		contentPane.add(lblPassword);
		JFrame tmp=this;
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				String username=user.getText();
				@SuppressWarnings("deprecation")
				String pass=password.getText();
				NetBuffer demandeConnexionMessage = new NetBuffer();
				demandeConnexionMessage.writeInt(1);
				demandeConnexionMessage.writeString(username);
				demandeConnexionMessage.writeString(pass);
				System.out.println(username+" "+pass);
				TCPClient tmp_client=getTcpclient();
				tmp_client.sendMessage(demandeConnexionMessage);
				sleep(1);
				//System.out.println(username+" "+pass);
				NetBuffer mess_recu;
				while ((mess_recu= tmp_client.getNewMessage()) ==null) {
					 try { Thread.sleep(1); } catch (InterruptedException e1) {
		                    e1.printStackTrace();
		            }
				}
				
				int id=mess_recu.readInt();
				System.out.println(id);
				boolean res=mess_recu.readBoolean();
				
				if (res) {
					System.out.println(username+" "+pass);
					tmp.dispose();
					Home h=new Home(tmp_client,id);
					h.setVisible(true);
					//this.setVisible(false);
					
					
					
				}
				else {
					System.out.println("faute");
					JOptionPane.showMessageDialog(null, "Compte invalide");
				}
			}
		});
		btnNewButton.setBounds(180, 219, 97, 25);
		contentPane.add(btnNewButton);
		
		password = new JPasswordField();
		password.setBounds(190, 165, 138, 22);
		contentPane.add(password);
		
		JButton btnInscrire = new JButton("S'inscrire");
		btnInscrire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tmp.dispose();
				Inscription ins=new Inscription(getTcpclient());
				ins.setVisible(true);
				
			}
		});
		btnInscrire.setBounds(357, 13, 97, 25);
		contentPane.add(btnInscrire);
		
		
		
	}
	public Connecte(TCPClient t) {
		//int tcpPort=6969;
		this.tcpclient=t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 484, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Connexion");
		lblNewLabel.setBounds(190, 39, 87, 28);
		
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("User");
		lblUsername.setBounds(81, 125, 87, 22);
		contentPane.add(lblUsername);
		
		JTextPane user = new JTextPane();
		user.setBounds(190, 125, 138, 22);
		contentPane.add(user);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 165, 87, 22);
		contentPane.add(lblPassword);
		JFrame tmp=this;
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				String username=user.getText();
				@SuppressWarnings("deprecation")
				String pass=password.getText();
				NetBuffer demandeConnexionMessage = new NetBuffer();
				demandeConnexionMessage.writeInt(1);
				demandeConnexionMessage.writeString(username);
				demandeConnexionMessage.writeString(pass);
				System.out.println(username+" "+pass);
				TCPClient tmp_client=getTcpclient();
				tmp_client.sendMessage(demandeConnexionMessage);
				sleep(1);
				//System.out.println(username+" "+pass);
				NetBuffer mess_recu;
				while ((mess_recu= tmp_client.getNewMessage()) ==null) {
					 try { Thread.sleep(1); } catch (InterruptedException e1) {
		                    e1.printStackTrace();
		            }
				}
				
				int id=mess_recu.readInt();
				System.out.println(id);
				boolean res=mess_recu.readBoolean();
				
				if (res) {
					System.out.println(username+" "+pass);
					tmp.dispose();
					Home h=new Home(tmp_client,id);
					h.setVisible(true);
					//this.setVisible(false);
					
					
					
				}
				else {
					System.out.println("faute");
					JOptionPane.showMessageDialog(null, "Compte invalide");
				}
			}
		});
		btnNewButton.setBounds(180, 219, 97, 25);
		contentPane.add(btnNewButton);
		
		password = new JPasswordField();
		password.setBounds(190, 165, 138, 22);
		contentPane.add(password);
		
		JButton btnInscrire = new JButton("S'inscrire");
		btnInscrire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tmp.dispose();
				Inscription ins=new Inscription(getTcpclient());
				ins.setVisible(true);
				
			}
		});
		btnInscrire.setBounds(357, 13, 97, 25);
		contentPane.add(btnInscrire);
		
		
		
	}
}
