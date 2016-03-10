package server;

import java.sql.*;

public class DatabaseConnection {

	private Connection connection;
	private Server creatorServer;
	
	public DatabaseConnection(Server creatorServer){
		this.creatorServer = creatorServer;
		
		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "dbteach2.cs.bham.ac.uk";
		String db = "team_chennai_calendar";
		String user = "mxw589";
		String dbpasswd = "ilovedatabases";
		String URL ="jdbc:" + protocol + "://" + server + "/" + db;
		
		getCreatorServer().getServerModel().addToText("-------- PostgreSQL JDBC Connection Testing ------------\n");

		try {

			Class.forName(driverName);

		} catch (ClassNotFoundException e) {

			getCreatorServer().getServerModel().addToText("Where is your PostgreSQL JDBC Driver? Include in your library path!\n");
			e.printStackTrace();
			System.exit(-1);
		}

		getCreatorServer().getServerModel().addToText("PostgreSQL JDBC Driver Registered!\n");

		this.connection = null;

		try {

			this.connection = DriverManager.getConnection(URL, user, dbpasswd);

		} catch (SQLException e) {

			getCreatorServer().getServerModel().addToText("Connection Failed! Check output console\n");
			e.printStackTrace();
			System.exit(-1);
		}

		if (connection != null) {
			getCreatorServer().getServerModel().addToText("Made it! Can now submit SQL queries\n");
		} else {
			getCreatorServer().getServerModel().addToText("Failed to make connection!\n");
			System.exit(-1);
		}
		
		Statement stmnt = null;
		
		try {
			stmnt = this.connection.createStatement();
			getCreatorServer().getServerModel().addToText("Setting schema\n");
			stmnt.execute("SET search_path TO calendar");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Server getCreatorServer() {
		return creatorServer;
	}
	
}
