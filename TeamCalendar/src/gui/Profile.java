package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import model.Model;

/** Class to build Profile view including menuPanel
 * 
 * @author nataliemcdonnell
 *
 */
public class Profile extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	MenuPanel bar;
	ProfilePanel profile;
	
	/**
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 * @param menuPanel a MenuPanel object
	 */
	public Profile(Client controller, Model model, MenuPanel menuPanel){
		
		this.controller = controller;
		this.model = model;
		this.bar = menuPanel;
		this.profile = new ProfilePanel(controller, model);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(1000,650));
		setMaximumSize(new Dimension(1000,650));
		setMinimumSize(new Dimension(1000,650));

		add(bar);
		add(profile);
	}
	
	/**
	 * 
	 */
	public void refresh(){
		this.removeAll();
		this.add(bar);
		this.add(profile);
	}

}
