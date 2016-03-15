/**
 * 
 */
package objectTransferrable;

/**
 * Sends login credentials
 * @author tmd668
 *
 */
public class OTLogin extends ObjectTransferrable {
	private String username;
	/**
	 * @param opCode
	 */
	public OTLogin(String username) {
		super("0012");
		this.username = username;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

}
