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
	
	public void changeCurrentState(State state){
		this.currentstate = state;
		this.setChanged();
		this.notifyObservers();
	}

	
}
