package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import model.ModelState;
import objectTransferrable.Event;

public class ListPanel extends JPanel {

	private static final long serialVersionUID = -3193985081542643253L;
	Client controller;
	Model model;
	JPanel left = new JPanel();

	JPanel list = new JPanel();
	JScrollPane listscroll;

	JDialog dialog;
	JFrame saveUserEdits;

	JPanel event = new JPanel();
	JLabel name = new JLabel("Event title");
	JLabel empty = new JLabel("");
	JLabel descriptionLabel = new JLabel("Description");
	JLabel dateLabel = new JLabel("Date  (dd/mm/yyyy)");
	JLabel stimeLabel = new JLabel("Start Time  (mm/hh)");
	JLabel etimeLabel = new JLabel("End Time  (mm/hh)");
	JLabel locationLabel = new JLabel("Location");
	JButton edit = new JButton("Edit Event");
	JButton submit = new JButton("Add Event");

	Calendar c = new GregorianCalendar();

	JPanel top = new JPanel();
	JLabel date;
	JButton addEvent = new JButton("+");
	JButton refresh = new JButton("Refresh");

	JPanel changeDay = new JPanel();
	JButton previous = new JButton("Previous Day");
	JButton next = new JButton("Next Day");

	static TitledBorder border;

	ArrayList<JLabel> times = new ArrayList<JLabel>();
	static ArrayList<JPanel> events = new ArrayList<JPanel>();

	// ----------------------Constructor------------------------------//

	/** Constructor to create the home panel that contains the list of meetings
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 */
	public ListPanel(Client controller, Model model) {

		this.controller = controller;
		this.model = model;
		this.model.setCalendar(this.c);

		// sets dimension and layout of the panel
		setPreferredSize(new Dimension(1000,580));
		setMaximumSize(new Dimension(1000,580));
		setMinimumSize(new Dimension(1000,580));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// creates JLabel with the date on
		date = new JLabel(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH),
				c.get(Calendar.YEAR)));
		date.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		date.setForeground(Color.DARK_GRAY);

		// creates addEvent button
		addEvent.setMargin(new Insets(0, 0, 0, 0));
		addEvent.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
		addEvent.setMinimumSize(new Dimension(35, 35));
		addEvent.setMaximumSize(new Dimension(35, 35));
		addEvent.setBackground(Color.LIGHT_GRAY);
//		addEvent.setForeground(new Color(255, 255, 245));
		refresh.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		refresh.setMinimumSize(new Dimension(100, 35));
		refresh.setMaximumSize(new Dimension(100, 35));
		refresh.setBackground(Color.LIGHT_GRAY);
//		refresh.setForeground(new Color(255, 255, 245));

		// creates panel containing date label and addEvent button
		top.setMaximumSize(new Dimension(900, 70));
		top.setMinimumSize(new Dimension(900, 70));
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(date);
		top.add(Box.createRigidArea(new Dimension(20, 0)));
		top.add(addEvent);
		refresh.setAlignmentX(Component.RIGHT_ALIGNMENT);
		top.add(refresh);

		// updates model with the meetings for today and adds them to panel list
		model.updateMeetings(new Date(c.getTimeInMillis()));
		addMeetings(model.getMeetings());

		// adds list to listscroll and sets the size
		listscroll = new JScrollPane(list);
		listscroll.setPreferredSize(new Dimension(900, 450));
		listscroll.setMaximumSize(new Dimension(900, 450));
		listscroll.setMinimumSize(new Dimension(900, 450));
		listscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listscroll.setAlignmentY(Component.TOP_ALIGNMENT);

		// creates panel for next and previous buttons
		next.setBackground(Color.LIGHT_GRAY);
//		next.setForeground(new Color(255, 255, 245));
		previous.setBackground(Color.LIGHT_GRAY);
