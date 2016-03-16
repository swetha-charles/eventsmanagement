package client;

public class HeartBeatThread implements Runnable {
	private Client client;
	private boolean running = true;

	public HeartBeatThread(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (running) {
			Thread.currentThread();
			try {
				// send heartbeat every 3 seconds
				Thread.sleep(3000);
//				System.out.println("Sending Heartbeat to server");
				this.client.sendHeartBeat();
			} catch (InterruptedException e) {
				System.out.println("Heartbeat was interrupted and is going to sleep");
				this.running = false;
			}

		}
		System.out.println("HBThread stopped");
	}

	public void setRunningToFalse(){
		System.out.println("HBThread asked to stop");
		this.running = false;
		System.out.println("HBThread running set to false");
		return;
	}
}
