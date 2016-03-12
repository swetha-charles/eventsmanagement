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
	private String pwHash;
	/**
	 * @param opCode
	 */
	public OTLogin(String username, String pwHash) {
		super("0012");
		this.username = username;
		this.pwHash = pwHash;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the pwHash
	 */
	public String getPwHash() {
		return pwHash;
	}

}
