package JDBCTesting;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JDBCTest {

	public static void main(String[] args) {
		try
		{
			JSch jsch=new JSch();

			String user = "mxw589";
			String host = "tinky-winky.cs.bham.ac.uk";

			Session session=jsch.getSession(user, host, 22);

			String passwd = "Mo0money!";
			session.setPassword(passwd);

			session.setConfig("StrictHostKeyChecking", "no");

			session.connect(30000);			

			System.out.println("Connected to SSH");
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "localhost";
		String port = "5432";
		String db = "dbteach2";
		String user = "mxw589";
		String passwd = "ilovedatabases";

		String URL ="jdbc:" + protocol + "://" + server + ":" + port + "/" + db;

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
	}

}
