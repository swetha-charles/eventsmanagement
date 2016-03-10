package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.ObjectClientController;


public class MenuPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ObjectClientController controller = null;
	JPanel menuBar = new JPanel();
	public static JLabel home = new JLabel("Home");
	public static JLabel profile = new JLabel("Profile");
	public static JLabel logout = new JLabel("Logout");

	public MenuPanel(ObjectClientController controller){
		
		this.controller = controller;
		
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), 70));
		setMinimumSize(new Dimension((int)dimension.getWidth(), 70));
		setMaximumSize(new Dimension((int)dimension.getWidth(), 70));
		
		menuBar.setBackground(Color.DARK_GRAY);

		home.setForeground(Color.WHITE);
		profile.setForeground(Color.WHITE);
		logout.setForeground(Color.WHITE);
		home.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		profile.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		logout.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 22));
		
		home.addMouseListener(controller);
		profile.addMouseListener(controller);
		logout.addMouseListener(controller);
		
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
		ObjectClientController controller = new ObjectClientController();
		
		MenuPanel menu = new MenuPanel(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
