package objectTransferrable;

public class OTEmailCheck extends ObjectTransferrable{
	private String email;
	private boolean alreadyExists;
	
	public OTEmailCheck(String email){
		setOpCode("0002");
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
