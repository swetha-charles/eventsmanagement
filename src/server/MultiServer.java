package server;
import java.io.*;
import java.net.*;

public class MultiServer {
	public static void main(String[] args) {
		int portNumber1 = 5000;
		/*int portNumber2 = 10000;
		int portNumber3 = 20000;*/
		
		boolean listening = true;

		try (ServerSocket serverSocket1 = new ServerSocket(portNumber1)) {
			while (listening) {
				
				//.accept() creates a new socket!
				
				new MultiServerThread(serverSocket1.accept()).start();
			}
		} catch (IOException e) {
			
			System.out.println("Could not listen on port " + portNumber1);
			System.exit(-1);
			
		}
		
	}
}
