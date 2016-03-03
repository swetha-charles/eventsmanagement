package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class TestController implements ActionListener {
	public static TestModel model;

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Test controller: action performed");
		model.changeState();
		
	}
	
	public void  add(TestModel model){
		this.model = model;
	}

	

}
