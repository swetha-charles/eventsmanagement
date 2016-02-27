package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Background extends JPanel{
	
	JPanel background;
	
	public Background() throws IOException{
		
		background = new JPanel() {
			private Image backgroundImage = ImageIO.read(new File("/Users/nataliemcdonnell/Dropbox/CS/Java/calendar.jpg"));
			public void paint( Graphics g ) { 
			    super.paint(g);
			    g.drawImage(backgroundImage, 0, 0, null);
			  }
			};
			
		background.setPreferredSize(new Dimension(1500, 700));
		add(background);
			
	}
	
	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame();
		
		Background background = new Background();
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(background);
		frame.setSize(1000,650);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
