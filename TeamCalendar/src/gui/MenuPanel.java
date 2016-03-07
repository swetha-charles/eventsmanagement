package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class MenuPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller = null;
	JPanel menuBar = new JPanel();
	JLabel home = new JLabel("Home");
	JLabel profile = new JLabel("Profile");
	JLabel logout = new JLabel("Logout");

	public MenuPanel(Controller controller){
		
		this.controller = controller;
		
		setPreferredSize(new Dimension(Integer.MAX_VALUE, 70));
		setMinimumSize(new Dimension(Integer.MAX_VALUE, 70));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
		
		menuBar.setBackground(Color.DARK_GRAY);

		home.setForeground(Color.WHITE);
		profile.setForeground(Color.WHITE);
		logout.setForeground(Color.WHITE);
		home.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		profile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		logout.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		menuBar.setLayout(grid);
		
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		menuBar.add(home,gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		menuBar.add(profile, gbc);
		gbc.gridx = 7;
		gbc.gridy = 0;
		menuBar.add(logout,gbc);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		add(menuBar);

	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Controller controller = new Controller();
		
		MenuPanel menu = new MenuPanel(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
