package gui;

import java.io.IOException;

import javax.swing.JLayeredPane;

public class Login extends JLayeredPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Login() throws IOException{
		
		Background background = new Background();
		LoginPanel loginPanel = new LoginPanel();
		add(loginPanel);
		add(background);
		setBounds(0, 0, 1500, 750);
		loginPanel.setBounds(250, 75, 500, 500);
		loginPanel.setOpaque(true);
		background.setBounds(0, 0, 1500, 700);
		background.setOpaque(true);
	
	}
}

