package objectTransferrable;

public class OTUpdatePassword extends ObjectTransferrable{
	private String pwhash;
	/**
	 * Send by client to inform server that the user
	 * has changed their password. 
	 * @param pwHash
	 */
	public OTUpdatePassword(String pwHash) {
		super("0023");
		this.pwhash = pwHash;
	}

	public String getPwhash() {
		return pwhash;
	}

}