//		previous.setForeground(new Color(255, 255, 245));
		changeDay.setPreferredSize(new Dimension(900, 50));
		changeDay.setLayout(new BoxLayout(changeDay, BoxLayout.LINE_AXIS));
		changeDay.add(previous);
		changeDay.add(next);

		// creates
		top.setAlignmentX(Component.CENTER_ALIGNMENT);
		listscroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeDay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(top);
		add(listscroll);
		add(changeDay);

		// ----------------------Listeners----------------------//

		previous.addActionListener((e) -> {
			c.add(Calendar.DATE, -1);
			setC(c);
			setDate();
			model.updateMeetings(new Date(c.getTimeInMillis()));
			addMeetings(model.getMeetings());
			model.changeCurrentState(ModelState.EVENTSUPDATE);
		});

		next.addActionListener((e) -> {
			c.add(Calendar.DATE, 1);
			setC(c);
			setDate();
			model.updateMeetings(new Date(c.getTimeInMillis()));
			addMeetings(model.getMeetings());
			model.changeCurrentState(ModelState.EVENTSUPDATE);

		});

		addEvent.addActionListener((e) -> {
			JDialog.setDefaultLookAndFeelDecorated(true);
			NewEvent eventPopup = new NewEvent(controller, model, this);
			event.setLayout(new GridLayout(7, 2));
			event.setPreferredSize(new Dimension(500, 320));
			event.setMinimumSize(new Dimension(500, 320));
			event.setMaximumSize(new Dimension(500, 320));
			dialog = new JDialog();
			dialog.add(eventPopup);
			dialog.setSize(600, 400);
			dialog.setResizable(false);
			// getting the bloody thing to turn up in the center
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Dimension screenSize = toolkit.getScreenSize();
			final int x = (screenSize.width - dialog.getWidth()) / 2;
			final int y = (screenSize.height - dialog.getHeight()) / 2;
			dialog.setLocation(x, y);
			dialog.setVisible(true);

		});

		refresh.addActionListener((e) -> {
			model.updateMeetings(new Date(getC().getTimeInMillis()));
			addMeetings(model.getMeetings());
			JOptionPane.showMessageDialog(this,"Refreshed!");
		});

		// submit.addActionListener((e) -> ;
	}

	// ---------Change Date at the top of page---------//

	/** Method to update the date for the list view
	 * 
	 */
	public void setDate() {
		this.date.setText(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH),
				c.get(Calendar.YEAR)));
		this.revalidate();
		this.repaint();
	}

	// ------------Add meeting panels to listscroll-----------------//

	/** Method the add panels for each meeting to one bigger panel
	 * 
	 * @param arraylist an arraylist of events that have been received by the server 
	 * for the date give
	 */
	public void addMeetings(ArrayList<Event> arraylist) {

		//		list.setSize(880, 450);
		list.removeAll();
		events.clear();
		if (arraylist.isEmpty()) {
			//			list.setPreferredSize(new Dimension(880, 450));
			list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));

			JLabel l = new JLabel("You have no events right now!");
			l.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			l.setForeground(Color.RED);
			list.add(l);

		} else {

			for (int i = 0; i < arraylist.size(); i++) {

				JLabel title = new JLabel();

				if (arraylist.get(i).getGlobalEvent() == true) {
					String s = arraylist.get(i).getEventTitle() + " - Global Event!";
					title.setText(s);
					title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
					title.setForeground(new Color(92, 181, 47));
				} else {
					title.setText(arraylist.get(i).getEventTitle());
					title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
					title.setForeground(new Color(218, 78, 8));
				}

				// creates new JLabel of event name for each event



				String a = "Notes :  " + arraylist.get(i).getEventDescription();
				int notesLength = (int)a.length()/62;
				JTextArea description = new JTextArea(a, notesLength, 62);
				description.setLineWrap(true);
				description.setEditable(false);
				
				String b = "Location :  " + arraylist.get(i).getLocation();
				int locationLength = (int)b.length()/62;
				JTextArea location = new JTextArea(b, locationLength, 62);
				location.setLineWrap(true);
				location.setEditable(false);

				JButton edit = new JButton("Edit event");
				JButton delete = new JButton("Delete event");

				description.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				location.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				delete.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				edit.setBackground(Color.LIGHT_GRAY);
//				edit.setForeground(new Color(255, 255, 245));
				delete.setBackground(Color.LIGHT_GRAY);
//				delete.setForeground(new Color(255, 255, 245));

				description.setForeground(Color.DARK_GRAY);
				location.setForeground(Color.DARK_GRAY);

				// creates title border with time of each event
				String s = arraylist.get(i).getStartTime().toString().substring(0, 5) + " - "
						+ arraylist.get(i).getEndTime().toString().substring(0, 5);
				border = BorderFactory.createTitledBorder(s);
				border.setTitleFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));

				// creates new JPanel with title border
				JPanel p = new JPanel();
				//				p.setPreferredSize(new Dimension(635, 300));

				System.out.println("Notes" + a);
				

				p.setMaximumSize(new Dimension(880, 100 + 40 + 35 + 35 + (notesLength+countLines(a)*23) + (locationLength*23)));
				p.setMinimumSize(new Dimension(880, 100 + 40 + 35 + 35 + (notesLength+countLines(a)*23) + (locationLength*23)));
				p.setBorder(border);
				p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
				p.setBackground(Color.WHITE);

				// adds Jlabel to JPanel and adds to events arraylist
				p.add(title);
				title.setAlignmentX(Component.LEFT_ALIGNMENT);
				p.add(Box.createRigidArea(new Dimension(0, 10)));
				p.add(location);
				location.setAlignmentX(Component.LEFT_ALIGNMENT);
				p.add(Box.createRigidArea(new Dimension(0, 5)));
				p.add(description);
				description.setAlignmentX(Component.LEFT_ALIGNMENT);
				p.add(Box.createRigidArea(new Dimension(0, 10)));
				p.add(edit);
				edit.setAlignmentX(Component.LEFT_ALIGNMENT);
				p.add(delete);
				delete.setAlignmentX(Component.LEFT_ALIGNMENT);
				events.add(p);
				Event clickedevent = arraylist.get(i);

				edit.addActionListener((e) -> {

					JDialog.setDefaultLookAndFeelDecorated(true);
					JPanel editEventPopup = new EditEventPanel(controller, model, clickedevent, this);
					event.setPreferredSize(new Dimension(500, 260));
					event.setMinimumSize(new Dimension(500, 260));
					event.setMaximumSize(new Dimension(500, 260));
					JDialog.setDefaultLookAndFeelDecorated(true);
					dialog = new JDialog();
					dialog.setModal(true);
					dialog.add(editEventPopup);
					dialog.setSize(600, 350);
					dialog.setResizable(false);
					// getting the bloody thing to turn up in the center
					final Toolkit toolkit = Toolkit.getDefaultToolkit();
					final Dimension screenSize = toolkit.getScreenSize();
					final int x = (screenSize.width - dialog.getWidth()) / 2;
					final int y = (screenSize.height - dialog.getHeight()) / 2;
					dialog.setLocation(x, y);
					dialog.setVisible(true);

				});

				delete.addActionListener((e) -> {
					JDialog.setDefaultLookAndFeelDecorated(true);
					JPanel deleteEventPopup = new DeleteEvent(controller, model, clickedevent, this);
					event.setPreferredSize(new Dimension(500, 260));
					event.setMinimumSize(new Dimension(500, 260));
					event.setMaximumSize(new Dimension(500, 260));
					dialog = new JDialog();
					dialog.add(deleteEventPopup);
					dialog.setSize(400, 100);
					dialog.setResizable(false);
					// getting the bloody thing to turn up in the center
					final Toolkit toolkit = Toolkit.getDefaultToolkit();
					final Dimension screenSize = toolkit.getScreenSize();
					final int x = (screenSize.width - dialog.getWidth()) / 2;
					final int y = (screenSize.height - dialog.getHeight()) / 2;
					dialog.setLocation(x, y);
					dialog.setVisible(true);
				});
			}

			list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
			list.setBackground(Color.WHITE);

			for (int i = 0; i < events.size(); i++) {
				list.add(events.get(i));
				list.add(Box.createRigidArea(new Dimension(0, 20)));
			}
		}
		this.repaint();
		this.revalidate();
	}

	private static int countLines(String str){
		String[] lines = str.split("\r\n|\r|\n");
		return  lines.length -1;
	}

	// -------------------Creates JLabel for date-----------------------//

	/** Method to create a string for the date
	 * 
	 * @param day int of day of week
	 * @param date int of day of month
	 * @param month int of month
	 * @param year int of year
	 * @return string of full date
	 */
	public static String getDate(int day, int date, int month, int year) {

		StringBuffer s = new StringBuffer();

		switch (day) {
		case 1:
			s.append("Sunday ");
			break;
		case 2:
			s.append("Monday ");
			break;
		case 3:
			s.append("Tuesday ");
			break;
		case 4:
			s.append("Wednesday ");
			break;
		case 5:
			s.append("Thursday ");
			break;
		case 6:
			s.append("Friday ");
			break;
		case 7:
			s.append("Saturday ");
			break;
		}

		s.append(date);

		if (date == 1 || date == 21 || date == 31) {
			s.append("st ");
		} else if (date == 2 || date == 22) {
			s.append("nd ");
		} else if (date == 3 || date == 23) {
			s.append("rd ");
		} else {
			s.append("th ");
		}

		switch (month) {
		case 0:
			s.append("January");
			break;
		case 1:
			s.append("February");
			break;
		case 2:
			s.append("March");
			break;
		case 3:
			s.append("April");
			break;
		case 4:
			s.append("May");
			break;
		case 5:
			s.append("June");
			break;
		case 6:
			s.append("July");
			break;
		case 7:
			s.append("August");
			break;
		case 8:
			s.append("September");
			break;
		case 9:
			s.append("October");
			break;
		case 10:
			s.append("November");
			break;
		case 11:
			s.append("December");
			break;
		}

		s.append(" " + year);

		return s.toString();

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

	// --------------------converts string to time and date----------------//

	/** Changes the time from a string to an sql Time object
	 * 
	 * @param hours string for number of hours
	 * @param minutes string for number of minutes
	 * @return Time object formed from the hours and minutes
	 */
	public Time stringToTime(String hours, String minutes) {
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
		return new Time((h * 3600000) + (m * 60000));
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

	// --------------------------getters and setters-------------------------------------//

	/** Getter for calendar
	 * 
	 * @return calendar object
	 */
	public Calendar getC() {
		return c;
	}

	/** Setter for calendar
	 * 
	 * @param c Calendar
	 */
	public void setC(Calendar c) {
		this.c = c;
		this.model.setCalendar(this.c);
	}

	/**
	 * 
	 */
	public void closeDialog() {
		this.dialog.dispose();
	}

	/**
	 * 
	 */
	public void transferToJFrame(){
		Container panel = this.dialog.getContentPane();
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.saveUserEdits = new JFrame();
		saveUserEdits.add(panel);
		saveUserEdits.setSize(500, 300);
		saveUserEdits.setVisible(true);
		saveUserEdits.setAlwaysOnTop(true);
	}

	/**
	 * 
	 * @param bool
	 */
	public void changeModality(boolean bool){
		dialog.setModal(bool);
	}

	/**
	 * 
	 */
	public void setModalityModeless(){
		dialog.setModalityType(ModalityType.MODELESS);
	}
}
