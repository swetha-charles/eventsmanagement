package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import model.Model;

public class Password extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891502734315534592L;
	private Client controller = null;
	private Model model;
	MenuPanel bar;
	PasswordPanel password;
	
	public Password(Client controller, Model model){
		
		this.controller = controller;
		this.model = model;
		bar = new MenuPanel(controller, model);
		password = new PasswordPanel(controller, model);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(password);
	}

//	public static void main(String[] args) {
//		
//		JFrame frame = new JFrame();
//		Client controller = new Client();
//		
//		Password menu = new Password(controller);
//		
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setContentPane(menu);
//		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
//		frame.setResizable(true);
//		frame.setVisible(true);
//	}

}
