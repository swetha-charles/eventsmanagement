package server;

import java.sql.*;
import java.util.*;
import objectTransferrable.*;



public class QueryManager {

	private ObjectTransferrable operation;
	private Server server;
	private ClientInfo clientInfo;

	public QueryManager(ObjectTransferrable operation, Server server, ClientInfo clientInfo){
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

	
	public void runOperation() throws SQLException{
	
		ObjectTransferrable currentOperation = getOperation();
		String query = null;
		Connection dbconnection = getServer().getDatabase().getConnection();
		//Prepared st?
		Statement stmnt = dbconnection.createStatement();
		
		
		/**
		 * List of present opcodes
		 * OTUsernameCheck "0001"
		 * OTEmailCheck = "0002"
		 * OTRegistrationCheck = "0003"
		 * OTRegistrationInformation = "0004"
		 * OTExitGracefully = "0005" - Should not appear at query manager
		 * OTRegistrationInformationConfirmation = "0006" - Should not appear at query manager
		 */
		//OTUsernameCheck "0001"
		if(currentOperation.getOpCode().equals("0001")){
			query = checkUsername(stmnt);
		} 
		//OTEmailCheck = "0002"
		else if(currentOperation.getOpCode().equals("0002")){
			query = checkEmailvalid(stmnt);
		}
		//OTRegistrationCheck = "0003"
		else if(currentOperation.getOpCode().equals("0003")){
			query = checkRegistration(stmnt);	
		}
		//OTRegistrationInformation = "0004"
		else if(currentOperation.getOpCode().equals("0004")){
			setOperation(new OTErrorResponse("OP code currently out of use!" , false, 0004));
			System.err.println("opcode is presently depricated! Responding with Error Object");
		}
		//OP CODE 0005 SPECIAL CASE TO EXIT PROGRAM
		else if(currentOperation.getOpCode().equals("0005")){
			System.err.println("Specially reserved opcode for exiting program has arrived at query manager!");
			//No reason to tell the client, they gone, possible shutdown communication?
		}
		//OP CODE 0006 RETURN FROM SERVER, SHOULD NEVER APPEAR HERE
		else if(currentOperation.getOpCode().equals("0006")){
			setOperation(new OTErrorResponse("Server specified confirmation message recieved from client!" , false, 0006));
			System.err.println("The object assocatied with this opcode should not be recieved from client! Responding with Error Object");
		}
		// The client has returned an error, considering client passive previous server response was bad
		else if(currentOperation.getOpCode().equals("0007")){
			dealWithError();
		}
		//Request for meetings on specific Day
		else if(currentOperation.getOpCode().equals("0008")){
			getMeetings(stmnt);
		}
		//Server Response to get meetings, should never get here
		else if(currentOperation.getOpCode().equals("0009")){
			setOperation(new OTErrorResponse("A message meant to be sent by the server (return list of meetings) has been found at the query manager!" , false));
			System.err.println("A message meant to be sent by the server (return list of meetings) has been found at the query manager!");
		}
		//Request to create an event from the client
		else if(currentOperation.getOpCode().equals("0010")){
			createEvent(stmnt);
		}
		//This is a return message for event creation successful and should not be seen by server
		else if(currentOperation.getOpCode().equals("0011")){
			setOperation(new OTErrorResponse("A message meant to be sent by the server (sucessful event creation) has been found at the query manager!" , false));
			System.err.println("A message meant to be sent by the server (sucessful event creation) has been found at the query manager!");
		}
		//Get login credentials from the client
		else if(currentOperation.getOpCode().equals("0012")){
			checkLoginCreds(stmnt);
		}
		//Login success state object, should not be received by query manager
		else if(currentOperation.getOpCode().equals("0013")){
			setOperation(new OTErrorResponse("A message meant to be sent by the server (login success state) has been found at the query manager!" , false));
			System.err.println("A message meant to be sent by the server (login success state) has been found at the query manager!");
		}
		//Unknown OP code response
		else{
			setOperation(new OTErrorResponse("An unknown opCode has been recieved by the query manager!" , false));
			System.err.println("opcode of object not known by query manager! Responding with Error Object");
		}
		
	}
	
	
	private String checkUsername(Statement stmnt){
		OTUsernameCheck classifiedOperation = (OTUsernameCheck) getOperation();
		getServer().getServerModel().addToText("Checking to see if " + classifiedOperation.getUsername() + " is in the database...\n");
		String query = "SELECT count(u.userName) " + 
				"FROM users u " +
				"GROUP BY u.userName " +
				"HAVING u.userName = '" + classifiedOperation.getUsername() + "'" ;
		try {
			ResultSet rs = stmnt.executeQuery(query);
			
			if(rs.next()){
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
	
	private String checkEmailvalid(Statement stmnt){
		OTEmailCheck classifiedOperation = (OTEmailCheck) getOperation();
		getServer().getServerModel().addToText("Email received: " +classifiedOperation.getEmail()+"\n");
		//not sure what field name of email is? inserted guess
		String query = "SELECT count(u.userEmail) " + 
				"FROM users u " +
				"GROUP BY u.userEmail " +
				"HAVING u.userEmail = '" + classifiedOperation.getEmail() + "'" ;
		
		getServer().getServerModel().addToText(query);
		try {
			ResultSet rs = stmnt.executeQuery(query);
			
			if(rs.next()){
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
	
	private String checkRegistration(Statement stmnt){
		OTRegistrationInformation classifiedOperation = (OTRegistrationInformation)getOperation();
		getServer().getServerModel().addToText("Attempting to create a user with name: "+ classifiedOperation.getUsername() + "\n");
		String update = "INSERT INTO users VALUES ('"+ classifiedOperation.getUsername() +"', '" + classifiedOperation.getPwHash() + "', '"+classifiedOperation.getFirstname()+"', '"+classifiedOperation.getLastname()+"', '"+classifiedOperation.getEmail()+"')";
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

	private String dealWithError(){
		
		OTErrorResponse error = (OTErrorResponse) getOperation();
		//Is known error response can go here
		if (error.getErrCode()==0){
		System.err.println("Undefined error from client, Description: " + error.getErrorDescription() + " Communications being shut down? " + error.isShouldShutdownCommunication());
		}
		else{
			/**
			 * TODO any specific error handling can go here
			 */
		}
		
		if (error.isShouldShutdownCommunication()){
			
			/**
			 * TODO need to work out a call to shutdown, this is where being able to call exit gracefully could come in
			 */
		
		}
		return "";
	}
	
	private String getMeetings(Statement stmnt){
		OTRequestMeetingsOnDay classifiedOperation =(OTRequestMeetingsOnDay) getOperation();
		//TODO SQL query here
		
		setOperation(new OTReturnDayEvents(new ArrayList<Event>()));
		return "";
	}
	
//	private String createEvent(Statement stmnt){
//		OTCreateEvent classifiedOperation = (OTCreateEvent)getOperation();
//		Event event = classifiedOperation.getEvent();
//		java.sql.Date eventDate = new java.sql.Date(event.getStartTime().getTimeInMillis());
//		String username = event;
//		String query = "INSERT INTO";
//		
//		setOperation(new OTErrorResponse("The method for creating events has not yet been completed on the server", false));
//		return "";
//	}
	
	private String createEvent(Statement stmnt){
		OTCreateEvent classifiedOperation = (OTCreateEvent)getOperation();
		Event event = classifiedOperation.getEvent();
		java.sql.Date eventDate = new java.sql.Date(event.getStartTime().getTimeInMillis());
		String username = this.clientInfo.getUserName();
		
		
		String query = "INSERT INTO " ;
		
		setOperation(new OTErrorResponse("The method for creating events has not yet been completed on the server", false));
		return "";
	}
	
	private String checkLoginCreds(Statement stmnt){
		OTLogin classifiedOperation = (OTLogin)getOperation();
		//not sure what field name of email is? inserted guess
		String query = "SELECT u.userName, u.firstName, u.lastName, u.userEmail, u.password " + 
				"FROM users u " +
				"WHERE u.userName = '" + classifiedOperation.getUsername() + "'";
		try {
			ResultSet rs = stmnt.executeQuery(query);
			
			if(rs.next()){
				String pwFromDB = rs.getString(5);
				String pwFromLogin = classifiedOperation.getPwHash();
				
				getServer().getServerModel().addToText("Supplied PW: "+pwFromLogin+" PW from DB: "+pwFromDB+"\n");
				
				if(pwFromDB.equals(pwFromLogin)){
					getServer().getServerModel().addToText("Login details CORRECT for "+ classifiedOperation.getUsername() +"\n");
					String firstName, lastName, email;
					firstName = rs.getString(2);
					lastName = rs.getString(3);
					email = rs.getString(4);
					setOperation(new OTLoginSuccessful(true, firstName, lastName, email));
				} else {
					getServer().getServerModel().addToText("Password INCORRECT for "+ classifiedOperation.getUsername() +"\n");
					String firstName, lastName, email;
					firstName = rs.getString(2);
					lastName = rs.getString(3);
					email = rs.getString(4);
					setOperation(new OTLoginSuccessful(true, firstName, lastName, email));
				}

			} else {
				getServer().getServerModel().addToText("User "+ classifiedOperation.getUsername() +" does not exist"+"\n");
				setOperation(new OTLoginSuccessful(false, null, null, null));
			}
		} catch (SQLException e) {
			setOperation(new OTErrorResponse("SQL Server failed with email query", false));
			e.printStackTrace();
		}
		
		return "";
	}


}