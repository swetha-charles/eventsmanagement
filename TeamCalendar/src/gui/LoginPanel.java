package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

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
	JPanel userAndPasswordPanel = new JPanel();

	JPanel logoPanel = new JPanel();
	JLabel error = new JLabel("");
	JButton login = new JButton("Login");
	public static JLabel signup = new JLabel("Sign Up");
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	@SuppressWarnings({ "unchecked"})
	public LoginPanel(Client client, Model model){
		this.client= client;
		this.model = model;
//		setBorder(new OvalBorder(10, 10));
		//sets a character limit on username textfield
		AbstractDocument usernameDoc = (AbstractDocument) username.getDocument();
		if (usernameDoc instanceof AbstractDocument) {
			usernameDoc = (AbstractDocument)usernameDoc;
			usernameDoc.setDocumentFilter(new DocumentSizeFilter(20));
		} 
		//sets a character limit on the password textfield
		AbstractDocument passwordDoc = (AbstractDocument) password.getDocument();
		if (passwordDoc instanceof AbstractDocument) {
			passwordDoc = (AbstractDocument)passwordDoc;
			passwordDoc.setDocumentFilter(new DocumentSizeFilter(60));
		} 
		//sets the dimension of the login panel
		setPreferredSize(new Dimension(470,430));
		setMinimumSize(new Dimension(470,430));
				
		//Sets dimension of textFields
		Dimension size1 = new Dimension(300,30);
		username.setPreferredSize(size1);
		password.setPreferredSize(size1);
		username.setMinimumSize(size1);
		password.setMinimumSize(size1);
		username.setMaximumSize(size1);
		password.setMaximumSize(size1);
				
		//sets the dimension of the user and password panels
		Dimension size2 = new Dimension(400, 40);
		userAndPasswordPanel.setMaximumSize(size2);
		userAndPasswordPanel.setMinimumSize(size2);

		Dimension size3 = new Dimension(120, 40);
		userLabel.setMaximumSize(size3);
		userLabel.setMinimumSize(size3);
		passwordLabel.setMaximumSize(size3);
		passwordLabel.setMinimumSize(size3);
		
		//sets dimension of panel containing logo
		logoPanel.setMinimumSize(new Dimension(350, 200));
		logoPanel.setMaximumSize(new Dimension(350, 200));
		logoPanel.setPreferredSize(new Dimension(400, 200));
		
//		//sets background colours of panels
		setBackground(Color.DARK_GRAY);
		userAndPasswordPanel.setBackground(Color.DARK_GRAY);
//		passwordPanel.setBackground(Color.DARK_GRAY);
//		logoPanel.setBackground(Color.DARK_GRAY);
		
		//sets background colours of panels
//		setBackground(Color.lightGray);
//		userAndPasswordPanel.setBackground(Color.lightGray);
//		passwordPanel.setBackground(Color.lightGray);
//		logoPanel.setBackground(Color.lightGray);
		
		//set borders
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border compound = BorderFactory.createCompoundBorder(
                loweredbevel, raisedbevel);
		setBorder(compound);
		
		//sets colour of text in JLabels
		userLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		signup.setForeground(Color.WHITE);
		
		//sets fonts of JLabels and JButton
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		login.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		signup.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		Font font = signup.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		signup.setFont(font.deriveFont(attributes));
		
		//sets dimension of button
		login.setPreferredSize(new Dimension(100,40));
		
		//adds Labels and text fields to user and password panels
		userAndPasswordPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 0;
		//gbc.ipadx = 10;
		//gbc.ipady = 10;
		gbc.insets = new Insets(0,15,15,15);
		userAndPasswordPanel.add(userLabel, gbc);
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.ipadx = 0;
		//gbc.ipady = 10;
		gbc.insets = new Insets(0,0,15,15);
		userAndPasswordPanel.add(username, gbc);
		
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 1;
		//gbc.ipadx = 10;
		//gbc.ipady = 10;
		//gbc.insets = new Insets(0,0,5,0);	
		gbc.insets = new Insets(0,15,15,15);
		userAndPasswordPanel.add(passwordLabel, gbc);
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = 1;
		gbc.gridy = 1;
		//gbc.ipadx = 0;
		//gbc.ipady = 10;
		//gbc.insets = new Insets(0,0,5,0);
		gbc.insets = new Insets(0,0,15,15);
		userAndPasswordPanel.add(password, gbc);
		
		
		//sets layout of loginPanel 
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		gbc = new GridBagConstraints();
		
		//adds all panels and button to loginPanel
//		gbc.gridx = 1;
//		gbc.gridy = 0;
//		gbc.gridwidth = 3;
//		add(logoPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		//gbc.ipadx = 30;
		//gbc.ipady = 30;
		gbc.insets = new Insets(70,0,0,0);
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(userAndPasswordPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(login, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		//gbc.insets = new Insets(5,5,5,5);
		gbc.anchor = GridBagConstraints.CENTER;
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
	//tried to make the panel circular. Did not like it. 
//	@Override
//    protected void paintComponent(Graphics g) {
//        g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
//      
//    }
	
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
