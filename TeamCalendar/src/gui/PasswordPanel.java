package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;

import server.ObjectClientController;

public class PasswordPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjectClientController controller = null;
	JLabel hello = new JLabel("Want to change your password?");
	JPanel detailsPanel = new JPanel();
	JLabel oldPassword = new JLabel("Old Password");
	JLabel newPassword = new JLabel("New Password");
	JLabel confirmNew = new JLabel("Confirm New Password");
	JLabel comment = new JLabel("To save changes please enter your password");
	JLabel empty = new JLabel();

	JPasswordField oldPasswordA = new JPasswordField();
	JPasswordField newPasswordA = new JPasswordField();
	JPasswordField confirmNewA = new JPasswordField();
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	
	public PasswordPanel(ObjectClientController controller){
		
		this.controller = controller;
		
		setPreferredSize(new Dimension(Integer.MAX_VALUE, 500));
	
		hello.setForeground(Color.DARK_GRAY);
		oldPassword.setForeground(Color.DARK_GRAY);
		newPassword.setForeground(Color.DARK_GRAY);
		confirmNew.setForeground(Color.DARK_GRAY);
		comment.setForeground(Color.GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		oldPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		newPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		confirmNew.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		oldPasswordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		newPasswordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		confirmNewA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		comment.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
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
	}
	
public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		ObjectClientController controller = new ObjectClientController();
		
		PasswordPanel menu = new PasswordPanel(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}

