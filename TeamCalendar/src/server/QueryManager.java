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
		 * OTExitGracefully = "0005" - Should appear at query manager
		 * OTRegistrationInformationConfirmation = "0006" - Should not appear at query manager
		 */
		
		if(currentOperation.getOpCode().equals("0001")){
			query = checkUsername(stmnt);
		} 
		else if(currentOperation.getOpCode().equals("0002")){
			query = checkEmailvalid(stmnt);
		}
		else if(currentOperation.getOpCode().equals("0003")){
			query = checkRegistration(stmnt);
		}
		else if(currentOperation.getOpCode().equals("0004")){
			query = checkRegistrationInformation(stmnt);
		}
		//OP CODE 0005 SPECIAL CASE TO EXIT PROGRAM
		else if(currentOperation.getOpCode().equals("0006")){
			System.err.println("Specially reserved opcode for exiting program has arrived at query manager!");
		}
		//OP CODE 0006 RETURN FROM SERVER, SHOULD NEVER APPEAR HERE
		else if(currentOperation.getOpCode().equals("0006")){
			System.err.println("The object assocatied with this opcode should not be recieved from client! Responding with Error Object");
		}
		//Unknown OP code response
		else{
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setOperation(classifiedOperation);
		return query;
	}
	
	private String checkEmailvalid(Statement stmnt){
		OTEmailCheck classifiedOperation = (OTEmailCheck) getOperation();
		//not sure what field name of email is? inserted guess
		String query = "SELECT count(u.email) " + 
				"FROM users u " +
				"GROUP BY u.email " +
				"HAVING u.email = '" + classifiedOperation.getEmail() + "'" ;
		return "";
	}
	
	private String checkRegistration(Statement stmnt){
		return "";
	}
	
	private String checkRegistrationInformation(Statement stmnt){
		return "";
	}
	


}