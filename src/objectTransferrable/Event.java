/**
 * 
 */
package objectTransferrable;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private String eventTitle;
	private String location;
	private String[] attendees; //May not be used
	
	public Event(Calendar StartTime, int eventLengthHours, int eventLengthMins, String eventDescription, String eventTitle, String location){
		this.StartTime = StartTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = new String[] {""};
	}
	
	public Event(Calendar StartTime, int eventLengthHours, int eventLengthMins, String eventDescription,String eventTitle, String location, String[] attendees){
		this.StartTime = StartTime;
		this.eventLengthHours = eventLengthHours;
		this.eventLengthMins = eventLengthMins;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.attendees = attendees;
	}
	
	public Event(String eventTitle){
		this.eventTitle = eventTitle;
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
	
	public String getTitle(){
		return this.eventTitle;
	}
	
	public String getYear(){
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		String year = yearFormat.format(this.StartTime.getTime());
		return year;
	}
	
	public String getMonth(){
		DateFormat monthFormat = new SimpleDateFormat("MM");
		String month = monthFormat.format(this.StartTime.getTime());
		return month;
	}
	
	public String getDay(){
		DateFormat dayFormat = new SimpleDateFormat("dd");
		String day = dayFormat.format(this.StartTime.getTime());
		return day;
	}
	
	public String getStartHour(){
		DateFormat hourFormat = new SimpleDateFormat("HH");
		String hour = hourFormat.format(this.StartTime.getTime());
		return hour;
	}
}
