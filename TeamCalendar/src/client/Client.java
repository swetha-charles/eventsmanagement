package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import gui.MainView;
import model.Model;
import model.ModelState;
import objectTransferrable.OTEmailCheck;
import objectTransferrable.OTExitGracefully;
import objectTransferrable.OTRegistrationInformation;
import objectTransferrable.OTUsernameCheck;
import objectTransferrable.ObjectTransferrable;

public class Client {
	private int portnumber;
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Model model;
	private MainView view;
	private Socket s;
	private Thread threadForServer = null;
	private LinkedBlockingQueue<String> waitingFor;

	public Client(int portnumber) {
		model = new Model(this);
		view = new MainView(this, model);
		model.addObserver(view);
		this.waitingFor = new LinkedBlockingQueue<String>();

		try {
			this.portnumber = portnumber;
			s = new Socket("localhost", portnumber);
			System.out.println("Client connected to port " + portnumber);
		} catch (IOException e) {
			model.changeCurrentState(ModelState.UNABLETOOPENSOCKET);
		}

		try {
			toServer = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			model.changeCurrentState(ModelState.UNABLETOOPENSTREAMS);
		}

		try {
			fromServer = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			model.changeCurrentState(ModelState.UNABLETOOPENSTREAMS);
		}

		threadForServer = new Thread(new ThreadForServer(this, this.fromServer, this.model));
		threadForServer.start();

	}

	public Client() {

	}

	/**
	 * This is the only way to send object transferrables from the client. It is
	 * a private method.
	 * 
	 * @param OT
	 */
	private void writeToServer(ObjectTransferrable OT, boolean exit, String complementOpCode) {
		try {
			this.toServer.writeObject(OT);
			if (!exit) {
				while (true) {
					try {
						this.waitingFor.put(complementOpCode);
						break;
					} catch (InterruptedException e) {
						// waitingFor is being accessed, try again!
					}
				}

			}
		} catch (IOException e) {
			// Connection is down, what do we do?
			this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
			System.out.println("Connection malfunctioned");
			this.attemptRecovery();

		}
	}

	public LinkedBlockingQueue<String> getWaitingFor() {
		return waitingFor;
	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);

		this.writeToServer(otuc, false, otuc.getOpCode());
		System.out.println("Sent OT with opcode" + otuc.getOpCode());

	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);

		this.writeToServer(otec, false, otec.getOpCode());
		System.out.println("Client: Sent OT with opcode" + otec.getOpCode());

	}

	public void checkRegistration(OTRegistrationInformation otri) {
		String complementOpCode = "0004";
		this.writeToServer(otri, false, complementOpCode);
		System.out.println("Client: Send OT with opcode " + otri.getOpCode());
		System.out.println("Client: Expecting OT with opcode " + complementOpCode);
	}

	public void exitGracefully() {
		if (s != null) {
			OTExitGracefully oeg = new OTExitGracefully();
			this.writeToServer(oeg, true, null);
			System.out.println("Send OT to server to exit");

			try {
				toServer.close();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			this.threadForServer.interrupt();
			System.out.println("Interrupted threadForServer");
			int count = 0;
			// wait for ~1 minute for threadForServer to die.
			while (threadForServer.isAlive() && count < 60) {
				try {
					count++;
					threadForServer.interrupt();
					Thread.currentThread();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Client has been interrupted and will quit");
					break;
				}
			}

			// threadForServer may not yet have died but that's OK, we can't
			// wait forever.

			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Could not close socket. \n Setting socket to null");
				s = null;
			}
		}
	}

	private void attemptToCloseConnections() {
		int safeClosure = 0;
		if (s != null) {
			try {
				s.shutdownOutput();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			this.threadForServer.interrupt();
			System.out.println("Interrupted threadForServer");
			int count = 0;
			while (threadForServer.isAlive() && count < 20) {
				try {
					Thread.currentThread();
					Thread.sleep(1000);
					count++;
				} catch (InterruptedException e) {
					try {
						s.shutdownInput();
					} catch (IOException e1) {
						// Socket may already be closed
					}
					break;
				}
			}

			if (!s.isClosed()) {
				if (!s.isInputShutdown()) {
					try {
						s.shutdownInput();
					} catch (IOException e) {
						System.out.println("Input stream from server is malfunctioning");
					}
				}
				if (!s.isOutputShutdown()) {
					try {
						s.shutdownInput();
					} catch (IOException e) {
						System.out.println("Output stream to server is malfunctioning");
					}
				}
				try {
					s.close();
				} catch (IOException e) {
					s = null;
					System.out.println("Socket is malfunctinoning");
				}
			}
		}
	}

	private void attemptRecovery() {
		int count = 0;
		while (!s.isClosed() && count <= 2) {
			this.attemptToCloseConnections();
		}
		model.promptRestart();

	}

	@SuppressWarnings("unused")
	private void deprecatedAttemptRecovery() {
		this.attemptToCloseConnections();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();

		while (!this.model.getCurrentState().equals(ModelState.EXIT)) {
			// allows the user ~16.67 minutes to get their shit together
			if (endTime - startTime < 1000000) {
				try {
					this.s = new Socket("localhost", portnumber);
					break;
				} catch (IOException e) {
					System.out.println(
							"Connection has not yet been restored. Client will sleep for 1 second, then try again");
					Thread.currentThread();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						System.out.println("Client could not recover");
						this.model.changeCurrentState(ModelState.PROMPTRELOAD);
						return;
					}
				}
				if (Thread.interrupted()) {
					this.model.changeCurrentState(ModelState.PROMPTRELOAD);
					return;
				}
				System.out.println("Client is awake, trying to restore connection");
				endTime = System.currentTimeMillis();
			} else {
				System.out.println("Client could not recover in a timely fashion. ");
				this.model.changeCurrentState(ModelState.PROMPTRELOAD);
				return;
			}

		}

		if (!this.model.getCurrentState().equals(ModelState.EXIT)) {
			try {
				toServer = new ObjectOutputStream(s.getOutputStream());
			} catch (IOException e) {
				System.out.println("Could not create output stream to server");
			}

			try {
				fromServer = new ObjectInputStream(s.getInputStream());
			} catch (IOException e) {
				System.out.println("Could not create input stream to server");
			}
			threadForServer = new Thread(new ThreadForServer(this, this.fromServer, this.model));
			threadForServer.start();
		} else {
			System.out.println("Goodbye!");
			return;
		}

	}

	public static void main(String[] args) {
		Client C = new Client(4449);
	}

}
