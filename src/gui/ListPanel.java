package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Date;
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

import client.Client;
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
	
	JPanel eventsListPanel = new JPanel();
	JScrollPane listscroll;
	
	JPanel event = new JPanel();
	JLabel name = new JLabel("Event title");
	JLabel eventName;
	JLabel empty = new JLabel("");
	JLabel descriptionLabel = new JLabel("Description");
	JLabel decription;
	JLabel dateLabel = new JLabel("Date");
	JLabel dateA;
	JLabel timeLabel = new JLabel("Time");
	JLabel timeA;
	JLabel locationLabel = new JLabel("Location");
	JLabel locationA;
	JButton edit = new JButton("Edit Event");
	JButton submit = new JButton("Add Event");
	
	Calendar c = new GregorianCalendar();
	
	JPanel top = new JPanel();
	JLabel date;
	JButton addEvent = new JButton("+");
	
	JPanel changeDay = new JPanel();
	JButton previous = new JButton("Previous Day");
	JButton next = new JButton("Next Day");
	
	static TitledBorder border;
	
	ArrayList<JLabel> times = new ArrayList<JLabel>();
	static ArrayList<JPanel> events = new ArrayList<JPanel>();
	
	Event clickedEvent;
	
	//----------------------Constructor------------------------------//
	
	public ListPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		//clickedEvent = new Event(null);
		
		//sets dimension and layout of the panel
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight()-200)));
		setMinimumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setMaximumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		date= new JLabel(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
		
//		date.setPreferredSize(new Dimension(100,50));
		date.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		date.setForeground(Color.DARK_GRAY);
		
		//creates addEvent button
		addEvent.setMargin(new Insets(0, 0, 0, 0));
		addEvent.setBackground(Color.PINK);
		addEvent.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		addEvent.setMinimumSize(new Dimension(35,35));
		addEvent.setMaximumSize(new Dimension(35,35));
		
		//creates panel containing date label and addEvent button
		top.setMaximumSize(new Dimension(900,70));
		top.setMinimumSize(new Dimension(900,70));
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(date);
		top.add(Box.createRigidArea(new Dimension(20,0)));
		top.add(addEvent);
		
		//updates model with the meetings for today and adds them to panel list
		model.updateMeetings(new Date(c.getTimeInMillis()));
		addMeetings(model.getMeetings());
		
		//adds list to listscroll and sets the size
		listscroll = new JScrollPane(eventsListPanel);
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
		
		event.add(name);
		event.add(descriptionLabel);
		event.add(dateLabel);
		event.add(timeLabel);
		event.add(locationLabel);
		
		
		//----------------------Listeners----------------------//
		
		previous.addActionListener((e) -> {
			c.add(Calendar.DATE, -1);
			setDate();
			model.updateMeetings(new Date(c.getTimeInMillis()));
			addMeetings(model.getMeetings());
			model.changeCurrentState(ModelState.EVENTSUPDATE);
		});
		
		next.addActionListener((e) -> {
			c.add(Calendar.DATE, 1);
			setDate();
			model.updateMeetings(new Date(c.getTimeInMillis()));
			addMeetings(model.getMeetings());
			model.changeCurrentState(ModelState.EVENTSUPDATE);
			
		});
		
		addEvent.addActionListener((e) -> {
			JPanel eventPopup = new JPanel();
			event.setLayout(new GridLayout(7,2));
			event.setPreferredSize(new Dimension(400,200));
			event.setMinimumSize(new Dimension(400,200));
			event.setMaximumSize(new Dimension(400,200));
			setEvent(eventPopup, 1);
		});
		
//		submit.addActionListener((e) -> ;
	}
	

	
	//---------------------Change Event Panel----------------------------//
	
	public void setEvent(JPanel event, int a) {
		if(a==1){
			
			event.setLayout(new GridLayout(7,2));
			event.setPreferredSize(new Dimension(400,200));
			event.setMinimumSize(new Dimension(400,200));
			event.setMaximumSize(new Dimension(400,200));
			
//			set panel as add new event
			event.add(name);
			JTextField newEventTitle = new JTextField();
			event.add(newEventTitle);
			
			event.add(descriptionLabel);
			JTextField newEventDescription = new JTextField();
			event.add(newEventDescription);
		
			event.add(dateLabel);
			JTextField newEventDate= new JTextField();
			event.add(newEventDate);
			
			event.add(timeLabel);
			JTextField newEventTime= new JTextField();
			event.add(newEventTime);
			
			event.add(locationLabel);
			JTextField newEventLocation  = new JTextField();
			event.add(newEventLocation);

			event.add(submit);
			int result = JOptionPane.showConfirmDialog(this, event, "Add event", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			 if (result == JOptionPane.OK_OPTION) {
				 
		           model.addEvents(newEventTitle.getText(), newEventDescription.getText(), 
		        		   newEventDate.getText(), newEventTime.getText(), newEventLocation.getText());
		        } 
			
		} else {
//			set panel as event clicked on
			eventName = new JLabel();
			decription = new JLabel();
			dateA = new JLabel();
			timeA = new JLabel();
			locationA = new JLabel();
			
			event.add(eventName);
			event.add(empty);
			event.add(descriptionLabel);
			event.add(decription);
			event.add(dateLabel);
			event.add(dateA);
			event.add(timeLabel);
			event.add(timeA);
			event.add(locationLabel);
			event.add(locationA);
			event.add(edit);
		}
		
		this.event = event;
		this.revalidate();
		this.repaint();
	}
	
	//----------------------Change Date at the top of page------------------------------//
	
	public void setDate(){
		this.date.setText(getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR)));
		this.revalidate();
		this.repaint();
		
	}
	
		
	//---------------------Add meeting panels to listscroll-------------------------//

	public void addMeetings(ArrayList<Event> arraylist){
		this.eventsListPanel.removeAll();
		events.clear();
		if (arraylist.isEmpty()){
			
			eventsListPanel.setPreferredSize(new Dimension(650,800));
			eventsListPanel.setLayout(new GridLayout(1,1));
			
			JLabel l = new JLabel("You have no events right now!");
			l.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
			l.setForeground(Color.RED);
			eventsListPanel.add(l);
			
		} else {
			
			for(int i=0; i<arraylist.size(); i++){
			
				//creates new JLabel of event name for each event
				JLabel title = new JLabel(arraylist.get(i).getEventTitle(), SwingConstants.LEFT);
				String a = "Notes :  " + arraylist.get(i).getEventDescription();
				JLabel description = new JLabel(a);
				String b = "Location :  " + arraylist.get(i).getLocation();
				JLabel location = new JLabel(b);
				JButton edit = new JButton("Edit event");
				
				title.setVerticalAlignment(SwingConstants.CENTER);
				description.setVerticalAlignment(SwingConstants.CENTER);
				location.setVerticalAlignment(SwingConstants.CENTER);
				edit.setVerticalAlignment(SwingConstants.CENTER);
				
				title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
				description.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				location.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
				edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
				
				title.setForeground(Color.RED);
				description.setForeground(Color.DARK_GRAY);
				location.setForeground(Color.DARK_GRAY);
			
				// creates title border with time of each event
				String s = arraylist.get(i).getStartTime().toString().substring(0, 5) + " - " + arraylist.get(i).getEndTime().toString().substring(0, 5);
//				Border line = BorderFactory.createLineBorder(Color.red);
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
				events.add(p);	
			}
			
		
			eventsListPanel.setPreferredSize(new Dimension(650,800));
			eventsListPanel.setLayout(new GridLayout(events.size(),1));
			
			for(int i = 0; i<events.size(); i++){
				eventsListPanel.add(events.get(i));
				eventsListPanel.add(Box.createRigidArea(new Dimension(0,20)));
			}
		}
		
		
	}
	
	//---------------------Says which event is clicked on--------------//
	
//	public static void setEventClickedOn(){
//		Event eventClicked = new Event()
//		
//	}
	
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
	
	public void getEventsListPanel(){
		
	}

}
