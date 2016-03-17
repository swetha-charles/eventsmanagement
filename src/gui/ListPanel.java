package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.Client;
import gui.RegistrationPanel.JTextFieldLimit;
import model.Model;
import model.ModelState;
import objectTransferrable.Event;

public class ListPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3193985081542643253L;
	Client controller;
	Model model;
	JPanel left = new JPanel();
	
	JPanel list = new JPanel();
	JScrollPane listscroll;
	
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

	
	//----------------------Constructor------------------------------//
	
	public ListPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		
		//sets dimension and layout of the panel
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight()-200)));
		setMinimumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setMaximumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//creates JLabel with the date on
		date = new JLabel(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
		date.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		date.setForeground(Color.DARK_GRAY);
		
		//creates addEvent button
		addEvent.setMargin(new Insets(0, 0, 0, 0));
		addEvent.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
		addEvent.setMinimumSize(new Dimension(35,35));
		addEvent.setMaximumSize(new Dimension(35,35));
		refresh.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		refresh.setMinimumSize(new Dimension(100,35));
		refresh.setMaximumSize(new Dimension(100,35));
		
		//creates panel containing date label and addEvent button
		top.setMaximumSize(new Dimension(900,70));
		top.setMinimumSize(new Dimension(900,70));
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(date);
		top.add(Box.createRigidArea(new Dimension(20,0)));
		top.add(addEvent);
		top.add(Box.createRigidArea(new Dimension(420,0)));
		top.add(refresh);
		
		//updates model with the meetings for today and adds them to panel list
		model.updateMeetings(new Date(c.getTimeInMillis()));
		addMeetings(model.getMeetings());
		
		//adds list to listscroll and sets the size
		listscroll = new JScrollPane(list);
		listscroll.setPreferredSize(new Dimension(900,450));
		listscroll.setMaximumSize(new Dimension(900,450));
		listscroll.setMinimumSize(new Dimension(900,450));
		listscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listscroll.setAlignmentY(Component.TOP_ALIGNMENT);
		
		//creates panel for next and previous buttons
		changeDay.setPreferredSize(new Dimension(900,50));
		changeDay.setLayout(new BoxLayout(changeDay, BoxLayout.LINE_AXIS));
		changeDay.add(previous);
		changeDay.add(next);
		
		//creates
		top.setAlignmentX(Component.CENTER_ALIGNMENT);
		listscroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeDay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(top);
		add(listscroll);
		add(changeDay);
		
		//----------------------Listeners----------------------//
		
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
			JPanel eventPopup = new NewEvent(controller, model);
			event.setLayout(new GridLayout(7,2));
			event.setPreferredSize(new Dimension(500,260));
			event.setMinimumSize(new Dimension(500,260));
			event.setMaximumSize(new Dimension(500,260));
			setEvent(eventPopup, 1);
		});
		
		refresh.addActionListener((e) -> {
			model.updateMeetings(new Date(getC().getTimeInMillis()));
			addMeetings(model.getMeetings());
			model.changeCurrentState(ModelState.EVENTSUPDATE);
		});
		
