package objectTransferrable;

/**
 * This is a request from client to server. Attempts 
 * to update an event. 
 * @author swetha
 *
 */
public class OTUpdateEvent extends ObjectTransferrable {

	private Event oldEvent;
	private Event newEvent;
	
	public OTUpdateEvent(Event oldEvent, Event newEvent) {
		super("0017");
		this.oldEvent = oldEvent;
		this.newEvent = newEvent;
	}

	public Event getOldEvent() {
		return oldEvent;
	}

	public Event getNewEvent() {
		return newEvent;
	}

}
