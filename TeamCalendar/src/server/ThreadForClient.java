package server;

import java.io.*;
import java.net.*;

public class ThreadForClient implements Runnable{

	Socket clientSocket;
	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	
	public ThreadForClient(Socket clientSocket){
		this.clientSocket = clientSocket;
		try {
			fromClient = new ObjectInputStream(clientSocket.getInputStream());
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			ObjectTransferrable receivedOperation = null;
			try {
				receivedOperation = (ObjectTransferrable) fromClient.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ObjectTransferrable classifiedOperation;
			classifiedOperation = operationClassification(receivedOperation);
			
			classifiedOperation.run();
		}
	}
	
	public ObjectTransferrable operationClassification(ObjectTransferrable receivedOperation){
		ObjectTransferrable classifiedOperation = null;
		
		if(receivedOperation.getOpCode().equals("0001")){
			classifiedOperation = (OTUsernameCheck) receivedOperation;
		}
		return classifiedOperation;
	}

}	
