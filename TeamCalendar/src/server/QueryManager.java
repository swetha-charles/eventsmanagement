package server;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import objectTransferrable.*;

public class QueryManager {

	private Server server;

	public QueryManager(Server server) {
		this.server = server;

	}

	public Server getServer() {
		return server;
	}

	public ObjectTransferrable runOperation(ObjectTransferrable currentOperation, ClientInfo client) throws SQLException {

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
			return checkUsername(stmnt, currentOperation, client);
		}
		// OTEmailCheck = "0002"
		else if (currentOperation.getOpCode().equals("0002")) {
			return checkEmailvalid(stmnt, currentOperation, client);
		}
		// OTRegistrationCheck = "0003"
		else if (currentOperation.getOpCode().equals("0003")) {
			return checkRegistration(stmnt, currentOperation, client);
		}
		// OTRegistrationInformation = "0004"
		else if (currentOperation.getOpCode().equals("0004")) {
			getServer().getServerModel().addToText("opcode is presently depricated! Responding with Error Object\n");
			return new OTErrorResponse("OP code currently out of use!", false, 0004);
		}
		// OP CODE 0005 SPECIAL CASE TO EXIT PROGRAM
		else if (currentOperation.getOpCode().equals("0005")) {
			getServer().getServerModel()
			.addToText("Specially reserved opcode for exiting program has arrived at query manager!\n");
			// Acknowledge the client is exiting. 
			return new OTExitGracefully();
			//ETRunTask will close connections.  
		}
		// OP CODE 0006 RETURN FROM SERVER, SHOULD NEVER APPEAR HERE
		else if (currentOperation.getOpCode().equals("0006")) {
			getServer().getServerModel().addToText(
					"The object assocatied with this opcode should not be recieved from client! Responding with Error Object\n");
			return new OTErrorResponse("Server specified confirmation message recieved from client!", false, 0006);

		}
		// The client has returned an error, considering client passive previous
		// server response was bad
		else if (currentOperation.getOpCode().equals("0007")) {
			return dealWithError(stmnt, currentOperation, client);
		}
		// Request for meetings on specific Day
		else if (currentOperation.getOpCode().equals("0008")) {
			return getMeetings(stmnt, currentOperation, client);
		}
		// Server Response to get meetings, should never get here
		else if (currentOperation.getOpCode().equals("0009")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (return list of meetings) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (return list of meetings) has been found at the query manager!",
					false);

		}
		// Request to create an event from the client
		else if (currentOperation.getOpCode().equals("0010")) {
			return createEvent(stmnt, currentOperation, client);
		}
		// This is a return message for event creation successful and should not
		// be seen by server
		else if (currentOperation.getOpCode().equals("0011")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sucessful event creation) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sucessful event creation) has been found at the query manager!",
					false);

		}
		// Get users hashed password for the client
		else if (currentOperation.getOpCode().equals("0012")) {
			return hashToClient(stmnt, currentOperation, client);
		}
		// Gets the users login details for the client
		else if (currentOperation.getOpCode().equals("0013")) {
			return getUserDetails(stmnt, currentOperation, client);
		} else if (currentOperation.getOpCode().equals("0014")) {
			return new OTHeartBeat();
			//getServer().getServerModel().addToText(
			//		"Server received heartbeat and has responded");
		}
		// This is a return message for sending the hash to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0015")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending the hash to the client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending the hash to the client) has been found at the query manager!",
					false);

		}
		// This is a return message for sending user details to client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0016")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending user details to client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending user details to client) has been found at the query manager!",
					false);

		}
		//Updates an events details
		else if (currentOperation.getOpCode().equals("0017")) {
			return updateEvent(dbconnection, currentOperation, client);
		}
		// This is a return message for sending meeting update success to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0018")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending meeting update success to the client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending meeting update success to the client) has been found at the query manager!",
					false);

		}
		//Deletes an event
		else if (currentOperation.getOpCode().equals("0019")) {
			return deleteEvent(stmnt, dbconnection, currentOperation, client);
		}
		// This is a return message for sending meeting delete success to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0020")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending meeting delete success to the client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending meeting delete success to the client) has been found at the query manager!",
					false);

		}
		//Updates a users profile
		else if (currentOperation.getOpCode().equals("0021")) {
			return updateUserProfile(stmnt, currentOperation, client);
		}
		// This is a return message for sending update user profile success to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0022")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending update user profile success to the client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending update user profile success to the client) has been found at the query manager!",
					false);

		}
		//Updates a users password
		else if (currentOperation.getOpCode().equals("0023")) {
			return updateUserPassword(stmnt, currentOperation, client);
		}
		// This is a return message for sending update user password success to the client and
		// should not be seen by server
		else if (currentOperation.getOpCode().equals("0024")) {
			getServer().getServerModel().addToText(
					"A message meant to be sent by the server (sending update user password success to the client) has been found at the query manager!\n");
			return new OTErrorResponse(
					"A message meant to be sent by the server (sending update user password success to the client) has been found at the query manager!",
					false);

		}
		// Unknown OP code response
		else {
			getServer().getServerModel()
			.addToText("opcode of object not known by query manager! Responding with Error Object\n");
			return new OTErrorResponse("An unknown opCode has been recieved by the query manager!", false);

		}

	}

	private ObjectTransferrable updateUserPassword(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTUpdatePassword classifiedOperation = (OTUpdatePassword) operation;

		getServer().getServerModel()
		.addToText("Attempting to update the following users password: " + client.getUserName() + "\n");

		String update = "UPDATE users " 
				+"SET password= '" + classifiedOperation.getPwhash() 
				+"' "
				+"WHERE userName= '" + client.getUserName()
				+"'";
		getServer().getServerModel()
		.addToText("Running this update: " + update + "\n");
		try {
			stmnt.executeUpdate(update);
			getServer().getServerModel().addToText("Successfully updated user password\n");
			return new OTUpdatePasswordSuccessful();
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't update user password\n");
			e.printStackTrace();
			return new OTErrorResponse("Couldn't update user password", false);

		}

	}

	private ObjectTransferrable updateUserProfile(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTUpdateUserProfile classifiedOperation = (OTUpdateUserProfile) operation;

		getServer().getServerModel()
		.addToText("Attempting to update the following users profile: " + client.getUserName() + "\n");

		String update = "UPDATE users " 
				+"SET firstName= '" + classifiedOperation.getFirstName() 
				+ "', lastName= '"+ classifiedOperation.getLastName()
				+"', userEmail= '"+ classifiedOperation.getEmail()
				+"' "
				+"WHERE userName= '" + client.getUserName()
				+"'";

		getServer().getServerModel()
		.addToText("Running this update: " + update + "\n");
		try {
			stmnt.executeUpdate(update);
			getServer().getServerModel().addToText("Successfully updated user profile\n");
			return new OTUpdateUserProfileSuccessful(classifiedOperation.getFirstName(), classifiedOperation.getLastName(), classifiedOperation.getEmail());
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't update user profile\n");
			e.printStackTrace();
			return new OTErrorResponse("Couldn't update user profile", false);
		}
	}

	private ObjectTransferrable deleteEvent(Statement stmnt, Connection con, ObjectTransferrable operation, ClientInfo client) {
		OTDeleteEvent classifiedOperation = (OTDeleteEvent) operation;
		Event eventToDelete = classifiedOperation.getEvent();
		getServer().getServerModel().addToText("Event Title: " + eventToDelete.getEventTitle() + "\n");
		getServer().getServerModel().addToText("Global event marker: " + eventToDelete.getGlobalEvent() + "\n");
		try {
			if(getAMeeting(con, eventToDelete, client)){
				String creator;
				if(eventToDelete.getGlobalEvent()){
					creator = "global";
				} else {
					creator = client.getUserName();
				}

				getServer().getServerModel()
				.addToText("Attempting to delete a meeting for: " + creator + "\n");

				String update = "DELETE FROM meetings " 
						+"WHERE creatorID= '" + client.getUserName() 
						+"' AND meetingDate= '"+eventToDelete.getDate().toString()
						+"' AND meetingTitle= '"+eventToDelete.getEventTitle()
						+"' AND meetingDescription= '"+eventToDelete.getEventDescription()
						+"' AND meetingLocation= '"+eventToDelete.getLocation()
						+"' AND meetingStartTime= '"+eventToDelete.getStartTime().toString()
						+"' AND meetingEndTime= '"+eventToDelete.getEndTime().toString()
						+"' AND lockVersion= "+eventToDelete.getLockVersion()
						+"";

				getServer().getServerModel()
				.addToText("Running this update: " + update + "\n");

				stmnt.executeUpdate(update);

				getServer().getServerModel().addToText("Successfully deleted event\n");
				return new OTDeleteEventSuccessful(true);
			} else {
				getServer().getServerModel().addToText("Couldn't delete event - stale lock version\n");
				return new OTDeleteEventSuccessful(false);
			}

		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't delete event\n");
			e.printStackTrace();
			return new OTErrorResponse("Couldn't delete event", false);
		}
	}

	private synchronized ObjectTransferrable updateEvent(Connection con, ObjectTransferrable operation, ClientInfo client) {
		OTUpdateEvent classifiedOperation = (OTUpdateEvent) operation;
		Event oldEvent = classifiedOperation.getOldEvent();
		Event newEvent = classifiedOperation.getNewEvent();

		Time oldStartTime = oldEvent.getStartTime(); 
		Time oldEndTime = oldEvent.getEndTime();
		String oldEventDescription = oldEvent.getEventDescription();
		String oldEventTitle = oldEvent.getEventTitle();
		String oldLocation = oldEvent.getLocation();
		Date oldDate = oldEvent.getDate();
		boolean oldGlobalEvent = oldEvent.getGlobalEvent();
		int oldLockVersion = oldEvent.getLockVersion();

		Time newStartTime = newEvent.getStartTime(); 
		Time newEndTime = newEvent.getEndTime();
		String newEventDescription = newEvent.getEventDescription();
		String newEventTitle = newEvent.getEventTitle();
		String newLocation = newEvent.getLocation();
		Date newDate = newEvent.getDate();
		int newLockVersion = newEvent.getLockVersion();

		try {
			if(getAMeeting(con, oldEvent, client)){
				String creator;
				if(oldGlobalEvent){
					creator = "global";
				} else {
					creator = client.getUserName();
				}


				String update = "UPDATE meetings " 
						+"SET meetingDate= ?, meetingTitle= ?, meetingDescription= ?, "
						+"meetingLocation= ?, meetingStartTime= ?, meetingEndTime= ?, lockVersion= ? "

						+"WHERE creatorID= ? AND meetingDate= ? AND meetingTitle= ? "
						+"AND meetingDescription= ? AND meetingLocation= ? "
						+"AND meetingStartTime= ? AND meetingEndTime= ? AND lockVersion= ?";


				PreparedStatement updateEvent = con.prepareStatement(update);

				updateEvent.setDate(1, newDate);
				updateEvent.setString(2, newEventTitle);
				updateEvent.setString(3, newEventDescription);
				updateEvent.setString(4, newLocation);
				updateEvent.setTime(5, newStartTime);
				updateEvent.setTime(6, newEndTime);
				updateEvent.setInt(7, newLockVersion);

				updateEvent.setString(8, creator);
				updateEvent.setDate(9, oldDate);
				updateEvent.setString(10, oldEventTitle);
				updateEvent.setString(11, oldEventDescription);
				updateEvent.setString(12, oldLocation);
				updateEvent.setTime(13, oldStartTime);
				updateEvent.setTime(14, oldEndTime);
				updateEvent.setInt(15, oldLockVersion);

				getServer().getServerModel().addToText("QUERY: " +updateEvent.toString()+"\n");
				updateEvent.executeUpdate();
				getServer().getServerModel().addToText("Successfully updated event\n");
				return new OTUpdateEventSuccessful(true);
			} else {
				getServer().getServerModel().addToText("Failed to update event - stale lock version\n");
				return new OTUpdateEventSuccessful(false);
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't update meeting - SQL Exception\n");
			e.printStackTrace();
			return new OTErrorResponse("Couldn't update meeting - SQL Exception", false);
		}
	}

	private boolean getAMeeting(Connection con, Event event, ClientInfo client) throws SQLException{
		String query = "SELECT * "
				+"FROM meetings m "
				+"WHERE creatorID= ? AND meetingDate= ? AND meetingTitle= ? "
				+"AND meetingDescription= ? AND meetingLocation= ? "
				+"AND meetingStartTime= ? AND meetingEndTime= ? "
				+"AND lockVersion = ?";

		PreparedStatement meetingQuery;



		meetingQuery = con.prepareStatement(query);

		String creator;

		if(event.getGlobalEvent()){
			creator = "global";
		} else {
			creator = client.getUserName();
		}

		meetingQuery.setString(1, creator);
		meetingQuery.setDate(2, event.getDate());
		meetingQuery.setString(3, event.getEventTitle());
		meetingQuery.setString(4, event.getEventDescription());
		meetingQuery.setString(5, event.getLocation());
		meetingQuery.setTime(6, event.getStartTime());
		meetingQuery.setTime(7, event.getEndTime());
		meetingQuery.setInt(8, event.getLockVersion());

		getServer().getServerModel().addToText("Lock Version of received event: " + event.getLockVersion() + "\n");
		getServer().getServerModel().addToText("Creator of received event: " + creator + "\n");
		getServer().getServerModel().addToText("QUERY: " + meetingQuery.toString() + "\n");

		ResultSet rs = meetingQuery.executeQuery();

		if (rs.next()) {
			getServer().getServerModel().addToText("Found matching meeting, returning true.\n");
			return true;
		} else {

			getServer().getServerModel().addToText("Found no such meeting, returning false.\n");
			return false;
		}

	}

	private ObjectTransferrable createEvent(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTCreateEvent classifiedOperation = (OTCreateEvent) operation;

		String update;
		if(classifiedOperation.getEvent().getGlobalEvent()){
			getServer().getServerModel()
			.addToText("Attempting to create a meeting for: global\n");
			getServer().getServerModel()
			.addToText("Meeting received has date: " + classifiedOperation.getEvent().getDate().toString() + "\n");
			update = "INSERT INTO meetings VALUES (DEFAULT, 'global', '"
					+ classifiedOperation.getEvent().getDate().toString() + "', '" + classifiedOperation.getEvent().getEventTitle() + "', '"
					+ classifiedOperation.getEvent().getEventDescription() + "', '" + classifiedOperation.getEvent().getLocation()
					+ "', '" + classifiedOperation.getEvent().getStartTime().toString() + "', '" 
					+ classifiedOperation.getEvent().getEndTime().toString() +"', 1)";
		} else {
			getServer().getServerModel()
			.addToText("Attempting to create a meeting for: " + client.getUserName() + "\n");
			getServer().getServerModel()
			.addToText("Meeting received has date: " + classifiedOperation.getEvent().getDate().toString() + "\n");
			update = "INSERT INTO meetings VALUES (DEFAULT, '" + client.getUserName() + "', '"
					+ classifiedOperation.getEvent().getDate().toString() + "', '" + classifiedOperation.getEvent().getEventTitle() + "', '"
					+ classifiedOperation.getEvent().getEventDescription() + "', '" + classifiedOperation.getEvent().getLocation()
					+ "', '" + classifiedOperation.getEvent().getStartTime().toString() + "', '" 
					+ classifiedOperation.getEvent().getEndTime().toString() +"', 1)";
		}
		getServer().getServerModel()
		.addToText("Running this update: " + update + "\n");

		try {
			stmnt.executeUpdate(update);
			getServer().getServerModel().addToText("Successfully created meeting\n");
			return new OTCreateEventSucessful(classifiedOperation.getEvent());
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't create meeting\n");
			e.printStackTrace();
			return new OTErrorResponse("Couldn't create meeting", false);
		}
	}

	private ObjectTransferrable checkUsername(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTUsernameCheck classifiedOperation = (OTUsernameCheck) operation;
		try {
			if(checkForUserExistance(stmnt, classifiedOperation.getUsername(), client)){
				classifiedOperation.setAlreadyExists(true);
			} else {
				classifiedOperation.setAlreadyExists(false);
			}
			return classifiedOperation;
		} catch (SQLException e) {
			e.printStackTrace();
			return new OTErrorResponse("SQL failed with username query", false);
		}
	}

	private synchronized boolean checkForUserExistance(Statement stmnt, String username, ClientInfo client) throws SQLException{
		getServer().getServerModel()
		.addToText("Checking to see if " + username + " is in the database...\n");

		String query = "SELECT count(u.userName) " + "FROM users u " + "GROUP BY u.userName " + "HAVING u.userName = '"
				+ username + "'";

		ResultSet rs = stmnt.executeQuery(query);

		if (rs.next()) {
			getServer().getServerModel().addToText("Found matching username, returning true.\n");
			return true;
		} else {
			getServer().getServerModel().addToText("Found no such user, returning false.\n");
			return false;
		}
	}

	private ObjectTransferrable checkEmailvalid(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTEmailCheck classifiedOperation = (OTEmailCheck) operation;
		try {
			if(checkForEmailExistance(stmnt, classifiedOperation.getEmail(), client)){
				classifiedOperation.setAlreadyExists(true);
			} else {
				classifiedOperation.setAlreadyExists(false);
			}
			return classifiedOperation;
		} catch (SQLException e) {
			e.printStackTrace();
			return new OTErrorResponse("SQL failed with email query", false);
		}
	}

	private synchronized boolean checkForEmailExistance(Statement stmnt, String email, ClientInfo client) throws SQLException {
		getServer().getServerModel().addToText("Checking to see if: " + email + " is in the database\n");

		String query = "SELECT count(u.userEmail) " + "FROM users u " + "GROUP BY u.userEmail "
				+ "HAVING u.userEmail = '" + email + "'";

		ResultSet rs = stmnt.executeQuery(query);

		if (rs.next()) {
			getServer().getServerModel().addToText("Email exists, returning true.\n");
			return true;
		} else {
			getServer().getServerModel().addToText("Email not in use, returning false.\n");
			return false;
		}
	}
	private synchronized ObjectTransferrable checkRegistration(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTRegistrationInformation classifiedOperation = (OTRegistrationInformation) operation;
		getServer().getServerModel()
		.addToText("Attempting to create a user with name: " + classifiedOperation.getUsername() + "\n");
		try {
			if(!checkForUserExistance(stmnt, classifiedOperation.getUsername(), client)){
				if(!checkForEmailExistance(stmnt, classifiedOperation.getEmail(), client)){
					String update = "INSERT INTO users VALUES ('" + classifiedOperation.getUsername() + "', '"
							+ classifiedOperation.getPwHash() + "', '" + classifiedOperation.getFirstname() + "', '"
							+ classifiedOperation.getLastname() + "', '" + classifiedOperation.getEmail() + "')";
					stmnt.executeUpdate(update);
					getServer().getServerModel().addToText("Succesfully created user");
					return new OTRegistrationInformationConfirmation(true, null, null);
				} else {
					return new OTRegistrationInformationConfirmation(false, null, "Email already exists");
				}
			} else {
				return new OTRegistrationInformationConfirmation(false, null, "Username already exists");
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("Couldn't create user");
			e.printStackTrace();
			return new OTErrorResponse("Error with SQL query", false);
		}

	}

	private ObjectTransferrable dealWithError(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {

		OTErrorResponse error = (OTErrorResponse) operation;
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
		return null;
	}

	private ObjectTransferrable getMeetings(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTRequestMeetingsOnDay classifiedOperation = (OTRequestMeetingsOnDay) operation;

		try {
			ArrayList<Event> meetings = retrieveMeetingsFromDB(classifiedOperation.getDate(), client, stmnt);
			getServer().getServerModel().addToText("Returning " + meetings.size() + " meetings to client" + "\n");
			OTReturnDayEvents returnEvents = new OTReturnDayEvents(meetings);
			return returnEvents;
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with user details request" + "\n");
			e.printStackTrace();
			return new OTErrorResponse("SQL Server failed with meeting request", false);
		}
	}

	private ObjectTransferrable getUserDetails(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {

		OTLoginSuccessful classifiedOperation = (OTLoginSuccessful) operation;

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
				getServer().getServerModel()
				.addToText("Set Client username to " + classifiedOperation.getUsername() + "\n");
				client.setUserName(classifiedOperation.getUsername());
				return new OTLoginProceed(true, firstName, lastName, email);
			} else {
				getServer().getServerModel()
				.addToText("User " + classifiedOperation.getUsername() + " does not exist" + "\n");
				return new OTLoginProceed(false, null, null, null);
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with user details request" + "\n");
			e.printStackTrace();
			return new OTErrorResponse("SQL Server failed with user details request", false);
		}
	}

	private ObjectTransferrable hashToClient(Statement stmnt, ObjectTransferrable operation, ClientInfo client) {
		OTLogin classifiedOperation = (OTLogin) operation;

		String query = "SELECT u.password " + "FROM users u " + "WHERE u.userName = '"
				+ classifiedOperation.getUsername() + "'";

		try {
			ResultSet rs = stmnt.executeQuery(query);

			if (rs.next()) {
				String pwFromDB = rs.getString(1);

				getServer().getServerModel().addToText("Sending user following hash: " + pwFromDB + "\n");
				return new OTHashToClient(true, pwFromDB);

			} else {
				getServer().getServerModel()
				.addToText("User " + classifiedOperation.getUsername() + " does not exist" + "\n");
				return new OTHashToClient(false, null);
			}
		} catch (SQLException e) {
			getServer().getServerModel().addToText("SQL Server failed with hash request" + "\n");
			e.printStackTrace();
			return new OTErrorResponse("SQL Server failed with hash request", false);
		}
	}

	private ArrayList<Event> retrieveMeetingsFromDB(Date date, ClientInfo client, Statement stmnt) throws SQLException {

		String query = "SELECT m.creatorID, m.meetingtitle, m.meetingdescription, m.meetinglocation, m.meetingstarttime, m.meetingendtime, m.lockVersion "
				+ "FROM meetings m " + "WHERE (m.creatorid = '" + client.getUserName()
				+ "' OR m.creatorid = 'global') AND m.meetingdate = '" + date.toString() + "' "
				+ "ORDER BY m.meetingstarttime ASC";

		ResultSet rs;

		rs = stmnt.executeQuery(query);
		ArrayList<Event> meetings = new ArrayList<Event>();
		getServer().getServerModel()
		.addToText("Requesting meeting information for " + client.getUserName() + "\n");
		while (rs.next()) {
			String creator = rs.getString(1);
			String title = rs.getString(2);
			String description = rs.getString(3);
			String location = rs.getString(4);
			Time startTime = rs.getTime(5);
			Time endTime = rs.getTime(6);
			int lockVersion = rs.getInt(7);
			Event event;

			if(creator.equals("global")){
				event = new Event(startTime, endTime, description, title, location, date, true, lockVersion);
			} else {
				event = new Event(startTime, endTime, description, title, location, date, false, lockVersion);
			}
			meetings.add(event);
		}
		return meetings;

	}

}