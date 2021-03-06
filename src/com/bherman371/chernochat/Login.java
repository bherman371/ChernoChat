package com.bherman371.chernochat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Login.java
 * 
 * Defines the login behaves.
 * 
 * @author benja
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField txtName;
	private JTextField txtAddress;
	private JLabel lblAddress;
	private JLabel lblPort;
	private JTextField txtPort;
	private JLabel lblAddressDesc;
	private JLabel lblPortDesc;

	/**
	 * Creates the login frame.
	 */
	public Login() {
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(69, 62, 148, 24);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblName.setBounds(125, 39, 35, 13);
		contentPane.add(lblName);
		
		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(69, 131, 148, 24);
		contentPane.add(txtAddress);
		
		lblAddress = new JLabel("IP Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAddress.setBounds(112, 108, 65, 13);
		contentPane.add(lblAddress);
		
		lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPort.setBounds(130, 189, 29, 13);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(69, 212, 148, 24);
		contentPane.add(txtPort);
		
		lblAddressDesc = new JLabel("(eg. 192.168.0.2)");
		lblAddressDesc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAddressDesc.setBounds(92, 160, 101, 13);
		contentPane.add(lblAddressDesc);
		
		lblPortDesc = new JLabel("(eg. 8192)");
		lblPortDesc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPortDesc.setBounds(112, 240, 61, 13);
		contentPane.add(lblPortDesc);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name, address, port);
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(100, 304, 85, 24);
		contentPane.add(btnLogin);
	}
	
	/**
	 * Logs the user in.
	 */
	private void login(String name, String address, int port) {
		dispose();
		new Client(name, address, port);
	}
	
	/**
	 * Launches the login window.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
