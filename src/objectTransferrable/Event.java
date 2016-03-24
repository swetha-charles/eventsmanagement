
package objectTransferrable;

import java.sql.Date;
import java.sql.Time;

/**
 * This object is used to represent events. It implements serializable to enable it to be sent 
 * using a socket. 
 * @author swetha
 *
 */
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
	
	/**
	 * Information about an event is used to construct this event object. 
	 * @param startTime
	 * @param endTime
	 * @param eventDescription
	 * @param eventTitle
	 * @param location
	 * @param date
	 * @param globalEvent a boolean value indicating whether this event is global i.e. visible and editable by everyone. 
	 * @param lockVersion The current lock version of the event in the database. Differentiates between stale and fresh event versions. 
	 */
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
