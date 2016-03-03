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
		int localport = 3366;
		int uniport = 5432;
		String db = "team_chennai_calendar";
		String user = "mxw589";
		String dbpasswd = "ilovedatabases";

		String URL ="jdbc:" + protocol + "://" + server + ":" + localport + "/" + db;
		Session session = null;
		
		try {
			JSch jsch=new JSch();

			String host = "tinky-winky.cs.bham.ac.uk";

			session=jsch.getSession(user, host, 22);

			String sshpasswd = "Mo0money!";
			session.setPassword(sshpasswd);

			session.setConfig("StrictHostKeyChecking", "no");


			session.connect(30000);

			System.out.println("Connecting to SSH...");
			session.setPortForwardingL(localport, "dbteach2", uniport);
			System.out.println("Connected to SSH");
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}

		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");

		try {

			Class.forName(driverName);

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(URL, user, dbpasswd);

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			  System.exit(-1);
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
			System.exit(-1);
		}

		Statement stmt;

		try {
			stmt = connection.createStatement();
			System.out.println("Setting schema");
			stmt.execute("SET search_path TO calendar");

			System.out.println("Running Query");
			ResultSet rs = stmt.executeQuery("SELECT m.meetingTitle, m.meetingStartTime, m.meetingEndTime, m.meetingDescription " + 
											"FROM users u, meetings m " + 
											"WHERE u.userID = m.creatorID AND u.userName = 'mwizzle' AND m.meetingDate = '2016-03-24'");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print("|  ");
		        System.out.print(rsmd.getColumnName(i));
		    }
		    System.out.println("");
			
			while (rs.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print("|  ");
			        String columnValue = rs.getString(i);
			        System.out.print(columnValue);
			    }
			    System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println("Closing!");
			if (connection != null) {connection.close();}
			session.disconnect();
			System.exit(-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
