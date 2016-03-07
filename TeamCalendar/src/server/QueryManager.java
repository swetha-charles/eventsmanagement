package server;

import java.sql.*;
import java.util.*;

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
//		Statement stmnt = dbconnection.createStatement(); TODO
		
		if(currentOperation.getOpCode().equals("0001")){
			OTUsernameCheck classifiedOperation = (OTUsernameCheck) currentOperation;
//			query = "SELECT count(u.userName) " +  TODO
//					"FROM users u " +
//					"GROUP BY u.userName " +
//					"HAVING u.userName = '" + classifiedOperation.getUsername() + "'" ;
//			try {
//				ResultSet rs = stmnt.executeQuery(query);
//				
//				if(rs.next()){
//					classifiedOperation.setAlreadyExists(true);
//				} else {
//					classifiedOperation.setAlreadyExists(false);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			if (classifiedOperation.getUsername().equals("mwizzle")){
				classifiedOperation.setAlreadyExists(false);
			} else {
				classifiedOperation.setAlreadyExists(true);
			}
			setOperation(classifiedOperation);
		} 
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
