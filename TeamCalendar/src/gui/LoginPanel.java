package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

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
	JPanel passwordPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JLabel error = new JLabel("");
	JButton login = new JButton("Login");
	JLabel signup = new JLabel("Sign Up");
	JLabel logo;
	
	/** This constructor builds a login panel where the user can input
	 * their username and password.
	 */
	@SuppressWarnings({ "unchecked"})
	public LoginPanel(Client client, Model model){
		this.client= client;
		this.model = model;
		
		try {
			logo = new JLabel() {
				
				private static final long serialVersionUID = 1L;
				private Image backgroundImage = ImageIO.read(new File("eventually3.png"));
				Image scaled = backgroundImage.getScaledInstance(350, 170, Image.SCALE_DEFAULT);
				public void paint( Graphics g ) { 
				    super.paint(g);
				    g.drawImage(scaled, 0, 0, null);
				  }
				};
		} catch (IOException e) {
			// no biggie, just couldn't find a file. 
			e.printStackTrace();
		}
		
		//set size
		setPreferredSize(new Dimension(500, 420));
		setMaximumSize(new Dimension(500, 420));
		setMinimumSize(new Dimension(500, 420));
		
		Dimension size1 = new Dimension(300,30);
		username.setPreferredSize(size1);
		password.setPreferredSize(size1);
		username.setMinimumSize(size1);
		password.setMinimumSize(size1);
		username.setMaximumSize(size1);
		password.setMaximumSize(size1);
		username.setDocument(new JTextFieldLimit(20));
		password.setDocument(new JTextFieldLimit(60));
				
		//sets the dimension of the user and password panels
		Dimension size2 = new Dimension(400, 40);
		userAndPasswordPanel.setMaximumSize(size2);
		userAndPasswordPanel.setMinimumSize(size2);
		passwordPanel.setMaximumSize(size2);
		passwordPanel.setMinimumSize(size2);
		Dimension size3 = new Dimension(120, 40);
		userLabel.setMaximumSize(size3);
		userLabel.setMinimumSize(size3);
		passwordLabel.setMaximumSize(size3);
		passwordLabel.setMinimumSize(size3);
		
		//sets dimension of panel containing logo
		logoPanel.setMinimumSize(new Dimension(350, 170));
		logoPanel.setMaximumSize(new Dimension(350, 170));
		logoPanel.setPreferredSize(new Dimension(350, 170));
		logoPanel.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
		gBC.weightx = 1;
		gBC.weighty = 1;
		gBC.fill = GridBagConstraints.BOTH;
		gBC.gridwidth = 3;
		gBC.gridheight = 3;
		gBC.gridx = 0;
		gBC.gridy = 0;
		logoPanel.add(logo, gBC);
		
		//sets background colours of panels
		setBackground(Color.DARK_GRAY);
		userAndPasswordPanel.setBackground(Color.DARK_GRAY);
		passwordPanel.setBackground(Color.DARK_GRAY);
		
		//sets colour of text in JLabels
		userLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		signup.setForeground(Color.WHITE);
		
		//sets fonts of JLabels and JButton
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		login.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		signup.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		Font font = signup.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		signup.setFont(font.deriveFont(attributes));
		
		//sets dimension of button
		login.setPreferredSize(new Dimension(100,40));
		login.setMaximumSize(new Dimension(100,40));
		login.setMinimumSize(new Dimension(100,40));
		login.setBackground(new Color(255, 255, 245));
		login.setForeground(Color.DARK_GRAY);
		
		//adds Labels and text fields to user and password panels
		userAndPasswordPanel.setLayout(new GridBagLayout());
		userAndPasswordPanel.setPreferredSize(new Dimension(400, 100));
		userAndPasswordPanel.setMaximumSize(new Dimension(400, 100));
		userAndPasswordPanel.setMinimumSize(new Dimension(400, 100));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 10;
		userAndPasswordPanel.add(userLabel, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		userAndPasswordPanel.add(username, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipadx = 10;	
		userAndPasswordPanel.add(passwordLabel, gbc);
		
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.ipadx = 0;
		userAndPasswordPanel.add(password, gbc);
		
		
		//sets layout of loginPanel 
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		add(Box.createRigidArea(new Dimension(0,40)));
		add(logoPanel);
		logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(userAndPasswordPanel);
		userAndPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0,10)));
		add(login);
		login.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(signup);
		signup.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
	
	public class JTextFieldLimit extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1676065906883095552L;
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
	
}
