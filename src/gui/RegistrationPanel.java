package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import client.Client;
import listener.interfaces.FocusLostListener;
import model.Model;
import model.ModelState;

public class RegistrationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2535316040411018240L;
	private Client client = null;
	private Model model = null;
	JTextField firstName = new JTextField(50);
	JTextField lastName = new JTextField(50);
	JTextField username = new JTextField(50);
	JTextField dob = new JTextField();
	JTextField email = new JTextField();
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

	/**
	 * This constructor builds a login panel where the user can input their
	 * username and password.
	 */
	public RegistrationPanel(Client client, Model model) {
		this.client = client;
		this.model = model;
		// sets the dimension of the login panel
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(800, 500));

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

		add(fields);
		add(submit);
		add(cancel);

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
		
		dob.addFocusListener((FocusLostListener) (e) -> this.model.validateDOB(dob.getText()));		
		
		email.addFocusListener((FocusLostListener) (e) -> this.model.checkEmail(email.getText()));
		
		password.addFocusListener((FocusLostListener) (e) -> this.model.validatePassword(password.getPassword()));
		
		//confirm listener
		cancel.addActionListener((e) -> this.model.changeCurrentState(ModelState.LOGIN));
		submit.addActionListener((e) -> {
			if (!this.model.isEmailUnique()) {
				JOptionPane.showMessageDialog(this, "Email already exists! Did you forget your password?");
			} else if (!this.model.isUsernameUnique()) {
				JOptionPane.showMessageDialog(this, "Username already exists! Pick another ones");
			} else if (!this.model.isEmailMatchesRegex()) {
				JOptionPane.showMessageDialog(this, "Incorrect email format");
			} else if(!this.model.checkConfirmMatchesPassword(confirm.getPassword())){
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
		if (dobLabel.contains("incorrect") | dobLabel.contains("18")) {
			this.dobLabel.setForeground(Color.RED);
		} else {
			this.dobLabel.setForeground(Color.WHITE);
		}
	}

	public JLabel getUserLabel() {
		return userLabel;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public JLabel getConfirmLabel() {
		return confirmLabel;
	}

	public void setPasswordLabel(String passwordLabel) {
		this.passwordLabel.setText(passwordLabel);
	}

	public void setConfirmLabel(String confirmLabel) {
		this.confirmLabel.setText(confirmLabel);
	}

	public JLabel getEmailLabel() {
		return emailLabel;
	}

	public JLabel getFirstLabel() {
		return firstLabel;
	}

	public void setFirstLabel(String firstLabel) {
		this.firstLabel.setText(firstLabel);
		if (firstLabel.contains("incorrect")) {
			this.firstLabel.setForeground(Color.RED);
		} else {
			this.firstLabel.setForeground(Color.WHITE);
		}
	}

	public JLabel getLastLabel() {
		return lastLabel;
	}

	public void setLastLabel(String lastLabel) {
		this.lastLabel.setText(lastLabel);
		if (lastLabel.contains("incorrect")) {
			this.lastLabel.setForeground(Color.RED);
		} else {
			this.lastLabel.setForeground(Color.WHITE);
		}
	};

	public void setUserLabel(String userLabel) {
		this.userLabel.setText(userLabel);
		if (userLabel.contains("exists")) {
			this.userLabel.setForeground(Color.RED);
		} else {
			this.userLabel.setForeground(Color.WHITE);
		}
	}

	public void setEmailLabel(String emailLabel) {
		this.emailLabel.setText(emailLabel);
		if (emailLabel.contains("exists") || emailLabel.contains("incorrect")) {
			this.emailLabel.setForeground(Color.RED);
		} else {
			this.emailLabel.setForeground(Color.WHITE);
		}
	}
	// ------ End Email & User labels ---------//

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		Client client = new Client();

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(1000, 650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
