package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import controller.Controller;

public class RegistrationPanel extends JPanel{
	private Controller controller = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	
	JTextField firstName = new JTextField();
	JTextField lastName = new JTextField();
	JTextField username = new JTextField();
	JTextField dob = new JTextField();
	JTextField email = new JTextField();
	JTextField password = new JTextField();
	JTextField confirm = new JTextField();
	
	JLabel firstLabel = new JLabel("First Name*");
	JLabel lastLabel = new JLabel("Last Name*");
	JLabel userLabel = new JLabel("Username*");
	JLabel dobLabel = new JLabel("Date of Birth*");
	JLabel emailLabel = new JLabel("Email*");
	JLabel passwordLabel = new JLabel("Password*");
	JLabel confirmLabel = new JLabel("Confirm Password*");
	
	JPanel firstPanel = new JPanel();
	JPanel lastPanel = new JPanel();
	JPanel userPanel = new JPanel();
	JPanel dobPanel = new JPanel();
	JPanel emailPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel confirmPanel = new JPanel();
	
	JButton submit = new JButton("Submit");
	JButton cancel = new JButton("Cancel");
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	public RegistrationPanel(Controller controller){
		this.controller = controller;
		
		//sets the dimension of the login panel
		setPreferredSize(new Dimension(400,400));
		
		//Sets dimension of textFields
		Dimension size1 = new Dimension(300,20);
		firstName.setPreferredSize(size1);
		lastName.setPreferredSize(size1);
		username.setPreferredSize(size1);
		dob.setPreferredSize(size1);
		email.setPreferredSize(size1);
		password.setPreferredSize(size1);
		confirm.setPreferredSize(size1);
		
		//sets the dimension of the user and password panels
		Dimension size2 = new Dimension(400, 70);
		firstPanel.setPreferredSize(size2);
		lastPanel.setPreferredSize(size2);
		userPanel.setPreferredSize(size2);
		dobPanel.setPreferredSize(size2);
		emailPanel.setPreferredSize(size2);
		passwordPanel.setPreferredSize(size2);
		confirmPanel.setPreferredSize(size2);
		
		//sets dimension of panel containing logo
//		logoPanel.setMinimumSize(new Dimension(350, 200));
//		logoPanel.setMaximumSize(new Dimension(350, 200));
//		logoPanel.setPreferredSize(new Dimension(400, 200));
		
		//sets background colours of panels
		setBackground(Color.DARK_GRAY);
		firstPanel.setBackground(Color.DARK_GRAY);
		lastPanel.setBackground(Color.DARK_GRAY);
		userPanel.setBackground(Color.DARK_GRAY);
		dobPanel.setBackground(Color.DARK_GRAY);
		emailPanel.setBackground(Color.DARK_GRAY);
		passwordPanel.setBackground(Color.DARK_GRAY);
		confirmPanel.setBackground(Color.DARK_GRAY);
		submit.setBackground(Color.lightGray);
		cancel.setBackground(Color.lightGray);
		
		//sets colour of text in JLabels
		firstLabel.setForeground(Color.WHITE);
		lastLabel.setForeground(Color.WHITE);
		userLabel.setForeground(Color.WHITE);
		dobLabel.setForeground(Color.WHITE);
		emailLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		confirmLabel.setForeground(Color.WHITE);
		submit.setForeground(Color.WHITE);
		cancel.setForeground(Color.WHITE);
		
		//sets fonts of JLabels and JButton
		firstLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lastLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		dobLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		emailLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		confirmLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
		
		//sets dimension of button
		submit.setPreferredSize(new Dimension(100,40));
		cancel.setPreferredSize(new Dimension(100,40));
		
		//adds Labels and text fields to user and password panels
		firstPanel.add(firstLabel);
		lastPanel.add(lastLabel);
		userPanel.add(userLabel);
		dobPanel.add(dobLabel);
		emailPanel.add(emailLabel);
		passwordPanel.add(passwordLabel);
		confirmPanel.add(confirmLabel);
		firstPanel.add(firstName);
		lastPanel.add(lastName);
		userPanel.add(username);
		dobPanel.add(dob);
		emailPanel.add(email);
		passwordPanel.add(password);
		confirmPanel.add(confirm);
		
		//sets layout of registrationPanel 
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		//adds all panels and button to loginPanel
		add(firstPanel);
		add(lastPanel);
		add(userPanel);
		add(dobPanel);
		add(emailPanel);
		add(passwordPanel);
		add(confirmPanel);
		add(submit);
		add(cancel);
		
		//sets position of logoPanel
//		layout.putConstraint(SpringLayout.WEST, logoPanel,35,
//		         SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.NORTH, logoPanel,40,
//		         SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.NORTH, emailPanel,20,
		         SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, emailPanel,50,
		         SpringLayout.WEST, this);
		
//		
		layout.putConstraint(SpringLayout.NORTH, passwordPanel,20,
		         SpringLayout.NORTH, emailPanel);
		layout.putConstraint(SpringLayout.WEST, passwordPanel,50,
		         SpringLayout.WEST, this);
		
		
		layout.putConstraint(SpringLayout.NORTH, confirmPanel,20,
		         SpringLayout.NORTH, passwordPanel);
		layout.putConstraint(SpringLayout.WEST, confirmPanel,50,
		         SpringLayout.WEST, this);
		
		
//		//sets position of userPanel
		layout.putConstraint(SpringLayout.NORTH, firstPanel,20,
		         SpringLayout.NORTH, confirmPanel);
		layout.putConstraint(SpringLayout.WEST, firstPanel,50,
		         SpringLayout.WEST, this);
//		
		
//		//sets position of passwordPanel
		layout.putConstraint(SpringLayout.NORTH, lastPanel,25,
		         SpringLayout.SOUTH, firstPanel);
		layout.putConstraint(SpringLayout.WEST, lastPanel,50,
		         SpringLayout.WEST, this);
		
//		//sets position of passwordPanel
		layout.putConstraint(SpringLayout.NORTH, userPanel,25,
		         SpringLayout.SOUTH, lastPanel);
		layout.putConstraint(SpringLayout.WEST, userPanel,36,
		         SpringLayout.WEST, this);
		
//		//sets position of passwordPanel
		layout.putConstraint(SpringLayout.NORTH, dobPanel,25,
		         SpringLayout.SOUTH, userPanel);
		layout.putConstraint(SpringLayout.WEST, dobPanel,36,
		         SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, submit,25,
		         SpringLayout.SOUTH, dobPanel);
		layout.putConstraint(SpringLayout.WEST, submit,50,
		         SpringLayout.WEST, this);
		
		layout.putConstraint(SpringLayout.NORTH, cancel,25,
		         SpringLayout.SOUTH, dobPanel);
		layout.putConstraint(SpringLayout.WEST, cancel,150,
		         SpringLayout.WEST, submit);
	
//		
//		//sets position on login button
//		layout.putConstraint(SpringLayout.WEST, login,190,
//		         SpringLayout.WEST, this);
//		layout.putConstraint(SpringLayout.NORTH, login,370,
//		         SpringLayout.NORTH, this);
	
		
		
	}

	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Controller controller = new Controller();
		RegistrationPanel loginPanel = new RegistrationPanel(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
