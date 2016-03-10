package server;

import java.io.IOException;
import java.io.ObjectInputStream;

import java.net.Socket;
import java.net.SocketException;
import objectTransferrable.*;

import model.Model;
import model.ModelState;

public class ThreadForServer extends Thread {

	private ObjectClientController occ;
	private ObjectInputStream fromServer;
	private boolean running = true;
	private Model model;
	private Socket socket;
	private int count = 0;

	public ThreadForServer(ObjectClientController occ, ObjectInputStream fromServer, Model model, Socket socket) {

		this.fromServer = fromServer;

		this.model = model;
		this.socket = socket;

	}

	@Override
	public void run() {
		while (running) {

			ObjectTransferrable receivedOperation = null;

			try {
				receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
				System.out.println("Messaged receieved from server with opcode " + receivedOperation.getOpCode());
				this.runOT(receivedOperation);
				if (Thread.interrupted()) {
					System.out.println("Thread for Server has been interrupted, shutting down");
					fromServer.close();
					socket.close();
					return;
				}
			} catch (ClassNotFoundException e) {
				// When the ObjectTransferrable isn't the right class
				e.printStackTrace();
			} catch (SocketException e1) {
				// Time out Exception
			} catch (IOException e2) {
				// fromserver is not OK, connection might be down.
				
				System.out.println("Server connection is down");

				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
				return;

			}
		}

	}

	public void exit() {
		this.running = false;

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
