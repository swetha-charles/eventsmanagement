package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField username = new JTextField();
	JTextField password = new JTextField();
	JPanel mainPanel = new JPanel();
	JPanel userPanel = new JPanel();
	JPanel passwordPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JButton login = new JButton("Login");
	
	public LoginPanel(){
		
		setLayout(new FlowLayout());
		setSize(new Dimension(500,500));
		
		Dimension size1 = new Dimension(300,20);
		username.setPreferredSize(size1);
		password.setPreferredSize(size1);
		
		Dimension size2 = new Dimension(400, 40);
		userPanel.setMaximumSize(size2);
		userPanel.setMinimumSize(size2);
		passwordPanel.setMaximumSize(size2);
		passwordPanel.setMinimumSize(size2);
		
		Dimension panelSize = new Dimension(500,500);
		mainPanel.setPreferredSize(panelSize);
		mainPanel.setMaximumSize(panelSize);
		mainPanel.setMinimumSize(panelSize);
		
		logoPanel.setMinimumSize(new Dimension(400, 200));
		logoPanel.setMaximumSize(new Dimension(400, 200));
		logoPanel.setPreferredSize(new Dimension(400, 200));
		
		mainPanel.setBackground(Color.DARK_GRAY);
		
		userPanel.add(new JLabel("Username"));
		passwordPanel.add(new JLabel("Password"));
		
		SpringLayout layout = new SpringLayout();
		mainPanel.setLayout(layout);
		mainPanel.add(logoPanel);
		userPanel.add(username);
		passwordPanel.add(password);
		mainPanel.add(userPanel);
		mainPanel.add(passwordPanel);
		mainPanel.add(login);
		
		layout.putConstraint(SpringLayout.WEST, logoPanel,50,
		         SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, logoPanel,50,
		         SpringLayout.NORTH, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, userPanel,25,
		         SpringLayout.SOUTH, logoPanel);
		layout.putConstraint(SpringLayout.NORTH, passwordPanel,40,
		         SpringLayout.SOUTH, userPanel);
		layout.putConstraint(SpringLayout.WEST, userPanel,60,
		         SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.WEST, passwordPanel,60,
		         SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.WEST, login,200,
		         SpringLayout.WEST, mainPanel);
		layout.putConstraint(SpringLayout.NORTH, login,400,
		         SpringLayout.NORTH, mainPanel);
		
		add(mainPanel);	
	}

public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		LoginPanel loginPanel = new LoginPanel();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
