package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import client.Client;
import model.Model;

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
	
	
	JPanel top = new JPanel();
	JLabel date;
	JButton addEvent = new JButton("+");
	
	JPanel changeDay = new JPanel();
	JButton previous = new JButton("Previous Day");
	JButton next = new JButton("Next Day");
	
	ArrayList<JLabel> times = new ArrayList<JLabel>();
	ArrayList<JPanel> timeevent = new ArrayList<JPanel>();
	
	public ListPanel(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)(dimension.getHeight()-200)));
		setMinimumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setMaximumSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-200));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.date=getDate();
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
		
		for(int i=0; i<24; i++){
			String s = Integer.toString(i)+":00"; 
			JLabel l = new JLabel(s, SwingConstants.LEFT);
			l.setVerticalAlignment(SwingConstants.CENTER);
			l.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
			l.setForeground(Color.GRAY);
			l.setPreferredSize(new Dimension(20,100));
			times.add(l);
			JPanel p = new JPanel();
			timeevent.add(p);
		}
		
		list.setPreferredSize(new Dimension(650,800));
		list.setLayout(new GridLayout(24,2));
		
		for(int i = 0; i<24; i++){
			list.add(times.get(i));
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
		
		eventName = new JLabel("Group Meeting");
		decription = new JLabel("Software Workshop group project meeting.");
		dateA = new JLabel("12/05/2016");
		timeA = new JLabel("13:00");
		locationA = new JLabel("CS 222");
		
		eventName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		descriptionLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		decription.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		dateLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		dateA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		timeLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		timeA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		locationLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		locationA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		
		descriptionLabel.setForeground(Color.GRAY);
		decription.setForeground(Color.DARK_GRAY);
		dateLabel.setForeground(Color.GRAY);
		dateA.setForeground(Color.DARK_GRAY);
		timeLabel.setForeground(Color.GRAY);
		timeA.setForeground(Color.DARK_GRAY);
		locationLabel.setForeground(Color.GRAY);
		locationA.setForeground(Color.DARK_GRAY);
		
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
	
	public static JLabel getDate(){
		
		Calendar c = new GregorianCalendar();
		int day = c.get(Calendar.DAY_OF_WEEK);
		int date = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);
		
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
