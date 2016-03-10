package server;

import java.io.IOException;

import javax.swing.JFrame;

public class ServerGUI {
	
    public static void main(String[] args) {
        JFrame frame = new JFrame("Server");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        ServerController panel = new ServerController(4444);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
