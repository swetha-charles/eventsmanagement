package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client.Client;
import listener.interfaces.MouseClickedListener;
import model.Model;
import model.ModelState;

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
	JLabel name = new JLabel("Event name");
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
	
	TitledBorder title;
	
	ArrayList<JLabel> times = new ArrayList<JLabel>();
	ArrayList<JPanel> timeevent = new ArrayList<JPanel>();
	ArrayList<JLabel> eventNames = new ArrayList<JLabel>();
	
	public ListPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight()-200)));
		setMinimumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setMaximumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		date=getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		
//		date.setPreferredSize(new Dimension(100,50));
		date.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		date.setForeground(Color.DARK_GRAY);
		
		addEvent.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
		addEvent.setMinimumSize(new Dimension(35,35));
		addEvent.setMaximumSize(new Dimension(35,35));
		
		top.setMaximumSize(new Dimension(600,70));
		top.setLayout(new BoxLayout(top, BoxLayout.LINE_AXIS));
		top.add(date);
		top.add(Box.createRigidArea(new Dimension(20,0)));
		top.add(addEvent);
		
		//for each event do this
		for(int i=0; i<24; i++){
//			l.setPreferredSize(new Dimension(20,100));
			JLabel m = new JLabel("Event name", SwingConstants.LEFT);
			m.setVerticalAlignment(SwingConstants.CENTER);
			m.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
			m.setForeground(Color.DARK_GRAY);
			eventNames.add(m);
			JPanel p = new JPanel();
			title = BorderFactory.createTitledBorder(model.getMeetings().get(i).getStartTime().get(Calendar.HOUR_OF_DAY));
			Border blackline = BorderFactory.createLineBorder(Color.red);
			title.setTitleFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
//			title.setForeground(Color.GRAY);
			p.setBorder(title);
			timeevent.add(p);
		}
		
		list.setPreferredSize(new Dimension(650,800));
		list.setLayout(new GridLayout(24,2));
		
		for(int i = 0; i<24; i++){
			timeevent.get(i).add(eventNames.get(i));
			list.add(timeevent.get(i));
		}
		
		listscroll = new JScrollPane(list);
		listscroll.setPreferredSize(new Dimension(660,500));
		listscroll.setMaximumSize(new Dimension(660,500));
		listscroll.setMinimumSize(new Dimension(660,500));
		listscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listscroll.setAlignmentY(Component.TOP_ALIGNMENT);
		
		
		event.setLayout(new GridLayout(7,2));
		event.setPreferredSize(new Dimension(400,200));
		event.setMinimumSize(new Dimension(400,200));
		event.setMaximumSize(new Dimension(400,200));
		
		name.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		eventName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		descriptionLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		decription.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		dateLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		dateA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		timeLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		timeA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		locationLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		locationA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		submit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		
		name.setForeground(Color.DARK_GRAY);
		descriptionLabel.setForeground(Color.GRAY);
		decription.setForeground(Color.DARK_GRAY);
		dateLabel.setForeground(Color.GRAY);
		dateA.setForeground(Color.DARK_GRAY);
		timeLabel.setForeground(Color.GRAY);
		timeA.setForeground(Color.DARK_GRAY);
		locationLabel.setForeground(Color.GRAY);
		locationA.setForeground(Color.DARK_GRAY);
		
		changeDay.setPreferredSize(new Dimension(610,50));
		changeDay.setLayout(new BoxLayout(changeDay, BoxLayout.LINE_AXIS));
		changeDay.add(previous);
		top.add(Box.createRigidArea(new Dimension(370,0)));
		changeDay.add(next);
		
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
		left.setPreferredSize(new Dimension(750, 700));
		top.setAlignmentX(Component.CENTER_ALIGNMENT);
		listscroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeDay.setAlignmentX(Component.CENTER_ALIGNMENT);
		left.add(top);
		left.add(listscroll);
		left.add(changeDay);
		
		add(Box.createRigidArea(new Dimension(50,0)));
		add(left);
		add(Box.createRigidArea(new Dimension(50,0)));
		add(event);
		
		//----------------------Listeners----------------------//
		
		for(int i=0; i<timeevent.size(); i++){
			timeevent.get(i).addMouseListener((MouseClickedListener) (e) ->{
				setEvent(event, 2);
			});
		}
		
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				c.add(Calendar.DATE, 1);
				setDate(date, c);
			}
		});
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				c.add(Calendar.DATE, -1);
				setDate(date, c);
			}
		});
		
		addEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				setEvent(event, 1);		
			}
		});
	}
	
	
	
	public void setEvent(JPanel event, int a) {
		if(a==1){
//			set panel as add new event
			event.add(name);
			event.add(new JTextField());
			event.add(descriptionLabel);
			event.add(new JTextField());
			event.add(dateLabel);
			event.add(new JTextField());
			event.add(timeLabel);
			event.add(new JTextField());
			event.add(locationLabel);
			event.add(new JTextField());
			event.add(submit);
			
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
	}
	
	public void setDate(JLabel date, Calendar c){
		date = getDate(c.get(Calendar.DAY_OF_WEEK), c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		this.date = date;
	}

	public static void getMeetings(){
		//get tuples with todays date in ascending order of time
		//while there is a next store in arraylist of array and return
	}
	
	public static void addMeetings(){
		//get times from getMeetings and put in arraylist of strings
		//get event name and put in arrylist of strings
		//make arraylist of JLabels with times and name
		//make arraylist of JPanels with JLabels inside and return
	}
	
	public static JLabel getDate(int day, int date, int month, int year){
		
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
		
		return new JLabel(s.toString());
		
	}
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
		Client controller = new Client();
		
		ListPanel loginPanel = new ListPanel(controller);
		JScrollPane scroll = new JScrollPane(loginPanel);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(scroll);
		frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
