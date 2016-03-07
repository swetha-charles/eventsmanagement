package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

public class Edit extends JPanel{

	private static final long serialVersionUID = 1L;
	private Controller controller = null;
	MenuPanel bar;
	EditPanel edit;
	
	public Edit(Controller controller){
		
		this.controller = controller;
		bar = new MenuPanel(controller);
		edit = new EditPanel(controller);
				
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(edit);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Controller controller = new Controller();
		
		Edit menu = new Edit(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
