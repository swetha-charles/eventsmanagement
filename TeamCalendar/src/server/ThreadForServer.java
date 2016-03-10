package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

import model.Model;

public class ThreadForServer extends Thread{
	LinkedBlockingQueue<ObjectTransferrable> OT;
	private ObjectClientController occ;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	private Model model;
	
	public ThreadForServer(ObjectClientController occ, ObjectInputStream fromServer, ObjectOutputStream toServer, Model model){
		this.OT = new LinkedBlockingQueue<ObjectTransferrable>();
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.model = model;
		
	}

	@Override
	public void run() {
		while(true){
			ObjectTransferrable receivedOperation = null;
			try {
				receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
				this.runOT(receivedOperation);
			} catch (ClassNotFoundException e) {
				// When the ObjectTransferrable isn't the right class
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	private void runOT(ObjectTransferrable receivedOperation){
		System.out.println(receivedOperation.getOpCode());
		switch(receivedOperation.getOpCode()){
		
		case "0001":
			OTUsernameCheck otuc = (OTUsernameCheck) receivedOperation;
			if(otuc.getAlreadyExists()){
				this.model.setUsername(otuc.getUsername());
				this.model.setUsernameExists(true);
			} else if(!otuc.getAlreadyExists()){
				this.model.setUsername(otuc.getUsername());
				this.model.setUsernameExists(false);
			}
			break;
		case "0002":
			OTEmailCheck otec = (OTEmailCheck) receivedOperation;
			if(otec.getAlreadyExists()){
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(true);
			} else if(!otec.getAlreadyExists()){
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(false);
			}
			break;
		}
	}
	
}
