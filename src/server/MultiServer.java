package server;

import java.io.*;
import java.net.*;

public class MultiServer {
	public static void main(String[] args) {
		int portNumber1 = 5001;
		ServerSocket serverSocket1 = null;
		/*
		 * int portNumber2 = 10000; int portNumber3 = 20000;
		 */

		boolean listening = true;

		try {
			serverSocket1 = new ServerSocket(portNumber1);
			while (listening) {
				System.out.println("Server now listening on port:" + portNumber1);
				// .accept() creates a new socket!

				new MultiServerThread(serverSocket1.accept()).start();
			}
			
		} catch (IOException e) {

			System.out.println("Could not listen on port " + portNumber1);
			System.exit(-1);

		} finally {
			try {
				serverSocket1.close();
			} catch (IOException e) {
				System.out.println("Could not close socket listening on " + portNumber1);
				e.printStackTrace();
			}
		}

	}
}
