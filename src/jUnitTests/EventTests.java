package jUnitTests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.events.EventException;

import objectTransferrable.Event;
public class EventTests {
	private Event testEvent;
	private Time startTime;
	private Time endTime;
	private String description, title, location;
	private Date date;
	private boolean globalEvent;
	private int lockVersion;
	
	@Before
	public void setup(){
		startTime = Time.valueOf("18:50:00");
		endTime = Time.valueOf("20:30:00");
		description = "test description";
		title = "test title";
		location = "test location";
		date = Date.valueOf("2016-06-15");
		globalEvent = false;
		lockVersion = 0;
		testEvent = new Event(startTime, endTime, description, title, location, date, globalEvent, lockVersion);
	}
	
	@Test
	public void test() {
		assertTrue(testEvent.getStartTime().equals(startTime));
		assertTrue(testEvent.getEndTime().equals(endTime));
		assertTrue(testEvent.getEventDescription().equals(description));
		assertTrue(testEvent.getEventTitle().equals(title));
		assertTrue(testEvent.getLocation().equals(location));
		assertTrue(testEvent.getDate().equals(date));
		assertTrue(testEvent.getGlobalEvent() == (globalEvent));
		assertTrue(testEvent.getLockVersion() == 0);
		
	}

}
