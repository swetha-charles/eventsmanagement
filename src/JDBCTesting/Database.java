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
		String protocol = Main.settings.getProperty("DBProtocol");
		if (protocol == null)
			protocol = "postgresql";
		String driverName = Main.settings.getProperty("DBDriver");
		if (driverName == null)
			driverName = "org.postgresql.Driver";
		String server = Main.settings.getProperty("DBServer");
		if (server == null)
			server = "bro.cs.bham.ac.uk";
		String port = Main.settings.getProperty("DBPort");
		if (port == null)
			port = "5432";
		String db = Main.settings.getProperty("DBName");
		if (db == null)
			db = "udr";
		String user = Main.settings.getProperty("DBUser");
		if (user == null)
			user = "udr";
		String passwd = Main.settings.getProperty("DBPassword");
		if (passwd == null)
			passwd = "";

		String URL =
			"jdbc:" + protocol + "://" + server + ":" + port + "/" + db;

		Class driver = null;
		try {
			driver = Class.forName(driverName);
			DriverManager.registerDriver((Driver) driver.newInstance());
		} catch (ClassNotFoundException e) {
			System.err.println("Database driver not found");
			System.out.println(e);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Driver could not be registered");
			System.exit(1);
		}

		try {
			con = DriverManager.getConnection(URL, user, passwd);

			stmt = con.createStatement();

		} catch (SQLException e) {
			System.err.println("SQL Exception");
			System.err.println(e);
			System.exit(1);
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

