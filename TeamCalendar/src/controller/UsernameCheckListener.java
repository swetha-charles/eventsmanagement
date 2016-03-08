package controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface UsernameCheckListener extends FocusListener{

	public void usernameFocusGained(FocusEvent e);
	public void usernameFocusLost(FocusEvent e);

}
