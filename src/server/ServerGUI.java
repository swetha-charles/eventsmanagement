
package server;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
/**
 * a class the establishes the frame for the server GUI to be held in.
 * It also contains the main method that sets the entire server process
 * in motion
 * @author Mark
 *
 */
public class ServerGUI {
	/**
	 * the main method for initialising the server. For when the server is
	 * packaged as a jar file, it makes allowance for the Server IP and port
	 * number being specific as main method arguments
	 * @param args
	 */
    public static void main(String[] args) {
    	int port = 50280;
		String iNetAddress = "localhost" ; 
		if (args.length == 1){
				System.out.println("No port specified, using default");
				iNetAddress = args[0];
		}
		else if (args.length >= 2){
			iNetAddress = args[0];
			port = Integer.parseInt(args[1]);
		}
		else{
			System.out.println("No arguments supplied, using default address " + iNetAddress + " and port " + port); 
		}
    		
        JFrame frame = new JFrame("Server");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        ServerController panel = new ServerController(iNetAddress,port);
        JScrollPane scrollPanel = new JScrollPane(panel);
        frame.add(scrollPanel);
        frame.setVisible(true);
    }
}
