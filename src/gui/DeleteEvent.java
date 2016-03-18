package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.Client;
import model.Model;
import objectTransferrable.Event;

public class DeleteEvent extends JPanel{
	
	private Client controller = null;
	private Model model;
	private Event event;
	private ListPanel listPanel;
	
	JLabel headingLabel = new JLabel("Are you sure you want to delete?");
	JButton yes = new JButton("Yes");
	JButton cancel = new JButton("Cancel");
	
	public DeleteEvent(Client controller, Model model, Event event, ListPanel listPanel){
		
		this.controller = controller;
		this.model = model;
		this.event = event;
		this.listPanel = listPanel;
		
		setPreferredSize(new Dimension(200,160));
		headingLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		headingLabel.setForeground(Color.DARK_GRAY);
		
		add(headingLabel);
		yes.addActionListener((e)-> {
			this.model.deleteEvent(getEvent());
			if (model.getMeetingDeleteSuccessful() == true) {
				model.setMeetingDeleteSuccessful(false);
				model.updateMeetings(new Date(this.listPanel.getC().getTimeInMillis()));
				this.listPanel.addMeetings(model.getMeetings());

				JOptionPane.showMessageDialog(this, "Meeting successfully deleted!");
				this.setVisible(false);
				this.listPanel.closeDialog();
			} else {
				JOptionPane.showMessageDialog(this, "I'm sorry your meeting was not able to be deleted. \n "
						+ "Refresh the page and try again");
				this.listPanel.closeDialog();
			}
		});
		cancel.addActionListener((e1)-> {
			this.setVisible(false);
			this.listPanel.closeDialog();
		});
		add(yes);
		add(cancel);
	}

	public Event getEvent() {
		return event;
	}

}
