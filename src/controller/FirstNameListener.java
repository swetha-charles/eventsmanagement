package controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface FirstNameListener extends FocusListener{
	public void focusGained(FocusEvent e);
	public void focusLost(FocusEvent e);
}
