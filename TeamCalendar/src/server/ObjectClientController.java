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
import gui.RegistrationPanel;
import model.Model;
import model.State;

public class ObjectClientController implements ActionListener, MouseListener {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private Model model;
	private MainView view;
	private Socket s;
	private Thread threadForServer = null;
	private boolean running;

	public ObjectClientController() {
		try {
			int portnumber = 5046;
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
//		MainView view;
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

		// add an observer (view) to the model

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
			//System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
//			model.changeCurrentState(State.LOGIN);
			//System.out.println("Controller: Model's state chanhed to" + model.getCurrentState());
			model.setPanel(view, new Login(this));
		}

	}

	// mouse clicking, right now it's used for changing from login page to
	// registration up
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent() == LoginPanel.signup) {
			//System.out.println("Controller: Model's state  about to, current state " + model.getCurrentState());
//			model.changeCurrentState(State.REGISTRATION);
			//System.out.println("Controller: Model's state changed to" + model.getCurrentState());
			
			model.setPanel(view, new Registration(this, model));
		}

	}

	public void checkUsername(String username) {
		OTUsernameCheck otuc = new OTUsernameCheck(username);
		try{
			this.toServer.writeObject(otuc);
			System.out.println("Client: Sent OT with opcode" + otuc.getOpCode());
		}catch(IOException e){
			//toserver isn't working
		}
	}

	public void checkEmail(String email) {
		OTEmailCheck otec = new OTEmailCheck(email);
		try {
			
			this.toServer.writeObject(otec);
			System.out.println("Client: Sent OT with opcode" + otec.getOpCode());

		} catch (IOException e) {

		}
		// ObjectTransferrable OT = (ObjectTransferrable)
		// this.fromServer.readObject();
		// if(OT.getOpCode().equals("0002")){
		// if(!((OTUsernameCheck) OT).getAlreadyExists()){
		// this.model.changeCurrentState(State.REGISTRATIONEMAILOK);
		// } else {
		// this.model.changeCurrentState(State.REGISTRATIONEMAILEXISTS);
		// }
		// } else {
		// System.out.println("ERROR: Server needs to communicate an
		// UsernameCheckObject back to Client");
		// this.model.changeCurrentState(State.ERRORSERVERCOMMUNICATION);
		// }
		// } catch (IOException e) {
		// //run this block of code if the toServer OR fromServer do not work
		// System.out.println("Connection down, sorry");
		// this.model.changeCurrentState(State.ERRORCONNECTIONDOWN);
		// } catch (ClassNotFoundException e) {
		// //Communication from the server is not what's expected!
		// System.out.println("Server is not communicating an Object of type
		// ObjectTransferrable");
		// this.model.changeCurrentState(State.ERRORCONNECTIONDOWN);
		// }

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

	public boolean getRunning(){
		return this.running;
	}
	
	public void setRunning(boolean running){
		this.running = running;
		if(!running){
			threadForServer.interrupt();
		}
	}
	
	public static void main(String[] args) {
		ObjectClientController occ = new ObjectClientController();
	}

}
