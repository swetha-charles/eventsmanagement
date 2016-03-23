package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import client.Client;
import listener.interfaces.*;
import model.Model;
import model.ModelState;

/** Class which creates a JFrame object which is the view
 * 
 * @author swethacharles
 *
 */
public class MainView extends JFrame implements Observer {

	Model model = null;
	Client client = null;

	/** Constructor to build MainView
	 * 
	 * @param client an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 */
	public MainView(Client client, Model model) {
		this.client = client;
		this.model = model;
		JScrollPane scroll;
		JPanel login = new Login(this.client, this.model);

		scroll = new JScrollPane(login);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(scroll);
		setPreferredSize(new Dimension(1100,690));
		setMaximumSize(new Dimension(1100,690));
		setMinimumSize(new Dimension(1100,690));
		setResizable(false);
		setVisible(true);

		// --------------Lambda Listeners-------------------//

		this.addWindowListener((WindowClosingListener) (e) -> {
			if(client.getError()){
				this.dispose();
			} else {
				model.changeCurrentState(ModelState.EXIT);
			}
		
		});

		// -------------End Lambda Listeners---------------//

	}


	/** Overriding the update method that is used for the observer
	 * 
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		if (model.getCurrentState().equals(ModelState.EXIT)) {
			this.dispose();

		} else {
			this.setContentPane(model.getCurrentScrollPanel());
			this.validate();
		}

	}

}
