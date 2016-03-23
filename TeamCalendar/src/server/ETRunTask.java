package server;

import java.io.IOException;
import java.sql.SQLException;
import objectTransferrable.*;

/**
 * task that is added to the thread pool when an object has been
 * received from a client. It manages calling the query manager on the
 * task, and the posting of a response back to the client. If the message
 * was a log out message, it attempts to close the connection with the 
 * client
 * @author Mark
 *
 */
public class ETRunTask implements ExecutableTask{

	private Server masterServer;
	private ClientInfo clientInfo;
	private ObjectTransferrable query;
	/**
	 * constructor for the task. It needs the server so that the query manager
	 * can fetch the database connection, and write to the GUI. It also needs the
	 * client info to know where the object came from and where resultant objects
	 * should be sent to. It also need the object that was given to the server
	 * by the client
	 * @param masterServer the instance of the server that is running
	 * @param clientInfo the client that passed the object
	 * @param query the object that the client passed
	 */
	public ETRunTask(Server masterServer,
			ClientInfo clientInfo,
			ObjectTransferrable query) {
		this.masterServer = masterServer;
		this.clientInfo = clientInfo;
		this.query = query;
	}
	/**
	 * getter for the server
	 * @return the server
	 */
	public Server getMasterServer() {
		return masterServer;
	}
	/**
	 * the getter for the clients info
	 * @return the clients info
	 */
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	/**
	 * the getter for the object that the client transferred
	 * @return the object that the client transferred
	 */
	public ObjectTransferrable getQuery() {
		return query;
	}

	@Override
	/**
	 * the main run method for the task. It passes the received object to
	 * the query manager for processing, and passes the result of the query
	 * managers processing back to the client.
	 * 
	 * If the object that was sent was the log off object, then it closes the
	 * clients connection
	 * 
	 * If the connection hasn't been closed, then it creates a new instance of
	 * the task that searches for objects from the client that passed this one.
	 */
	public void run() {
		//create a new QueryManager object to handle the execution of the received object
		QueryManager runQuery = getMasterServer().getQueryManager();
		ObjectTransferrable returnObject = null;

		//execute the query, updating the object
		returnObject = runQuery.runOperation(getQuery(), getClientInfo());
		//get the updated object and pass it back to the client
		try {
			if(!returnObject.getOpCode().equals("0014")){
//				getMasterServer().getServerModel().addToText("Sending back Object with opCode " + returnObject.getOpCode() + "\n");
			}
			//write back to client
			getClientInfo().getClientOutput().writeObject(returnObject);
			if(!returnObject.getOpCode().equals("0014")){
//				getMasterServer().getServerModel().addToText("SENT" + "\n");
			} else {
//				getMasterServer().getServerModel().addToText(getMasterServer().getSocketArray().indexOf(getClientInfo()) + "HB\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		//in case of exit, close down all inputs and outputs 
		// and do not make a new ETSearchForObject. 
		if(returnObject.getOpCode().equals("0005")){
			try {
				getClientInfo().getClientSocket().shutdownInput();
			} catch (IOException e) {
				getMasterServer().getServerModel()
				.addToText("Could not close input stream \n");
			}
			try {
				getClientInfo().getClientSocket().shutdownOutput();
			} catch (IOException e) {
				getMasterServer().getServerModel()
				.addToText("Could not close output stream \n");
			}
			try {
				getClientInfo().getClientSocket().close();
				getMasterServer().getServerModel()
				.addToText("Client's socket has been closed \n");
			} catch (IOException e) {
				getMasterServer().getServerModel()
				.addToText("Could not close socket\n");
			}
			getMasterServer().removeClient(getClientInfo());

		} else {
			//create a new instance of ETSearchForObject so to pick up more queries from this client and pass it to the threadpool
			ETSearchForObject newSearch = new ETSearchForObject(getMasterServer(), getClientInfo());
			getMasterServer().getThreadPool().execute(newSearch);
		}

	}

}
