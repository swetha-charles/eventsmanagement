package jUnitTests;
/**
 * These JUnit tests are now deprecated due to changes to the client
 */
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
		model.changeCurrentState(ModelState.REGISTRATION);
		assertTrue(model.getCurrentState().equals(ModelState.REGISTRATION));
		model.changeCurrentState(ModelState.LOGIN);
		assertTrue(model.getCurrentState().equals(ModelState.LOGIN));
		model.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGUSERNAME);
		assertTrue(model.getCurrentState().equals(ModelState.LOGINUNSUCCESSFULWRONGUSERNAME));
		model.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGPASSWORD);
		assertTrue(model.getCurrentState().equals(ModelState.LOGINUNSUCCESSFULWRONGPASSWORD));
		model.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		assertTrue(model.getCurrentState().equals(ModelState.REGISTRATIONUPDATE));
		model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
		assertTrue(model.getCurrentState().equals(ModelState.ERRORCONNECTIONDOWN));
		model.changeCurrentState(ModelState.ERRORCONNECTIONDOWNSTILL);
		assertTrue(model.getCurrentState().equals(ModelState.ERRORCONNECTIONDOWNSTILL));
		model.changeCurrentState(ModelState.PROMPTRELOAD);
		assertTrue(model.getCurrentState().equals(ModelState.PROMPTRELOAD));
		
		model.changeCurrentState(ModelState.EVENTSUPDATE);
		assertTrue(model.getCurrentState().equals(ModelState.EVENTSUPDATE));
		model.changeCurrentState(ModelState.PROFILE);
		assertTrue(model.getCurrentState().equals(ModelState.PROFILE));
		model.changeCurrentState(ModelState.EDIT);
		assertTrue(model.getCurrentState().equals(ModelState.EDIT));
		model.changeCurrentState(ModelState.PASSWORD);
		assertTrue(model.getCurrentState().equals(ModelState.PASSWORD));
	}
	
}
