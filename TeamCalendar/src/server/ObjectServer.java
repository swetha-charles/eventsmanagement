package server;

import java.net.*;
import java.sql.Connection;
import java.util.*;
import java.io.*;

public class ObjectServer {

	public static void main(String[] args) {
		try {
			DatabaseConnection dbinstance = null;
			//= new DatabaseConnection(); TODO

			ServerSocket ss;

			ss = new ServerSocket(4444);

			ArrayList<Thread> clientThreads = new ArrayList<Thread>();

			while(true){
				Socket newConnection = ss.accept();
				Connection bait = null;
				Thread newClientThread = new Thread(new ThreadForClient(newConnection, bait)); 
						//dbinstance.getConnection())); TODO
				newClientThread.start();
				clientThreads.add(newClientThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
