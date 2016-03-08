package gui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.LoginPanel;
import server.ObjectClientController;

public class Login extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel background;
	ObjectClientController controller;
	TestController Tcontroller;

	/**
	 * Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Login(ObjectClientController controller2) throws IOException {
		this.controller = controller2;

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
//		gbc.anchor = GridBagConstraints.BASELINE;
//		gbc.fill = GridBagConstraints.BOTH;
		
		
//		ImageIcon backgroundImage = new ImageIcon("calendar.jpg");
		
		//method that reads picture from file to set as background
		background = new JLabel() {
			
			private static final long serialVersionUID = 1L;
			private Image backgroundImage = ImageIO.read(new File("calendar.jpg"));
			public void paint( Graphics g ) { 
			    super.paint(g);
			    g.drawImage(backgroundImage, 0, 0, null);
			  }
			};
//		background.setPreferredSize(new Dimension(1000, 750));
		add(background, gbc);
		
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 1;
		LoginPanel loginPanel = new LoginPanel(this.controller);
		add(loginPanel, gbc);
		
		loginPanel.setOpaque(true);
	}



	public static void main(String[] args) throws IOException {

		/*JFrame frame = new JFrame();
		ObjectClientController c1 = new ObjectClientController();

		Login loginPanel = new Login(c1);

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(loginPanel);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);*/
	}
}
