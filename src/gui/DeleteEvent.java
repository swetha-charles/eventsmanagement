package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;

import javax.swing.BoxLayout;
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
	JPanel comment = new JPanel();
	JPanel buttons = new JPanel();
	JButton yes = new JButton("Yes");
	JButton cancel = new JButton("Cancel");
	
	/** constructor to build the popup panel that is seen when deleting an event
	 * 
	 * @param controller an object that connects the view to the server
	 * @param model an object that contains the methods to update the view
	 * @param event an Event object that contains information about the event being deleted
	 * @param listPanel a JPanel that contains the view of the meetings
	 */
	public DeleteEvent(Client controller, Model model, Event event, ListPanel listPanel){
		
		this.controller = controller;
		this.model = model;
		this.event = event;
		this.listPanel = listPanel;
		
		setPreferredSize(new Dimension(400,100));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		headingLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		headingLabel.setForeground(Color.DARK_GRAY);
		
		comment.setMaximumSize(new Dimension(400,50));
		comment.setMinimumSize(new Dimension(400,50));
		
		yes.setBackground(Color.DARK_GRAY);
		cancel.setBackground(Color.DARK_GRAY);
		yes.setForeground(new Color(255, 255, 245));
		cancel.setForeground(new Color(255, 255, 245));
		
		comment.add(headingLabel);
		
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
		
		buttons.add(yes);
		buttons.add(cancel);
		
		add(comment);
		add(buttons);
	}

	/** Getter for the event object
	 * 
	 * @return event
	 */
	public Event getEvent() {
		return event;
	}

}
