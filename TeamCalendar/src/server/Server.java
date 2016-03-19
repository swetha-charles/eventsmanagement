package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{

	private ArrayList<ClientInfo> socketArray;
	private ServerSocket serverSocket;
	private DatabaseConnection database;
	private ExecutorService threadPool;
	private boolean serverActive;
	private ServerModel serverModel;
	private QueryManager queryManager;
	
	public Server(int portNumber, ServerModel model) throws IOException{
		this.serverModel = model;
		//Create connection to database
		this.database = new DatabaseConnection(this);
		//Create server socket to listen for new clients
		this.serverSocket = new ServerSocket(portNumber);
		getServerModel().addToText("Server: Listening on port " + portNumber + "\n");
		//Set the flag that shows the server is active
		this.serverActive = true;
		//initialise the socketArray to hold client information
		this.socketArray = new ArrayList<ClientInfo>();
		//create the threadpool
		this.threadPool = Executors.newFixedThreadPool(4);
		this.queryManager = new QueryManager(this);
	}
	
	public void run(){
		while(isServerActive() == true){
			//accept a new connection if there is one
			Socket newConnection;
			try {
				try{
				newConnection = getServerSocket().accept();
				//set the timeout time to 50 milliseconds
				newConnection.setSoTimeout(100);
				getServerModel().addToText("Received a new client connection. Assigning port: " + newConnection.getPort() + "\n");
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
				} catch(SocketException e) {
					getServerModel().addToText("SOCKET EXCEPTION - Could be normal if stopping server\n");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
    public ServerModel getServerModel() {
		return serverModel;
	}

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public synchronized void serverStop(){
		//stopping the while loop that checks for new client connections
        this.serverActive = false;
        //stopping the clients and the server socket
        try {
        	for(ClientInfo client : socketArray){
        		client.getClientInput().close();
        		client.getClientOutput().close();
        		client.getClientSocket().close();
        	}
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not close a socket");
        }
        //closing the database connection
    	try {
			this.getDatabase().getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

	public ExecutorService getThreadPool() {
		return threadPool;
	}

}
