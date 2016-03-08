package controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface FocusLostListener extends FocusListener{
	
	@Override
	public default void focusGained(FocusEvent e){
		
	}
	
	
}
