package JDBCTesting;

import java.sql.*;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JDBCTest {

	public static void main(String[] args) {
		
		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "localhost";
		int localport = 3307;
		int uniport = 5432;
		String db = "team_chennai_calendar";
		String user = "mxw589";
		String dbpasswd = "ilovedatabases";

		String URL ="jdbc:" + protocol + "://" + server + ":" + localport + "/" + db;
		
		try
		{
			JSch jsch=new JSch();

			String host = "tinky-winky.cs.bham.ac.uk";

			Session session=jsch.getSession(user, host, 22);

			String sshpasswd = "Mo0money!";
			session.setPassword(sshpasswd);

			session.setConfig("StrictHostKeyChecking", "no");

			session.connect(30000);
			
			session.setPortForwardingL(localport, "dbteach2", uniport);
			System.out.println("Connected to SSH");
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

		try {

			Class.forName(driverName);

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(URL, user, dbpasswd);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
		Statement stmt;
		
		try {
			stmt = connection.createStatement();
			System.out.println("Setting schema");
			stmt.executeQuery("SET search_path TO calendar");
			
			System.out.println("Running Query");
			ResultSet rs = stmt.executeQuery("SELECT userName FROM users");
			
			while (rs.next()) {
				  String lastName = rs.getString("userName");
				  System.out.println(lastName + "\n");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	


}
