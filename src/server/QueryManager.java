package server;

import java.sql.*;
import java.util.*;

import objectTransferrable.OTUsernameCheck;
import objectTransferrable.ObjectTransferrable;

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
		
		if(currentOperation.getOpCode().equals("0001")){
			OTUsernameCheck classifiedOperation = (OTUsernameCheck) currentOperation;
			getServer().getServerModel().addToText("Checking to see if " + classifiedOperation.getUsername() + " is in the database...\n");
			query = "SELECT count(u.userName) " + 
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
		} 

	}

}
