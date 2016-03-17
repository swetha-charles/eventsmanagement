package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Client;
import model.Model;
import objectTransferrable.Event;

public class DeleteEvent extends JPanel{
	
	private Client controller = null;
	private Model model;
	private Event event;
	
	JLabel headingLabel = new JLabel("Are you sure you want to delete?");
	
	public DeleteEvent(Client controller, Model model, Event event){
		
		this.controller = controller;
		this.model = model;
		this.event = event;
		
		setPreferredSize(new Dimension(200,160));
		headingLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		headingLabel.setForeground(Color.DARK_GRAY);
		
		add(headingLabel);
		
	}

	public Event getEvent() {
		return event;
	}

}
