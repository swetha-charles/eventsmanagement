package server;

import java.io.*;
import java.net.*;

public class ThreadForClient implements Runnable{

	Socket clientSocket;
	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	boolean loggedIn;
	
	public ThreadForClient(Socket clientSocket){
		this.clientSocket = clientSocket;
		try {
			fromClient = new ObjectInputStream(clientSocket.getInputStream());
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(loggedIn = true){
			
		}
	}

}	
