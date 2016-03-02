package JDBCTesting;

import java.sql.*;

/*
 * Created on Aug 7, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * Manages a Cedar database defined in the .properties file
 * @author udr
 */

public class Database {
	private Connection con;
	private Statement stmt;

	public Statement open() {
		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "tinky-winky.cs.bham.ac.uk";
		String port = "5432";
		String db = "dbteach2";
		String user = "mxw589";
		String passwd = "ilovedatabases";
	
		String URL =
			"jdbc:" + protocol + "://" + server + ":" + port + "/" + db;
	
		Class driver = null;
		
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");
	
		try {
	
			Class.forName("org.postgresql.Driver");
	
		} catch (ClassNotFoundException e) {
	
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
	
		}
	
		System.out.println("PostgreSQL JDBC Driver Registered!");
	
		Connection connection = null;
	
		try {
	
			connection = DriverManager.getConnection(URL, user, passwd);
	
		} catch (SQLException e) {
	
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
	
		}
	
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	
		return stmt;
	}

	public void close() {
		try {
			stmt.close();
			con.close();
		} catch (Throwable e) {
		}
	}

	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public ResultSet runQuery(String q) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(q);
		} catch (SQLException e) {
			System.err.println(e);
			System.exit(1);
		}
		return rs;
	}
	
	public int update(String q) {
		try {
			return stmt.executeUpdate(q);
		} catch (SQLException e) {
			System.err.println(e);
			System.exit(1);
		}
		return 0;
	}

}