//		submit.addActionListener((e) -> ;
	}
	

	
	//----------------------Change Date at the top of page------------------------------//
	
	public void setDate(){
		this.date.setText(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
		this.revalidate();
		this.repaint();
	}
	
	//-----------------------Sets event popup---------------------------------------//
	
	public void setEvent(JPanel event, int a) {
		
		if(a==1){
			
			int result = JOptionPane.showConfirmDialog(this, event, "Add event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) {
					
					Time st = stringToTime(((NewEvent) event).getShoursA().getText(), ((NewEvent) event).getSminutesA().getText());
					Time et = stringToTime(((NewEvent) event).getEhoursA().getText(), ((NewEvent) event).getEminutesA().getText());
					Date d = new Date(0);
					try {
						d = stringToDate(((NewEvent) event).getDateA().getText(), ((NewEvent) event).getMonthA().getText(), ((NewEvent) event).getYearA().getText());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println("new date d = " + d.toString());
					
					if (((NewEvent) event).getGlobal() == true){
						model.addEvents(new Event(st, et, ((NewEvent) event).getNotesA().getText(), ((NewEvent) event).getNameA().getText(), 
								((NewEvent) event).getLocationA().getText(), d, true, 1));
					} else {
						model.addEvents(new Event(st, et, ((NewEvent) event).getNotesA().getText(), ((NewEvent) event).getNameA().getText(), 
								((NewEvent) event).getLocationA().getText(), d, false, 1));
					}
					
					if(model.getMeetingCreationSuccessful() == true){
						model.setMeetingCreationSuccessful(false);
						model.updateMeetings(new Date(c.getTimeInMillis()));
						addMeetings(model.getMeetings());
						
						JOptionPane.showMessageDialog(this, "Meeting successfully created!");
					} else {
						JOptionPane.showMessageDialog(this, "I'm sorry your meeting was not able to be created.");
					}
				
				}
			
		} else if (a==2){
			
			int result = JOptionPane.showConfirmDialog(this, event, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			 	if (result == JOptionPane.OK_OPTION) {
			 		
			 		Time st = stringToTime(((EditEventPanel) event).getShoursA().getText(), ((EditEventPanel) event).getSminutesA().getText());
					Time et = stringToTime(((EditEventPanel) event).getEhoursA().getText(), ((EditEventPanel) event).getEminutesA().getText());
					Date d = new Date(0);
					try {
						d = stringToDate(((EditEventPanel) event).getDateA().getText(), ((EditEventPanel) event).getMonthA().getText(), ((EditEventPanel) event).getYearA().getText());
					} catch (ParseException e) {
						e.printStackTrace();
					}
			 		
					Event changedEvent = new Event(st, et, ((EditEventPanel) event).getNotesA().getText(), ((EditEventPanel) event).getNameA().getText(), 
							((EditEventPanel) event).getLocationA().getText(), d, ((EditEventPanel) event).getEvent().getGlobalEvent(), ((EditEventPanel) event).getEvent().getLockVersion() + 1);
							
			 		model.updateEvent(((EditEventPanel) event).getEvent(), changedEvent);
			 		
			 		if(model.getMeetingUpdateSuccessful() == true){
						model.setMeetingUpdateSuccessful(false);
						model.updateMeetings(new Date(c.getTimeInMillis()));
						addMeetings(model.getMeetings());
						
						JOptionPane.showMessageDialog(this, "Meeting successfully changed!");
					} else {
						JOptionPane.showMessageDialog(this, "I'm sorry, this is not the most up to date version of this meeting. "
								+ "\n Please close, refresh your events and try again, thank you!");
					}
			 	}
			 	
		} else {
			
			int result = JOptionPane.showConfirmDialog(this, event, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		 	if (result == JOptionPane.OK_OPTION) {
		 		
						
		 		model.deleteEvent(((DeleteEvent) event).getEvent());
		 		
		 		if(model.getMeetingDeleteSuccessful() == true){
					model.setMeetingDeleteSuccessful(false);
					model.updateMeetings(new Date(c.getTimeInMillis()));
					addMeetings(model.getMeetings());
					
					JOptionPane.showMessageDialog(this, "Meeting successfully deleted!");
				} else {
					JOptionPane.showMessageDialog(this, "I'm sorry, this is not the most up to date version of this meeting. "
							+ "\n Please refresh your events and try again, thank you!");
				}
		 	}
			
		}
		
		this.event = event;
		this.revalidate();
		this.repaint();
	}
	
	
	//---------------------Add meeting panels to listscroll-------------------------//

	public void addMeetings(ArrayList<Event> arraylist){
			
		list.removeAll();
		events.clear();
		if (arraylist.isEmpty()){
			
			list.setPreferredSize(new Dimension(650,800));
			list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
			
			JLabel l = new JLabel("You have no events right now!");
			l.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			l.setForeground(Color.RED);
			list.add(l);
			
		} else {
			
			for(int i=0; i<arraylist.size(); i++){

				JLabel title = new JLabel();
				
				if(arraylist.get(i).getGlobalEvent() == true){
					String s = arraylist.get(i).getEventTitle() + " - Global Event!";
					title.setText(s);
					title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
					title.setForeground(Color.ORANGE);
				} else {
					title.setText(arraylist.get(i).getEventTitle());
					title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
					title.setForeground(Color.RED);
				}
				
				//creates new JLabel of event name for each event
				
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
				String s = arraylist.get(i).getStartTime().toString().substring(0, 5) + " - " + arraylist.get(i).getEndTime().toString().substring(0, 5);
				border = BorderFactory.createTitledBorder(s);
				border.setTitleFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
				
				// creates new JPanel with title border
				JPanel p = new JPanel();
				p.setMaximumSize(new Dimension(890, 200));
				p.setMinimumSize(new Dimension(890, 200));
				p.setBorder(border);
				p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
			
				//adds Jlabel to JPanel and adds to events arraylist 
				p.add(title);
				p.add(Box.createRigidArea(new Dimension(0,10)));
				p.add(location);
				p.add(Box.createRigidArea(new Dimension(0,5)));
				p.add(description);
				p.add(Box.createRigidArea(new Dimension(0,10)));
				p.add(edit);
				p.add(delete);
				events.add(p);	
				Event clickedevent = arraylist.get(i);
				
				edit.addActionListener((e) -> {
					JPanel eventPopup = new EditEventPanel(controller, model, clickedevent);
					event.setPreferredSize(new Dimension(500,260));
					event.setMinimumSize(new Dimension(500,260));
					event.setMaximumSize(new Dimension(500,260));
					setEvent(eventPopup, 2);
				});
				
				delete.addActionListener((e) -> {
					JPanel deleteEvent = new DeleteEvent(controller, model, clickedevent);
					event.setPreferredSize(new Dimension(500,260));
					event.setMinimumSize(new Dimension(500,260));
					event.setMaximumSize(new Dimension(500,260));
					setEvent(deleteEvent, 3);
				});
			}
			
			list.setLayout(new BoxLayout(list, BoxLayout.PAGE_AXIS));
			
			for(int i = 0; i<events.size(); i++){
				list.add(events.get(i));
				list.add(Box.createRigidArea(new Dimension(0,20)));
			}
		}
	
	}
	
	//-------------------Creates JLabel for date-----------------------//
		

public static String getDate(int day, int date, int month, int year){
		
		StringBuffer s = new StringBuffer();
		
		switch(day){
			case 1: s.append("Sunday "); break;
			case 2: s.append("Monday "); break;
			case 3: s.append("Tuesday "); break;
			case 4: s.append("Wednesday "); break;
			case 5: s.append("Thursday "); break;
			case 6: s.append("Friday "); break;
			case 7: s.append("Saturday "); break;
		}
		
		s.append(date);
		
		if (date == 1){
			s.append("st ");
		} else if (date == 2){
			s.append("nd ");
		} else if (date == 3) {
			s.append("rd ");
		} else {
			s.append("th ");
		}
		
		switch(month){
			case 0: s.append("January"); break;
			case 1: s.append("February"); break;
			case 2: s.append("March"); break;
			case 3: s.append("April"); break;
			case 4: s.append("May"); break;
			case 5: s.append("June"); break;
			case 6: s.append("July"); break;
			case 7: s.append("August"); break;
			case 8: s.append("September"); break;
			case 9: s.append("October"); break;
			case 10: s.append("November"); break;
			case 11: s.append("December"); break;
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
	
	//--------------------converts string to time and date----------------//
	
	public Time stringToTime(String hours, String minutes){
		
		int h = Integer.parseInt(hours);
		int m = Integer.parseInt(minutes);
	
		return new Time((h*3600000)+(m*60000));
		
	}
	
	public Date stringToDate(String day, String month, String year) throws ParseException{
		
		String s = day + "." + month + "." + year;
		System.out.println("string s = " + s);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		java.util.Date d = sdf.parse(s);
		System.out.println("date d = " + d.toString());
		
		return new Date(d.getTime());
		
	}
	
	//--------------------------getters and setters-------------------------------------//
	
	public Calendar getC() {
		return c;
	}

	public void setC(Calendar c) {
		this.c = c;
	}

}
