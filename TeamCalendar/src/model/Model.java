package model;

import java.util.Observable;

import server.ObjectClientController;

public class Model extends Observable{
	private State currentstate;
	private ObjectClientController controller;
	
	public Model(ObjectClientController controller2){
		this.controller = controller2;
		
		this.currentstate = State.LOGIN;
	}
	
	
	
	public State getCurrentState(){
		return this.currentstate;
		
	}
	
	public synchronized void changeCurrentState(State state){
		System.out.println("Model: has how many observers?"+this.countObservers());
		this.currentstate = state;
		System.out.println("Model: Model's state changed to " + this.currentstate);
		this.setChanged();
		System.out.println("Model: About to notify observers!");
		super.notifyObservers();
		System.out.println("Model: Notified observers!");
		
	}

	
}
