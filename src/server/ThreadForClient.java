package server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ThreadForClient implements Runnable{

	private Socket clientSocket;
	private Connection connection;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	
	public ThreadForClient(Socket clientSocket, Connection connection){
		this.clientSocket = clientSocket;
		this.connection = connection;
		try {
			fromClient = new ObjectInputStream(clientSocket.getInputStream());
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void run() {
		while(true){
			ObjectTransferrable receivedOperation = null;
			try {
				receivedOperation = (ObjectTransferrable) fromClient.readObject();
				System.out.println("Recieved Object with opCode: " + receivedOperation.getOpCode());
				QueryManager runQuery = new QueryManager(receivedOperation, getConnection());
				try {
					runQuery.runOperation();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				toClient.writeObject(runQuery.getOperation());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

}	
