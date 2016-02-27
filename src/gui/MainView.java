package gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;

public class MainView {
	
	public static void main(String[] args) throws HeadlessException, IOException {
		
		Login login = new Login();
		JFrame frame = new JFrame("Calendar");
		frame.setLayout(new BorderLayout());
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(login);
		frame.setSize(1000,650);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
