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
import model.Model;
import model.ModelState;
import objectTransferrable.*;

public class ObjectClientController implements ActionListener, MouseListener {
	private ObjectOutputStream toServer = null;
	private ObjectInputStream fromServer = null;
	private Model model;
	private MainView view;
	private Socket s;
	private Thread threadForServer = null;
	private boolean running;

	public ObjectClientController() {
	try {
			int portnumber = 4449;
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



	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "cancel":
			model.setPanel( new Login(this));
		}

	}

	// mouse clicking, right now it's used for changing from login page to registration
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == LoginPanel.signup) {
			model.changeCurrentState(ModelState.REGISTRATION);
		}

	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		try {

			this.toServer.writeObject(otuc);
			System.out.println("Sent OT with opcode" + otuc.getOpCode());
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

	public void exitGracefully(){
		if(s != null){
			OTExitGracefully oeg = new OTExitGracefully();
			try {
				toServer.writeObject(oeg);
				System.out.println("Send OT to server to exit");
				toServer.close();
				System.out.println("Output stream from client has been shutdown");
				this.threadForServer.interrupt();
				} catch (IOException e1) {
				//toServer isn't working, what to do?
				s = null;
				fromServer = null;
				toServer = null;
			}
			
		}
	}
	public static void main(String[] args) {
		ObjectClientController occ = new ObjectClientController();
	}

}
