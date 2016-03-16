package objectTransferrable;

public class OTUpdateUserProfileSuccessful extends ObjectTransferrable{

	private String firstName;
	private String lastName;
	private String email;
	
	public OTUpdateUserProfileSuccessful(String firstName, String lastName, String email) {
		super("0022");
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
