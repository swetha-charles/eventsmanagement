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
			try {
				// send heartbeat every 1 second
				Thread.currentThread();
				Thread.sleep(1000);
				if(!client.isBusy()){
					this.client.sendHeartBeat();
				}
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
