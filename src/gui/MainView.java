package gui;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;

public class MainView {
	
	public static void main(String[] args) throws HeadlessException, IOException {
		
		Login login = new Login();
		JFrame frame = new JFrame("Calendar");
//		frame.setLayout(new BorderLayout());
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
