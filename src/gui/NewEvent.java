package gui;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import objectTransferrable.Event;

public class NewEvent extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;

	JLabel hello = new JLabel("Want to create a new event?");
	JPanel detailsPanel = new JPanel();
	JPanel comment = new JPanel();
	JPanel fullDate = new JPanel();
	JPanel fullStartTime = new JPanel();
	JPanel fullEndTime = new JPanel();
	JLabel firstName = new JLabel("Event Name");
	JLabel date = new JLabel("Date  (dd/mm/yyyy)");
	JLabel lastName = new JLabel("StartTime  (hh/mm)");
	JLabel email = new JLabel("End Time  (hh/mm)");
	JLabel password = new JLabel("Location");
	JLabel notes = new JLabel("Notes");
	JLabel empty = new JLabel();

	JTextField nameA;
	JTextField dateA;
	JTextField monthA;
	JTextField yearA;
	JTextField shoursA;
	JTextField sminutesA;
	JTextField ehoursA;
	JTextField eminutesA;
	JTextField locationA;
	JTextField notesA;
	Checkbox global;
	
	
	public NewEvent(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		
		nameA = new JTextField();
		dateA = new JTextField();
		dateA.setDocument(new JTextFieldLimit(2));
		monthA = new JTextField();
		monthA.setDocument(new JTextFieldLimit(2));
		yearA= new JTextField();
		yearA.setDocument(new JTextFieldLimit(4));
		shoursA = new JTextField();
		shoursA.setDocument(new JTextFieldLimit(2));
		sminutesA = new JTextField();
		sminutesA.setDocument(new JTextFieldLimit(2));
		ehoursA = new JTextField();
		ehoursA.setDocument(new JTextFieldLimit(2));
		eminutesA = new JTextField();
		eminutesA.setDocument(new JTextFieldLimit(2));
		locationA = new JTextField();
		notesA = new JTextField();
		
	
		setPreferredSize(new Dimension(500,260));
	
		hello.setForeground(Color.WHITE);
		comment.setBackground(Color.DARK_GRAY);
		firstName.setForeground(Color.DARK_GRAY);
		date.setForeground(Color.DARK_GRAY);
		lastName.setForeground(Color.DARK_GRAY);
		email.setForeground(Color.DARK_GRAY);
		password.setForeground(Color.DARK_GRAY);
		notes.setForeground(Color.GRAY);
		
		hello.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 17));
		firstName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		nameA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		date.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		dateA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		monthA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		yearA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		lastName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		shoursA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		sminutesA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		email.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		ehoursA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		eminutesA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		password.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		locationA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		notes.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		notesA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		
		fullDate.setLayout(new BoxLayout(fullDate, BoxLayout.LINE_AXIS));
		fullDate.add(dateA);
		fullDate.add(Box.createRigidArea(new Dimension(10, 0)));
		fullDate.add(monthA);
		fullDate.add(Box.createRigidArea(new Dimension(10, 0)));
		fullDate.add(yearA);
		fullStartTime.setLayout(new BoxLayout(fullStartTime, BoxLayout.LINE_AXIS));
		fullStartTime.add(shoursA);
		fullStartTime.add(Box.createRigidArea(new Dimension(30, 0)));
		fullStartTime.add(sminutesA);
		fullEndTime.setLayout(new BoxLayout(fullEndTime, BoxLayout.LINE_AXIS));
		fullEndTime.add(ehoursA);
		fullEndTime.add(Box.createRigidArea(new Dimension(30, 0)));
		fullEndTime.add(eminutesA);
		
		global = new Checkbox("Make meeting global");
		global.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
		
		detailsPanel.setLayout(new GridLayout(7,2));
		detailsPanel.setPreferredSize(new Dimension(500,200));
		detailsPanel.add(firstName);
		detailsPanel.add(nameA);
		detailsPanel.add(date);
		detailsPanel.add(fullDate);
		detailsPanel.add(lastName);
		detailsPanel.add(fullStartTime);
		detailsPanel.add(email);
		detailsPanel.add(fullEndTime);
		detailsPanel.add(password);
		detailsPanel.add(locationA);
		detailsPanel.add(notes);
		detailsPanel.add(notesA);
		detailsPanel.add(global);
		
		comment.add(hello);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		add(comment);
		add(detailsPanel);

	}

	public JTextField getNameA() {
		return nameA;
	}


	public JTextField getDateA() {
		return dateA;
	}


	public JTextField getMonthA() {
		return monthA;
	}


	public JTextField getYearA() {
		return yearA;
	}


	public JTextField getShoursA() {
		return shoursA;
	}


	public JTextField getSminutesA() {
		return sminutesA;
	}


	public JTextField getEhoursA() {
		return ehoursA;
	}


	public JTextField getEminutesA() {
		return eminutesA;
	}


	public JTextField getLocationA() {
		return locationA;
	}


	public JTextField getNotesA() {
		return notesA;
	}
	
	public boolean getGlobal() {
		return global.getState();
	}

	public class JTextFieldLimit extends PlainDocument {
		
		private static final long serialVersionUID = 3693304660903406545L;
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
}
