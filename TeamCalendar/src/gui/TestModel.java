package gui;

import java.util.Observable;

public class TestModel extends Observable {
	private static String state;
	
	public void changeState(){
		setChanged();
//		System.out.println("testModel: changed state");
//		System.out.println("tstModel: observer count"+this.countObservers());
		//System.out.println());
		super.notifyObservers();
//		System.out.println("TestModel: Notified observers");
	}
	
}
