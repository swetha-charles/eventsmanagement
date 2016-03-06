package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Profile extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MenuPanel bar = new MenuPanel();
	ProfilePanel profile = new ProfilePanel();
	
	public Profile(){
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(profile);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		Profile menu = new Profile();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
