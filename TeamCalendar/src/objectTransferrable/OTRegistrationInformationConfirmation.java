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
	private String whatFailed, reasonForFailure, opcode;
	
	
	public OTRegistrationInformationConfirmation(boolean registrationSuccess, String whatFailed, String reasonForFailure) {
		opcode = "0006";
		this.registrationSuccess = registrationSuccess;
		this.whatFailed = whatFailed;
		this.reasonForFailure = reasonForFailure;
	}
	
	public boolean isRegistrationSuccess() {
		return registrationSuccess;
	}

	public String getWhatFailed() {
		return whatFailed;
	}

	public String getReasonForFailure() {
		return reasonForFailure;
	}

	public String getOpcode() {
		return opcode;
	}

}
