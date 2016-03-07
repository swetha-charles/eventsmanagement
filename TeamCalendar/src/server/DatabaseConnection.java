package server;

import java.sql.*;

public class DatabaseConnection {

	private Connection connection;
	
	public DatabaseConnection(){
		
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

		this.connection = null;

		try {

			this.connection = DriverManager.getConnection(URL, user, dbpasswd);

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
		
		Statement stmnt = null;
		
		try {
			stmnt = this.connection.createStatement();
//			System.out.println("Setting schema");
			stmnt.execute("SET search_path TO calendar");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	
}
