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
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;
import server.ObjectClientController;

public class Registration extends JPanel{

	private ObjectClientController controller = null;
	private Model model;
	private static final long serialVersionUID = 1L;
	JPanel background;
	RegistrationPanel rp;

	/**Constructor to create a Login
	 * 
	 * @throws IOException
	 */
	public Registration(ObjectClientController controller2, Model model){
		
		this.controller = controller2;
		this.model = model;
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		
//		method that reads picture from file to set as background
		try {
			background = new JPanel() {
				
				private static final long serialVersionUID = 1L;
				private Image backgroundImage = ImageIO.read(new File("calendar.jpg"));
				public void paint( Graphics g ) { 
				    super.paint(g);
				    g.drawImage(backgroundImage, 0, 0, null);
				  }
				};
				this.add(background, gbc);
		} catch (IOException e) {
			//File could not be found
			background = null;
		}

		background.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		
		rp = new RegistrationPanel(controller2, model);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(rp, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(background, gbc);
	
	}
	
	public RegistrationPanel getRegistrationPanel(){
		return this.rp;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
		ObjectClientController controller = new ObjectClientController();
		
		
		//Registration r = new Registration(controller, model);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setContentPane(r);
		frame.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}

