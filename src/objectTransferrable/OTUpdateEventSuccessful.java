package objectTransferrable;

/**
 * Response from server indicating that 
 * the event has either been modified or 
 * the edit was not successful.  
 * @author swetha
 *
 */
public class OTUpdateEventSuccessful extends ObjectTransferrable {
	
	private boolean successful;
	
	public OTUpdateEventSuccessful(boolean successful){
		super("0018");
		this.successful = successful;
	}

	public boolean getSuccessful() {
		return successful;
	}
	
	
}
