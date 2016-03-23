package server;

import java.io.*;
import java.net.Socket;
/**
 * 
 * @author Mark
 *
 */
public class ClientInfo {

	private String userName;
	private Socket clientSocket;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	long HBReceivedMillis;
	
	public ClientInfo(Socket clientSocket,
					  ObjectInputStream clientInput,
					  ObjectOutputStream clientOutput){
		this.userName = null;
		this.clientSocket = clientSocket;
		this.clientInput = clientInput;
		this.clientOutput = clientOutput;
		this.HBReceivedMillis = System.currentTimeMillis();
	}

	public long getHBReceivedMillis() {
		return HBReceivedMillis;
	}

	public void setHBReceivedMillis(long hBReceivedMillis) {
		HBReceivedMillis = hBReceivedMillis;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ObjectInputStream getClientInput() {
		return clientInput;
	}

	public ObjectOutputStream getClientOutput() {
		return clientOutput;
	}

}
