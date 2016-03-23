package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import model.Model;

public class Profile extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	MenuPanel bar;
	ProfilePanel profile;
	
	public Profile(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		bar = new MenuPanel(controller, model);
		profile = new ProfilePanel(controller, model);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(profile);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Client controller = new Client();
		Model model = new Model(controller);
		
		Profile menu = new Profile(controller, model);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
