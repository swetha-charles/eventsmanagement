package server;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import objectTransferrable.*;

public class ETSearchForObject implements ExecutableTask {

	private Server masterServer;
	private ClientInfo clientInfo;

	public ETSearchForObject(Server masterServer, ClientInfo clientInfo) {
		this.masterServer = masterServer;
		this.clientInfo = clientInfo;
	}

	public Server getMasterServer() {
		return masterServer;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	@Override
	public void run() {
		Object receivedObject = null;
		long differenceBetweenLastHBAndCurrent = System.currentTimeMillis() - getClientInfo().getHBReceivedMillis();
		int indexOfClient = getMasterServer().getSocketArray().indexOf(getClientInfo());
		if (getMasterServer().isServerActive() == true && differenceBetweenLastHBAndCurrent < 3000) {
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

