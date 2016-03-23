package server;

import java.io.IOException;
import java.sql.SQLException;
import objectTransferrable.*;


public class ETRunTask implements ExecutableTask{

	private Server masterServer;
	private ClientInfo clientInfo;
	private ObjectTransferrable query;

	public ETRunTask(Server masterServer,
			ClientInfo clientInfo,
			ObjectTransferrable query) {
		this.masterServer = masterServer;
		this.clientInfo = clientInfo;
		this.query = query;
	}

	public Server getMasterServer() {
		return masterServer;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public ObjectTransferrable getQuery() {
		return query;
	}

	@Override
	public void run() {
		//create a new QueryManager object to handle the execution of the received object
		QueryManager runQuery = getMasterServer().getQueryManager();
		ObjectTransferrable returnObject = null;

		//execute the query, updating the object
		try {
			returnObject = runQuery.runOperation(getQuery(), getClientInfo());
			//get the updated object and pass it back to the client
			try {
				if(!returnObject.getOpCode().equals("0014")){
					getMasterServer().getServerModel().addToText("Sending back Object with opCode " + returnObject.getOpCode() + "\n");
				}
				//write back to client
				getClientInfo().getClientOutput().writeObject(returnObject);
				if(!returnObject.getOpCode().equals("0014")){
					getMasterServer().getServerModel().addToText("SENT" + "\n");
				} else {
					getMasterServer().getServerModel().addToText(getMasterServer().getSocketArray().indexOf(getClientInfo()) + "HB\n");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
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
