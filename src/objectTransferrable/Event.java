
package objectTransferrable;

import java.sql.Time;

public class Event {
	private Time startTime; 
	private Time endTime;
	private String eventDescription;
	private String eventTitle;
	private String location;

	public Event(Time startTime, Time endTime, String eventDescription, String eventTitle, String location){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public String getLocation() {
		return location;
	}
	
}
