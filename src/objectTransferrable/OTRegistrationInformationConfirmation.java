/**
 * 
 */
package objectTransferrable;

/**
 * Confirmation Object for a attempted registration
 * @author tmd668
 *
 */
public class OTRegistrationInformationConfirmation extends ObjectTransferrable {
	private boolean registrationSuccess;
	private String whatFailed, reasonForFailure;
	
	
	public OTRegistrationInformationConfirmation(boolean registrationSuccess, String whatFailed, String reasonForFailure) {
		super("0006");
		this.registrationSuccess = registrationSuccess;
		this.whatFailed = whatFailed;
		this.reasonForFailure = reasonForFailure;
	}
	
	public boolean getRegistrationSuccess() {
		return registrationSuccess;
	}

	public String getWhatFailed() {
		return whatFailed;
	}

	public String getReasonForFailure() {
		return reasonForFailure;
	}

}
