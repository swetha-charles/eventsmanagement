package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private ArrayList<ClientInfo> socketArray;
	private ServerSocket serverSocket;
	private DatabaseConnection database;
	private ExecutorService threadPool;
	private boolean serverActive;
	
	public Server(int portNumber) throws IOException{
		//Create connection to database
		this.database = new DatabaseConnection();
		//Create server socket to listen for new clients
		this.serverSocket = new ServerSocket(portNumber);
		System.out.println("ObjectServer: Listening on port " + portNumber);
		//Set the flag that shows the server is active
		this.serverActive = true;
		//initialise the socketArray to hold client information
		this.socketArray = new ArrayList<ClientInfo>();
		//create the threadpool
		this.threadPool = Executors.newFixedThreadPool(4);
		
		//start accepting conncetions
		acceptConnections();
	}
	
	public void acceptConnections() throws IOException{
		while(isServerActive() == true){
			//accept a new connection if there is one
			Socket newConnection = getServerSocket().accept();
			//set the timeout time to 50 milliseconds
			newConnection.setSoTimeout(50);
			System.out.println("Received a new client connection. Assigning port: " + newConnection.getPort());
			//create input and output streams
			ObjectInputStream clientInputStream = new ObjectInputStream(newConnection.getInputStream());
			ObjectOutputStream clientOutputStream = new ObjectOutputStream(newConnection.getOutputStream());
			//place the newly created objects into a ClientInfo object
			ClientInfo newClientInfo = new ClientInfo(newConnection, clientInputStream, clientOutputStream);
			//add the client info to the socketArray
			getSocketArray().add(newClientInfo);
			
			//put the initial task that checks for objects into the ExecutorService
			ETSearchForObject startSearching = new ETSearchForObject(this, newClientInfo);
			getThreadPool().execute(startSearching);
		}
	}
	
	public ArrayList<ClientInfo> getSocketArray() {
		return socketArray;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public boolean isServerActive() {
		return serverActive;
	}
	
	public void setServerActive(boolean serverActive){
		this.serverActive = serverActive;
	}

	public DatabaseConnection getDatabase() {
		return database;
	}
	
	public void closeServer(){
		//TODO method that closes the server properly
	}

	public ExecutorService getThreadPool() {
		return threadPool;
	}

	public static void main(String[] args) {
		
		try {
			Server newServer = new Server(4444);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
