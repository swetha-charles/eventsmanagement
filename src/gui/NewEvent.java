package gui;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import objectTransferrable.Event;
/** this class builds a panel for a popup window to build a new event
 * 
 * @author nataliemcdonnell
 *
 */
public class NewEvent extends JPanel {
	
	public static void main(String[] args) {

		Client client = new Client();
		Model model = new Model(client);
		ListPanel lp = new ListPanel(client, model);
		NewEvent newEvent = new NewEvent(client, model, lp);
		JFrame frame = new JFrame();
		/*
		 * frame.setVisible(true); frame.setSize(400,400); frame.add(newEvent);
		 */
		JDialog dialog = new JDialog();
		dialog.add(newEvent);
		dialog.setVisible(true);
		dialog.setSize(600, 320);
		dialog.setDefaultLookAndFeelDecorated(true);

	}

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
	JTextArea notesA;
	Checkbox global;
	JPanel userResponse;
	JButton submit;
	JButton cancel;
	private ListPanel listPanel;

	/** Constructor to build panel
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 * @param listPanel is an object ListPanel
	 */
	public NewEvent(Client controller, Model model, ListPanel listPanel) {
		this.controller = controller;
		this.model = model;
		this.listPanel = listPanel;

		nameA = new JTextField();
		nameA.setDocument(new JTextFieldLimit(50));
		dateA = new JTextField();
		dateA.setDocument(new JTextFieldLimit(2));
		monthA = new JTextField();
		monthA.setDocument(new JTextFieldLimit(2));
		yearA = new JTextField();
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
		locationA.setDocument(new JTextFieldLimit(200));
		notesA = new JTextArea();
		notesA.setDocument(new JTextFieldLimit(200));

		setPreferredSize(new Dimension(600, 400));

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
		userResponse = new JPanel();
		submit = new JButton("Submit");
		submit.setBackground(Color.DARK_GRAY);
		submit.setForeground(new Color(255, 255, 245));
		submit.addActionListener((e) -> {
			// editing events!
			if (nameA.getText().length() != 0) {
				Event newEvent = null;
				String startTimeHours = getShoursA().getText();
				String startTimeMinutes = getSminutesA().getText();

				// Validate Start Time
				if (this.model.validateNewStartTime(startTimeHours, startTimeMinutes)) {
					Time startTime = this.stringToTime(startTimeHours, startTimeMinutes);
					String endTimeHours = getEhoursA().getText();
					String endTimeMinutes = getEminutesA().getText();

					// Validate End Time
					if (this.model.validateNewEndTime(endTimeHours, endTimeMinutes)) {
						Time endTime = this.stringToTime(endTimeHours, endTimeMinutes);
						String day = getDateA().getText();
						String month = getMonthA().getText();
						String year = getYearA().getText();
						if(startTime.before(endTime)){
							// Validate Date
							if (this.model.validateNewDate(day, month, year)) {
//								System.out.println("TRUE");
								java.sql.Date newDateSQL = stringToDate(day, month, year);
								if (getGlobal() == true) {
									newEvent = new Event(startTime, endTime, getNotesA().getText(), getNameA().getText(),
											getLocationA().getText(), newDateSQL, true, 1);
								} else {
									newEvent = new Event(startTime, endTime, getNotesA().getText(), getNameA().getText(),
											getLocationA().getText(), newDateSQL, false, 1);
								}
								model.addEvents(newEvent);
								if (model.getMeetingCreationSuccessful() == true) {
									model.setMeetingCreationSuccessful(false);
									model.updateMeetings(new Date(this.listPanel.getC().getTimeInMillis()));
									this.listPanel.addMeetings(model.getMeetings());

									JOptionPane.showMessageDialog(this, "Meeting successfully created!");
									this.setVisible(false);
									this.setEnabled(false);
									this.listPanel.closeDialog();
								} else {
									JOptionPane.showMessageDialog(this,
											"Meeting could not be created");
									//what do we do here??
								}
							} else {
								// Date could not be validated
								JOptionPane.showMessageDialog(this, "Check date, current input is invalid");
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
			} else {
				// Event name is blank. 
				JOptionPane.showMessageDialog(this, "Event name must be filled in");
			}

		});
		cancel = new JButton("Cancel");
		cancel.setBackground(Color.DARK_GRAY);
		cancel.setForeground(new Color(255, 255, 245));
		cancel.addActionListener((e) -> {
			this.setVisible(false);
			this.setEnabled(false);
			this.listPanel.closeDialog();
		});
		userResponse.add(submit);
		userResponse.add(cancel);

		detailsPanel.setLayout(new GridLayout(6, 2));
		detailsPanel.setPreferredSize(new Dimension(500, 220));
		detailsPanel.setMaximumSize(new Dimension(500, 220));
		detailsPanel.setMinimumSize(new Dimension(500, 220));
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
		detailsPanel.add(global);
		
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

	/** Getter for name textfield
	 * 
	 * @return JTextField nameA
	 */
	public JTextField getNameA() {
		return nameA;
	}

	/** Getter for date textfield
	 * 
	 * @return JTextField dateA
	 */
	public JTextField getDateA() {
		return dateA;
	}

	/** Getter for month textfield
	 * 
	 * @return JTextField monthA
	 */
	public JTextField getMonthA() {
		return monthA;
	}

	/** Getter for year textfield
	 * 
	 * @return JTextField yearA
	 */
	public JTextField getYearA() {
		return yearA;
	}

	/** Getter for start time hours textfield
	 * 
	 * @return JTextField shoursA
	 */
	public JTextField getShoursA() {
		return shoursA;
	}

	/** Getter for start time minutes textfield
	 * 
	 * @return JTextField sminutesA
	 */
	public JTextField getSminutesA() {
		return sminutesA;
	}

	/** Getter for end time hours textfield
	 * 
	 * @return JTextField ehoursA
	 */
	public JTextField getEhoursA() {
		return ehoursA;
	}

	/** Getter for end time minutes textfield
	 * 
	 * @return JTextField eminutesA
	 */
	public JTextField getEminutesA() {
		return eminutesA;
	}

	/** Getter for location textfield
	 * 
	 * @return JTextField location
	 */
	public JTextField getLocationA() {
		return locationA;
	}

	/** Getter for notes textarea
	 * 
	 * @return JTextArea of notes
	 */
	public JTextArea getNotesA() {
		return notesA;
	}

	/** Getter for boolean global
	 * 
	 * @return true if checkbox is tick false otherwise
	 */
	public boolean getGlobal() {
		return global.getState();
	}

	/** Changes the time from a string to an sql Time object
	 * 
	 * @param hours string for number of hours
	 * @param minutes string for number of minutes
	 * @return Time object formed from the hours and minutes
	 */
	public Time stringToTime(String hours, String minutes) {
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
		Time time = new Time(((h-1) * 3600000) + (m * 60000));
		return time;
	}

	/** Changes the date from a string to an swl date object
	 * 
	 * @param day string for day of month
	 * @param month string for month
	 * @param year string for year
	 * @return Date object formed from the date
	 */
	public Date stringToDate(String day, String month, String year) {
		return this.model.sanitizeDateAndMakeSQLDate(day, month, year);

	}

	/** Inner class to limit the number of characters in the text fields
	 * 
	 * @author nataliemcdonnell
	 *
	 */
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
