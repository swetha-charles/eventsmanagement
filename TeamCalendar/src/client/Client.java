package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JOptionPane;

import gui.ListPanel;
import gui.List;
import gui.MainView;
import model.Model;
import model.ModelState;
import objectTransferrable.OTCreateEvent;
import objectTransferrable.OTCreateEventSucessful;
import objectTransferrable.OTDeleteEvent;
import objectTransferrable.OTDeleteEventSuccessful;
import objectTransferrable.OTEmailCheck;
import objectTransferrable.OTErrorResponse;
import objectTransferrable.OTExitGracefully;
import objectTransferrable.OTHashToClient;
import objectTransferrable.OTHeartBeat;
import objectTransferrable.OTLogin;
import objectTransferrable.OTLoginProceed;
import objectTransferrable.OTLoginSuccessful;
import objectTransferrable.OTRegistrationInformation;
import objectTransferrable.OTRegistrationInformationConfirmation;
import objectTransferrable.OTRequestMeetingsOnDay;
import objectTransferrable.OTReturnDayEvents;
import objectTransferrable.OTUpdateEvent;
import objectTransferrable.OTUpdateEventSuccessful;
import objectTransferrable.OTUpdatePassword;
import objectTransferrable.OTUpdateUserProfile;
import objectTransferrable.OTUpdateUserProfileSuccessful;
import objectTransferrable.OTUsernameCheck;
import objectTransferrable.ObjectTransferrable;

public class Client {
	private int portnumber;
	private HeartBeatThread hb;
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Model model;
	private MainView view;
	private Socket s;
	private boolean error = false;

	public Client(int portnumber, String url) {
		model = new Model(this);
		view = new MainView(this, model);
		model.addObserver(view);
		this.portnumber = portnumber;
		try {

			InetAddress addr = InetAddress.getByName(url);
			s = new Socket(addr, portnumber);		
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

	/**
	 * Left for testing
	 */
	public Client() {

	}

	
	/**
	 * This is the only way to send object transferables from the client. It is
	 * a private method.
	 * 
	 * @param OT
	 */
	private synchronized void writeToServer(ObjectTransferrable OT, String complementOpCode) {
		if (!error) {
			try {
				this.toServer.writeObject(OT);
				this.readFromServer(complementOpCode);
			} catch (IOException e) {
				System.out.println("Connection malfunctioned");
				this.error = true;
				this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
				this.cleanUpAndPromptUserToRestart();

			}
		} else {
			return;
		}

	}

	/**
	 * This is the only way to read objects from server. It is a private method. 
	 * @param waitingForOpcode
	 */
	private synchronized void readFromServer(String waitingForOpcode) {
		if (!error) {
			ObjectTransferrable receivedOperation = null;
			// if what was last sent was a heartbeat, use the waitForHeartBeat()
			// method
			if (waitingForOpcode.equals("0014")) {
				this.waitForHeartBeat();
				// if we're waiting for exit confirmation, use a different
				// method.
			} else if (waitingForOpcode.equals("0005")) {
				this.waitForExitConfirmation();
			} else {
				try {
					receivedOperation = (ObjectTransferrable) this.fromServer.readObject();
					System.out.println("Messaged receieved from server with opcode " + receivedOperation.getOpCode());
					receivedOperation.get(5000, TimeUnit.MILLISECONDS);
				} catch (ClassNotFoundException e) {
					// When the object recieved is not an Object Transferrable.
					// Likely to be that an exception thrown from server side.
					this.error = true;
					System.out.println("Input from server could not be read, it was not an OT");
					this.cleanUpAndPromptUserToRestart();
					this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);

				} catch (IOException e2) {
					this.error = true;
					System.out.println("Server connection is down");
					this.cleanUpAndPromptUserToRestart();
					this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);

				} catch (InterruptedException | ExecutionException | TimeoutException e) {
					this.error = true;
					System.out.println("Server has now responded in time!");
					this.cleanUpAndPromptUserToRestart();
					this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
				}

				if (receivedOperation.getOpCode().equals("0007")) {
					// received an error response from server
					OTErrorResponse oter = (OTErrorResponse) receivedOperation;
					System.out.println("Client received error message from server: " + oter.getErrorDescription());
				} else if (!receivedOperation.getOpCode().equals(waitingForOpcode)) {
					// not an error message and not what was expected either
					//Throw an exception?
					throw new UnexpectedOTReceivedException();
				} else if (receivedOperation.getOpCode().equals(waitingForOpcode)) {
					// what was expected, then run.
					this.runObjectTransferrableReceivedFromServer(receivedOperation);
				}
			}

		}

	}



