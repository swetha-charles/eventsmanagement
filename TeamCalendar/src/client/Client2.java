package client;

import java.io.IOException;
import java.net.UnknownHostException;

import controller.Controller;
import gui.MainView;
import model.Model;

public class Client2 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("Client started!");

		/*
		 * try (Socket clientSocket = new Socket("127.0.0.1", 5001); PrintWriter
		 * out = new PrintWriter(clientSocket.getOutputStream(), true);
		 * BufferedReader in = new BufferedReader(new
		 * InputStreamReader(clientSocket.getInputStream())); Scanner fromUser =
		 * new Scanner(System.in);) {
		 * 
		 * 
		 * }
		 */

		// start the GUI
		Controller controller = new Controller();
		Model model = new Model(controller);
		MainView view = new MainView(controller, model);

		// add an observer (view) to the model
		model.addObserver(view);
		controller.addModel(model);
		controller.addView(view);
		
	

	}

}
