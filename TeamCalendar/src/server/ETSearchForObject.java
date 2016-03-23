package server;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import objectTransferrable.*;
/**
 * the class that scans for object input from a given client. If no input is
 * found by the timeout time, then it will create a new object that checks
 * for the same clients input until either a heart beat hasn't been received
 * for a fixed period of time, the server is closed, or the client has requested
 * a log out.
 * @author Mark
 *
 */
public class ETSearchForObject implements ExecutableTask {

	private final int HEARTBEAT_TIMEOUT = 3000;
	private Server masterServer;
	private ClientInfo clientInfo;
	/**
	 * constructor for the object, it needs to know the client that input should
	 * be search for from, and the server details
	 * @param masterServer the server that holds the threadpool into which this
	 * task, and future tasks should be placed
	 * @param clientInfo the client that input needs to be gained from
	 */
	public ETSearchForObject(Server masterServer, ClientInfo clientInfo) {
		this.masterServer = masterServer;
		this.clientInfo = clientInfo;
	}
	/**
	 * getter for the master server information, should be mainly used for adding
	 * log messages to the GUI and for adding tasks to the threadpool. Is is also
	 * necessary to pass this information to the task that executes objects if one
	 * is found
	 * @return the master server information
	 */
	public Server getMasterServer() {
		return masterServer;
	}
	/**
	 * getter for the client info
	 * @return the clients info
	 */
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	/**
	 * the method that searches for user input, and decides what action to take based
	 * on an object being found (create an ETRunTask to process the object), a client
	 * not having sent a heartbeat in a period of time (close the client connection),
	 * or no object being found (create a new object of this type for the client)
	 */
	@Override
	public void run() {
		Object receivedObject = null;
		long differenceBetweenLastHBAndCurrent = System.currentTimeMillis() - getClientInfo().getHBReceivedMillis();
		int indexOfClient = getMasterServer().getSocketArray().indexOf(getClientInfo());
		if (getMasterServer().isServerActive() == true && differenceBetweenLastHBAndCurrent < HEARTBEAT_TIMEOUT) {
			try {			
				receivedObject = getClientInfo().getClientInput().readObject();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {

			} catch (SocketTimeoutException e) {
			} catch (IOException e2) {
				getMasterServer().getServerModel().addToText(indexOfClient + " Server (ETSFO) - IOException \n");
				getMasterServer().getServerModel().addToText(indexOfClient + " Server (ETSFO) - will close streams\n");
				try {
					getClientInfo().getClientInput().close();
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: input closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: could not close input\n");
				}
				try {
					getClientInfo().getClientOutput().close();
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: output closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: could not close output\n");
				}

				try {
					getClientInfo().getClientSocket().close();
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: socket closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText(indexOfClient + " ETSFO: could not close socket\n");
				}
			}
		} else if(getMasterServer().isServerActive() == true){
			getMasterServer().getServerModel().addToText("No heartbeat received from " + indexOfClient + " in more than 3 seconds, attempting to close the clients connection\n");
			try {
				getClientInfo().getClientInput().close();
				getMasterServer().getServerModel().addToText(indexOfClient + " input closed\n");
			} catch (IOException e) {
				getMasterServer().getServerModel().addToText(indexOfClient + " could not close input\n");
			}
			try {
				getClientInfo().getClientOutput().close();
				getMasterServer().getServerModel().addToText(indexOfClient + " output closed\n");
			} catch (IOException e) {
				getMasterServer().getServerModel().addToText(indexOfClient + " could not close output\n");
			}

			try {
				getClientInfo().getClientSocket().close();
				getMasterServer().getServerModel().addToText(indexOfClient + " socket closed\n");
			} catch (IOException e) {
				getMasterServer().getServerModel().addToText(indexOfClient + " could not close socket\n");
			}
			getMasterServer().getServerModel().addToText("Removing client: " + indexOfClient + "\n");
			getMasterServer().getSocketArray().remove(getClientInfo());
			return;
		}

		if (receivedObject == null) {
			// create a new ETSearchForObject task with the same info and
			// place it in the ExecutorService after a brief pause
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(getMasterServer().isServerActive()){
				ETSearchForObject refreshedSearch = new ETSearchForObject(getMasterServer(), getClientInfo());
				getMasterServer().getThreadPool().execute(refreshedSearch);
			}
		} else {
			ObjectTransferrable receivedOperation = (ObjectTransferrable) receivedObject;
			if (!receivedOperation.getOpCode().equals("0014") && !receivedOperation.getOpCode().equals("0005")) {
				getMasterServer().getServerModel()
				.addToText("Received Object with opCode: " + receivedOperation.getOpCode()
				+ " from client with port " + getClientInfo().getClientSocket().getPort() + "\n");
			}
			// Create and ETRunTask object, and place it in the
			// ExecutorService
			if(getMasterServer().isServerActive()){
				ETRunTask newQueryToRun = new ETRunTask(getMasterServer(), getClientInfo(), receivedOperation);
				getMasterServer().getThreadPool().execute(newQueryToRun);
			}
		}
	}
}

