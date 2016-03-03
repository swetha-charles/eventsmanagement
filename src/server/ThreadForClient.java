package server;

import java.net.*;

public class ThreadForClient implements Runnable{

	Socket clientSocket;
	
	public ThreadForClient(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {		
	}

}	
