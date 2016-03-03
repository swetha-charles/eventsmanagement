package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.BASELINE;
		gbc.fill = GridBagConstraints.BOTH;
		
		//method that reads picture from file to set as background
		background = new JPanel() {
			
			private static final long serialVersionUID = 1L;
			private Image backgroundImage = ImageIO.read(new File("calendar.jpg"));
			public void paint( Graphics g ) { 
			    super.paint(g);
			    g.drawImage(backgroundImage, 0, 0, null);
			  }
			};
		
		add(background, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		LoginPanel loginPanel = new LoginPanel();
		add(loginPanel, gbc);
		
		loginPanel.setOpaque(true);
		background.setOpaque(true);
	
	}
}

