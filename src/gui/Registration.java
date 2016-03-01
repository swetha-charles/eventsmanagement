package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Registration extends JPanel {
	JButton submit;
	
	JTextField emailField;
	JLabel emailLabel;
	JTextField passwordField1;
	JLabel passwordLabel1;
	JTextField passwordField2;
	JLabel passwordLabel2;
	
	public Registration(){
		this.setLayout(new GridBagLayout());
		submit = new JButton("Submit");
		GridBagConstraints submitButtonGC = new GridBagConstraints();
		submitButtonGC.gridx = 1;
		submitButtonGC.gridy = 3;
		this.add(submit, submitButtonGC);
		
		emailField = new JTextField("Enter your email");
		GridBagConstraints emailFieldGC = new GridBagConstraints();
		emailFieldGC.gridx = 1;
		emailFieldGC.gridy = 0;
		emailFieldGC.ipadx = 20;
		emailFieldGC.ipady = 20;
		//inset from outside the component
		emailFieldGC.insets = new Insets(0,10,0,10);
		this.add(emailField, emailFieldGC);
		
		emailLabel = new JLabel("Email");
		GridBagConstraints emailLabelGC = new GridBagConstraints();
		emailLabelGC.gridx = 0;
		emailLabelGC.gridy = 0;
		this.add(emailLabel, emailLabelGC);
		
		passwordField1 = new JTextField("Enter your password");
		GridBagConstraints psswdField1GC = new GridBagConstraints();
		psswdField1GC.gridx = 1;
		psswdField1GC.gridy = 1;
		psswdField1GC.ipadx = 20;
		psswdField1GC.ipady = 20;
		//inset from outside the component
		emailFieldGC.insets = new Insets(0,10,0,10);
		this.add(passwordField1, psswdField1GC);
		
		passwordLabel1 = new JLabel("Password");
		GridBagConstraints psswdLabel1GC = new GridBagConstraints();
		psswdLabel1GC.gridx = 0;
		psswdLabel1GC.gridy = 1;
		this.add(passwordLabel1, psswdLabel1GC);
		
		
		
		
		
		
		
		//this.add(nameLabel, BorderLayout.CENTER);
	}
}
