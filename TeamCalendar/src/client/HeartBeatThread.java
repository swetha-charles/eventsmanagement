package client;
/**
 * This is the heart beat thread. It's sole purpose is to prompt the client to send 
 * a heartbeat object to the server. 
 * @author swetha
 *
 */
public class HeartBeatThread implements Runnable {
	private Client client;
	private boolean running = true;

	/**
	 * Constructor to create the thread
	 * @param client
	 */
	public HeartBeatThread(Client client) {
		this.client = client;
	}

	/**
	 * Starts the thread. The thread sleeps for 1000ms. 
	 * If interrupted, it stops. 
	 */
	@Override
	public void run() {
		while (running) {
			try {
				// send heartbeat every 1 second
				Thread.currentThread();
				Thread.sleep(1000);
				this.client.sendHeartBeat();
			} catch (InterruptedException e) {
				System.out.println("Heartbeat was interrupted and is going to sleep");
				this.running = false;
			}
		}
		System.out.println("HBThread stopped");
	}

	/**
	 * Used to stop the heartbeat thread
	 */
	public void setRunningToFalse() {
		System.out.println("HBThread asked to stop");
		this.running = false;
		System.out.println("HBThread running set to false");
		return;
	}
}
