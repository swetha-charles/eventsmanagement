package listener.interfaces;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*public class WindowClosingListener extends ClosingListenerClass{
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}


 class ClosingListenerClass implements WindowsClosingHelper{

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
*/

public interface WindowClosingListener extends WindowListener{
	@Override
	public default void windowOpened(WindowEvent e) {
	}

	@Override
	public default void windowClosing(WindowEvent e) {
	}
	
	@Override
	public default void windowIconified(WindowEvent e) {
	}

	@Override
	public default void windowDeiconified(WindowEvent e) {
	}

	@Override
	public default void windowActivated(WindowEvent e)  {
	}
	@Override
	public default void windowDeactivated(WindowEvent e)  {
	}


}
