package model;

import java.util.Observable;

import controller.Controller;

public class Model extends Observable{
	private State currentstate;
	private Controller controller;
	
	public Model(Controller controller){
		this.controller = controller;
		
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
