package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import client.Client;
import model.Model;
import model.ModelState;

public class clientModelTests {
	Client client;
	Model model;
	@Before
	public void setup(){
		client = new Client();
		model = new Model(client);
	}
	@Test
	public void testStates() {
		model.setUpdatePasswordSuccess(true);
		assertTrue(model.getCurrentState().equals(ModelState.PROFILE));
		//is the panel supposed to have changed too?
		
	}

}
