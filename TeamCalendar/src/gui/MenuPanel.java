package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Client;
import listener.interfaces.MouseClickedListener;
import model.Model;
import model.ModelState;


public class MenuPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client client = null;
	private Model model = null;
	private ListPanel listPanel;
	JPanel menuBar = new JPanel();
	public static JLabel home = new JLabel("Home");
	public static JLabel profile = new JLabel("Profile");
	public static JLabel logout = new JLabel("Logout");

	public MenuPanel(Client client, Model model, ListPanel listPanel){
		
		this.client = client;
		this.model = model;
		this.listPanel = listPanel;
		
		setPreferredSize(new Dimension(1100, 70));
		setMinimumSize(new Dimension(1100, 70));
		setMaximumSize(new Dimension(1100, 70));
		
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
		
		//------------------Action Listeners---------------------//
		
		MenuPanelMouseListener menuMouseListener = new MenuPanelMouseListener(this.model, this.listPanel);
		
		home.setName("home");
		home.addMouseListener(menuMouseListener);
		
		profile.setName("profile");
		profile.addMouseListener(menuMouseListener);
		
		logout.setName("logout");
		logout.addMouseListener(menuMouseListener);

	}

}
