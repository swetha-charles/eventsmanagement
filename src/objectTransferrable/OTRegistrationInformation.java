	//For efficiency i think it would be best to return a different object to this one from the server.

package objectTransferrable;

public class OTRegistrationInformation extends ObjectTransferrable{
	private String opcode;
	private String username;
	private String email;
	private String firstname;
	private String lastname;
	private String dob;
	private String pwHash;
	
	public OTRegistrationInformation(String username, String email, String firstname, String lastname, String dob, String pwHash){
		this.opcode = "0004";
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.pwHash = pwHash;
	}
	
	public String getOpcode() {
		return opcode;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getDob() {
		return dob;
	}

	public String getPwHash() {
		return pwHash;
	}


}
