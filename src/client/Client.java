package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import gui.MainView;
import model.Model;
import model.ModelState;
import objectTransferrable.OTEmailCheck;
import objectTransferrable.OTExitGracefully;
import objectTransferrable.OTLogin;
import objectTransferrable.OTLoginSuccessful;
import objectTransferrable.OTRegistrationInformation;
import objectTransferrable.OTUsernameCheck;
import objectTransferrable.ObjectTransferrable;

public class Client {
	private int portnumber;
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Model model;
	private MainView view;
	private Socket s;

	public Client(int portnumber) {
		model = new Model(this);
		view = new MainView(this, model);
		model.addObserver(view);
		this.portnumber = portnumber;
		try {
			s = new Socket("localhost", portnumber);
			System.out.println("Client connected to port " + portnumber);
			toServer = new ObjectOutputStream(s.getOutputStream());
			fromServer = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			model.changeCurrentState(ModelState.UNABLETOOPENSTREAMS);
			e.printStackTrace();
		}

	}

	public Client() {

	}

	/**
	 * This is the only way to send object transferrables from the client. It is
	 * a private method.
	 * 
	 * @param OT
	 */
	private void writeToServer(ObjectTransferrable OT, boolean exit, String complementOpCode) {
		try {
			this.toServer.writeObject(OT);
			if (!exit) {
				this.readFromServer(complementOpCode);
			}
		} catch (IOException e) {
			// Connection is down, what do we do?
			this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
			System.out.println("Connection malfunctioned");
			this.attemptRecovery();

		}
	}

	public void readFromServer(String waitingForOpcode) {
		ObjectTransferrable receivedOperation = null;
		try {
			receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
			System.out.println("Messaged receieved from server with opcode " + receivedOperation.getOpCode());
			if (!receivedOperation.getOpCode().equals(waitingForOpcode)) {
				System.out.println("Client was expecting OT with OpCode " + waitingForOpcode
						+ "but received OT with OpCode " + receivedOperation.getOpCode());
				// should we cause an error here? throw new RuntimeException();
			}
			this.runOT(receivedOperation);
		} catch (ClassNotFoundException e) {
			// When the ObjectTransferrable isn't the right class
			System.out.println("OT from server could not be read");

		} catch (IOException e2) {
			System.out.println("Server connection is down");
			this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
			return;
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
		case "0013":
			OTLoginSuccessful loginObject = (OTLoginSuccessful) receivedOperation;
			if (loginObject.isLoginSuccessful()) {
				this.model.setSuccessfulLogin(true);
				this.model.setFirstName(loginObject.getFirstName());
				this.model.setLastname(loginObject.getLastName());
				this.model.setEmail(loginObject.getEmail());
				this.model.changeCurrentState(ModelState.LIST);
			} else {
				this.model.setSuccessfulLogin(false);
				this.model.setFirstName(null);
				this.model.setLastname(null);
				this.model.setEmail(null);
				this.model.setUsername(null);
			}
			break;
		}
	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);

		this.writeToServer(otuc, false, otuc.getOpCode());
		System.out.println("Sent OT with opcode " + otuc.getOpCode());

	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);

		this.writeToServer(otec, false, otec.getOpCode());
		System.out.println("Client: Sent OT with opcode " + otec.getOpCode());

	}

	public void checkRegistration(OTRegistrationInformation otri) {
		String complementOpCode = "0006";
		this.writeToServer(otri, false, complementOpCode);
		System.out.println("Client: Send OT with opcode " + otri.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
	}

	public void checkLoginDetails(OTLogin loginObject) {
		String complementOpCode = "0013";
		this.writeToServer(loginObject, false, complementOpCode);
		System.out.println("Client: Send OT with opcode " + loginObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
	}

	public void exitGracefully() {
		if (s != null) {
			OTExitGracefully oeg = new OTExitGracefully();
			this.writeToServer(oeg, true, null);
			System.out.println("Send OT to server to exit");

			try {
				toServer.close();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Could not close socket. \n Setting socket to null");
				s = null;
			}
		}
	}

	private void attemptToCloseConnections() {
		int safeClosure = 0;
		if (s != null) {
			try {
				s.shutdownOutput();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			try {
				s.shutdownInput();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			try {
				s.close();
			} catch (IOException e) {
				s = null;
				System.out.println("Socket is malfunctinoning. \n Setting socket to null");
			}
		}

	}

	private void attemptRecovery() {
		int count = 0;
		while (!s.isClosed() && count <= 2) {
			this.attemptToCloseConnections();
		}
		model.promptRestart();

	}

	@SuppressWarnings("unused")
	private void deprecatedAttemptRecovery() {
		this.attemptToCloseConnections();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();

		while (!this.model.getCurrentState().equals(ModelState.EXIT)) {
			// allows the user ~16.67 minutes to get their shit together
			if (endTime - startTime < 1000000) {
				try {
					this.s = new Socket("localhost", portnumber);
					break;
				} catch (IOException e) {
					System.out.println(
							"Connection has not yet been restored. Client will sleep for 1 second, then try again");
					Thread.currentThread();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						System.out.println("Client could not recover");
						this.model.changeCurrentState(ModelState.PROMPTRELOAD);
						return;
					}
				}
				if (Thread.interrupted()) {
					this.model.changeCurrentState(ModelState.PROMPTRELOAD);
					return;
				}
				System.out.println("Client is awake, trying to restore connection");
				endTime = System.currentTimeMillis();
			} else {
				System.out.println("Client could not recover in a timely fashion. ");
				this.model.changeCurrentState(ModelState.PROMPTRELOAD);
				return;
			}

		}

		if (!this.model.getCurrentState().equals(ModelState.EXIT)) {
			try {
				toServer = new ObjectOutputStream(s.getOutputStream());
			} catch (IOException e) {
				System.out.println("Could not create output stream to server");
			}

			try {
				fromServer = new ObjectInputStream(s.getInputStream());
			} catch (IOException e) {
				System.out.println("Could not create input stream to server");
			}
		} else {
			System.out.println("Goodbye!");
			return;
		}

	}

	/**
	 * @return the fromServer
	 */
	public ObjectInputStream getFromServer() {
		return fromServer;
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	public static void main(String[] args) {
		Client C = new Client(4449);
	}

}
