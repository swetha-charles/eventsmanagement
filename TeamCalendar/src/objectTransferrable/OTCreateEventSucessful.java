/**
 * 
 */
package objectTransferrable;

/**
 * Server Sends this object if the event creation was successful, otherwise it will send the error object?
 * So this object could probably be empty.
 * @author tmd668
 *
 */
public class OTCreateEventSucessful extends ObjectTransferrable {
	private Event event;

	public OTCreateEventSucessful(Event event) {
		super("0011");
	}

	public Event getEvent() {
		return event;
	}
}
