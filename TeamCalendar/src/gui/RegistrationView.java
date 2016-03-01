package gui;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;

public class RegistrationView {
	public static void main(String[] args) throws HeadlessException, IOException {

		Registration register = new Registration();
		JFrame frame = new JFrame("Register");
		// frame.setLayout(new BorderLayout());

		JFrame.setDefaultLookAndFeelDecorated(true);
		
		frame.add(register);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
