package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.Client;
import model.Model;

public class Login extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel background;
	Client client;
	Model model;
	//TestController Tcontroller;

	/**
	 * Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Login(Client client, Model model) {
		this.client = client;
		this.model = model;

		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		
		try {
			background = new JLabel() {
				
				private static final long serialVersionUID = 1L;
				private Image backgroundImage = ImageIO.read(new File("calendar.jpg"));
				public void paint( Graphics g ) { 
				    super.paint(g);
				    g.drawImage(backgroundImage, 0, 0, null);
				  }
				};
		} catch (IOException e) {
			// no biggie, just couldn't find a file. 
			e.printStackTrace();
		}

		background.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
		LoginPanel loginPanel = new LoginPanel(this.client, this.model);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(loginPanel, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(background, gbc);
	}



	public static void main(String[] args) throws IOException {

		/*JFrame frame = new JFrame();
		Client c1 = new Client();

		Login loginPanel = new Login(c1);

		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(loginPanel);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);*/
	}
}
