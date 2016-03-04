package server;

import java.sql.*;
import java.util.*;

public class JDBCForThread {

	private ObjectTransferrable operation;
	private Statement stmnt = null;

	public JDBCForThread(ObjectTransferrable operation){
		this.operation = operation;
	}	
	
	public ObjectTransferrable getOperation() {
		return operation;
	}

	public void setOperation(ObjectTransferrable operation) {
		this.operation = operation;
	}
	
	public Statement getStmnt() {
		return stmnt;
	}

	public void setStmnt(Statement stmnt) {
		this.stmnt = stmnt;
	}

	public void runOperation(){
		
		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "dbteach2.cs.bham.ac.uk";
		String db = "team_chennai_calendar";
		String user = "mxw589";
		String dbpasswd = "ilovedatabases";
		String URL ="jdbc:" + protocol + "://" + server + "/" + db;

		//System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

		try {

			Class.forName(driverName);

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			System.exit(-1);
		}

		//System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(URL, user, dbpasswd);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			System.exit(-1);
		}

		if (connection != null) {
			System.out.println("Made it! Can now submit SQL queries");
		} else {
			System.out.println("Failed to make connection!");
			System.exit(-1);
		}
		
		try {
			setStmnt(connection.createStatement());
//			System.out.println("Setting schema");
			getStmnt().execute("SET search_path TO calendar");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ObjectTransferrable currentOperation = getOperation();
		String query = null;
		
		if(currentOperation.getOpCode().equals("0001")){
			OTUsernameCheck classifiedOperation = (OTUsernameCheck) currentOperation;
			query = "SELECT count(u.userName) " + 
					"FROM users u " +
					"GROUP BY u.userName " +
					"HAVING u.userName = '" + classifiedOperation.getUsername() + "'" ;
			try {
				ResultSet rs = getStmnt().executeQuery(query);
				
				if(rs.next()){
					classifiedOperation.setAlreadyExists(true);
				} else {
					classifiedOperation.setAlreadyExists(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setOperation(classifiedOperation);
		} 
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("Closing!");
			if (connection != null) {connection.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
