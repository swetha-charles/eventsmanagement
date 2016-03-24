package objectTransferrable;

public class OTUpdateUserProfile extends ObjectTransferrable {
	private String firstName;
	private String lastName;
	private String email;
	
	/**
	 * Request sent from client to server indicating that the user
	 * would like to change their profile information.
	 * This information has already been validated. 
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public OTUpdateUserProfile(String firstName, String lastName, String email) {
		super("0021");
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
