package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import objectTransferrable.*;

import model.Model;
import model.ModelState;

public class ThreadForServer extends Thread {

	private Client client;
	private ObjectInputStream fromServer;
	private boolean running = true;
	private Model model;

	public ThreadForServer(Client client) {
		this.client = client;
		this.fromServer = client.getFromServer();
		this.model = client.getModel();
		//TODO could be simplified as all of this is stored by the client right? could be simplified further by just getting from the client object?
		}

	
	@Override
	public void run() {
		while (running) {
			ObjectTransferrable receivedOperation = null;
			try {
				receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
				System.out.println("Messaged receieved from server with opcode " + receivedOperation.getOpCode());
				String waitingForOpCode;
				while (true) {
					try {
						waitingForOpCode = this.client.getWaitingFor().take();
						break;
					} catch (InterruptedException e) {
						//WaitingFor is blocked, try again
					}
				}
				if (!waitingForOpCode.equals(receivedOperation.getOpCode())) {
					System.out.println("OT from server does not match what client is waiting for");
					throw new RuntimeException();
				}
				
				this.runOT(receivedOperation);

				if (Thread.interrupted()) {
					System.out.println("Thread for Server has been interrupted, shutting down");
					this.running = false;
					fromServer.close();
					return;
				}
			} catch (ClassNotFoundException e) {
				// When the ObjectTransferrable isn't the right class
				System.out.println("OT from server could not be read");

			} catch (IOException e2) {
				System.out.println("Server connection is down");
				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
				return;

			}
		}

	}

	private void runOT(ObjectTransferrable receivedOperation) {
		switch (receivedOperation.getOpCode()) {

		case "0001":
			OTUsernameCheck otuc = (OTUsernameCheck) receivedOperation;
			if (otuc.getAlreadyExists()) {
				this.model.setUsername(otuc.getUsername());
				this.model.setUsernameExists(true);
			} else if (!otuc.getAlreadyExists()) {
				this.model.setUsername(otuc.getUsername());
				this.model.setUsernameExists(false);
			}
			break;
		case "0002":
			OTEmailCheck otec = (OTEmailCheck) receivedOperation;
			if (otec.getAlreadyExists()) {
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(true);
			} else if (!otec.getAlreadyExists()) {
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(false);
			}
			break;
		}
	}

}
