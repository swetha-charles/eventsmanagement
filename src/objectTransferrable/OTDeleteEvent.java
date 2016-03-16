package objectTransferrable;

public class OTDeleteEvent extends ObjectTransferrable{
	private Event event;
	
	public OTDeleteEvent(Event event) {
		super("0019");
	}

	public Event getEvent() {
		return event;
	}

}
