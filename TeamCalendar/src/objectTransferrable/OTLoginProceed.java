package objectTransferrable;

public class OTLoginProceed extends ObjectTransferrable{

	private boolean loginProceed;
	private String firstName, lastName, email;
	
	public OTLoginProceed(boolean loginProceed, String firstName, String lastName, String email){
		super("0016");
		this.loginProceed = loginProceed;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public boolean getLoginProceed() {
		return loginProceed;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
}
