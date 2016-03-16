package objectTransferrable;

public class OTUpdatePassword extends ObjectTransferrable{
	private String pwhash;
	
	public OTUpdatePassword(String pwHash) {
		super("0023");
		this.pwhash = pwHash;
	}

	public String getPwhash() {
		return pwhash;
	}

}
