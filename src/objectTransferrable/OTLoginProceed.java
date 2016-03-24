package objectTransferrable;

public class OTLoginProceed extends ObjectTransferrable{

	private boolean loginProceed;
	private String firstName, lastName, email;
	
	/**
	 * This is sent from the server to the client. 
	 * It acts as a reply to the client's message to
	 * inform server that client has input the correct password. 
	 * After receiving this OT, the client requests meeting for the 
	 * current day from the server 
	 * 
	 * @param loginProceed
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
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
