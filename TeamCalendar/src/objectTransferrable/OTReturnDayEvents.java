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
	private final String opcode;
	public OTReturnDayEvents(ArrayList<Event> eventList){
		this.eventList = eventList;
		this.opcode = "0009";
	}
	
	/**
	 * @return the opcode
	 */
	public String getOpcode() {
		return opcode;
	}

	/**
	 * @return the eventList
	 */
	public ArrayList<Event> getEventList() {
		return eventList;
	}
	
	
}
