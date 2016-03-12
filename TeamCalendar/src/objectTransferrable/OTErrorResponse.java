/**
 * 
 */
package objectTransferrable;

/**
 * This is an error response object to be transferred in case of any error that has not downed communication informing the client server of the problem and potentially
 * shutting down the connection if necessary.
 * @author tmd668
 *
 */
public class OTErrorResponse extends ObjectTransferrable {
	
	//Just here so you can give the error a potential specific response
	private int errCode;
	private String errorDescription;
	private boolean shouldShutdownCommunication;
	/**
	 * Described error code, using a constructor without this will set it to zero (uncommunicated error)
	 * @param errorDescription
	 * @param shouldShutdownCommunication
	 * Should the communication be shutdown?
	 * @param errCode
	 * Error code if this error has a particular way of being dealt with
	 */
	public OTErrorResponse(String errorDescription, boolean shouldShutdownCommunication,int errCode){
		this.errCode = errCode;
		this.errorDescription = errorDescription;
		this.shouldShutdownCommunication = shouldShutdownCommunication;
		
	}
	/**
	 * Constructor if the error does not have a relevant error code.
	 * @param errorDescription
	 * description of the error in a string to communicate to the sender
	 * @param shouldShutdownCommunication
	 * Should the communication be shutdown?
	 */
	public OTErrorResponse(String errorDescription, boolean shouldShutdownCommunication){
		this.errCode = 0;
		this.errorDescription = errorDescription;
		this.shouldShutdownCommunication = shouldShutdownCommunication;
		
	}
	/**
	 * @return the errCode
	 */
	public int getErrCode() {
		return errCode;
	}
	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * @return the shouldShutdownCommunication
	 */
	public boolean isShouldShutdownCommunication() {
		return shouldShutdownCommunication;
	}

}
