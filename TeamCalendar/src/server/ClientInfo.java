package server;

import java.io.*;
import java.net.Socket;

public class ClientInfo {

	private Socket clientSocket;
	private ObjectInputStream clientInput;
	private ObjectOutputStream clientOutput;
	
	public ClientInfo(Socket clientSocket,
					  ObjectInputStream clientInput,
					  ObjectOutputStream clientOutput){
		this.clientSocket = clientSocket;
		this.clientInput = clientInput;
		this.clientOutput = clientOutput;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public ObjectInputStream getClientInput() {
		return clientInput;
	}

	public ObjectOutputStream getClientOutput() {
		return clientOutput;
	}

}
