package objectTransferrable;

public class OTUsernameCheck  extends ObjectTransferrable{
	
	private String username;
	private boolean alreadyExists;
	
	/**
	 * Initially sent by client to check whether username entered 
	 * in the registration page already exists. 
	 * 
	 * It is pinged back by the server with the alreadyExists boolean
	 * value already filled. 
	 * @param username
	 */
	public OTUsernameCheck(String username){
		super("0001");
		this.username= username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getAlreadyExists() {
		return alreadyExists;
	}

	public void setAlreadyExists(boolean alreadyExists) {
		this.alreadyExists = alreadyExists;
	}
	
}
