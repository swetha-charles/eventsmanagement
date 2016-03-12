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
	private String username;
	private Event event;
	/**
	 * @param opCode
	 */
	public OTCreateEvent(String username, Event event) {
		super("0010");
		this.username = username;
		this.event = event;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}
		
		
}
