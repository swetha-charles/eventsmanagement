package server;

import java.io.EOFException;
import java.io.IOException;

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
		try {
			receivedOperation = (ObjectTransferrable) getClientInfo().getClientInput().readObject();
			System.out.println("Received Object with opCode: " + receivedOperation.getOpCode() + "from client with port " + getClientInfo().getClientSocket().getPort());
			//Create and ETRunTask object, and place it in the ExecutorService
			ETRunTask newQueryToRun = new ETRunTask(getMasterServer(), getClientInfo(), receivedOperation);
			getMasterServer().getThreadPool().execute(newQueryToRun);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {

		} catch (IOException e){
			e.printStackTrace();
		}

		if(receivedOperation == null){
			//create a new ETSearchForObject task with the same info and place it in the ExecutorService
			ETSearchForObject refreshedSearch = new ETSearchForObject(getMasterServer(), getClientInfo());
			getMasterServer().getThreadPool().execute(refreshedSearch);
		}
	}

}
