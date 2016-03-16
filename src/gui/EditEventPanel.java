package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import gui.ListPanel.JTextFieldLimit;
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
	
	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");
	
	
	public EditEventPanel(Client controller, Model model, Event event){
		
		this.controller = controller;
		this.model = model;
		this.event = event;
		
		nameA = new JTextField(event.getEventTitle());
		String a = event.getDate().toString().substring(8, 10);
		dateA = new JTextField(a);
//		dateA.setDocument(new JTextFieldLimit(2));
		String b = event.getDate().toString().substring(5, 7);
		monthA = new JTextField(b);
//		monthA.setDocument(new JTextFieldLimit(2));
		String c = event.getDate().toString().substring(0, 4);
		yearA= new JTextField(c);
//		yearA.setDocument(new JTextFieldLimit(4));
		String d = event.getStartTime().toString().substring(0, 2);
		shoursA = new JTextField(d);
//		shoursA.setDocument(new JTextFieldLimit(2));
		String e = event.getStartTime().toString().substring(3, 5);
		sminutesA = new JTextField(e);
//		sminutesA.setDocument(new JTextFieldLimit(2));
		String f = event.getEndTime().toString().substring(0, 2);
		ehoursA = new JTextField(f);
//		ehoursA.setDocument(new JTextFieldLimit(2));
		String g = event.getEndTime().toString().substring(3, 5);
		eminutesA = new JTextField(g);
//		eminutesA.setDocument(new JTextFieldLimit(2));
		locationA = new JTextField(event.getLocation());
		notesA = new JTextField(event.getEventDescription());
		
		Dimension dimension = new Dimension(500,260);
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-70));
	
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
		
		
		detailsPanel.setLayout(new GridLayout(6,2));
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
		
		comment.add(hello);
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		
		add(comment);
		add(detailsPanel);

	}

	public Event getEvent() {
		return event;
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
