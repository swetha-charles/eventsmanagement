package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

public class Profile extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller = null;
	MenuPanel bar;
	ProfilePanel profile;
	
	public Profile(Controller controller){
		
		this.controller = controller;
		bar = new MenuPanel(controller);
		profile = new ProfilePanel(controller);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(profile);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Controller controller = new Controller();
		
		Profile menu = new Profile(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
