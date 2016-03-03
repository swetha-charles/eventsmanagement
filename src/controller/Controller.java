package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.MainView;
import model.Model;
import model.State;

public class Controller implements ActionListener{
	private Model model;
	private MainView view;
	
	public Controller(){
		
		
	}
	
	public void addModel(Model model){
		this.model = model;
	}
	
	public void addView(MainView view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "register":
			
			model.changeCurrentState(State.REGISTRATION);
		
		}
		
	}

	
}
