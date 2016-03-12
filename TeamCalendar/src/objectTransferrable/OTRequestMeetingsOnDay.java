/**
 * 
 */
package objectTransferrable;

import java.util.Calendar;

/**
 * @author tmd668
 *
 */
public class OTRequestMeetings extends ObjectTransferrable {
	private String userName;
	private Calendar dateRequest;
	public OTRequestMeetings(String userName, Calendar dateRequest) {
		this.dateRequest = dateRequest;
		this.userName = userName;
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
