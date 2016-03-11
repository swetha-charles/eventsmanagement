package objectTransferrable;

public class OTRegistrationCheck extends ObjectTransferrable {
	
	String username;
	String email;
	String firstname;
	String lastname;
	String dob;
	String error;
	boolean registered;
	
	public OTRegistrationCheck(String username, String email, String firstname, String lastname, String dob, String error, boolean registered){
		setOpCode("0004");
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.error = error;
		this.registered = registered;
		
	}
	
	
}
