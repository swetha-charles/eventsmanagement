package server;

import java.io.IOException;
import java.util.Observable;

public class ServerModel extends Observable{

	private String text;
	private Server server;
	
	public ServerModel(int portNumber){
		this.text = "";
		try {
			Server serverThread = new Server(portNumber, this);
			serverThread.start();
			this.server = serverThread;
		} catch (IOException e) {
			System.out.println("Error starting server");
			e.printStackTrace();
		}
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public Server getServer() {
		return server;
	}

	public synchronized void addToText(String additionalText){
		setText(getText() + additionalText);
        setChanged();
        notifyObservers();
	}
	
	public void refreshText(){
        setChanged();
        notifyObservers();
	}
	
	public void closeServer(){
		getServer().serverStop();
		addToText("-----------------SERVER STOPPED-----------------\n");
        setChanged();
        notifyObservers();
	}
}
