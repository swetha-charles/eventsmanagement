package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import model.Model;
import model.ModelState;

public class MenuPanelMouseListener implements MouseListener {
	private Model model;
	private ListPanel listPanel;

	public MenuPanelMouseListener(Model model, ListPanel listPanel) {
		this.model = model;
		this.listPanel = listPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		if (l.getName().equals("home")) {
			System.out.println("Home clicked");
			this.model.updateMeetings();
			this.listPanel.addMeetings(this.model.getMeetings());
			this.model.changeCurrentState(ModelState.EVENTSUPDATE);
		} else if (l.getName().equals("profile")) {
			System.out.println("Profile clicked");
			this.model.changeCurrentState(ModelState.PROFILE);
		} else if (l.getName().equals("logout")) {
			System.out.println("Log out clicked");
			this.model.changeCurrentState(ModelState.LOGIN);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
