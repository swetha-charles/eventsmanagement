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
	/**
	 * @param opCode
	 */
	public OTLoginSuccessful(boolean loginSuccessful) {
		super("0013");
		this.loginSuccessful =  loginSuccessful;
	}
	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

}
