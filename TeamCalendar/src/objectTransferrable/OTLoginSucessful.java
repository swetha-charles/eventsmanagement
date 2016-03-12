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
public class OTLoginSucessful extends ObjectTransferrable {
private final boolean loginSucessful;
	/**
	 * @param opCode
	 */
	public OTLoginSucessful(boolean loginSucessful) {
		super("0013");
		this.loginSucessful =  loginSucessful;
	}

}
