package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import server.ObjectClientController;

public class ListPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3193985081542643253L;
	ObjectClientController controller;
	JPanel list = new JPanel();
	JScrollPane listscroll;
	JPanel event = new JPanel();
	JLabel date;
	JButton edit = new JButton("Edit Event");
	JButton previous;
	JButton next;
	ArrayList<JLabel> time = new ArrayList<JLabel>(24);
	ArrayList<JPanel> timeevent = new ArrayList<JPanel>(24);
	
	public ListPanel(ObjectClientController controller){
		
		this.controller = controller;
		Dimension dimension = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		setPreferredSize(new Dimension((int)dimension.getWidth(), (int)dimension.getHeight()-70));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
//		Calendar c = new GregorianCalendar();
//		int month = c.get(Calendar.HOUR_OF_DAY);
		
		for(int i=0; i<24; i++){
			String s = Integer.toString(i)+":00"; 
			JLabel l = new JLabel(s, SwingConstants.LEFT);
			l.setVerticalAlignment(SwingConstants.CENTER);
			l.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 10));
			l.setForeground(Color.GRAY);
			l.setPreferredSize(new Dimension(20,100));
			time.add(l);
			JPanel p = new JPanel();
			timeevent.add(p);
		}
		
		list.setPreferredSize(new Dimension(600,800));
		list.setLayout(new GridLayout(24,2));
		
		for(int i = 0; i<24; i++){
			list.add(time.get(i));
			list.add(timeevent.get(i));
		}
		
		listscroll = new JScrollPane(list);
		listscroll.setPreferredSize(new Dimension(610,500));
		listscroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		edit.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
		event.add(new JLabel("EVENT"));
		event.add(edit);
		
		add(listscroll);
		add(event);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
		ObjectClientController controller = new ObjectClientController();
		
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
