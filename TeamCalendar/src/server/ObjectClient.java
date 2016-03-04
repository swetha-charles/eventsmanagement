package server;

import java.net.*;
import java.io.*;

public class ObjectClient {

	public static void main(String[] args) throws IOException {

		Socket s = new Socket("localhost", 4444);
		ObjectOutputStream toServer = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream fromServer = new ObjectInputStream(s.getInputStream());
		
	    toServer.writeObject(new OTUsernameCheck("mwizzle"));


	}

}
