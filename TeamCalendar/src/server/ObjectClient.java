package server;

import java.net.*;

import controller.Controller;
import gui.MainView;
import model.Model;

import java.io.*;

public class ObjectClient {

	public static void main(String[] args) throws IOException {

		Socket s = new Socket("localhost", 4444);
		ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());

//		Controller controller = new Controller(toServer, fromServer);
//		Model model = new Model(controller);
//		MainView view = new MainView(controller, model);
//
//		// add an observer (view) to the model
//		model.addObserver(view);
//		controller.addModel(model);
//		controller.addView(view);

		toServer.writeObject(new OTUsernameCheck("mwizzle"));

		try {
			OTUsernameCheck p = (OTUsernameCheck) fromServer.readObject();
			System.out.println(p.getAlreadyExists());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		toServer.writeObject(new OTUsernameCheck("dave"));

		try {
			OTUsernameCheck p = (OTUsernameCheck) fromServer.readObject();
			System.out.println(p.getAlreadyExists());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
