package JDBCTesting;

import java.sql.*;
import java.util.*;

public class JDBCTest {

	public static void main(String[] args) {
	    int id;
	    String subject;
	    String permalink;
	    
	    
	}
	
	public Connection connectToDatabase(){
		Connection conn = null;
		
	    try
	    {
	      Class.forName("org.postgresql.Driver");
	      String url = "jdbc:postgresql://host:port/database";
	      conn = DriverManager.getConnection(url,"THE_USER", "THE_PASSWORD");
	    }
	    catch (ClassNotFoundException e)
	    {
	      e.printStackTrace();
	      System.exit(1);
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	      System.exit(2);
	    }
	    return conn;
	}
}
