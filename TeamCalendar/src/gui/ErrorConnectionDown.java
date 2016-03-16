package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;

public class ErrorConnectionDown extends JPanel {
	Model model;
	JLabel warning = new JLabel("Your connection is down. Hold on...");
	JButton restart = new JButton("Restart");

	public ErrorConnectionDown(Model model) {
		this.model = model;
		this.setLayout(new BorderLayout());
		this.add(warning, BorderLayout.NORTH);
		restart.addActionListener((e) -> {
			model.userRequestedRestart();
		});
	}
	
	public void addRestartButton(){
		warning.setText("Your connection is down. Reconnect internet and press restart");
		this.add(restart, BorderLayout.CENTER);
		repaint();
	}
	public void connectionStillDown(){
		warning.setText("Your connection is still down, we apologies for the inconvenience.  \n RECONNECT THE INTERNET before pressing restart");
		revalidate();
		}
	
	
}
