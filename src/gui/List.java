package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.ObjectClientController;

public class List extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4150977899101941440L;
	MenuPanel bar;
	ListPanel list;
	
	public List(ObjectClientController controller){
		
		bar = new MenuPanel(controller);
		list = new ListPanel(controller);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(list);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		ObjectClientController controller = new ObjectClientController();
		
		List menu = new List(controller);
		JScrollPane scroll = new JScrollPane(menu);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(scroll);
		frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
