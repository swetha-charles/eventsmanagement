/**
 * 
 */
package objectTransferrable;

import java.lang.reflect.Constructor;
import java.util.Calendar;

/**
 * Class to define object for an event, stores the StartTime, and Event Length parameters as unfortunately its surprisingly hard to find the time between two dates or calendar objects in java
 * 
 * @author tmd668
 *
 */
public class Event {
	private Calendar StartTime;
	private int eventLengthHours, eventLengthMins;
	private String eventDescription;
	private String location;
	private String[] attendees; //May not be used
	
	public Event(Calendar StartTime, int eventLengthHours, int eventLengthMins, String eventDescription, String location){
		this.StartTime = StartTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.location = location;
		this.attendees = new String[] {""};
	}
	
	public Event(Calendar StartTime, int eventLengthHours, int eventLengthMins, String eventDescription, String location, String[] attendees){
		this.StartTime = StartTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.location = location;
		this.attendees = attendees;
	}

	/**
	 * @return the startTime
	 */
	public Calendar getStartTime() {
		return StartTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Calendar startTime) {
		StartTime = startTime;
	}

	/**
	 * @return the eventLengthHours
	 */
	public int getEventLengthHours() {
		return eventLengthHours;
	}

	/**
	 * @param eventLengthHours the eventLengthHours to set
	 */
	public void setEventLengthHours(int eventLengthHours) {
		this.eventLengthHours = eventLengthHours;
	}

	/**
	 * @return the eventLengthMins
	 */
	public int getEventLengthMins() {
		return eventLengthMins;
	}

	/**
	 * @param eventLengthMins the eventLengthMins to set
	 */
	public void setEventLengthMins(int eventLengthMins) {
		this.eventLengthMins = eventLengthMins;
	}

	/**
	 * @return the eventDescription
	 */
	public String getEventDescription() {
		return eventDescription;
	}

	/**
	 * @param eventDescription the eventDescription to set
	 */
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the attendees
	 */
	public String[] getAttendees() {
		return attendees;
	}

	/**
	 * @param attendees the attendees to set
	 */
	public void setAttendees(String[] attendees) {
		this.attendees = attendees;
	}
	
	/**
	 * Calculates and returns the end time of the event
	 * @return end time of the event
	 */
	public Calendar getEndTime(){
		Calendar endTime = (Calendar)StartTime.clone();
		endTime.add(Calendar.HOUR, eventLengthHours);
		endTime.add(Calendar.MINUTE, eventLengthMins);
		return endTime;
	}
}
