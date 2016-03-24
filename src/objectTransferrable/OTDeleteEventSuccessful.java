package objectTransferrable;

/**
 * Sent by server to client to signfy successful deletion of an event. 
 * @author swetha
 *
 */
public class OTDeleteEventSuccessful extends ObjectTransferrable {
	private boolean successful;
	
	public OTDeleteEventSuccessful(boolean successful) {
		super("0020");
		this.successful = successful;
	}

	public boolean getSuccessful() {
		return successful;
	}

}
