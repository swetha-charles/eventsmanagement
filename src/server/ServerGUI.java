
package server;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class ServerGUI {
	
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
			System.out.println("No arguements supplied, using default address " + iNetAddress + "and port " + port); 
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
