package model;

import java.util.Observable;
import server.ObjectClientController;

public class Model extends Observable {
	private State currentstate;
	private ObjectClientController controller;

	private String username;
	private String email;
	

	private boolean RegUsernameexists = false;
	private boolean RegEmailexists = false;

	public Model(ObjectClientController controller2) {
		this.controller = controller2;

		this.currentstate = State.LOGIN;
	}

	public void checkUsername(String username) {
		this.username = username;
		this.controller.checkUsername(username);
	}

	public void checkEmail(String email) {
		this.email = email;
		this.controller.checkEmail(email);
	}

	public boolean getRegEmailExists() {
		return RegEmailexists;
	}

	public boolean getRegUsernameExists() {
		return RegUsernameexists;
	}

	public void setRegUsernameExists(boolean bool) {
		this.RegUsernameexists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}

	public void setRegEmailExists(boolean bool) {
		this.RegUsernameexists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}

	

	public State getCurrentState() {
		return this.currentstate;

	}
	
	

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
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
