package JDBCTesting;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCTest {

	public static void main(String[] args) {
		String protocol = "postgresql";
		String driverName = "org.postgresql.Driver";
		String server = "tinky-winky.cs.bham.ac.uk";
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
