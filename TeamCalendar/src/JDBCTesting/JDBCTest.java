package JDBCTesting;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JDBCTest {

	public static void main(String[] args) {

		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "dbteach2.cs.bham.ac.uk";
		String db = "team_chennai_calendar";
		String user = "mxw589";
		String dbpasswd = "ilovedatabases";
		String URL ="jdbc:" + protocol + "://" + server + "/" + db;
		Session session = null;

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
			System.out.println("Made it! Can now submit SQL queries");
		} else {
			System.out.println("Failed to make connection!");
			System.exit(-1);
		}

		Statement stmt;

		try {
			stmt = connection.createStatement();
			System.out.println("Setting schema");
			stmt.execute("SET search_path TO calendar");

			System.out.println("Running Query:");

			String query = "SELECT m.meetingTitle, m.meetingStartTime, m.meetingEndTime, m.meetingDescription " + 
					"FROM users u, meetings m " + 
					"WHERE u.userID = m.creatorID AND u.userName = 'mwizzle' AND m.meetingDate = '2016-03-24'";

			System.out.println(query);

			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			int columnWidth = 20;
			
			
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) System.out.print("|  ");
				String columnName = rsmd.getColumnName(i);
				
				while(columnName.length() <20){ columnName = columnName + " ";}
				System.out.print(columnName);
			}
			System.out.println("");
			
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print("|  ");
					String columnValue = rs.getString(i);
					while(columnValue.length() <20){ columnValue = columnValue + " ";}
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
