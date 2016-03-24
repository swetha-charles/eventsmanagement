package objectTransferrable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is initially sent by the client when user has decided to exit. 
 * Once received, server pings the object back and closes streams from server end. 
 * On receipt or after time limit of 200 ms has passed, client closes down streams and sockets. 
 * @author swetha
 *
 */
public class OTExitGracefully extends ObjectTransferrable  implements Future<Boolean>{
	private static final long serialVersionUID = 1L;
	
	public OTExitGracefully(){
		super("0005");
		
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
		return true;
		}
}
