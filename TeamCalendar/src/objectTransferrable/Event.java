/**
 * 
 */
package objectTransferrable;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to define object for an event, stores the startTime, and Event Length parameters as unfortunately its surprisingly hard to find the time between two dates or calendar objects in java
 * 
 * @author tmd668
 *
 */
public class Event {
	private Calendar startTime, endTime;
	private int eventLengthHours, eventLengthMins;
	private String eventDescription;
	private String eventTitle;
	private String location;
	private String[] attendees; //May not be used
	
	/**
	 * Deprecated method to create event.
	 * @param startTime
	 * @param eventLengthHours
	 * @param eventLengthMins
	 * @param eventDescription
	 * @param eventTitle
	 * @param location
	 */
	public Event(Calendar startTime, int eventLengthHours, int eventLengthMins, String eventDescription, String eventTitle, String location){
		this.startTime = startTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = new String[] {""};
		Calendar endTime = (Calendar)startTime.clone();
		endTime.add(Calendar.HOUR, eventLengthHours);
		endTime.add(Calendar.MINUTE, eventLengthMins);
		this.endTime = endTime;
	}
	/**
	 * Deprecated method to create event with attendees
	 * @param startTime
	 * @param eventLengthHours
	 * @param eventLengthMins
	 * @param eventDescription
	 * @param eventTitle
	 * @param location
	 * @param attendees
	 */
	public Event(Calendar startTime, int eventLengthHours, int eventLengthMins, String eventDescription,String eventTitle, String location, String[] attendees){
		this.startTime = startTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = attendees;
		Calendar endTime = (Calendar)startTime.clone();
		endTime.add(Calendar.HOUR, eventLengthHours);
		endTime.add(Calendar.MINUTE, eventLengthMins);
		this.endTime = endTime;
	}
	
	
	public Event(Calendar startTime, Calendar endTime, String eventDescription, String eventTitle, String location){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = new String[] {""};
	}
	
	/**
	 * Constructor 
	 * @param startTime
	 * @param endTime
	 * @param eventDescription
	 * @param eventTitle
	 * @param location
	 * @param attendees
	 */
	public Event(Calendar startTime, Calendar endTime, String eventDescription, String eventTitle, String location, String[] attendees){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = attendees;
	}
	
	/**
	 * Constructor just taking in event title.
	 * @param eventTitle
	 */
	public Event(String eventTitle){
		this.eventTitle = eventTitle;
		
		this.startTime = Calendar.getInstance();
		this.endTime = startTime;
		this.eventDescription = "";
		this.location = "";
		this.attendees = new String[] {""};
	}

	/**
	 * @return the startTime
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
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
	
	public void setEndTime(Calendar endTime){
		this.endTime = endTime;
	}
	/**
	 * Calculates and returns the end time of the event
	 * @return end time of the event
	 */
	public Calendar getEndTime(){
		
		return endTime;
	}
	
	public String getTitle(){
		return this.eventTitle;
	}
	
	public String getYear(){
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		String year = yearFormat.format(this.startTime.getTime());
		return year;
	}
	
	public String getMonth(){
		DateFormat monthFormat = new SimpleDateFormat("MM");
		String month = monthFormat.format(this.startTime.getTime());
		return month;
	}
	
	public String getDay(){
		DateFormat dayFormat = new SimpleDateFormat("dd");
		String day = dayFormat.format(this.startTime.getTime());
		return day;
	}
	
	public String getStartHour(){
		DateFormat hourFormat = new SimpleDateFormat("HH");
		String hour = hourFormat.format(this.startTime.getTime());
		return hour;
	}
}
