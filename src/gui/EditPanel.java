package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import model.ModelState;
import objectTransferrable.OTUpdateUserProfile;

public class EditPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	JLabel hello = new JLabel("Want to change your details?");
	JPanel detailsPanel = new JPanel();
	JLabel firstName = new JLabel("First Name");
	JLabel lastName = new JLabel("Last Name");
	JLabel email = new JLabel("Email");
	JLabel password = new JLabel("Password");
	JLabel comment = new JLabel("To save changes please enter your password");
	JLabel empty = new JLabel();

	JTextField firstNameA;
	JTextField lastNameA;
	JTextField emailA;
	JPasswordField passwordA = new JPasswordField();
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	/** constructor to build panel to edit profile information
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 */
	public EditPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		firstNameA = new JTextField();
		firstNameA.setDocument(new JTextFieldLimit(30));
		firstNameA.setText(model.getFirstName());
		
		lastNameA = new JTextField();
		lastNameA.setDocument(new JTextFieldLimit(30));
		lastNameA.setText(model.getLastname());
	
		emailA = new JTextField();
		emailA.setDocument(new JTextFieldLimit(30));
		emailA.setText(model.getEmail());
		
		
		setPreferredSize(new Dimension(1000,580));
		setMaximumSize(new Dimension(1000,580));
		setMinimumSize(new Dimension(1000,580));
	
		hello.setForeground(Color.DARK_GRAY);
		firstName.setForeground(Color.DARK_GRAY);
		lastName.setForeground(Color.DARK_GRAY);
		email.setForeground(Color.DARK_GRAY);
		password.setForeground(Color.DARK_GRAY);
		comment.setForeground(Color.GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		firstName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		firstNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lastName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lastNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		email.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		emailA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		password.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		passwordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		comment.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
		detailsPanel.setLayout(new GridLayout(6,2));
		detailsPanel.setPreferredSize(new Dimension(700,150));
		detailsPanel.add(firstName);
		detailsPanel.add(firstNameA);
		detailsPanel.add(lastName);
		detailsPanel.add(lastNameA);
		detailsPanel.add(email);
		detailsPanel.add(emailA);
		detailsPanel.add(comment);
		detailsPanel.add(empty);
		detailsPanel.add(password);
		detailsPanel.add(passwordA);
		
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		add(hello);
		add(detailsPanel);
		add(submit);
		add(cancel);
		
		layout.putConstraint(SpringLayout.WEST, hello, 70, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, hello, 50, SpringLayout.NORTH, this);
		
		layout.putConstraint(SpringLayout.WEST, detailsPanel, 80, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, detailsPanel, 20, SpringLayout.SOUTH, hello);
		
		layout.putConstraint(SpringLayout.WEST, submit, 70, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, submit, 20, SpringLayout.SOUTH, detailsPanel);
		
		layout.putConstraint(SpringLayout.WEST, cancel, 10, SpringLayout.EAST, submit);
		layout.putConstraint(SpringLayout.NORTH, cancel, 20, SpringLayout.SOUTH, detailsPanel);
		
		//----------------------Listeners----------------------//
		
		submit.addActionListener((e) -> {
			String password = new String(this.passwordA.getPassword());
			if(model.checkPassword(password)){
				this.model.updateProfile(firstNameA.getText(),lastNameA.getText(),emailA.getText());
				if(this.model.getUpdateProfileSuccess()){
					JOptionPane.showMessageDialog(this, "Update Successful!");
					this.model.changeCurrentState(ModelState.PROFILE);
				} else {
					JOptionPane.showMessageDialog(this, "Update unsuccessful, connection may be down");
				}
			}else {
				JOptionPane.showMessageDialog(this, "Incorrect password");
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				model.changeCurrentState(ModelState.PROFILE);
			}
		});
	}
	
	/** Inner class to limit the number of characters in the text fields
	 * 
	 * @author nataliemcdonnell
	 *
	 */
	public class JTextFieldLimit extends PlainDocument {

		private static final long serialVersionUID = 3693304660903406545L;
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

