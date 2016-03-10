package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
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

	public MainView(ObjectClientController objectClientController, Model model) throws IOException {
		this.controller = objectClientController;

		this.login = new Login(this.controller);
		// this.registration = new RegistrationPanel(this.controller);
		this.model = model;
		
		scroll = new JScrollPane(login);

//		frame = new JFrame("Calendar");
//		frame.setLayout(new BorderLayout());
//		frame.add(login);
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(scroll);
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		// frame.setSize(700, 700);
		setResizable(true);
		setVisible(true);
		addWindowListener(new WindowListener() {

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

	public void addController(ObjectClientController controller) {
		this.controller = controller;
	}

	// Observer
	@Override
	public synchronized void update(Observable o, Object arg) {
		//System.out.println("View: Update method has been called");
		switch (model.getCurrentState()) {
//		case REGISTRATION:

//			setLogin(new Registration(this.controller, this.model));
//			scroll = new JScrollPane(new Registration(this.controller, this.model));
////			this.registration = new Registration(this.controller, this.model);
//			getContentPane().removeAll();
////			add(this.registration);
//			setContentPane(scroll);
//			getContentPane().invalidate();
//			getContentPane().validate();
//		 	repaint();
//			break;

		case LOGIN:
			////////FRAME!!!!!!!/////////////////
			getContentPane().removeAll();
			login = new Login(controller);
			add(login);
			getContentPane().invalidate();
			getContentPane().validate();
			repaint();
			break;

		case REGISTRATIONUPDATE:
			if(!model.getEmailMatchesRegex()){
				this.registration.getRegistrationPanel().setEmailLabel("Email incorrect format*");
				repaint();
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
			repaint();
			//System.out.println("email exists or username exists!");
			break;
		// this.registration.repaint();

		case EXIT:
			dispose();
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
