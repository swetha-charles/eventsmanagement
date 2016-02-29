package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	
	JTextField username = new JTextField();
	JTextField password = new JTextField();
	JLabel userLabel = new JLabel("Username");
	JLabel passwordLabel = new JLabel("Password");
	JPanel userPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JButton login = new JButton("Login");
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	public LoginPanel(){
		
		//sets the dimension of the login panel
		setPreferredSize(new Dimension(400,400));
		
		//Sets dimension of textFields
		Dimension size1 = new Dimension(300,20);
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
		
		//sets dimension of button
		login.setPreferredSize(new Dimension(100,40));
		
		//adds Labels and text fields to user and password panels
		userPanel.add(userLabel);
		passwordPanel.add(passwordLabel);
		userPanel.add(username);
		passwordPanel.add(password);
		
		//sets layout of loginPanel 
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		//adds all panels and button to loginPanel
		add(logoPanel);
		add(userPanel);
		add(passwordPanel);
		add(login);
		
		//sets position of logoPanel
		layout.putConstraint(SpringLayout.WEST, logoPanel,35,
		         SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, logoPanel,40,
		         SpringLayout.NORTH, this);
		
		//sets position of userPanel
		layout.putConstraint(SpringLayout.NORTH, userPanel,20,
		         SpringLayout.SOUTH, logoPanel);
		layout.putConstraint(SpringLayout.WEST, userPanel,35,
		         SpringLayout.WEST, this);
		
		//sets position of passwordPanel
		layout.putConstraint(SpringLayout.NORTH, passwordPanel,25,
		         SpringLayout.SOUTH, userPanel);
		layout.putConstraint(SpringLayout.WEST, passwordPanel,36,
		         SpringLayout.WEST, this);
		
		//sets position on login button
		layout.putConstraint(SpringLayout.WEST, login,190,
		         SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, login,370,
		         SpringLayout.NORTH, this);
	
	}

	/** Main method to veiw the logoPanel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		LoginPanel loginPanel = new LoginPanel();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
