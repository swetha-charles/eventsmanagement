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

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {
			} catch (SocketTimeoutException e) {
			} catch (IOException e2) {
				getMasterServer().getServerModel().addToText("\nServer (ETSFO) - IOException");
				getMasterServer().getServerModel().addToText("\nServer (ETSFO) - IOException \n");
				getMasterServer().getServerModel().addToText("\nServer (ETSFO) - will close streams\n");
				try {
					getClientInfo().getClientInput().close();
					getMasterServer().getServerModel().addToText("ETSFO: input closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText("ETSFO: could not close input\n");
				}
				try {
					getClientInfo().getClientOutput().close();
					getMasterServer().getServerModel().addToText("ETSFO: output closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText("ETSFO: could not close output\n");
				}

				try {
					getClientInfo().getClientSocket().close();
					getMasterServer().getServerModel().addToText("ETSFO: socket closed\n");
				} catch (IOException e) {
					getMasterServer().getServerModel().addToText("ETSFO: could not close socket\n");
				}
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
				ObjectTransferrable receivedOperation = (ObjectTransferrable) receivedObject;
				if (receivedOperation.getOpCode().equals("0005")) {
					getMasterServer().getServerModel()
					.addToText("Specially reserved opcode for exiting program has arrived at server!" + "\n");
					getMasterServer().getServerModel().addToText("Server will acknowledge exit confirmation \n");

				}
				if (!receivedOperation.getOpCode().equals("0014") && !receivedOperation.getOpCode().equals("0005")) {
					getMasterServer().getServerModel()
					.addToText("Received Object with opCode: " + receivedOperation.getOpCode()
					+ " from client with port " + getClientInfo().getClientSocket().getPort() + "\n");
				}
				// Create and ETRunTask object, and place it in the
				// ExecutorService
				ETRunTask newQueryToRun = new ETRunTask(getMasterServer(), getClientInfo(), receivedOperation);
				getMasterServer().getThreadPool().execute(newQueryToRun);
			}
		}
	}
}
