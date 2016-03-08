package gui;

import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import model.State;
import server.ObjectClientController;

public class MainView extends JFrame implements Observer {

	Model model = null;
	ObjectClientController controller = null;

	JPanel login = null;
	Registration registration = null;
	JPanel loggedIn = null;
	JFrame frame;

	public MainView(ObjectClientController objectClientController, Model model) throws IOException {
		this.controller = objectClientController;

		this.login = new Login(this.controller);
		// this.registration = new RegistrationPanel(this.controller);
		this.model = model;

		frame = new JFrame("Calendar");
		// frame.setLayout(new BorderLayout());

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		// frame.setSize(700, 700);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				MainView.this.model.changeCurrentState(State.EXIT);

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void addController(ObjectClientController controller) {
		this.controller = controller;
	}

	// Observer
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

		case REGISTRATIONUPDATE:
			if (model.getRegUsernameExists()) {
				this.registration.getRegistrationPanel().setUserLabel("Username already exists!*");
			} else {
				this.registration.getRegistrationPanel().setUserLabel("Username*");
			}

			if (model.getRegEmailExists()) {
				this.registration.getRegistrationPanel().setEmailLabel("Email already exists!*");
			} else {
				this.registration.getRegistrationPanel().setEmailLabel("Email*");
			}
			this.registration.repaint();
			System.out.println("email exists or username exists!");
			break;
		// this.registration.repaint();

		case EXIT:
			frame.dispose();
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
