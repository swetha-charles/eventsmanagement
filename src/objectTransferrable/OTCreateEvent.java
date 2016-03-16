/**
 * 
 */
package objectTransferrable;

/**
 * Sent by the client to create a new event on the database
 * @author tmd668
 *
 */
public class OTCreateEvent extends ObjectTransferrable {
	private Event event;
	/**
	 * @param opCode
	 */
	public OTCreateEvent(Event event) {
		super("0010");
		this.event = event;
	}
	
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}
		
		
}
