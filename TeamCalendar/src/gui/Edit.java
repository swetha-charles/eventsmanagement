package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import server.ObjectClientController;



public class Edit extends JPanel{

	private static final long serialVersionUID = 1L;
	private ObjectClientController controller = null;
	MenuPanel bar;
	EditPanel edit;
	
	public Edit(ObjectClientController controller){
		
		this.controller = controller;
		bar = new MenuPanel(controller);
		edit = new EditPanel(controller);
				
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(edit);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		ObjectClientController controller = new ObjectClientController();
		
		Edit menu = new Edit(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
