package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.Client;
import model.Model;

public class List extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4150977899101941440L;
	private Model model;
	Client controller;
	MenuPanel bar;
	ListPanel list;
	
	public List(Client controller, Model model){
		this.controller = controller;
		this.model = model;
		bar = new MenuPanel(controller, model);
		list = new ListPanel(controller, model);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(bar);
		add(list);
	}
	
	public ListPanel getListPanel(){
		return this.list;
	}

	public static void main(String[] args) {
		/*
		JFrame frame = new JFrame();
		Client client = new Client();
		
		List menu = new List(controller);
		JScrollPane scroll = new JScrollPane(menu);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(scroll);
		frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.setResizable(true);
		frame.setVisible(true);*/
	}

}
