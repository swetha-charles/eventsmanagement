package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Password extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891502734315534592L;
	MenuPanel bar = new MenuPanel();
	PasswordPanel password = new PasswordPanel();
	
	public Password(){
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(password);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		Password menu = new Password();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
