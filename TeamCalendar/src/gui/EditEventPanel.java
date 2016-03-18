package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import gui.ListPanel.JTextFieldLimit;
import model.Model;
import model.ModelState;
import objectTransferrable.Event;

public class EditEventPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	private Event event;
	private ListPanel listPanel;
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
	JTextArea notesA;
	JPanel userResponse;

	JButton submit = new JButton("Confirm Changes");
	JButton cancel = new JButton("Cancel");

	public EditEventPanel(Client controller, Model model, Event event, ListPanel listPanel) {
		this.controller = controller;
		this.model = model;
		this.event = event;
		this.listPanel = listPanel;

		nameA = new JTextField();
		nameA.setDocument(new JTextFieldLimit(50));
		nameA.setText(event.getEventTitle());
		String a = event.getDate().toString().substring(8, 10);
		dateA = new JTextField();
		dateA.setDocument(new JTextFieldLimit(2));
		dateA.setText(a);
		String b = event.getDate().toString().substring(5, 7);
		monthA = new JTextField();
		monthA.setDocument(new JTextFieldLimit(2));
		monthA.setText(b);
		String c = event.getDate().toString().substring(0, 4);
		yearA = new JTextField();
		yearA.setDocument(new JTextFieldLimit(4));
		yearA.setText(c);
		String d = event.getStartTime().toString().substring(0, 2);
		shoursA = new JTextField();
		shoursA.setDocument(new JTextFieldLimit(2));
		shoursA.setText(d);
		String e = event.getStartTime().toString().substring(3, 5);
		sminutesA = new JTextField();
		sminutesA.setDocument(new JTextFieldLimit(2));
		sminutesA.setText(e);
		String f = event.getEndTime().toString().substring(0, 2);
		ehoursA = new JTextField();
		ehoursA.setDocument(new JTextFieldLimit(2));
		ehoursA.setText(f);
		String g = event.getEndTime().toString().substring(3, 5);
		eminutesA = new JTextField();
		eminutesA.setDocument(new JTextFieldLimit(2));
		eminutesA.setText(g);
		locationA = new JTextField();
		locationA.setDocument(new JTextFieldLimit(200));
		locationA.setText(event.getLocation());
		notesA = new JTextArea( 4, 50);
		notesA.setDocument(new JTextFieldLimit(200));
		notesA.setText(event.getEventDescription());

		setPreferredSize(new Dimension(600, 350));

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

		userResponse = new JPanel();
		submit = new JButton("Submit");
		submit.addActionListener((e1) -> {
			Event changedEvent = null;
			String startTimeHours = getShoursA().getText();
			String startTimeMinutes = getSminutesA().getText();

			// Validate Start Time
			if (this.model.validateChangedStartTime(startTimeHours, startTimeMinutes)) {
				Time startTime = this.stringToTime(startTimeHours, startTimeMinutes);
				String endTimeHours = getEhoursA().getText();
				String endTimeMinutes = getEminutesA().getText();

				// Validate End Time
				if (this.model.validateChangedEndTime(endTimeHours, endTimeMinutes)) {
					Time endTime = this.stringToTime(endTimeHours, endTimeMinutes);
					String day = getDateA().getText();
					String month = getMonthA().getText();
					String year = getYearA().getText();
					if (startTime.before(endTime)) {
						// Validate Date
						if (this.model.validateChangedDate(day, month, year)) {
							java.sql.Date newDateSQL = stringToDate(day, month, year);
							changedEvent = new Event(startTime, endTime, getNotesA().getText(), getNameA().getText(),
									getLocationA().getText(), newDateSQL, getEvent().getGlobalEvent(),
									getEvent().getLockVersion() + 1);
							// write to model!
							model.updateEvent(getEvent(), changedEvent);

							if (model.getMeetingUpdateSuccessful() == true) {
								model.setMeetingUpdateSuccessful(false);
								model.updateMeetings(new Date(this.listPanel.getC().getTimeInMillis()));
								this.listPanel.addMeetings(model.getMeetings());
								JOptionPane.showMessageDialog(this, "Meeting successfully changed!");
								this.listPanel.closeDialog();
							} else {
								int userChose = JOptionPane.showConfirmDialog(this, "Meeting could not be changed since it was recently updated by another user. \n"
										+ "Would you like to refresh page and view the most up to date "
										+ "information on the meetings? \n"
										+ "NB: If you click Yes, the edits you made will stay open\n"
										+ "", "Unsuccesful Edit", JOptionPane.YES_NO_OPTION);
								if(userChose ==  JOptionPane.NO_OPTION){
									this.listPanel.closeDialog();
								} else if (userChose == JOptionPane.YES_OPTION){
									hello.setText("Your old editing information");
									this.remove(submit);
									this.listPanel.transferToJFrame();
									this.listPanel.closeDialog();
									model.updateMeetings(new Date(listPanel.getC().getTimeInMillis()));
									this.listPanel.addMeetings(model.getMeetings());
									this.repaint();
									this.revalidate();
								}
							}
						} else {
							// Date could not be validated
							JOptionPane.showConfirmDialog(this, "Check date, current input is invalid");
						}
					} else {
						// Start time is not before end time
						JOptionPane.showMessageDialog(this, "Start-time should be before end-time");
					}
				} else {
					// End time could not be validated
					JOptionPane.showMessageDialog(this, "Check end-time, current input is invalid");

				}
			} else {
				// Start time could not been validated
				JOptionPane.showMessageDialog(this, "Check start-time, current input is invalid");

			}
		});
		cancel = new JButton("Cancel");
		cancel.addActionListener((e2) -> {
			this.setVisible(false);
			this.setEnabled(false);
			this.listPanel.closeDialog();
		});
		userResponse.add(submit);
		userResponse.add(cancel);

		detailsPanel.setLayout(new GridLayout(5, 2));
		detailsPanel.setPreferredSize(new Dimension(500, 170));
		detailsPanel.setMaximumSize(new Dimension(500, 170));
		detailsPanel.setMinimumSize(new Dimension(500, 1700));
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
		
		Border line = BorderFactory.createLineBorder(Color.GRAY);
		JPanel notesPanel = new JPanel();
		notesA.setBorder(line);
		notesA.setLineWrap(true);
		notesPanel.setLayout(new GridLayout(1, 2));
		notesPanel.setPreferredSize(new Dimension(480, 70));
		notesPanel.setMaximumSize(new Dimension(480, 70));
		notesPanel.setMinimumSize(new Dimension(480, 70));
		notesPanel.add(notes);
		notes.setAlignmentY(Component.TOP_ALIGNMENT);
		notesPanel.add(notesA);

		comment.setMaximumSize(new Dimension(600, 40));
		comment.setMinimumSize(new Dimension(600, 40));
		comment.add(hello);

		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);

		add(comment);
		add(detailsPanel);
		add(notesPanel);
		add(userResponse);

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

	public JTextArea getNotesA() {
		return notesA;
	}

	public Time stringToTime(String hours, String minutes) {
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
		Time time = new Time(((h-1) * 3600000) + (m * 60000));
		return time;
	}

	public Date stringToDate(String day, String month, String year) {
		return this.model.sanitizeDateAndMakeSQLDate(day, month, year);

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
