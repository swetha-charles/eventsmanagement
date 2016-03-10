package server;

import java.sql.*;
import java.util.*;

import objectTransferrable.OTUsernameCheck;
import objectTransferrable.ObjectTransferrable;

public class QueryManager {

	private ObjectTransferrable operation;
	private Connection connection;

	public QueryManager(ObjectTransferrable operation, Connection connection){
		this.operation = operation;
		this.connection = connection;
	}	
	
	public ObjectTransferrable getOperation() {
		return operation;
	}

	public void setOperation(ObjectTransferrable operation) {
		this.operation = operation;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void runOperation() throws SQLException{
	
		ObjectTransferrable currentOperation = getOperation();
		String query = null;
		Connection dbconnection = getConnection();
		//Prepared st?
		Statement stmnt = dbconnection.createStatement();
		
		if(currentOperation.getOpCode().equals("0001")){
			OTUsernameCheck classifiedOperation = (OTUsernameCheck) currentOperation;
			System.out.println("Checking to see if " + classifiedOperation.getUsername() + " is in the database...");
			query = "SELECT count(u.userName) " + 
					"FROM users u " +
					"GROUP BY u.userName " +
					"HAVING u.userName = '" + classifiedOperation.getUsername() + "'" ;
			try {
				ResultSet rs = stmnt.executeQuery(query);
				
				if(rs.next()){
					System.out.println("Found matching username, returning true.");
					classifiedOperation.setAlreadyExists(true);
				} else {
					System.out.println("Found no such user, returning false.");
					classifiedOperation.setAlreadyExists(false);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			setOperation(classifiedOperation);
		} 

	}

}
