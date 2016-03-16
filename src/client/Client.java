package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import gui.MainView;
import jBCrypt.BCrypt;
import model.Model;
import model.ModelState;
import objectTransferrable.*;

public class Client {
	private int portnumber;
	private Thread hb;
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
			this.hb = new Thread(new HeartBeatThread(this));
			this.hb.start();

		} catch (IOException e) {
			model.changeCurrentState(ModelState.UNABLETOOPENSTREAMS);
			e.printStackTrace();
		}

	}

	public Client() {

	}

	/**
	 * This is the only way to send object transferables from the client. It is
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

	// read from server
	public void readFromServer(String waitingForOpcode) {
		ObjectTransferrable receivedOperation = null;
		//if what was last sent was a heartbeat, use the waitForHeartBeat() method
		if (waitingForOpcode.equals("0014")) {
			waitForHeartBeat();
			return;
		}
		try {

			receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
			System.out.println("Messaged receieved from server with opcode " + receivedOperation.getOpCode());
			if (!receivedOperation.getOpCode().equals(waitingForOpcode)) {
				System.out.println("Client was expecting OT with OpCode " + waitingForOpcode
						+ "but received OT with OpCode " + receivedOperation.getOpCode());
				throw new UnexpectedOTReceivedException();

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
		case "0006":
			OTRegistrationInformationConfirmation regConf = (OTRegistrationInformationConfirmation) receivedOperation;
			if (regConf.getRegistrationSuccess()) {
				this.model.setSuccessfulRegistration(true);
				this.model.changeCurrentState(ModelState.LOGIN);
			} else {
				this.model.setSuccessfulRegistration(false);
			}
			break;
		case "0009":
			OTReturnDayEvents eventsObject = (OTReturnDayEvents) receivedOperation;
			this.model.setMeetings(eventsObject.getEventList());
			break;
		case "0011":
			OTCreateEventSucessful eventSuccess = (OTCreateEventSucessful) receivedOperation;
			this.model.setMeetingCreationSuccessful(true);
			break;
		case "0015":
			OTHashToClient userHash = (OTHashToClient) receivedOperation;
			String passwordAsString = this.model.getPasswordAsString();
			boolean userExists = userHash.getUserExists();
			if (userExists) {
				boolean successfulLogin = BCrypt.checkpw(passwordAsString, userHash.getHash());
				OTLoginSuccessful returnObject;
				if (successfulLogin) {
<<<<<<< .mine
					returnObject = new OTLoginSuccessful(this.model.getUsername());
					informServerLoginSuccess(returnObject);
=======
					returnObject = new OTLoginSuccessful(this.model.getUsername());
>>>>>>> .r260
				} else {
<<<<<<< .mine
=======
					returnObject = new OTLoginSuccessful(this.model.getUsername());
>>>>>>> .r260
					this.model.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGPASSWORD);
				}

			} else {
				this.model.setSuccessfulLogin(false);
				this.model.setFirstName(null);
				this.model.setLastname(null);
				this.model.setEmail(null);
				this.model.setUsername(null);
				this.model.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGUSERNAME);
			}
			break;
		case "0016":
			OTLoginProceed proceedOrNot = (OTLoginProceed) receivedOperation;
			boolean proceed = proceedOrNot.getLoginProceed();
			System.out.println("Proceed was: " + proceed);
			if (proceed) {
				this.model.setSuccessfulLogin(true);
				this.model.setFirstName(proceedOrNot.getFirstName());
				this.model.setLastname(proceedOrNot.getLastName());
				this.model.setEmail(proceedOrNot.getEmail());
				this.model.changeCurrentState(ModelState.EVENTS);
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

	// ---------------- writeToServer calls -----------------------//
	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		System.out.println("Sent OT with opcode" + otuc.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + otuc.getOpCode());
		this.writeToServer(otuc, false, otuc.getOpCode());

	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);
		System.out.println("Client: Sent OT with opcode" + otec.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + otec.getOpCode());
		this.writeToServer(otec, false, otec.getOpCode());

	}

	public void checkRegistration(OTRegistrationInformation otri) {
		String complementOpCode = "0006";
		System.out.println("Client: Sent OT with opcode " + otri.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(otri, false, complementOpCode);

	}

	public void checkLoginDetails(OTLogin loginObject) {
		String complementOpCode = "0015";
		System.out.println("Client: Sent OT with opcode " + loginObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(loginObject, false, complementOpCode);

	}

	public void informServerLoginSuccess(OTLoginSuccessful loginObject) {
		String complementOpCode = "0016";
		System.out.println("Client: Sent OT with opcode " + loginObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(loginObject, false, complementOpCode);

	}

	public void getMeetingsForDay(OTRequestMeetingsOnDay requestObject) {
		String complementOpCode = "0009";
		System.out.println("Client: Sent OT with opcode " + requestObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(requestObject, false, complementOpCode);
	}
	
	public void addNewEvent(OTCreateEvent newEvent) {
		String complementOpCode = "0011";
		System.out.println("Client: Sent OT with opcode " + newEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(newEvent, false, complementOpCode);
		
	}

	public void sendHeartBeat() {
		String complementOpCode = "0014";
		OTHeartBeat othb = new OTHeartBeat();
		this.writeToServer(othb, false, complementOpCode);
	}

	public void waitForHeartBeat(){
		try {
			OTHeartBeat future = (OTHeartBeat) this.fromServer.readObject();
			future.get(1000, TimeUnit.MILLISECONDS);
			System.out.println("Heartbeat received from server");
		} catch (ClassNotFoundException | IOException | InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("Hearbeat dead");
			this.attemptRecovery();
		}  
	}
	// ----------writeToServer calls Ends---------------------------//

	// --------------------- Exit -------------------------------------//
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

	public static void main(String[] args) {
		Client C = new Client(4449);
	}

}
