package server;

import java.net.*;
import java.sql.Connection;
import java.util.*;
import java.io.*;

public class ObjectServer {
	static DatabaseConnection dbinstance;
	static ServerSocket ss;

	public static void main(String[] args) {
		try {
			dbinstance = new DatabaseConnection();

			int portnumber = 5046;

			ss = new ServerSocket(portnumber);
			System.out.println("ObjectServer: Listening on port " + portnumber);
			ArrayList<Thread> clientThreads = new ArrayList<Thread>();

			while(true){
				Socket newConnection = ss.accept();
				System.out.println("Recieved a new client connection. Assigning port: " + newConnection.getPort());
				Thread newClientThread = new Thread(new ThreadForClient(newConnection, dbinstance.getConnection()));
				newClientThread.start();
				clientThreads.add(newClientThread);
				System.out.println("Currently " + clientThreads.size() + " thread(s) running.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				System.out.println("Closing");
			}
		}

	}
}
