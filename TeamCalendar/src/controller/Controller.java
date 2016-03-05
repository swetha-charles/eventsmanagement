package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.LoginPanel;
import gui.MainView;
import model.Model;
import model.State;

public class Controller implements ActionListener, MouseListener{
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
	public synchronized void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "cancel":
			System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
			model.changeCurrentState(State.LOGIN);
			System.out.println("Controller: Model's state chanhed to" + model.getCurrentState());
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent()==LoginPanel.signup){
			System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
			model.changeCurrentState(State.REGISTRATION);
			System.out.println("Controller: Model's state chanhed to" + model.getCurrentState());
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
