package server;

import java.io.IOException;
import java.util.Observable;
/**
 * class that holds the model for the GUI for the server, it just holds
 * the text for the pane and methods to add to it
 * @author Mark
 *
 */
public class ServerModel extends Observable{

	private String text;
	private Server server;
	/**
	 * constructor for the model class, constructs the server class that
	 * holds the key methodology for the server running
	 * @param addr the address that the server will be located on as a String
	 * @param portNumber the port number for the server to listen on
	 */
	public ServerModel(String addr, int portNumber){
		this.text = "";
		try {
			Server serverThread = new Server(addr, portNumber, this);
			serverThread.start();
			this.server = serverThread;
		} catch (IOException e) {
			System.out.println("Error starting server");
			e.printStackTrace();
		}
	}
	/**
	 * getter for the text that needs to be displayed on the GUI
	 * @return the text to be displayed on the GUI
	 */
	public String getText(){
		return this.text;
	}
	/**
	 * setter for the text on the GUI
	 * @param text the text that will be shown on the GUI pane
	 */
	public void setText(String text){
		this.text = text;
	}
	/**
	 * getter for the server that will contain the methodology for the
	 * server running
	 * @return the server
	 */
	public Server getServer() {
		return server;
	}
	
	/**
	 * a method that adds text to the pane, this is what will be used by
	 * the server to log important incidents and for process checking and
	 * debugging
	 * @param additionalText the text to be added to the pane, will normally
	 * need "\n" to be included
	 */
	public synchronized void addToText(String additionalText){
		setText(getText() + additionalText);
        setChanged();
        notifyObservers();
	}
	/**
	 * a method that is only called once, by the model to initially update it
	 * with the information given by the database set up
	 */
	public void refreshText(){
        setChanged();
        notifyObservers();
	}
	/**
	 * adds a line that makes it clear that the server has stopped. This is used
	 * by the Server to indicate the closing of the server
	 */
	public void closeServer(){
		getServer().serverStop();
		addToText("-----------------SERVER STOPPED-----------------\n");
        setChanged();
        notifyObservers();
	}
}
