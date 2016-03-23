package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import model.ModelState;

public class PasswordPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	
	JLabel hello = new JLabel("Want to change your password?");
	JPanel detailsPanel = new JPanel();
	JLabel oldPassword = new JLabel("Old Password");
	JLabel newPassword = new JLabel("New Password");
	JLabel confirmNew = new JLabel("Confirm New Password");
	JLabel empty = new JLabel();

	JPasswordField oldPasswordA = new JPasswordField();
	JPasswordField newPasswordA = new JPasswordField();
	JPasswordField confirmNewA = new JPasswordField();
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	
	public PasswordPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		
		setPreferredSize(new Dimension(1000,580));
		setMaximumSize(new Dimension(1000,580));
		setMinimumSize(new Dimension(1000,58));
		
		oldPasswordA.setDocument(new JTextFieldLimit(60));
		newPasswordA.setDocument(new JTextFieldLimit(60));
		confirmNewA.setDocument(new JTextFieldLimit(60));
	
		hello.setForeground(Color.DARK_GRAY);
		oldPassword.setForeground(Color.DARK_GRAY);
		newPassword.setForeground(Color.DARK_GRAY);
		confirmNew.setForeground(Color.DARK_GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		oldPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		newPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		confirmNew.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		oldPasswordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		newPasswordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		confirmNewA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
		submit.setBackground(Color.DARK_GRAY);
		submit.setForeground(new Color(255, 255, 245));
		cancel.setBackground(Color.DARK_GRAY);
		cancel.setForeground(new Color(255, 255, 245));
		
		detailsPanel.setLayout(new GridLayout(3,2));
		detailsPanel.setPreferredSize(new Dimension(700,100));
		detailsPanel.add(oldPassword);
		detailsPanel.add(oldPasswordA);
		detailsPanel.add(newPassword);
		detailsPanel.add(newPasswordA);
		detailsPanel.add(confirmNew);
		detailsPanel.add(confirmNewA);
		
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
			if(this.model.checkPassword(new String(oldPasswordA.getPassword()))){
				String password1 = new String(confirmNewA.getPassword());
				String password2 = new String(newPasswordA.getPassword());
				if(password1.equals(password2)){
					if(model.validatePassword(password1.toCharArray())){
						this.model.updatePassword(password1);
					} else {
						JOptionPane.showMessageDialog(this, "Password input is invalid. \n"
								+ "Must be greater than 6 and less than 60");
					}
					
				} else {
					JOptionPane.showMessageDialog(this, "Your new passwords do not match");
				}
			}else {
				JOptionPane.showMessageDialog(this, "Correctly enter your old password");
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				model.changeCurrentState(ModelState.PROFILE);
			}
		});
	}

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

