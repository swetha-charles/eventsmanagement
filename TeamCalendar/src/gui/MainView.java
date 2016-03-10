package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Model;
import model.State;
import server.ObjectClientController;

public class MainView extends JFrame implements Observer {

	Model model = null;
	ObjectClientController controller = null;

	JPanel login = null;
	Registration registration = null;
	JScrollPane scroll;
	JPanel loggedIn = null;
	JFrame frame;

	public MainView(ObjectClientController objectClientController, Model model) throws IOException {
		this.controller = objectClientController;

		this.login = new Login(this.controller);
		// this.registration = new RegistrationPanel(this.controller);
		this.model = model;
		
		scroll = new JScrollPane(login);

		frame = new JFrame("Calendar");
		// frame.setLayout(new BorderLayout());
//		frame.add(login);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(scroll);
		frame.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
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
		//System.out.println("View: Update method has been called");
		switch (model.getCurrentState()) {
		case REGISTRATION:

			this.registration = new Registration(this.controller, this.model);
			frame.getContentPane().removeAll();
			frame.add(this.registration);
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
		 	frame.repaint();
			break;

		case LOGIN:
			////////FRAME!!!!!!!/////////////////
			frame.getContentPane().removeAll();
			login = new Login(controller);
			frame.add(login);
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
			frame.repaint();
			break;

		case REGISTRATIONUPDATE:
			if(!model.getEmailMatchesRegex()){
				this.registration.getRegistrationPanel().setEmailLabel("Email incorrect format*");
				frame.repaint();
				break;
			} else {
				this.registration.getRegistrationPanel().setEmailLabel("Email*");
			}
			if (model.getUsernameExists()) {
				this.registration.getRegistrationPanel().setUserLabel("Username already exists!*");
			} else {
				this.registration.getRegistrationPanel().setUserLabel("Username*");
			}

			if (model.getEmailExists()) {
				this.registration.getRegistrationPanel().setEmailLabel("Email already exists!*");
			} else {
				this.registration.getRegistrationPanel().setEmailLabel("Email*");
			}
			frame.repaint();
			//System.out.println("email exists or username exists!");
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
