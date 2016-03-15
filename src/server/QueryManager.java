package server;

import java.sql.*;
import java.util.*;
import objectTransferrable.*;

public class QueryManager {

	private ObjectTransferrable operation;
	private Server server;
	private ClientInfo clientInfo;

	public QueryManager(ObjectTransferrable operation, Server server, ClientInfo clientInfo) {
		this.operation = operation;
		this.server = server;
		this.clientInfo = clientInfo;

	}

	public ObjectTransferrable getOperation() {
		return operation;
	}

	public void setOperation(ObjectTransferrable operation) {
		this.operation = operation;
	}

	public Server getServer() {
		return server;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void runOperation() throws SQLException {

		ObjectTransferrable currentOperation = getOperation();
		String query = null;
		Connection dbconnection = getServer().getDatabase().getConnection();
		// Prepared st?
		Statement stmnt = dbconnection.createStatement();

		/**
		 * List of present opcodes OTUsernameCheck "0001" OTEmailCheck = "0002"
		 * OTRegistrationCheck = "0003" OTRegistrationInformation = "0004"
		 * OTExitGracefully = "0005" - Should not appear at query manager
		 * OTRegistrationInformationConfirmation = "0006" - Should not appear at
		 * query manager
		 */
		// OTUsernameCheck "0001"
		if (currentOperation.getOpCode().equals("0001")) {
			query = checkUsername(stmnt);
		}
		// OTEmailCheck = "0002"
		else if (currentOperation.getOpCode().equals("0002")) {
			query = checkEmailvalid(stmnt);
		}
		// OTRegistrationCheck = "0003"
		else if (currentOperation.getOpCode().equals("0003")) {
			query = checkRegistration(stmnt);
		}
		// OTRegistrationInformation = "0004"
		else if (currentOperation.getOpCode().equals("0004")) {
			setOperation(new OTErrorResponse("OP code currently out of use!", false, 0004));
			getServer().getServerModel().addToText("opcode is presently depricated! Responding with Error Object");
		}
		// OP CODE 0005 SPECIAL CASE TO EXIT PROGRAM
		else if (currentOperation.getOpCode().equals("0005")) {
			getServer().getServerModel()
					.addToText("Specially reserved opcode for exiting program has arrived at query manager!");
			// No reason to tell the client, they gone, possible shutdown
			// communication?
		}
		// OP CODE 0006 RETURN FROM SERVER, SHOULD NEVER APPEAR HERE
		else if (currentOperation.getOpCode().equals("0006")) {
			setOperation(
					new OTErrorResponse("Server specified confirmation message recieved from client!", false, 0006));
			getServer().getServerModel().addToText(
					"The object assocatied with this opcode should not be recieved from client! Responding with Error Object");
		}
		// The client has returned an error, considering client passive previous
		// server response was bad
		else if (currentOperation.getOpCode().equals("0007")) {
			dealWithError();
		}
		// Request for meetings on specific Day
		else if (currentOperation.getOpCode().equals("0008")) {
			getMeetings(stmnt);
		}
		// Server Response to get meetings, should never get here
		else if (currentOperation.getOpCode().equals("0009")) {
			setOperation(new OTErrorResponse(
					"A message meant to be sent by the server (return list of meetings) has been found at the query manager!",
					false));
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (return list of meetings) has been found at the query manager!");
		}
		// Request to create an event from the client
		else if (currentOperation.getOpCode().equals("0010")) {
			// createEvent(stmnt);
		}
		// This is a return message for event creation successful and should not
		// be seen by server
		else if (currentOperation.getOpCode().equals("0011")) {
			setOperation(new OTErrorResponse(
					"A message meant to be sent by the server (sucessful event creation) has been found at the query manager!",
					false));
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sucessful event creation) has been found at the query manager!");
		}
		// Get users hashed password for the client
		else if (currentOperation.getOpCode().equals("0012")) {
			hashToClient(stmnt);
		}
		// Gets the users login details for the client
		else if (currentOperation.getOpCode().equals("0013")) {
			getUserDetails(stmnt);
		} else if (currentOperation.getOpCode().equals("0014")) {
			setOperation(new OTHeartBeat());
			getServer().getServerModel().addToText(
					"Server received heartbeat and has responded");
		}
		// This is a return message for sending the hash to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0015")) {
			setOperation(new OTErrorResponse(
					"A message meant to be sent by the server (sending the hash to the client) has been found at the query manager!",
					false));
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending the hash to the client) has been found at the query manager!");
		}
		// This is a return message for sending user details to client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0016")) {
			setOperation(new OTErrorResponse(
					"A message meant to be sent by the server (sending user details to client) has been found at the query manager!",
					false));
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending user details to client) has been found at the query manager!");
		}
		// Unknown OP code response
		else {
			setOperation(new OTErrorResponse("An unknown opCode has been recieved by the query manager!", false));
			getServer().getServerModel()
					.addToText("opcode of object not known by query manager! Responding with Error Object");
		}

	}

	private String checkUsername(Statement stmnt) {
		OTUsernameCheck classifiedOperation = (OTUsernameCheck) getOperation();
		getServer().getServerModel()
				.addToText("Checking to see if " + classifiedOperation.getUsername() + " is in the database...\n");
		String query = "SELECT count(u.userName) " + "FROM users u " + "GROUP BY u.userName " + "HAVING u.userName = '"
				+ classifiedOperation.getUsername() + "'";
		try {
			ResultSet rs = stmnt.executeQuery(query);

			if (rs.next()) {
				getServer().getServerModel().addToText("Found matching username, returning true.\n");
				classifiedOperation.setAlreadyExists(true);
			} else {
				getServer().getServerModel().addToText("Found no such user, returning false.\n");
				classifiedOperation.setAlreadyExists(false);
			}
			setOperation(classifiedOperation);
		} catch (SQLException e) {
			setOperation(new OTErrorResponse("SQL Server failed with username query", false));
			e.printStackTrace();

		}

		return query;
	}

	private String checkEmailvalid(Statement stmnt) {
		OTEmailCheck classifiedOperation = (OTEmailCheck) getOperation();
		getServer().getServerModel().addToText("Email received: " + classifiedOperation.getEmail() + "\n");
		// not sure what field name of email is? inserted guess
		String query = "SELECT count(u.userEmail) " + "FROM users u " + "GROUP BY u.userEmail "
				+ "HAVING u.userEmail = '" + classifiedOperation.getEmail() + "'";

		getServer().getServerModel().addToText(query);
		try {
			ResultSet rs = stmnt.executeQuery(query);

			if (rs.next()) {
				getServer().getServerModel().addToText("Email exists, returning true.\n");
				classifiedOperation.setAlreadyExists(true);
			} else {
				getServer().getServerModel().addToText("Email not in use, returning false.\n");
				classifiedOperation.setAlreadyExists(false);
			}
		} catch (SQLException e) {
			setOperation(new OTErrorResponse("SQL Server failed with email query", false));
			e.printStackTrace();

		}
		return "";
	}

	private String checkRegistration(Statement stmnt) {
		OTRegistrationInformation classifiedOperation = (OTRegistrationInformation) getOperation();
		getServer().getServerModel()
				.addToText("Attempting to create a user with name: " + classifiedOperation.getUsername() + "\n");
		String update = "INSERT INTO users VALUES ('" + classifiedOperation.getUsername() + "', '"
				+ classifiedOperation.getPwHash() + "', '" + classifiedOperation.getFirstname() + "', '"
				+ classifiedOperation.getLastname() + "', '" + classifiedOperation.getEmail() + "')";
		try {
			stmnt.executeUpdate(update);
			getServer().getServerModel().addToText("Succesfully created user");
			setOperation(new OTRegistrationInformationConfirmation(true, null, null));
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't create user");
			setOperation(new OTRegistrationInformationConfirmation(false, "ERROR", "ERROR"));
			e.printStackTrace();
		}

		return "";
	}

	private String dealWithError() {

		OTErrorResponse error = (OTErrorResponse) getOperation();
		// Is known error response can go here
		if (error.getErrCode() == 0) {
			System.err.println("Undefined error from client, Description: " + error.getErrorDescription()
					+ " Communications being shut down? " + error.isShouldShutdownCommunication());
		} else {
			/**
			 * TODO any specific error handling can go here
			 */
		}

		if (error.isShouldShutdownCommunication()) {

			/**
			 * TODO need to work out a call to shutdown, this is where being
			 * able to call exit gracefully could come in
			 */

		}
		return "";
	}

	private void getMeetings(Statement stmnt) {
		OTRequestMeetingsOnDay classifiedOperation = (OTRequestMeetingsOnDay) getOperation();

		String query = "SELECT m.meetingtitle, m.meetingdescription, m.meetinglocation, m.meetingstarttime, m.meetingendtime "
				+ "FROM meetings m " + "WHERE m.creatorid = '" + getClientInfo().getUserName()
				+ "' AND m.meetingdate = '" + classifiedOperation.getDate().toString() + "' "
				+ "ORDER BY m.meetingstarttime ASC";

		ResultSet rs;
		try {
			rs = stmnt.executeQuery(query);
			ArrayList<Event> meetings = new ArrayList<Event>();
			getServer().getServerModel()
					.addToText("Requesting meeting information for " + getClientInfo().getUserName() + "\n");
			while (rs.next()) {
				String title = rs.getString(1);
				String description = rs.getString(2);
				String location = rs.getString(3);
				Time startTime = rs.getTime(4);
				Time endTime = rs.getTime(5);

				Event event = new Event(startTime, endTime, description, title, location);
				meetings.add(event);
			}
			getServer().getServerModel().addToText("Returning " + meetings.size() + " meetings to client" + "\n");
			OTReturnDayEvents returnEvents = new OTReturnDayEvents(meetings);
			setOperation(returnEvents);
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with user details request" + "\n");
			setOperation(new OTErrorResponse("SQL Server failed with meeting request", false));
			e.printStackTrace();
		}
	}

	private void getUserDetails(Statement stmnt) {

		OTLoginSuccessful classifiedOperation = (OTLoginSuccessful) getOperation();

		String query = "SELECT u.firstName, u.lastName, u.userEmail " + "FROM users u " + "WHERE u.userName = '"
				+ classifiedOperation.getUsername() + "'";
		try {
			ResultSet rs = stmnt.executeQuery(query);

			if (rs.next()) {
				getServer().getServerModel()
						.addToText("Retrieved user details for " + classifiedOperation.getUsername() + "\n");
				String firstName, lastName, email;
				firstName = rs.getString(1);
				lastName = rs.getString(2);
				email = rs.getString(3);
				setOperation(new OTLoginProceed(true, firstName, lastName, email));
				getServer().getServerModel()
						.addToText("Set Client username to " + classifiedOperation.getUsername() + "\n");
				getClientInfo().setUserName(classifiedOperation.getUsername());
			} else {
				getServer().getServerModel()
						.addToText("User " + classifiedOperation.getUsername() + " does not exist" + "\n");
				setOperation(new OTLoginProceed(false, null, null, null));
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with user details request" + "\n");
			setOperation(new OTErrorResponse("SQL Server failed with user details request", false));
			e.printStackTrace();
		}
	}

	private void hashToClient(Statement stmnt) {
		OTLogin classifiedOperation = (OTLogin) getOperation();

		String query = "SELECT u.password " + "FROM users u " + "WHERE u.userName = '"
				+ classifiedOperation.getUsername() + "'";

		try {
			ResultSet rs = stmnt.executeQuery(query);

			if (rs.next()) {
				String pwFromDB = rs.getString(1);

				getServer().getServerModel().addToText("Sending user following hash: " + pwFromDB + "\n");
				setOperation(new OTHashToClient(true, pwFromDB));

			} else {
				getServer().getServerModel()
						.addToText("User " + classifiedOperation.getUsername() + " does not exist" + "\n");
				setOperation(new OTHashToClient(false, null));
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with hash request" + "\n");
			setOperation(new OTErrorResponse("SQL Server failed with hash request", false));
			e.printStackTrace();
		}
	}

}