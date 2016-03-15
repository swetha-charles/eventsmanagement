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
	private String username;

	/**
	 * @param opCode
	 */
	public OTLoginSuccessful(String username) {
		super("0013");
		this.username = username;
	}
	public String getUsername() {
		return username;
	}

}
