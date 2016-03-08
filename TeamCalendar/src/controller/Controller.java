package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import gui.LoginPanel;
import gui.MainView;
import model.Model;
import model.State;
import server.OTEmailCheck;
import server.OTUsernameCheck;
import server.ObjectTransferrable;

public class Controller implements ActionListener, MouseListener {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Model model;
	private MainView view;

	public Controller(ObjectOutputStream toServer, ObjectInputStream fromServer) {
		this.toServer = toServer;
		this.fromServer = fromServer;
	}

	public Controller() {

	}

	public void addModel(Model model) {
		this.model = model;
	}

	public void addView(MainView view) {
		this.view = view;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
			model.changeCurrentState(State.LOGIN);
			System.out.println("Controller: Model's state chanhed to" + model.getCurrentState());
		}

	}

	// mouse clicking, right now it's used for changing from login page to
	// registration up
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == LoginPanel.signup) {
			System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
			model.changeCurrentState(State.REGISTRATION);
			System.out.println("Controller: Model's state chanhed to" + model.getCurrentState());
		}

	}
	
	public boolean checkUsername(String username){
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		return false;
	}
	
	public void checkEmail(String email){
		OTEmailCheck otec = new OTEmailCheck(email);
		try {
			this.toServer.writeObject(otec);
			ObjectTransferrable OT = (ObjectTransferrable) this.fromServer.readObject();
			if(OT.getOpCode().equals("0002")){
				if(!((OTUsernameCheck) OT).getAlreadyExists()){
					this.model.changeCurrentState(State.REGISTRATIONEMAILOK);
				} else {
					this.model.changeCurrentState(State.REGISTRATIONEMAILEXISTS);
				}
			} else {
				System.out.println("ERROR: Server needs to communicate an UsernameCheckObject back to Client");
				this.model.changeCurrentState(State.ERRORSERVERCOMMUNICATION);
			}
		} catch (IOException e) {
			//run this block of code if the toServer OR fromServer do not work
			System.out.println("Connection down, sorry");
			this.model.changeCurrentState(State.ERRORCONNECTIONDOWN);
		} catch (ClassNotFoundException e) {
			//Communication from the server is not what's expected!
			System.out.println("Server is not communicating an Object of type ObjectTransferrable");
			this.model.changeCurrentState(State.ERRORCONNECTIONDOWN);
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
