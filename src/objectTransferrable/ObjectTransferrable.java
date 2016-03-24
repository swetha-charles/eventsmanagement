package objectTransferrable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * This is a abstract class created to act as a supertype for messages sent between client and server. 
 * It implement serializable to allow it to be sent through a socket. 
 * Implementing Future allows the client to set time limits on blocking actions such as reading an object
 * from an input stream. 
 * 
 * All object transferrables have an opcode which to different different subclasses extending this supertype. 
 * @author swetha
 *
 */
public abstract class ObjectTransferrable implements java.io.Serializable, Future<Boolean> {

	private static final long serialVersionUID = 85731937678276152L;
	private final String opCode;

	
	public ObjectTransferrable(String opCode) {
		this.opCode = opCode;
	}

	public String getOpCode() {
		return opCode;
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

	@Override
	public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return false;
	}
}
