package objectTransferrable;

public class OTHashToClient extends ObjectTransferrable{

	private String hash;
	private boolean userExists;
	
	public OTHashToClient(boolean userExists, String hash) {
		super("0015");
		this.hash = hash;
		this.userExists = userExists;
	}

	public String getHash() {
		return hash;
	}

	public boolean getUserExists() {
		return userExists;
	}

}
