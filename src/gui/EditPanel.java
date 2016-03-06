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
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class EditPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel hello = new JLabel("Want to change your details?");
	JPanel detailsPanel = new JPanel();
	JLabel firstName = new JLabel("First Name");
	JLabel lastName = new JLabel("Last Name");
	JLabel email = new JLabel("Email");
	JLabel dob = new JLabel("Date of Birth");
	JLabel password = new JLabel("Password");
	JLabel comment = new JLabel("To save changes please enter your password");
	JLabel empty = new JLabel();

	JTextField firstNameA = new JTextField("Natalie McDonnell");
	JTextField lastNameA = new JTextField("Natalie McDonnell");
	JTextField emailA = new JTextField("natalie.mcdonnell1@hotmail.co.uk");
	JTextField dobA = new JTextField("08/05/1994");
	JPasswordField passwordA = new JPasswordField();
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	
	public EditPanel(){
		
		setPreferredSize(new Dimension(Integer.MAX_VALUE, 500));
	
		hello.setForeground(Color.DARK_GRAY);
		firstName.setForeground(Color.DARK_GRAY);
		lastName.setForeground(Color.DARK_GRAY);
		email.setForeground(Color.DARK_GRAY);
		dob.setForeground(Color.DARK_GRAY);
		password.setForeground(Color.DARK_GRAY);
		comment.setForeground(Color.GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		firstName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		firstNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lastName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lastNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		email.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		emailA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		dob.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		dobA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
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
		detailsPanel.add(dob);
		detailsPanel.add(dobA);
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
	}
	
public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		EditPanel menu = new EditPanel();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}


}

