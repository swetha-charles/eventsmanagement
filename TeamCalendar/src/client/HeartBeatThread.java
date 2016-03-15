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
				// send heartbeat every 10 seconds
				Thread.sleep(10000);
				System.out.println("Sending Heartbeat to server");
				this.client.sendHeartBeat();
			} catch (InterruptedException e) {
				System.out.println("Heartbeat was interrupted and is going to sleep");
				this.running = false;
			}

		}

	}

	public void stop(){
		this.running = false;
	}
}
