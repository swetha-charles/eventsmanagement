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
		setPreferredSize(new Dimension(1000,650));
		setMaximumSize(new Dimension(1000,650));
		setMinimumSize(new Dimension(1000,650));

		add(bar);
		add(profile);
	}

}
