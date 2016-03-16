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
	private HeartBeatThread hb;
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Model model;
	private MainView view;
	private Socket s;
	private boolean error = false;

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
			this.hb = new HeartBeatThread(this);
			(new Thread(this.hb)).start();

		} catch (IOException e) {
			error = true;
			model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
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
		if (!error) {
			try {
				this.toServer.writeObject(OT);
				if (!exit) {
					this.readFromServer(complementOpCode);
				}
			} catch (IOException e) {
				System.out.println("Connection malfunctioned");
				this.error = true;
				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
				this.attemptRecovery();

			}
		}

	}

	// read from server
	public void readFromServer(String waitingForOpcode) {
		if (!error) {
			ObjectTransferrable receivedOperation = null;
			// if what was last sent was a heartbeat, use the waitForHeartBeat()
			// method
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
				// When the object recieved is not an Object Transferrable.
				// Likely to be that an exception has come in from server.
				System.out.println("OT from server could not be read");
				this.attemptRecovery();
				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);

			} catch (IOException e2) {
				System.out.println("Server connection is down");
				this.attemptRecovery();
				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);

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
			System.out.println("Received an arraylist of size " + eventsObject.getEventList().size());
			this.model.setMeetings(eventsObject.getEventList());
			break;

		case "0015":
			OTHashToClient userHash = (OTHashToClient) receivedOperation;
			String passwordAsString = this.model.getPasswordAsString();
			boolean userExists = userHash.getUserExists();
			if (userExists) {
				boolean successfulLogin = BCrypt.checkpw(passwordAsString, userHash.getHash());
				OTLoginSuccessful returnObject;
				if (successfulLogin) {
					returnObject = new OTLoginSuccessful(this.model.getUsername());
				} else {
					returnObject = new OTLoginSuccessful(this.model.getUsername());
					this.model.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGPASSWORD);
				}
				informServerLoginSuccess(returnObject);
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

		case "0018":
			OTUpdateEventSuccessful updateSuccess = (OTUpdateEventSuccessful) receivedOperation;
			this.model.setMeetingUpdateSuccessful(true);
			break;
		case "0020":
			OTDeleteEventSuccessful deleteSuccess = (OTDeleteEventSuccessful) receivedOperation;
			this.model.setMeetingDeleteSuccessful(true);
			break;
		case "0022":
			OTUpdateUserProfileSuccessful updateProfileSuccess = (OTUpdateUserProfileSuccessful) receivedOperation;
			this.model.setUpdateProfileSuccess(true);
			this.model.setFirstName(updateProfileSuccess.getFirstName());
			this.model.setLastname(updateProfileSuccess.getLastName());
			this.model.setEmail(updateProfileSuccess.getEmail());
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

	public void createEvent(OTCreateEvent newEvent) {
		String complementOpCode = "0011";
		System.out.println("Client: Sent OT with opcode " + newEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(newEvent, false, complementOpCode);

	}

	public void updateEvent(OTUpdateEvent changeEvent) {
		String complementOpCode = "0018";
		System.out.println("Client: Sent OT with opcode " + changeEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(changeEvent, false, complementOpCode);

	}

	public void deleteEvent(OTDeleteEvent deleteEvent) {
		String complementOpCode = "0020";
		System.out.println("Client: Sent OT with opcode " + deleteEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(deleteEvent, false, complementOpCode);

	}
	
	public void updateProfile(OTUpdateUserProfile updatedUserInfo) {
		String complementOpCode = "0022";
		System.out.println("Client: Sent OT with opcode " + updatedUserInfo.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(updatedUserInfo, false, complementOpCode);
		
	}
	
	public void sendHeartBeat() {
		String complementOpCode = "0014";
		OTHeartBeat othb = new OTHeartBeat();
		this.writeToServer(othb, false, complementOpCode);
	}

	// ----------writeToServer calls Ends---------------------------//
	@SuppressWarnings("deprecation")
	public void waitForHeartBeat() {
		try {
			OTHeartBeat future = (OTHeartBeat) this.fromServer.readObject();
			future.get(1000, TimeUnit.MILLISECONDS);
			System.out.println("Heartbeat received from server");
		} catch (ClassNotFoundException | IOException | InterruptedException | ExecutionException
				| TimeoutException e) {
			System.out.println("Hearbeat dead");
			this.attemptRecovery();
		}
	}

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
				fromServer.close();
				System.out.println("Input stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				fromServer = null;
			}

			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Could not close socket. \n Setting socket to null");
				s = null;
			}
		}
	}

	private void attemptRecovery() {
		System.out.println("Client is attempting recovery");
		this.hb.setRunningToFalse();
		this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
		int count = 0;
		while (!s.isClosed() && count < 2) {
			count++;
			System.out.println("Client closing connections, attempt " + count + ". Is socket closed? " + s.isClosed());
			this.attemptToCloseConnections();
			}
		System.out.println("prompting user to restart");
		model.promptUserToRestart();
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
				System.out.println("Input stream to server has been shutdown");
			} catch (IOException e1) {
				System.out.println("Input stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			try {
				s.close();
				System.out.println("Socket has been shutdown");
			} catch (IOException e) {
				s = null;
				System.out.println("Socket is malfunctinoning. \n Setting socket to null");
				
			}
		}

	}

	public void restart() {
		System.out.println("Client will attempt to reopen connections");
		attemptToOpenConnections();
	}

	private void attemptToOpenConnections() {
		try {
			s = new Socket("localhost", portnumber);
			System.out.println("Client connected to port " + portnumber);
			toServer = new ObjectOutputStream(s.getOutputStream());
			fromServer = new ObjectInputStream(s.getInputStream());
			this.view.dispose();
			System.out.println("Woohooo, all streams are open again!");
			model = new Model(this);
			System.out.println("New model made");
			view = new MainView(this, model);
			System.out.println("New view made");
			model.addObserver(view);
			System.out.println("New view is observing new model");
			this.hb = new HeartBeatThread(this);
			(new Thread(this.hb)).start();
			System.out.println("New heartbeat started");
			this.error = false;
		} catch (IOException e) {
			this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWNSTILL);
			try {
				Thread.currentThread();
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				System.out.println("Client was interrupted");
			}
			
			System.out.println("Hey, the server or internet connection is not working!");

		}
	}

	public static void main(String[] args) {
		Client C = new Client(4444);
	}

}
