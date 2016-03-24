/**
 * 
 */
package objectTransferrable;

import java.sql.Date;
import java.util.Calendar;

/**
 * This is sent by the client to the server to request 
 * meetings for a particular date. 
 * @author swetha
 *
 */
public class OTRequestMeetingsOnDay extends ObjectTransferrable {
	
	private Date date;
	/**
	 * 
	 * @param userName - The username of the user
	 * @param dateRequest - the date requested as a calendar
	 */
	public OTRequestMeetingsOnDay(Date date) {
		super("0008");
		this.date = date;
	}

	/**
	 * @return the dateRequest
	 */
	public Date getDate() {
		return date;
	}
}
