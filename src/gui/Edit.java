package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Edit extends JPanel{

	private static final long serialVersionUID = 1L;
	MenuPanel bar = new MenuPanel();
	EditPanel edit = new EditPanel();
	
	public Edit(){
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(edit);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		Edit menu = new Edit();
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
