/**
 * 
 */
package objectTransferrable;

import java.util.ArrayList;

/**
 * Returns the days event, for use as a response from the server
 * @author tmd668
 *
 */
public class OTReturnDayEvents extends ObjectTransferrable {
	
	private ArrayList<Event> eventList;
	public OTReturnDayEvents(ArrayList<Event> eventList){
		super("0009");
		this.eventList = eventList;
	}

	/**
	 * @return the eventList
	 */
	public ArrayList<Event> getEventList() {
		return eventList;
	}
	
	
}
