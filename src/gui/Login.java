package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Login extends JLayeredPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel background;

	/**Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Login() throws IOException{
		
		//method that reads picture from file to set as background
		background = new JPanel() {
			
			private static final long serialVersionUID = 1L;
			private Image backgroundImage = ImageIO.read(new File("calendar.jpg"));
			public void paint( Graphics g ) { 
			    super.paint(g);
			    g.drawImage(backgroundImage, 0, 0, null);
			  }
			};
			
		background.setPreferredSize(new Dimension(1500, 700));
		
		LoginPanel loginPanel = new LoginPanel();
		
		add(loginPanel);
		add(background);
		setBounds(0, 0, 1500, 750);
		loginPanel.setBounds(400, 75, 470, 450);
		loginPanel.setOpaque(true);
		background.setBounds(0, 0, 1500, 700);
		background.setOpaque(true);
	
	}
}

