package objectTransferrable;


/**
 * Used to check if an email already exists within the database. It is initially sent 
 * from the client and is pinged back with a filled in alreadyExists boolean value by 
 * the server. 
 * @author swetha
 *
 */
public class OTEmailCheck extends ObjectTransferrable{
	private String email;
	private boolean alreadyExists;
	
	public OTEmailCheck(String email){
		super("0002");
		this.email= email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getAlreadyExists() {
		return alreadyExists;
	}

	public void setAlreadyExists(boolean alreadyExists) {
		this.alreadyExists = alreadyExists;
	}
}
