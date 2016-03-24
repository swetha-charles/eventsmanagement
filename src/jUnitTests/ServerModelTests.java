package jUnitTests;
/**
 * Tests of the server model
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import server.*;

public class ServerModelTests {
	private ServerModel testModel;
	
	@Before
	public void setup(){
		testModel = new ServerModel("localhost", 8080);
	}
	
	@Test
	public void test() {
		String openingText = "-------- PostgreSQL JDBC Connection Testing ------------\nPostgreSQL JDBC Driver Registered!\nMade it! Can now submit SQL queries\nSetting schema\nServer: Listening on port 8080 and IP: localhost/127.0.0.1\n";
		assertTrue(testModel.getText().equals(openingText));
		testModel.setText("");
		testModel.addToText("test1");
		assertTrue(testModel.getText().equals("test1"));
		testModel.addToText("test2");
		assertTrue(testModel.getText().equals("test1test2"));
		testModel.setText("test3\n");
		assertTrue(testModel.getText().equals("test3\n"));
		testModel.closeServer();
		assertTrue(testModel.getText().equals("test3\nSOCKET EXCEPTION - Could be normal if stopping server\n-----------------SERVER STOPPED-----------------\n"));
	}
	
}
