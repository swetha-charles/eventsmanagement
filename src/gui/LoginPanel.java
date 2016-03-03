package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.Controller;

public class LoginPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	
	Controller controller;
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	JLabel userLabel = new JLabel("Username");
	JLabel passwordLabel = new JLabel("Password");
	JPanel userPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JButton login = new JButton("Login");
	JButton register = new JButton("Register");
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	public LoginPanel(Controller controller){
		this.controller= controller;
		
		password.setEchoChar('*');
		//sets the dimension of the login panel
		setPreferredSize(new Dimension(420,410));
		setMinimumSize(new Dimension(420,410));
		
		//Sets dimension of textFields
		Dimension size1 = new Dimension(300,18);
		username.setPreferredSize(size1);
		password.setPreferredSize(size1);
		
		//sets the dimension of the user and password panels
		Dimension size2 = new Dimension(400, 40);
		userPanel.setMaximumSize(size2);
		userPanel.setMinimumSize(size2);
		passwordPanel.setMaximumSize(size2);
		passwordPanel.setMinimumSize(size2);
		
		//sets dimension of panel containing logo
		logoPanel.setMinimumSize(new Dimension(350, 200));
		logoPanel.setMaximumSize(new Dimension(350, 200));
		logoPanel.setPreferredSize(new Dimension(400, 200));
		
		//sets background colours of panels
		setBackground(Color.DARK_GRAY);
		userPanel.setBackground(Color.DARK_GRAY);
		passwordPanel.setBackground(Color.DARK_GRAY);
		logoPanel.setBackground(Color.DARK_GRAY);
		
		//sets colour of text in JLabels
		userLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		
		//sets fonts of JLabels and JButton
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		login.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		register.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		
		//sets dimension of button
		login.setPreferredSize(new Dimension(100,40));
		register.setPreferredSize(new Dimension(100,40));
		
		//adds Labels and text fields to user and password panels
		userPanel.add(userLabel);
		passwordPanel.add(passwordLabel);
		userPanel.add(username);
		passwordPanel.add(password);
		
		//sets layout of loginPanel 
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
		//add listener to fucking register button
		register.setActionCommand("register");
		register.addActionListener(this.controller);
		
		//adds all panels and button to loginPanel
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(logoPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(userPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(passwordPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		add(login, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		add(register, gbc);
	
	}

	/** Main method to veiw the logoPanel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Controller controller = new Controller();
		
		LoginPanel loginPanel = new LoginPanel(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
