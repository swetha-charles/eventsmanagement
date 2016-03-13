package server;

import java.io.*;
import java.net.Socket;

public class ClientInfo {

	private String userName;
	private Socket clientSocket;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	
	public ClientInfo(Socket clientSocket,
					  ObjectInputStream clientInput,
					  ObjectOutputStream clientOutput){
		this.userName = null;
		this.clientSocket = clientSocket;
		this.clientInput = clientInput;
		this.clientOutput = clientOutput;
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
