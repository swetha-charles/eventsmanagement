package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;
import listener.interfaces.MouseClickedListener;
import model.Model;
import model.ModelState;

public class LoginPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	
	Client client;
	Model model;
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	JLabel userLabel = new JLabel("Username");
	JLabel passwordLabel = new JLabel("Password");
	JPanel userPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JLabel error = new JLabel("");
	JButton login = new JButton("Login");
	public static JLabel signup = new JLabel("Sign Up");
	JPanel input = new JPanel();
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	@SuppressWarnings({ "unchecked"})
	public LoginPanel(Client client, Model model){
		this.client= client;
		this.model = model;
		//sets the dimension of the login panel
		setPreferredSize(new Dimension(420,410));
		setMinimumSize(new Dimension(420,410));
				
		//Sets dimension of textFields
		Dimension size1 = new Dimension(300,18);
		username.setPreferredSize(size1);
		password.setPreferredSize(size1);
		username.setMinimumSize(size1);
		password.setMinimumSize(size1);
		username.setMaximumSize(size1);
		password.setMaximumSize(size1);
				
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
		input.setBackground(Color.DARK_GRAY);
		
		//sets colour of text in JLabels
		userLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		signup.setForeground(Color.WHITE);
		
		//sets fonts of JLabels and JButton
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		login.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		signup.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		Font font = signup.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		signup.setFont(font.deriveFont(attributes));
		
		//sets dimension of button
		login.setPreferredSize(new Dimension(100,40));
		
		//adds Labels and text fields to user and password panels
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(10);
		input.setLayout(grid);
		userPanel.add(userLabel);
		passwordPanel.add(passwordLabel);
		userPanel.add(username);
		passwordPanel.add(password);
		input.add(passwordPanel);
		input.add(userPanel);
		
		//sets layout of loginPanel 
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
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
		add(signup, gbc);
		
		//---------------------------------Lambda Listeners------------------------------------------------------//
		
		signup.addMouseListener((MouseClickedListener) (e) -> this.model.changeCurrentState(ModelState.REGISTRATION));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
					model.login(username.getText(), password.getPassword());
			}
		});
		
		//--------------------------------passwordEnd Lambda Listeners--------------------------------------------------//
	
	}
	
	public JLabel getError() {
		return error;
	}

	public void setError(JLabel error) {
		error.setText("Incorrect username or password");
		error.setForeground(Color.RED);
		this.error = error;
	}
	

	/** Main method to veiw the logoPanel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Client client = new Client();
		Model model = new Model(client);
		LoginPanel loginPanel = new LoginPanel(client, model);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
