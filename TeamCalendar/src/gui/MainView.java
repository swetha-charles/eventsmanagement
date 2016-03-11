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
	Client controller = null;

	JPanel login = null;
	Registration registration = null;
	JScrollPane scroll;
	JPanel loggedIn = null;

	public MainView(Client client, Model model) {
		this.controller = client;
		this.model = model;
		this.login = new Login(this.controller, this.model);

		scroll = new JScrollPane(login);

		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(scroll);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setResizable(true);
		setVisible(true);

		// --------------Lambda Listeners-------------------//

		this.addWindowListener((WindowClosingListener) (e) -> model.changeCurrentState(ModelState.EXIT));

		// -------------End Lambda Listeners---------------//

	}

	public JPanel getLogin() {
		return login;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setLogin(JPanel login) {
		this.login = login;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public void addController(Client controller) {
		this.controller = controller;
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		if (model.getCurrentState().equals(ModelState.EXIT)) {
			this.dispose();

		} else {
			this.setContentPane(model.getCurrentPanel());
			this.validate();
		}

	}

}
