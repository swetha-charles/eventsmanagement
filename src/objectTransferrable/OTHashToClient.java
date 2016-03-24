package objectTransferrable;

/**
 * This object is sent as a response from the server to the client. 
 * It aims to transmit the hashed password of the user the client requested
 * information on. 
 * 
 * In case the user doesn't exist, the userExists boolean value is set to false. 
 * @author swetha
 *
 */
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
