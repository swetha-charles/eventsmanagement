
package server;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class ServerGUI {
	
    public static void main(String[] args) {
        JFrame frame = new JFrame("Server");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        ServerController panel = new ServerController(4449);
        JScrollPane scrollPanel = new JScrollPane(panel);
        frame.add(scrollPanel);
        frame.setVisible(true);
    }
}
