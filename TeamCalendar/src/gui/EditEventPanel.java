package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import client.Client;
import model.Model;
import model.ModelState;
import objectTransferrable.Event;

public class EditEventPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	private Event event;
	JLabel hello = new JLabel("Want to edit your event?");
	JPanel detailsPanel = new JPanel();
	JPanel comment = new JPanel();
	JLabel firstName = new JLabel("Event Name");
	JLabel lastName = new JLabel("StartTime");
	JLabel email = new JLabel("End Time");
	JLabel password = new JLabel("Location");
	JLabel notes = new JLabel("Notes");
	JLabel empty = new JLabel();

	JTextField firstNameA;
	JTextField lastNameA;
	JTextField emailA;
	JTextField passwordA;
	JTextField notesA;
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	
	public EditEventPanel(Client controller, Model model, Event event){
		
		this.controller = controller;
		this.model = model;
		this.event = event;
		firstNameA = new JTextField(event.getEventTitle());
		String st = event.getStartTime().toString().substring(0, 5);
		lastNameA = new JTextField(st);
		String et = event.getEndTime().toString().substring(0, 5);
		emailA = new JTextField(et);
		passwordA = new JTextField(event.getLocation());
		notesA = new JTextField(event.getEventDescription());
		
		Dimension dimension = new Dimension(500,260);
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-70));
	
		hello.setForeground(Color.WHITE);
		comment.setBackground(Color.DARK_GRAY);
		firstName.setForeground(Color.DARK_GRAY);
		lastName.setForeground(Color.DARK_GRAY);
		email.setForeground(Color.DARK_GRAY);
		password.setForeground(Color.DARK_GRAY);
		comment.setForeground(Color.GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		firstName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		firstNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lastName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lastNameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		email.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		emailA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		password.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		passwordA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		cancel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
		detailsPanel.setLayout(new GridLayout(6,2));
		detailsPanel.setPreferredSize(new Dimension(500,200));
		detailsPanel.add(firstName);
		detailsPanel.add(firstNameA);
		detailsPanel.add(lastName);
		detailsPanel.add(lastNameA);
		detailsPanel.add(email);
		detailsPanel.add(emailA);
		detailsPanel.add(password);
		detailsPanel.add(passwordA);
		detailsPanel.add(notes);
		detailsPanel.add(notesA);
		
		comment.add(hello);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		add(comment);
		add(detailsPanel);

	}


	public JTextField getFirstNameA() {
		return firstNameA;
	}


	public JTextField getLastNameA() {
		return lastNameA;
	}


	public JTextField getEmailA() {
		return emailA;
	}


	public JTextField getPasswordA() {
		return passwordA;
	}


	public JTextField getNotesA() {
		return notesA;
	}

}
