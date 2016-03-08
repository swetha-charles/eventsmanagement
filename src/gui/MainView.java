package gui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.Model;

public class MainView extends JFrame implements Observer {

	Model model = null;
	Controller controller = null;

	JPanel login = null;
	Registration registration = null;
	JPanel loggedIn = null;
	JFrame frame;

	public MainView(Controller controller, Model model) throws IOException {
		this.controller = controller;

		this.login = new Login(this.controller);
		// this.registration = new RegistrationPanel(this.controller);
		this.model = model;

		frame = new JFrame("Calendar");
		// frame.setLayout(new BorderLayout());

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		// frame.setSize(700, 700);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	public void addController(Controller controller) {
		this.controller = controller;
	}
	
	//Observer
	@Override
	public synchronized void update(Observable o, Object arg) {
		System.out.println("View: Update method has been called");
		switch (model.getCurrentState()) {
		case REGISTRATION:

			this.registration = new Registration(this.controller);
			frame.getContentPane().removeAll();
			// System.out.println("Main view: Removed all panes");
			frame.add(this.registration);
			// System.out.println("Main view: Added registration panel");
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
			this.registration.repaint();
			// System.out.println("Main view: Done");
			break;

		case LOGIN:
			frame.setContentPane(this.login);
			break;

		case REGISTRATIONUSEREXISTS:
			this.registration.getRegistrationPanel().setUserLabel(new JLabel("Username already exists!*"));
			frame.getContentPane().removeAll();
			// System.out.println("Main view: Removed all panes");
			frame.add(this.registration);
			// System.out.println("Main view: Added registration panel");
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
			this.registration.repaint();
		case REGISTRATIONUSEROK:
			break;
		default:
			break;

		}

	}

	/*
	 * public static void main(String[] args) throws HeadlessException,
	 * IOException {
	 * 
	 * Login login = new Login(); JFrame frame = new JFrame("Calendar"); //
	 * frame.setLayout(new BorderLayout());
	 * 
	 * JFrame.setDefaultLookAndFeelDecorated(true);
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.setContentPane(login); frame.setSize(Integer.MAX_VALUE,
	 * Integer.MAX_VALUE); frame.setResizable(true); frame.setVisible(true); }
	 */
}
