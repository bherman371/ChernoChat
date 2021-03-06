package com.bherman371.chernochat;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Client.java
 * 
 * Determines how the client behaves.
 * 
 * @author benja
 */
public class Client extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtMessage;
	private JTextArea history;
	
	private String name, address;
	private int port;
	
	// Socket is TCP; DatagramSocket is UDP
	private DatagramSocket socket; // sending and receiving packets
	private InetAddress ip; // ip address
	private Thread send;

	/**
	 * Creates the client.
	 */
	public Client(String name, String address, int port) {
		setTitle("Cherno Chat Client");
		this.name = name;
		this.address = address;
		this.port = port;
		
		boolean connect = openConnection(address);
		if (!connect) {
			System.err.println("Connection failed.");
			console("Connection failed.");
		}
		createWindow();
		
		console("Attempting a connection to " + address + ":" + port + ", user: " + name);
		String connection = name + " connected from " + address + ":" + port;
		send(connection.getBytes());
	}
	
	/**
	 * Returns whether the client can connect to the server.
	 * @param address
	 * @param port
	 * @return
	 */
	private boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Receives packets.
	 * @return
	 */
	private String receive() {
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = new String(packet.getData());
		return message;
	}
	
	/**
	 * Sends packets.
	 * @param data
	 */
	private void send(final byte[] data) {
		send = new Thread("Send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		send.start();
	}
	
	/**
	 * Creates the client window.
	 */
	private void createWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// layout for the client window
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{28, 815, 30, 7}; // SUM = 880
		gbl_contentPane.rowHeights = new int[]{35, 475, 40}; // SUM = 550
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// text history area
		history = new JTextArea();
		history.setFont(new Font("Arial", Font.PLAIN, 12));
		history.setEditable(false);
		JScrollPane scroll = new JScrollPane(history);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.insets = new Insets(0, 0, 5, 5);
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 0;
		scrollConstraints.gridy = 0;
		scrollConstraints.gridwidth = 3;
		scrollConstraints.gridheight = 2;
		scrollConstraints.insets = new Insets(0, 5, 0, 0); // top, left, bottom, right
		contentPane.add(scroll, scrollConstraints);
		
		// text input field
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText());
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		// send button
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		setVisible(true);
		
		txtMessage.requestFocusInWindow();
	}
	
	/**
	 * Sends a message in the text history.
	 * @param message
	 */
	private void send(String message) {
		if (message.equals("")) return;
		message = name + ": " + message;
		console(message);
		send(message.getBytes());
		txtMessage.setText("");
	}
	
	/**
	 * Prints messages to the text history.
	 * @param message
	 */
	public void console(String message) {
		history.append(message + "\n\r");
		history.setCaretPosition(history.getDocument().getLength());
	}

}
