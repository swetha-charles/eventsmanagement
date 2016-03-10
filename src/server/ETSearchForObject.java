package server;

import java.io.EOFException;
import java.io.IOException;

import objectTransferrable.ObjectTransferrable;
import objectTransferrable.OTUsernameCheck;

public class ETSearchForObject implements ExecutableTask {

	private Server masterServer;
	private ClientInfo clientInfo;

	public ETSearchForObject(Server masterServer,
			ClientInfo clientInfo) {
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
		ObjectTransferrable receivedOperation = null;
		if(getMasterServer().isServerActive() == true){
			try {
				receivedOperation = (objectTransferrable.ObjectTransferrable) getClientInfo().getClientInput().readObject();
				getMasterServer().getServerModel().addToText("Received Object with opCode: " + receivedOperation.getOpCode() + " from client with port " + getClientInfo().getClientSocket().getPort() +"\n");
				//Create and ETRunTask object, and place it in the ExecutorService
				ETRunTask newQueryToRun = new ETRunTask(getMasterServer(), getClientInfo(), receivedOperation);
				getMasterServer().getThreadPool().execute(newQueryToRun);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException e) {
			} catch (IOException e){	
				//Nothing incoming from client
				e.printStackTrace();
			} 

			if(receivedOperation == null){
				//create a new ETSearchForObject task with the same info and place it in the ExecutorService after a brief pause
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ETSearchForObject refreshedSearch = new ETSearchForObject(getMasterServer(), getClientInfo());
				getMasterServer().getThreadPool().execute(refreshedSearch);
			}
		}
	}

}
