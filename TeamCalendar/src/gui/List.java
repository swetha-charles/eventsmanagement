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
	
	private static final long serialVersionUID = -4150977899101941440L;
	private Model model;
	Client controller;
	MenuPanel bar;
	ListPanel list;
	
	/** Constructor to build main home view (contains menu bar)
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 */
	public List(Client controller, Model model){
		this.controller = controller;
		this.model = model;
		list = new ListPanel(controller, model);
		bar = new MenuPanel(controller, model, list);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(1000,650));
		setMaximumSize(new Dimension(1000,650));
		setMinimumSize(new Dimension(1000,650));

		add(bar);
		add(list);
	}
	
	/** Getter for the listPanel
	 * 
	 * @return ListPanel
	 */
	public ListPanel getListPanel(){
		return this.list;
	}

	public MenuPanel getMenuPanel(){
		return this.bar;
	}
	
	public void refresh(){
		this.removeAll();
		this.add(bar);
		this.add(list);
	}
}
