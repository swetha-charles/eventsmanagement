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
		if (getMasterServer().isServerActive() == true) {
			try {
				receivedObject = getClientInfo().getClientInput().readObject();
//				getMasterServer().getServerModel().addToText("Server received object: " + receivedObject.toString() + "\n");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {
				//EOFexception
			} catch (SocketTimeoutException e) {
				//TimeoutException
			} catch (IOException e2) {
				getMasterServer().getServerModel().addToText("\nServer - IOException");
			}
			if (receivedObject == null) {
				// create a new ETSearchForObject task with the same info and
				// place it in the ExecutorService after a brief pause
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ETSearchForObject refreshedSearch = new ETSearchForObject(getMasterServer(), getClientInfo());
				getMasterServer().getThreadPool().execute(refreshedSearch);
			} else {
//				getMasterServer().getServerModel().addToText("Object not null, server will try to cast this object\n");

				ObjectTransferrable receivedOperation = (ObjectTransferrable) receivedObject;

//				getMasterServer().getServerModel().addToText("Server tried to cast object: " + receivedOperation.getOpCode() + "\n");

				if (receivedOperation != null) {
					getMasterServer().getServerModel()
							.addToText("Received Object with opCode: " + receivedOperation.getOpCode()
									+ " from client with port " + getClientInfo().getClientSocket().getPort() + "\n");
					// Create and ETRunTask object, and place it in the
					// ExecutorService
					ETRunTask newQueryToRun = new ETRunTask(getMasterServer(), getClientInfo(), receivedOperation);
					getMasterServer().getThreadPool().execute(newQueryToRun);
				}

			}

		}
	}

}
