package server;

import java.sql.*;
import java.util.*;
import objectTransferrable.*;



public class QueryManager {

	private ObjectTransferrable operation;
	private Server server;

	public QueryManager(ObjectTransferrable operation, Server server){
		this.operation = operation;
		this.server = server;
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
			System.err.println("opcode is presently depricated, look at query manager! Responding with Error Object");
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
		//not sure what field name of email is? inserted guess
		String query = "SELECT count(u.email) " + 
				"FROM users u " +
				"GROUP BY u.email " +
				"HAVING u.email = '" + classifiedOperation.getEmail() + "'" ;
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
		return "";
	}
	
	private String checkRegistrationInformation(Statement stmnt){
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
	


}