	/**
	 * This method runs the object received from server
	 * 
	 * @param receivedOperation
	 */
	private synchronized void runObjectTransferrableReceivedFromServer(ObjectTransferrable receivedOperation) {
		switch (receivedOperation.getOpCode()) {

		case "0001":
			// asking server to check whether this username is duplicated
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
			// asking server to check whether this email is duplicated
			OTEmailCheck otec = (OTEmailCheck) receivedOperation;
			if (otec.getAlreadyExists()) {
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(true);
			} else if (!otec.getAlreadyExists()) {
				this.model.setEmail(otec.getEmail());
				this.model.setEmailExists(false);
			}
			break;
		case "0003":
			System.out.println("Client should not have recieved this opcode");
			break;
		case "0004":
			System.out.println("This is an open opcode.\n" + "Should never be received by client. ");
			break;
		case "0005":
			System.out.println("Client should never be dealing with this opcode here\n"
					+ "waitForExitConfirmation() should run this method");
		case "0006":
			// received a response from server on whether user's registration
			// was
			// sucessful
			OTRegistrationInformationConfirmation regConf = (OTRegistrationInformationConfirmation) receivedOperation;

			if (regConf.getRegistrationSuccess()) {
				this.model.setSuccessfulRegistration(true);
				this.model.changeCurrentState(ModelState.LOGIN);
			} else {
				this.model.setSuccessfulRegistration(false);
				JOptionPane.showMessageDialog(model.getCurrentScrollPanel(),
						"Sorry, another user with the same username was just created! \n "
								+ "Change the username and try again.");
			}
			break;
		case "0007":
			// reserved for use by server
			System.out.println("The server has encountered an error \n ");
			OTErrorResponse errorResponse = (OTErrorResponse) receivedOperation;
			System.out.println("Error received was: " + errorResponse.getErrorDescription());
			break;
		case "0008":
			System.out.println("Should never be received by client. \n" + "Reserved for client-sending use only");
			break;
		case "0009":
			// received information from client with information on meetings
			// on specified day
			OTReturnDayEvents eventsObject = (OTReturnDayEvents) receivedOperation;
			System.out.println("Received an arraylist of size " + eventsObject.getEventList().size());
			this.model.setMeetings(eventsObject.getEventList());
			break;
		case "0010":
			// used by client to tell server to make a new event
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0011":
			// used by server to inform client whether adding the meetings
			// was successful or not
			OTCreateEventSucessful successfullyAddedEvent = (OTCreateEventSucessful) receivedOperation;
			this.model.setMeetingCreationSuccessful(true);
			model.updateMeetings(new Date(model.getCalendar().getTimeInMillis()));
			try {
				((gui.List) (this.model.getCurrentInnerPanel())).getListPanel().addMeetings(model.getMeetings());
				this.model.changeCurrentState(ModelState.EVENTSUPDATE);
			} catch (ClassCastException e) {
				JOptionPane.showMessageDialog((this.model.getCurrentInnerPanel()),
						"System encountered an error. \n" + "Press refresh and try again");
			}
			break;
		case "0012":
			// sent by client to try to login a user
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0013":
			// sent by client to inform the server that login
			// has been successful
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0014":
			// this is the heartbeat
			// there is a separate method to deal with hearbeats
			System.err.println("WARNING: Received heartbeat in main runOT(). \n" + "This should never happen");
			break;
		case "0015":
			// sent by server. Includes the hashed password ofthe user
			// trying to login.
			OTHashToClient userHash = (OTHashToClient) receivedOperation;
			boolean userExists = userHash.getUserExists();
			if (userExists) {
				model.setHashedPassword(userHash.getHash());
			} else {
				this.model.setSuccessfulLogin(false);
				this.model.setFirstName(null);
				this.model.setLastname(null);
				this.model.setEmail(null);
				this.model.setUsername(null);
				this.model.setHashedPassword(null);
			}
			break;
		case "0016":
			// received from server confirming successful login.
			// this object also populates the user's current day meetings
			// view
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

		case "0017":
			// Sent by client when user wants
			// to edit an event
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0018":
			// Sent by server to indicate whether the edit
			// to an event has been successful
			OTUpdateEventSuccessful updateSuccess = (OTUpdateEventSuccessful) receivedOperation;
			if (updateSuccess.getSuccessful()) {
				this.model.setMeetingUpdateSuccessful(true);
			} else {
				this.model.setMeetingUpdateSuccessful(false);
			}
			break;
		case "0019":
			// Sent by client when user wants
			// to delete an event
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0020":
			// Sent by server to indicate whether deleting
			// an event has been successful
			OTDeleteEventSuccessful deleteSuccess = (OTDeleteEventSuccessful) receivedOperation;
			if (deleteSuccess.getSuccessful()) {
				this.model.setMeetingDeleteSuccessful(true);
			} else {
				this.model.setMeetingDeleteSuccessful(false);
			}
			break;
		case "0021":
			// Sent by client when user wants to edit their
			// user information.
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0022":
			// Sent by server to indcate that the profile changes have
			// been successful.
			OTUpdateUserProfileSuccessful updateProfileSuccess = (OTUpdateUserProfileSuccessful) receivedOperation;
			this.model.setUpdateProfileSuccess(true);
			this.model.setFirstName(updateProfileSuccess.getFirstName());
			this.model.setLastname(updateProfileSuccess.getLastName());
			this.model.setEmail(updateProfileSuccess.getEmail());
			break;
		case "0023":
			// Sent by client when user wants to edit their
			// password.
			System.out.println("Should never be received by client\n " + "Reserved for client-sending use only");
			break;
		case "0024":
			// send by server to indicat that changing the password
			// has been successful. In case the password changed is not
			// succesful,
			// an OTErrorResponse is received instead.
			this.model.setUpdatePasswordSuccess(true);
			System.out.println("Client: Password has been updated");
			break;
		}

	}
	
