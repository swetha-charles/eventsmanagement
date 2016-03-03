package gui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import controller.Controller;

public class TestView extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	public TestView() throws InterruptedException, IOException {
		TestController controller = new TestController();
		TestModel model = new TestModel();
		controller.add(model);
		Login login = new Login(controller);
		
		frame = new JFrame("Calendar");
		// frame.setLayout(new BorderLayout());

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
	//	frame.setSize(700, 700);
		frame.setResizable(true);
		frame.setVisible(true);
		
		model.addObserver(this);
		System.out.println("Test View: Added  obs");
	}
	

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Test: update  has been called");
		Controller controller = new Controller();
		RegistrationPanel reg = new RegistrationPanel(controller);
		
		System.out.println("Main view:woken up");
		frame.getContentPane().removeAll();
		System.out.println("Main view: Removed all panes");
		frame.add(reg);
		System.out.println("Main view: Added registration panel");
		frame.getContentPane().invalidate();
		frame.getContentPane().validate();
		System.out.println("Main view:: Done");
		
	}
}


