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

import client.Client;
import listener.interfaces.*;
import model.Model;
import model.ModelState;

public class MainView extends JFrame implements Observer {

	Model model = null;
	Client client = null;
;

	public MainView(Client client, Model model) {
		this.client = client;
		this.model = model;
		JScrollPane scroll;
		JPanel login = new Login(this.client, this.model);

		scroll = new JScrollPane(login);

		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(scroll);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setResizable(true);
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
