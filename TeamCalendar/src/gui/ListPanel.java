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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import model.Model;
import model.ModelState;
import objectTransferrable.Event;

public class ListPanel extends JPanel {

	/**
	 * 
	 */
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

	public ListPanel(Client controller, Model model) {

		this.controller = controller;
		this.model = model;

		// sets dimension and layout of the panel
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int) dimension.getWidth(), (int) (dimension.getHeight() - 200)));
		setMinimumSize(new Dimension((int) dimension.getWidth(), (int) dimension.getHeight() - 200));
		setMaximumSize(new Dimension((int) dimension.getWidth(), (int) dimension.getHeight() - 200));
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
		refresh.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		refresh.setMinimumSize(new Dimension(100, 35));
		refresh.setMaximumSize(new Dimension(100, 35));

		// creates panel containing date label and addEvent button
		top.setMaximumSize(new Dimension(900, 70));
		top.setMinimumSize(new Dimension(900, 70));
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(date);
		top.add(Box.createRigidArea(new Dimension(20, 0)));
		top.add(addEvent);
		top.add(Box.createRigidArea(new Dimension(420, 0)));
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
			event.setPreferredSize(new Dimension(500, 260));
			event.setMinimumSize(new Dimension(500, 260));
			event.setMaximumSize(new Dimension(500, 260));
			dialog = new JDialog();
			dialog.add(eventPopup);
			dialog.setSize(600, 320);
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
		});

		// submit.addActionListener((e) -> ;
	}

	// ---------Change Date at the top of page---------//

	public void setDate() {
		this.date.setText(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH),
				c.get(Calendar.YEAR)));
		this.revalidate();
		this.repaint();
	}

	// ------------Add meeting panels to listscroll-----------------//

	public void addMeetings(ArrayList<Event> arraylist) {

		list.removeAll();
		events.clear();
		if (arraylist.isEmpty()) {

			list.setPreferredSize(new Dimension(650, 800));
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
					title.setForeground(Color.ORANGE);
				} else {
					title.setText(arraylist.get(i).getEventTitle());
					title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
					title.setForeground(Color.RED);
				}

				// creates new JLabel of event name for each event

				String a = "Notes :  " + arraylist.get(i).getEventDescription();
				JLabel description = new JLabel(a);
				String b = "Location :  " + arraylist.get(i).getLocation();
				JLabel location = new JLabel(b);
				JButton edit = new JButton("Edit event");
				JButton delete = new JButton("Delete event");

				description.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				location.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				delete.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));

				description.setForeground(Color.DARK_GRAY);
				location.setForeground(Color.DARK_GRAY);

				// creates title border with time of each event
				String s = arraylist.get(i).getStartTime().toString().substring(0, 5) + " - "
						+ arraylist.get(i).getEndTime().toString().substring(0, 5);
				border = BorderFactory.createTitledBorder(s);
				border.setTitleFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));

				// creates new JPanel with title border
				JPanel p = new JPanel();
				p.setMaximumSize(new Dimension(890, 200));
				p.setMinimumSize(new Dimension(890, 200));
				p.setBorder(border);
				p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

				// adds Jlabel to JPanel and adds to events arraylist
				p.add(title);
				p.add(Box.createRigidArea(new Dimension(0, 10)));
				p.add(location);
				p.add(Box.createRigidArea(new Dimension(0, 5)));
				p.add(description);
				p.add(Box.createRigidArea(new Dimension(0, 10)));
				p.add(edit);
				p.add(delete);
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
					dialog.setSize(600, 300);
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
					JDialog.setDefaultLookAndFeelDecorated(true);
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

			for (int i = 0; i < events.size(); i++) {
				list.add(events.get(i));
				list.add(Box.createRigidArea(new Dimension(0, 20)));
			}
		}
		this.repaint();
		this.revalidate();
	}

	// -------------------Creates JLabel for date-----------------------//

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

		if (date == 1) {
			s.append("st ");
		} else if (date == 2) {
			s.append("nd ");
		} else if (date == 3) {
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

	public Time stringToTime(String hours, String minutes) {
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
		Time time = new Time(((h-1) * 3600000) + (m * 60000));
		return time;
	}

	public Date stringToDate(String day, String month, String year) {
		return this.model.sanitizeDateAndMakeSQLDate(day, month, year);

	}

	// --------------------------getters and setters-------------------------------------//

	public Calendar getC() {
		return c;
	}

	public void setC(Calendar c) {
		this.c = c;
	}

	public void closeDialog() {
		this.dialog.dispose();
	}
	
	public void transferToJFrame(){
		Container panel = this.dialog.getContentPane();
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.saveUserEdits = new JFrame();
		saveUserEdits.add(panel);
		saveUserEdits.setSize(500, 300);
		saveUserEdits.setVisible(true);
		saveUserEdits.setAlwaysOnTop(true);
		
	}
	public void changeModality(boolean bool){
		dialog.setModal(bool);
	}
	public void setModalityModeless(){
		dialog.setModalityType(ModalityType.MODELESS);
	}
}
