package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.Controller;
import gui.MainView;
import model.Model;

public class ObjectClient {

	static Socket s;

	public static void main(String[] args) throws IOException {

		try{
			s = new Socket("localhost", 5006);
			ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());

			Controller controller = new Controller(toServer, fromServer);
			Model model = new Model(controller);
			MainView view = new MainView(controller, model);

			// add an observer (view) to the model
			model.addObserver(view);
			controller.addModel(model);
			controller.addView(view);

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

		} catch(IOException e){
			
		} finally {
			s.close();
		}
		
	}

}
