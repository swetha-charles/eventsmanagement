package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import gui.LoginPanel.JTextFieldLimit;
import listener.interfaces.FocusLostListener;
import model.Model;
import model.ModelState;

/** This class builds a JPanel which lets the user input details and register
 * 
 * @author nataliemcdonnell
 *
 */
public class RegistrationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	private Client client = null;
	private Model model = null;
	JTextField firstName = new JTextField(1);
	JTextField lastName = new JTextField(1);
	JTextField username = new JTextField(1);
	JTextField date = new JTextField(1);
	JTextField month = new JTextField(1);
	JTextField year = new JTextField(1);
	JTextField email = new JTextField(1);
	JPasswordField password = new JPasswordField();
	JPasswordField confirm = new JPasswordField();

	JLabel firstLabel = new JLabel("First Name*");
	JLabel lastLabel = new JLabel("Last Name*");
	JLabel userLabel = new JLabel("Username*");
	JLabel dobLabel = new JLabel("Date of Birth* dd/mm/yyyy");
	JLabel emailLabel = new JLabel("Email*");
	JLabel passwordLabel = new JLabel("Password*");
	JLabel confirmLabel = new JLabel("Confirm Password*");
	JLabel compulsary = new JLabel("* compulsary field");

	JPanel firstPanel = new JPanel();
	JPanel lastPanel = new JPanel();
	JPanel userPanel = new JPanel();
	JPanel dobPanel = new JPanel();
	JPanel emailPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel confirmPanel = new JPanel();
	JPanel fields = new JPanel();

	JButton submit = new JButton("Submit");
	JButton cancel = new JButton("Cancel");
	String dayInput = "";
	String monthInput = "";
	String yearInput = "";
	JLabel logo;
	JPanel logoPanel = new JPanel();

	/** Constructor to build registrationPanel
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 */
	public RegistrationPanel(Client client, Model model) {
		this.client = client;
		this.model = model;
		
		try {
			logo = new JLabel() {
				
				private static final long serialVersionUID = 1L;
				private Image backgroundImage = ImageIO.read(new File("eventually3.png"));
				Image scaled = backgroundImage.getScaledInstance(200, 100, Image.SCALE_DEFAULT);
				public void paint( Graphics g ) { 
				    super.paint(g);
				    g.drawImage(scaled, 0, 0, null);
				  }
				};
		} catch (IOException e) {
			// no biggie, just couldn't find a file. 
			e.printStackTrace();
		}
		
		// sets the dimension of the login panel
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(800, 500));
		
		username.setDocument(new JTextFieldLimit(20));
		password.setDocument(new JTextFieldLimit(60));
		confirm.setDocument(new JTextFieldLimit(60));
		firstName.setDocument(new JTextFieldLimit(30));
		lastName.setDocument(new JTextFieldLimit(30));
		email.setDocument(new JTextFieldLimit(30));

		// sets the dimension of the user and password panels
		Dimension size2 = new Dimension(350, 50);
		firstPanel.setPreferredSize(size2);
		lastPanel.setPreferredSize(size2);
		userPanel.setPreferredSize(size2);
		dobPanel.setPreferredSize(size2);
		emailPanel.setPreferredSize(size2);
		passwordPanel.setPreferredSize(size2);
		confirmPanel.setPreferredSize(size2);
		fields.setPreferredSize(size2);

		// sets background colours of panels
		setBackground(Color.DARK_GRAY);
		firstPanel.setBackground(Color.DARK_GRAY);
		lastPanel.setBackground(Color.DARK_GRAY);
		userPanel.setBackground(Color.DARK_GRAY);
		dobPanel.setBackground(Color.DARK_GRAY);
		emailPanel.setBackground(Color.DARK_GRAY);
		passwordPanel.setBackground(Color.DARK_GRAY);
		confirmPanel.setBackground(Color.DARK_GRAY);
		fields.setBackground(Color.DARK_GRAY);

		// sets colour of text in JLabels
		firstLabel.setForeground(Color.WHITE);
		lastLabel.setForeground(Color.WHITE);
		userLabel.setForeground(Color.WHITE);
		dobLabel.setForeground(Color.WHITE);
		emailLabel.setForeground(Color.WHITE);
		passwordLabel.setForeground(Color.WHITE);
		confirmLabel.setForeground(Color.WHITE);
		compulsary.setForeground(Color.WHITE);

		// sets fonts of JLabels and JButton
		firstLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		lastLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		userLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		dobLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		emailLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		confirmLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		compulsary.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));

		// sets dimension of button
		submit.setPreferredSize(new Dimension(100, 40));
		cancel.setPreferredSize(new Dimension(100, 40));
		submit.setForeground(Color.DARK_GRAY);
		submit.setBackground(new Color(255, 255, 245));
		cancel.setForeground(Color.DARK_GRAY);;
		cancel.setBackground(new Color(255, 255, 245));

		JPanel dob = new JPanel();
		JLabel empty = new JLabel();
		GridLayout grid2 = new GridLayout(1, 3);
		grid2.setHgap(30);
		dob.setLayout(grid2);
		dob.setBackground(Color.DARK_GRAY);
		date.setDocument(new JTextFieldLimit(2));
		month.setDocument(new JTextFieldLimit(2));
		year.setDocument(new JTextFieldLimit(4));
		dob.add(date);
		dob.add(month);
		dob.add(year);
		dob.add(empty);

		GridLayout grid = new GridLayout(2, 1);
		firstPanel.setLayout(grid);
		lastPanel.setLayout(grid);
		userPanel.setLayout(grid);
		dobPanel.setLayout(grid);
		emailPanel.setLayout(grid);
		passwordPanel.setLayout(grid);
		confirmPanel.setLayout(grid);

		// adds Labels and text fields to user and password panels
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

		// sets layout of registrationPanel
		GridLayout layout = new GridLayout(4, 2);
		layout.setVgap(10);
		layout.setHgap(5);
		fields.setPreferredSize(new Dimension(700, 240));
		fields.setLayout(layout);

		// adds all panels and button to loginPanel
		fields.add(firstPanel);
		fields.add(lastPanel);
		fields.add(userPanel);
		fields.add(dobPanel);
		fields.add(emailPanel);
		fields.add(passwordPanel);
		fields.add(confirmPanel);
		fields.add(compulsary);

		SpringLayout mainlayout = new SpringLayout();
		setLayout(mainlayout);

		logoPanel.setMinimumSize(new Dimension(200, 100));
		logoPanel.setMaximumSize(new Dimension(200, 100));
		logoPanel.setPreferredSize(new Dimension(200, 100));
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
		
		add(logoPanel);
		add(fields);
		add(submit);
		add(cancel);

		// sets  position of logo
		mainlayout.putConstraint(SpringLayout.NORTH, logoPanel, 40, SpringLayout.NORTH, this);
		mainlayout.putConstraint(SpringLayout.WEST, logoPanel, 280, SpringLayout.WEST, this);
		
		// sets position of userPanel
		mainlayout.putConstraint(SpringLayout.NORTH, fields, 150, SpringLayout.NORTH, this);
		mainlayout.putConstraint(SpringLayout.WEST, fields, 40, SpringLayout.WEST, this);

		// sets position of passwordPanel
		mainlayout.putConstraint(SpringLayout.NORTH, submit, 25, SpringLayout.SOUTH, fields);
		mainlayout.putConstraint(SpringLayout.WEST, submit, 290, SpringLayout.WEST, this);

		// sets position of passwordPanel
		mainlayout.putConstraint(SpringLayout.NORTH, cancel, 25, SpringLayout.SOUTH, fields);
		mainlayout.putConstraint(SpringLayout.WEST, cancel, 5, SpringLayout.EAST, submit);

		// ------- Lambda Listeners----------//
		firstName.addFocusListener((FocusLostListener) (e) -> {
			this.model.validateFirstName(firstName.getText());
		});

		lastName.addFocusListener((FocusLostListener) (e) -> {
			this.model.validateLastName(lastName.getText());
		});

		username.addFocusListener((FocusLostListener) (e) -> this.model.checkUsername(username.getText()));

		date.addFocusListener((FocusLostListener) (e) -> {
			if (date.getText().length() == 0) {
				this.dobLabel.setText("DOB: fill in date");
				this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			} else {
				try {
					Integer.parseInt(date.getText());
				} catch (NumberFormatException e2) {
					this.dobLabel.setText("DOB: input numbers only for date");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					return;
				}
				if (date.getText().length() == 1) {
					this.dobLabel.setText("Date Of Birth* dd/mm/yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					this.dayInput = "0" + date.getText();
				} else if (date.getText().length() == 2) {
					this.dobLabel.setText("Date Of Birth* dd/mm/yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					this.dayInput = date.getText();
				}
			}

		});

		month.addFocusListener((FocusLostListener) (e) -> {
			if (month.getText().length() == 0) {
				this.dobLabel.setText("DOB: fill in month");
				this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			} else {
				try {
					Integer.parseInt(month.getText());
				} catch (NumberFormatException e2) {
					this.dobLabel.setText("DOB: input numbers only for month");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					return;
				}
				if (month.getText().length() == 1) {
					this.dobLabel.setText("Date Of Birth* dd/mm/yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					this.monthInput = "0" + month.getText(); // if user only
																// puts one a
																// digit

				} else if (month.getText().length() == 2) {
					this.dobLabel.setText("Date Of Birth* dd/mm/yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					this.monthInput = month.getText();
				}
			}

		});

		year.addFocusListener((FocusLostListener) (e) -> {
			if (year.getText().length() == 0) {
				this.dobLabel.setText("DOB: fill in year");
				this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			} else {
				try {
					Integer.parseInt(year.getText());
				} catch (NumberFormatException e2) {
					this.dobLabel.setText("DOB: fill in numbers only for year");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					return;
				}
				if (year.getText().length() == 1 || year.getText().length() == 2 || year.getText().length() == 3) {
					// if user only puts one a digit
					this.dobLabel.setText("DOB: fill in valid year, format yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
				} else if (year.getText().length() == 4) {
					this.yearInput = year.getText();
					this.dobLabel.setText("Date Of Birth* dd/mm/yyyy");
					this.model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
					this.model.validateDOB(dayInput + "/" + monthInput + "/" + yearInput);
				}

			}

		});

		// dob.addFocusListener((FocusLostListener) (e) ->
		// this.model.validateDOB(dob.getText()));

		email.addFocusListener((FocusLostListener) (e) -> this.model.checkEmailReg(email.getText()));

		password.addFocusListener((FocusLostListener) (e) -> this.model.validatePasswordReg(password.getPassword()));

		// confirm listener
		cancel.addActionListener((e) -> this.model.changeCurrentState(ModelState.LOGIN));
		submit.addActionListener((e) -> {
			if (!this.model.isEmailUnique()) {
				JOptionPane.showMessageDialog(this, "Email already exists! Did you forget your password?");
			} else if (!this.model.isUsernameUnique()) {
				JOptionPane.showMessageDialog(this, "Username already exists! Pick another ones");
			} else if (!this.model.isEmailMatchesRegex()) {
				JOptionPane.showMessageDialog(this, "Incorrect email format");
			} else if (!this.model.checkConfirmMatchesPassword(confirm.getPassword(), password.getPassword())) {
				JOptionPane.showMessageDialog(this, "Passwords do not match");
			} else {
				model.checkRegistrationInformation();
			}

		});

	}
	// -------End Lambda Listeners------//

	// ------ Email & User labels ---------//

	public void setDobLabel(String dobLabel) {
		this.dobLabel.setText(dobLabel);
		if (dobLabel.contains("incorrect") || dobLabel.contains("18") || dobLabel.contains("DOB")) {
			this.dobLabel.setForeground(Color.RED);
		} else {
			this.dobLabel.setForeground(Color.WHITE);
		}
	}

	/** Getter for userLabel
	 * 
	 * @return userLabel a JLabel
	 */
	public JLabel getUserLabel() {
		return userLabel;
	}

	/** Getter for passwordLabel
	 * 
	 * @return
	 */
	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	/**Getter for confirmLabel
	 * 
	 * @return
	 */
	public JLabel getConfirmLabel() {
		return confirmLabel;
	}

	/** Setter for passwordLabel
	 * 
	 * @param passwordLabel a String
	 */
	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel.setText(passwordLabel);
	}

	/** Setter for confirmLabel
	 * 
	 * @param confirmLabel a String
	 */
	public void setConfirmLabel(String confirmLabel) {
		this.confirmLabel.setText(confirmLabel);
	}

	/** Getter for emailLabel
	 * 
	 * @return emailLabel a JLabel
	 */
	public JLabel getEmailLabel() {
		return emailLabel;
	}

	/** Getter for firstLabel
	 * 
	 * @return firstLabel a JLabel
	 */
	public JLabel getFirstLabel() {
		return firstLabel;
	}

	/** Setter for firstLabel depends on validity
	 * 
	 * @param firstLabel a String
	 */
	public void setFirstLabel(String firstLabel) {
		this.firstLabel.setText(firstLabel);
		if (firstLabel.contains("incorrect")) {
			this.firstLabel.setForeground(Color.RED);
		} else {
			this.firstLabel.setForeground(Color.WHITE);
		}
	}

	/** Getter for lastLabel
	 * 
	 * @return lastLabel a JLabel
	 */
	public JLabel getLastLabel() {
		return lastLabel;
	}

	/** Setter for lastLabel depends on validity
	 * 
	 * @param lastLabel a String
	 */
	public void setLastLabel(String lastLabel) {
		this.lastLabel.setText(lastLabel);
		if (lastLabel.contains("incorrect")) {
			this.lastLabel.setForeground(Color.RED);
		} else {
			this.lastLabel.setForeground(Color.WHITE);
		}
	};

	/** Setter for userLabel, depends on validity
	 * 
	 * @param userLabel a String
	 */
	public void setUserLabel(String userLabel) {
		this.userLabel.setText(userLabel);
		if (userLabel.contains("exists")) {
			this.userLabel.setForeground(Color.RED);
		} else {
			this.userLabel.setForeground(Color.WHITE);
		}
	}

	/** Setter for emailLabel, depends on validity
	 * 
	 * @param emailLabel a String
	 */
	public void setEmailLabel(String emailLabel) {
		this.emailLabel.setText(emailLabel);
		if (emailLabel.contains("exists") || emailLabel.contains("incorrect")) {
			this.emailLabel.setForeground(Color.RED);
		} else {
			this.emailLabel.setForeground(Color.WHITE);
		}
	}
	// ------ End Email & User labels ---------//

	/** Inner class to limit the number of characters in the text fields
	 * 
	 * @author nataliemcdonnell
	 *
	 */
	public class JTextFieldLimit extends PlainDocument {
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
