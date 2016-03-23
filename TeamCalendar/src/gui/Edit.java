package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import model.Model;



public class Edit extends JPanel{

	private static final long serialVersionUID = 1L;
	private Client controller = null;
	private Model model;
	MenuPanel bar;
	EditPanel edit;
	
	public Edit(Client controller, Model model){
		
		this.controller = controller;
		
		this.model = model;
		bar = new MenuPanel(controller, model);
		edit = new EditPanel(controller, model);
				
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(edit);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Client controller = new Client();
		Model model = new Model(controller);
		Edit menu = new Edit(controller, model);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
