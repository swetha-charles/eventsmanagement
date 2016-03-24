package objectTransferrable;


/**
 * Sent to the server to request deletion of a certain event. 
 * @author swetha
 *
 */
public class OTDeleteEvent extends ObjectTransferrable{
	private Event event;
	
	public OTDeleteEvent(Event event) {
		super("0019");
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

}
