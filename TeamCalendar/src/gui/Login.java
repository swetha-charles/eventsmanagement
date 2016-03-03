package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

public class Login extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel background;
	Controller controller;
	TestController Tcontroller;

	/**
	 * Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Login(Controller controller) throws IOException {
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
		// gbc.anchor = GridBagConstraints.BASELINE;
		// gbc.fill = GridBagConstraints.BOTH;

		ImageIcon backgroundImage = new ImageIcon("calendar.jpg");
		// ImageIO.read(new File("calendar.jpg"));
		// method that reads picture from file to set as background
		background = new JLabel(backgroundImage);
		{

			/*
			 * private static final long serialVersionUID = 1L;
			 * 
			 * public void paint( Graphics g ) { super.paint(g);
			 * //g.drawImage(backgroundImage, 0, 0, 0); }
			 */
		}
		;

		background.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;

		LoginPanel loginPanel = new LoginPanel(this.controller);
		background.add(loginPanel, gbc);
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		add(background, gbc);

//		gbc.gridx = 1;
//		gbc.gridy = 1;
//		gbc.weightx = 0;
//		gbc.weighty = 0;
		

		this.setVisible(true);
		loginPanel.setOpaque(true);
		background.setOpaque(true);
	}

	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame();
		Controller controller = new Controller();

		Login loginPanel = new Login(controller);

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(loginPanel);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
