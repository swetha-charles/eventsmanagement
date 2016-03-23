package server;

import java.io.*;
import java.net.Socket;
/**
 * this class holds all of the information that pertains to a particular
 * clients connection with the server
 * @author Mark
 *
 */
public class ClientInfo {

	private String userName;
	private Socket clientSocket;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	long HBReceivedMillis;
	
	/**
	 * constructor for the class. stores the object input and output streams
	 * for each class, and also sets up an original time that the client connected
	 * for use with the heartbeat, to determine if a client has disconnected strangely
	 * @param clientSocket the socket that the client connected on
	 * @param clientInput the input stream for objects from the client
	 * @param clientOutput the output stream to send objects to the client
	 */
	public ClientInfo(Socket clientSocket,
					  ObjectInputStream clientInput,
					  ObjectOutputStream clientOutput){
		this.userName = null;
		this.clientSocket = clientSocket;
		this.clientInput = clientInput;
		this.clientOutput = clientOutput;
		this.HBReceivedMillis = System.currentTimeMillis();
	}
	/**
	 * getter for the last time the server received a heartbeat from the client
	 * @return the time in milliseconds that the last heartbeat was received from
	 * the client
	 */
	public long getHBReceivedMillis() {
		return HBReceivedMillis;
	}
	/**
	 * setter for the last time the server received a heartbeat from the client
	 * @param hBReceivedMillis the new time in milliseconds that the last heartbeat
	 * was received from the client
	 */
	public void setHBReceivedMillis(long hBReceivedMillis) {
		HBReceivedMillis = hBReceivedMillis;
	}
	/**
	 * getter for the socket that the client has been assigned
	 * @return the socket that the client has been assigned
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}
	/**
	 * getter for the username of this particular client, should only be set
	 * when the client has successfully logged in
	 * @return the user name of the client
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * setter for the username of this particular client, should only be set
	 * when the client has successfully logged in
	 * @param userName the user name of the client
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * getter for the client input stream
	 * @return the client input stream
	 */
	public ObjectInputStream getClientInput() {
		return clientInput;
	}
	/**
	 * getter for the client output stream
	 * @return the client output stream
	 */
	public ObjectOutputStream getClientOutput() {
		return clientOutput;
	}

}
