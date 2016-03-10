package objectTransferrable;

public class OTRegistrationCheck {
	String opcode;
	String username;
	String email;
	String firstname;
	String lastname;
	String dob;
	
	public OTRegistrationCheck(String username, String email, String firstname, String lastname, String dob){
		this.opcode = "0003";
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		
	}
	
	
}
