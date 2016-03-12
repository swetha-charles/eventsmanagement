/**
 * 
 */
package objectTransferrable;

/**
 * Heartbeat function for checking connection is maintained, could be adapted into update check function if
 * functionality is expanded
 * @author tmd668
 *
 */
public class OTHeartBeat extends ObjectTransferrable {

	/**
	 * @param opCode
	 */
	public OTHeartBeat() {
		super("0014");
		
	}

}
