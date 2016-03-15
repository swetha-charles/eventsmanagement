package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gui.Login;
import gui.LoginPanel;
import gui.MainView;
import gui.Registration;
import model.Model;
import model.State;
import objectTransferrable.*;

public class ZDEPObjectClientController implements ActionListener, MouseListener {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Model model;
	private MainView view;
	private Socket s;
	private Thread threadForServer = null;
	private boolean running;

	public ZDEPObjectClientController() {
	try {
			int portnumber = 4446;
			s = new Socket("localhost", portnumber);
			System.out.println("Client: Listening on port " + portnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			toServer = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fromServer = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model = new Model(this);

		try {
			// this is what causes the exception
			view = new MainView(this, model);
			model.addObserver(view);
			this.addModel(model);
			this.addView(view);

			threadForServer = new Thread(new ThreadForServer(this, this.fromServer, this.toServer, this.model));
			threadForServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void addModel(Model model) {
		this.model = model;
	}

	public void addView(MainView view) {
		this.view = view;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":

			model.setPanel(view, new Login(this));
		}

	}

	// mouse clicking, right now it's used for changing from login page to
	// registration up
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == LoginPanel.signup) {
			model.changeCurrentState(State.REGISTRATION);
		}

	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		try {
			this.toServer.writeObject(otuc);
			System.out.println("Client: Sent OT with opcode" + otuc.getOpCode());
		} catch (IOException e) {
			// toserver isn't working
		}
	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);
		try {

			this.toServer.writeObject(otec);
			System.out.println("Client: Sent OT with opcode" + otec.getOpCode());

		} catch (IOException e) {

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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

	public static void main(String[] args) {
		ZDEPObjectClientController occ = new ZDEPObjectClientController();
	}

}
