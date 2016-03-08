package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import gui.MainView;
import model.Model;
import model.State;

public class ObjectClient {

	static Socket s;

	public static void main(String[] args) throws IOException {

//		try{
//			s = new Socket("localhost", 5010);
//			ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
//			ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());
//
//			Controller controller = new Controller(toServer, fromServer);
//			Model model = new Model(controller);
//			MainView view = new MainView(controller, model);
//
//			// add an observer (view) to the model
//			model.addObserver(view);
//			controller.addModel(model);
//			controller.addView(view);
//			
//			while(!model.getCurrentState().equals(State.EXIT)){
//				
//			}
//			if(model.getCurrentState().equals(State.EXIT)){
//				s.close();
//			}
			
			/*
			 * try { OTUsernameCheck p = (OTUsernameCheck) fromServer.readObject();
			 * System.out.println(p.getAlreadyExists()); } catch
			 * (ClassNotFoundException e) { e.printStackTrace(); }
			 * 
			 * toServer.writeObject(new OTUsernameCheck("dave"));
			 * 
			 * try { OTUsernameCheck p = (OTUsernameCheck) fromServer.readObject();
			 * System.out.println(p.getAlreadyExists()); } catch
			 * (ClassNotFoundException e) { e.printStackTrace(); }
			 */

	/*	} catch(IOException e){
			
		} finally {
			System.out.println("Client: Closing");
			s.close();
		}
		*/
	}

}
