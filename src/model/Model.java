package model;

import java.util.Observable;

import server.ObjectClientController;

public class Model extends Observable{
	private State currentstate;
	private ObjectClientController controller;
	private boolean RegUsernameexists = false;
	private boolean RegEmailexists = false;
	
	public Model(ObjectClientController controller2){
		this.controller = controller2;
		
		this.currentstate = State.LOGIN;
	}
	
	
	
	public State getCurrentState(){
		return this.currentstate;
		
	}
	
	
	public boolean getRegUsernameExists() {
		return RegUsernameexists;
	}



	public boolean getRegEmailExists() {
		return RegEmailexists;
	}



	public void setRegUsernameExists(boolean bool){
		this.RegUsernameexists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);
		
	}
	
	public void setRegEmailExists(boolean bool){
		this.RegUsernameexists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);
	
	}
	
	public synchronized void changeCurrentState(State state){
		System.out.println("Model: has how many observers?"+this.countObservers());
		this.currentstate = state;
		//System.out.println("Model: Model's state changed to " + this.currentstate);
		this.setChanged();
		//System.out.println("Model: About to notify observers!");
		super.notifyObservers();
		//System.out.println("Model: Notified observers!");
		
	}

	
}
