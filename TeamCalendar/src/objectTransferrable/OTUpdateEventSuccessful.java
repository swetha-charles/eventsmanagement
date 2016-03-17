package objectTransferrable;

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
