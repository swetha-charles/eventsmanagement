/**
 * 
 */
package objectTransferrable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Heartbeat function for checking connection is maintained, could be adapted into update check function if
 * functionality is expanded
 * @author tmd668
 *
 */
public class OTHeartBeat extends ObjectTransferrable implements Future<Boolean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8354488291813438349L;

	/**
	 * @param opCode
	 */
	public OTHeartBeat() {
		super("0014");
	}
	
	@Override
	public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		String opcode = this.getOpCode();
		if(opcode.equals("0014")){
			return true;
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
