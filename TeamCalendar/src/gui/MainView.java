package gui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import controller.Controller;
import model.Model;

public class MainView extends JFrame implements Observer {

	Model model = null;
	Controller controller = null;

	JPanel login = null;
	JPanel registration = null;
	JFrame frame;

	public MainView(Controller controller, Model model) throws IOException {
		this.controller = controller;

		this.login = new Login(this.controller);
	//	this.registration = new RegistrationPanel(this.controller);
		this.model = model;
		
		frame = new JFrame("Calendar");
		// frame.setLayout(new BorderLayout());

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
	//	frame.setSize(700, 700);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	public void addController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		System.out.println("View: Update method has been called");
		switch (model.getCurrentState()) {
		case REGISTRATION:
			RegistrationPanel reg = new RegistrationPanel(this.controller);
			
			System.out.println("Main view: registration state entered");
			frame.getContentPane().removeAll();
			System.out.println("Main view: Removed all panes");
			frame.add(reg);
			System.out.println("Main view: Added registration panel");
			frame.getContentPane().invalidate();
			frame.getContentPane().validate();
			reg.repaint();
			System.out.println("Main view: Done");
			this.registration = reg;
			break;

		case LOGIN:
			frame.setContentPane(this.login);
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
