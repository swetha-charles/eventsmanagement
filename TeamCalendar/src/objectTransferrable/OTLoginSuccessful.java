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
	private String username;

	/**
	 * @param opCode
	 */
	public OTLoginSuccessful(boolean loginSuccessful, String username) {
		super("0013");
		this.loginSuccessful =  loginSuccessful;
		this.username = username;
	}
	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}
	public String getUsername() {
		return username;
	}

}
