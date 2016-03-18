package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import server.*;

public class ServerModelTests {
	private ServerModel testModel;
	
	@Before
	public void setup(){
		testModel = new ServerModel(8080);
	}
	
	@Test
	public void test() {
		assertTrue(testModel.getText().equals(null));
		testModel.addToText("test1");
		assertTrue(testModel.getText().equals("test1"));
		testModel.addToText("test2");
		assertTrue(testModel.getText().equals("test1test2"));
		testModel.setText("test3\n");
		assertTrue(testModel.getText().equals("test3\n"));
		testModel.closeServer();
		assertTrue(testModel.getText().equals("test3\n-----------------SERVER STOPPED-----------------\n"));
	}
	
}
