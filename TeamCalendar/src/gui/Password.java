package gui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;

public class Password extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6891502734315534592L;
	private Client controller = null;
	MenuPanel bar;
	PasswordPanel password;
	
	public Password(Client controller){
		
		this.controller = controller;
		bar = new MenuPanel(controller);
		password = new PasswordPanel(controller);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(password);
	}

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Client controller = new Client();
		
		Password menu = new Password(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(menu);
		frame.setSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
