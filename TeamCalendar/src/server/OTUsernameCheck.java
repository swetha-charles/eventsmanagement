package server;

public class OTUsernameCheck extends ObjectTransferrable {
	
	private String username;
	private boolean alreadyExists;
	
	public OTUsernameCheck(String username){
		setOpCode("0001");
		this.username= username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAlreadyExists() {
		return alreadyExists;
	}

	public void setAlreadyExists(boolean alreadyExists) {
		this.alreadyExists = alreadyExists;
	}
	
	@Override
	public void run(){
		
	}
	
}
