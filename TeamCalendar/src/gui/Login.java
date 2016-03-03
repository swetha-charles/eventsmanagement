package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import controller.Controller;
import gui.LoginPanel;
import gui.Login;

public class Login extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel background;
	Controller controller;
	TestController Tcontroller;

	/**Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Login(Controller controller) throws IOException{
		this.controller = controller;
		
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
		
		//method that reads picture from file to set as background
		background = new JLabel() {
		
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
		gbc.weightx = 0;
		gbc.weighty = 0;
		LoginPanel loginPanel = new LoginPanel(this.controller);
		add(loginPanel, gbc);
		
		loginPanel.setOpaque(true);
		background.setOpaque(true);
	}
	
	public Login(TestController controller) throws IOException{
		this.Tcontroller = controller;
		
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
		
		//method that reads picture from file to set as background
		background = new JLabel() {
		
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
		gbc.weightx = 0;
		gbc.weighty = 0;
		LoginPanel loginPanel = new LoginPanel(this.Tcontroller);
		add(loginPanel, gbc);
		
		loginPanel.setOpaque(true);
		background.setOpaque(true);
	}
	
	
	public static void main(String[] args) throws IOException {
		
	/*	JFrame frame = new JFrame();
		Controller controller = new Controller();
		
		Login loginPanel = new Login(controller);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(loginPanel);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true)*/;
	}
}