	/**
	 * This read from server call is used in special cases of heartbeats.
	 * A heartbeat is a simple object for the server to construct and send back. 
	 * With this in mind, the wait on this operation is 200ms. When the client
	 * sends more database heavy operations, it waits on the server for longer. 
	 */
	private synchronized void waitForHeartBeat() {
		OTHeartBeat OT = null;
		try {
			OT = (OTHeartBeat) this.fromServer.readObject();
			OT.get(200, TimeUnit.MILLISECONDS);

		} catch (ClassNotFoundException | IOException | InterruptedException | ExecutionException
				| TimeoutException e) {
			System.out.println("Hearbeat may be dead, client encountered an error of type " + e.getClass());
			this.cleanUpAndPromptUserToRestart();
		}
	}
	
	/**
	 * This read from server call is used in special cases of waiting for exit confirmation.
	 * Again the wait is quite short - 200s. 
	 */
	private synchronized void waitForExitConfirmation() {
		OTExitGracefully OTExitGracefullyConfirmation;
		try {
			OTExitGracefullyConfirmation = (OTExitGracefully) this.fromServer.readObject();
			OTExitGracefullyConfirmation.get(200, TimeUnit.MILLISECONDS);
			System.out.println("Client received confirmation of exit from server");
		} catch (ClassNotFoundException | IOException | InterruptedException | ExecutionException
				| TimeoutException e) {
			System.out.println("Did not get confirmation of exit from server, will close down communication. \n"
					+ "Received an error " + e.getClass());
			e.printStackTrace();

		}
	}

