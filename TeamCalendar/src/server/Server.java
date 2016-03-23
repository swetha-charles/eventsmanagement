package server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * this class establishes the thread that will accept client connections, and
 * initialise the threadpool for client tasks. It also intialises the class that
 * establishes a connection with the databse back-end.  It will also contain the
 * methods required to close the server safely.
 * @author Mark
 *
 */
public class Server extends Thread{

	private ArrayList<ClientInfo> socketArray;
	private ServerSocket serverSocket;
	private DatabaseConnection database;
	private ExecutorService threadPool;
	private boolean serverActive;
	private ServerModel serverModel;
	private QueryManager queryManager;
	
	/**
	 * The constructor for the class. It establishes the server socket that clients
	 * will connect to, initialises the database connection, and initialises the
	 * threadpool (of 4 threads). It also initialises the instance of the Query
	 * Manager, which is what will process and create responses to client input.
	 * @param iNetAddr the address of the Server as a String
	 * @param portNumber the port that the server should listen on
	 * @param model the model that will be used to store important text for the
	 * server GUI
	 * @throws IOException can throw this exception if the server socket fails to
	 * be created
	 */
	public Server(String iNetAddr, int portNumber, ServerModel model) throws IOException{
		this.serverModel = model;
		//Create connection to database
		this.database = new DatabaseConnection(this);
		//Create server socket to listen for new clients
		InetAddress addr = InetAddress.getByName(iNetAddr);
		this.serverSocket = new ServerSocket(portNumber, 50, addr);
		getServerModel().addToText("Server: Listening on port " + portNumber + " and IP: " +serverSocket.getInetAddress().toString() + "\n");
		//Set the flag that shows the server is active
		this.serverActive = true;
		//initialise the socketArray to hold client information
		this.socketArray = new ArrayList<ClientInfo>();
		//create the threadpool
		this.threadPool = Executors.newFixedThreadPool(4);
		this.queryManager = new QueryManager(this);
	}
	
	/**
	 * the main run method for the server client, this repeatedly looks for new
	 * client connections to the database, and establishes a ClientInfo object for
	 * each client. It also adds the first instance of the task that the threadpool
	 * will use to search for client input to the threadpool for execution.
	 */
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
				e.printStackTrace();
			}
		}
	}
	/**
	 * getter for the ArrayList of ClientInfo objects that should indicate clients
	 * with a live connection
	 * @return the ArrayList of ClientInfo that represents clients with a live
	 * connection
	 */
	public ArrayList<ClientInfo> getSocketArray() {
		return socketArray;
	}
	/**
	 * getter for the server socket
	 * @return the server socket that will listen for client connections
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	/**
	 * getter for the state of the server
	 * @return a boolean that represents whether the server should be
	 * considered active or not
	 */
	public boolean isServerActive() {
		return serverActive;
	}
	/**
	 * setter for the state of the server
	 * @param serverActive a boolean that represents whether the server should be
	 * considered active or not
	 */
	public void setServerActive(boolean serverActive){
		this.serverActive = serverActive;
	}
	/**
	 * getter for the database connection
	 * @return the class that contains the connection to the database
	 */
	public DatabaseConnection getDatabase() {
		return database;
	}
	/**
	 * getter for the server model, this will be used to add new text to the
	 * server GUI
	 * @return the server model object that will be used to contain the text
	 * for the server GUI
	 */
    public ServerModel getServerModel() {
		return serverModel;
	}
    /**
     * getter for the query manager, this will be used to process client queries
     * @return the query manager to which client objects should be passed,
     * and the replies received
     */
	public QueryManager getQueryManager() {
		return queryManager;
	}
	/**
	 * getter for the threadpool to which new tasks should be passed
	 * for execution
	 * @return the thread pool to which new tasks should be added for
	 * searching, and processing client input
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}
	/**
	 * a method that stops the server safely, closing client connections behind it
	 */
	public synchronized void serverStop(){
		//stopping the while loop that checks for new client connections
        setServerActive(false);
        getThreadPool().shutdown();
        //stopping the clients and the server socket
        try {
        	for(ClientInfo client : getSocketArray()){
        		client.getClientInput().close();
        		client.getClientOutput().close();
        		client.getClientSocket().close();
        		getServerModel().addToText("Removing client: " + getSocketArray().indexOf(client) + "\n");
        	}
        	getSocketArray().clear();
            getServerSocket().close();
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
	/**
	 * a method that removes a client from the array of active clients.
	 * this should only be called after the clients socket has been
	 * safely closed
	 * @param clientInfo the client that should be removed from the array
	 */
	public void removeClient(ClientInfo clientInfo) {
		getServerModel().addToText("Removing client: " + getSocketArray().indexOf(clientInfo) + "\n");
		getSocketArray().remove(clientInfo);
		
	}

}
