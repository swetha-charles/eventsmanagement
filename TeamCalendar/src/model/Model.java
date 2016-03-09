package model;

import java.util.Observable;
import server.ObjectClientController;

public class Model extends Observable {
	private State currentstate;
	private ObjectClientController controller;

	private String username;
	private String email;
	

	private boolean usernameExists = false;
	private boolean emailExists = false;

	public Model(ObjectClientController controller2) {
		this.controller = controller2;

		this.currentstate = State.LOGIN;
	}
	//////////////////Check if username OR email is duplicated in the database///////////////////////////
	public void checkUsername(String username) {
		this.username = username;
		this.controller.checkUsername(username);
	}

	public void checkEmail(String email) {
		this.email = email;
		this.controller.checkEmail(email);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////Save information that comes back from the server/////////////////////////////////// 

	public void setUsernameExists(boolean bool) {
		this.usernameExists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}

	public void setEmailExists(boolean bool) {
		this.emailExists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getEmailExists() {
		return emailExists;
	}

	public boolean getUsernameExists() {
		return usernameExists;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////

	public State getCurrentState() {
		return this.currentstate;

	}
	
	

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	

	public synchronized void changeCurrentState(State state) {
		System.out.println("Model: has how many observers?" + this.countObservers());
		this.currentstate = state;
		// System.out.println("Model: Model's state changed to " +
		// this.currentstate);
		this.setChanged();
		// System.out.println("Model: About to notify observers!");
		super.notifyObservers();
		// System.out.println("Model: Notified observers!");

	}

}
