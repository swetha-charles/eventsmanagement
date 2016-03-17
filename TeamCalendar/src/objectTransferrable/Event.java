
package objectTransferrable;

import java.sql.Date;
import java.sql.Time;

public class Event implements java.io.Serializable{
	
	private static final long serialVersionUID = 3568874973406778347L;
	private Time startTime; 
	private Time endTime;
	private String eventDescription;
	private String eventTitle;
	private String location;
	private Date date;
	private boolean globalEvent;
	private int lockVersion;
	
	public Event(Time startTime, Time endTime, String eventDescription, String eventTitle, String location, Date date, boolean globalEvent, int lockVersion){
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDescription = eventDescription;
		this.eventTitle = eventTitle;
		this.location = location;
		this.date = date;
		this.globalEvent = globalEvent;
		this.lockVersion = lockVersion;
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

	public Date getDate() {
		return date;
	}
	
	public boolean getGlobalEvent() {
		return globalEvent;
	}

	public int getLockVersion() {
		return lockVersion;
	}
	
}
