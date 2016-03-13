/**
 * 
 */
package objectTransferrable;

/**
 * Object carrying a boolean saying whether the login was successful if false client should probably
 * just display incorrect username or password.
 * @author Tom
 *
 */
public class OTLoginSuccessful extends ObjectTransferrable {
	private final boolean loginSuccessful;
	private String firstName, lastName, email;
	/**
	 * @param opCode
	 */
	public OTLoginSuccessful(boolean loginSuccessful, String firstName, String lastName, String email) {
		super("0013");
		this.loginSuccessful =  loginSuccessful;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	public boolean isLoginSuccessful() {
		return loginSuccessful;
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