	// ---------------- writeToServer calls -----------------------//
	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		System.out.println("Sent OT with opcode" + otuc.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + otuc.getOpCode());
		this.writeToServer(otuc, otuc.getOpCode());

	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);
		System.out.println("Client: Sent OT with opcode" + otec.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + otec.getOpCode());
		this.writeToServer(otec, otec.getOpCode());

	}

	public void checkRegistration(OTRegistrationInformation otri) {
		String complementOpCode = "0006";
		System.out.println("Client: Sent OT with opcode " + otri.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(otri, complementOpCode);

	}

	public void checkLoginDetails(OTLogin loginObject) {
		String complementOpCode = "0015";
		System.out.println("Client: Sent OT with opcode " + loginObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(loginObject, complementOpCode);

	}

	public void informServerLoginSuccess(OTLoginSuccessful loginObject) {
		String complementOpCode = "0016";
		System.out.println("Client: Sent OT with opcode " + loginObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(loginObject, complementOpCode);

	}

	public void getMeetingsForDay(OTRequestMeetingsOnDay requestObject) {
		String complementOpCode = "0009";
		System.out.println("Client: Sent OT with opcode " + requestObject.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(requestObject, complementOpCode);
	}

	public void createEvent(OTCreateEvent newEvent) {
		String complementOpCode = "0011";
		System.out.println("Client: Sent OT with opcode " + newEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(newEvent, complementOpCode);

	}

	public void updateEvent(OTUpdateEvent changeEvent) {
		String complementOpCode = "0018";
		System.out.println("Client: Sent OT with opcode " + changeEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(changeEvent, complementOpCode);

	}

	public void deleteEvent(OTDeleteEvent deleteEvent) {
		String complementOpCode = "0020";
		System.out.println("Client: Sent OT with opcode " + deleteEvent.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(deleteEvent, complementOpCode);

	}

	public void updateProfile(OTUpdateUserProfile updatedUserInfo) {
		String complementOpCode = "0022";
		System.out.println("Client: Sent OT with opcode " + updatedUserInfo.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(updatedUserInfo, complementOpCode);

	}

	public void updatePassword(OTUpdatePassword updatedPassword) {
		String complementOpCode = "0024";
		System.out.println("Client: Sent OT with opcode " + updatedPassword.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
		this.writeToServer(updatedPassword, complementOpCode);
	}

	public synchronized void sendHeartBeat() {
		String complementOpCode = "0014";
		OTHeartBeat othb = new OTHeartBeat();
		this.writeToServer(othb, complementOpCode);
	}

	// -------------writeToServer calls end---------------------------//
	
	
	
	// --------------------- Clean Exit -------------------//
	
	/**
	 * When the user closes the window, the client runs this method
	 */
	public synchronized void exitGracefully() {
		if (s != null) {
			// stop the heartbeat
			this.hb.setRunningToFalse();
			OTExitGracefully oeg = new OTExitGracefully();
			String complementOpCode = "0005";
			System.out.println("Sending OT to server to exit");
			this.writeToServer(oeg, complementOpCode);
			// this chains together
			// waitForExitConfirmation().
			// Finally, we close connections
			this.attemptToCloseConnections();
		}
	}
	
	/**
	 * This method closes the connections	
	 */
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

	
	//---------------Not so clean exit-------------------//
	

	/**
	 * After an error or lack of hearbeat, client runs this private method. 
	 * Changing the mode's state to ErrorConnection down displays a suitable message. 
	 * 
	 * After connections are closed, the model.promptUserToRestart() is run. 
	 * This displays a restart button to the user. 
	 * 
	 * If that restart button is pressed, the client's restart() method is run. 
	 */
	private void cleanUpAndPromptUserToRestart() {
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

	/**
	 * When the user presses the restart button on the error page, this method is run.
	 */
	public void restart() {
		System.out.println("Client will attempt to reopen connections");
		attemptToOpenConnections();
	}
	
	/**
	 * This is a private method used to reopen connnections .
	 */
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
			//New connections have been started, 
			//set error to false!
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
	
	/**
	 * A getter that returns the boolean value of whether 
	 * the client is in error state. 
	 * @return
	 */
	public boolean getError() {
		return this.error;
	}
	public static void main(String[] args) {

		int port = 50280;
		String iNetAddress = "localHost" ; 
		if (args.length == 1){
				System.out.println("No port specified, using default");
				iNetAddress = args[0];
		}
		else if (args.length >= 2){
			iNetAddress = args[0];
			port = Integer.parseInt(args[1]);
		}
		else{
			System.out.println("No arguements supplied, using default address " + iNetAddress + "and port " + port); 
		}
		Client C = new Client(port, iNetAddress);

	}

}
