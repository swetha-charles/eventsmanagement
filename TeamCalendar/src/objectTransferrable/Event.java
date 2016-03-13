/**
 * 
 */
package objectTransferrable;

import java.lang.reflect.Constructor;
import java.util.Calendar;

/**
 * Class to define object for an event, stores the startTime, and Event Length parameters as unfortunately its surprisingly hard to find the time between two dates or calendar objects in java
 * 
 * @author tmd668
 *
 */
public class Event {
	private Calendar startTime;
	private Calendar endTime;
	private String eventDescription;
	private String location;
	private String[] attendees; //May not be used
	
	public Event(Calendar startTime, int eventLengthHours, int eventLengthMins, String eventDescription, String location, Calendar endTime){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.location = location;
		this.attendees = new String[] {""};
	}
	
	public Event(Calendar startTime, int eventLengthHours, int eventLengthMins, String eventDescription, String location, Calendar endTime, String[] attendees){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.location = location;
		this.attendees = attendees;
	}

	/**
	 * @return the startTime
	 */
	public Calendar getstartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setstartTime(Calendar startTime) {
		startTime = startTime;
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
		return endTime;
		
	}
}
