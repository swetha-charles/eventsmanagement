package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gui.MainView;
import model.Model;
import model.ModelState;
import objectTransferrable.OTEmailCheck;
import objectTransferrable.OTExitGracefully;
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
	private boolean running;

	public Client(int portnumber) {
		try {
			this.portnumber = portnumber;
			s = new Socket("localhost", portnumber);
			System.out.println("Client connected to port " + portnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		model = new Model(this);

		try {
			// this is what causes the exception
			view = new MainView(this, model);
			model.addObserver(view);
			threadForServer = new Thread(new ThreadForServer(this, this.fromServer, this.model, s));
			threadForServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This is the only way to send object transferrables from the client. It is
	 * a private method.
	 * 
	 * @param OT
	 */
	private void writeToServer(ObjectTransferrable OT) {
		try {
			this.toServer.writeObject(OT);
		} catch (IOException e) {
			// Connection is down, what do we do?
			this.model.changeCurrentState(ModelState.ERRORCONNECTIONDOWN);
			System.out.println("Connection lost, will attempt recovery");
			this.attemptRecovery();

		}
	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);

		this.writeToServer(otuc);
		System.out.println("Sent OT with opcode" + otuc.getOpCode());

	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);

		this.writeToServer(otec);
		System.out.println("Client: Sent OT with opcode" + otec.getOpCode());

	}

	public boolean getRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		if (!running) {
			threadForServer.interrupt();
		}
	}

	public void exitGracefully() {
		if (s != null) {
			OTExitGracefully oeg = new OTExitGracefully();
			this.writeToServer(oeg);
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

			while (threadForServer.isAlive()) {
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println(
							"Client has been interrupted and will go back to checking whether threadForServer is asleep");
				}
			}

			assert (!threadForServer.isAlive());

			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Could not close socket. \n Setting socket to null");
				s = null;
			}
		}
	}

	private void attemptToCloseConnections() {
		if (s != null) {
			try {
				toServer.close();
				System.out.println("Output stream to server has been shutdown");

			} catch (IOException e1) {
				System.out.println("Output stream to server is malfunctioning. \n Setting output stream to null");
				toServer = null;
			}

			this.threadForServer.interrupt();
			System.out.println("Interrupted threadForServer");

			while (threadForServer.isAlive()) {
				try {
					Thread.currentThread();
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println(
							"Client has been interrupted and will go back to checking whether threadForServer is asleep");
				}
			}

			assert (!threadForServer.isAlive());

			try {
				s.close();
			} catch (IOException e) {
				System.out.println("Could not close socket. \n Setting socket to null");
				s = null;
			}
		}

	}

	private void attemptRecovery() {
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
						this.model.changeCurrentState(ModelState.RELOAD);
						return;
					}
				}
				if (Thread.interrupted()) {
					this.model.changeCurrentState(ModelState.RELOAD);
					return;
				}
				System.out.println("Client is awake, trying to restore connection");
				endTime = System.currentTimeMillis();
			} else {
				System.out.println("Client could not recover in a timely fashion. ");
				this.model.changeCurrentState(ModelState.RELOAD);
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

			threadForServer = new Thread(new ThreadForServer(this, this.fromServer, this.model, s));
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
