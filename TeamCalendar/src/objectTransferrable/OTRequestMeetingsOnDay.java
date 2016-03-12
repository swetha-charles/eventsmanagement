/**
 * 
 */
package objectTransferrable;

import java.util.Calendar;

/**
 * @author tmd668
 *
 */
public class OTRequestMeetingsOnDay extends ObjectTransferrable {
	
	private String userName;
	private Calendar dateRequest;
	private final String opcode;
	/**
	 * 
	 * @param userName - The username of the user
	 * @param dateRequest - the date requested as a calendar
	 */
	public OTRequestMeetingsOnDay(String userName, Calendar dateRequest) {
		this.opcode = "0008";
		this.dateRequest = dateRequest;
		this.userName = userName;
	}
	
	/**
	 * @return the opcode
	 */
	public String getOpcode() {
		return opcode;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @return the dateRequest
	 */
	public Calendar getDateRequest() {
		return dateRequest;
	}
}
