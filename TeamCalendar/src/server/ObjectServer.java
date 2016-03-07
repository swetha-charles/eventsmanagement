package server;

import java.net.*;
import java.sql.Connection;
import java.util.*;
import java.io.*;

public class ObjectServer {

	public static void main(String[] args) {
		try {
			DatabaseConnection dbinstance = new DatabaseConnection();

			ServerSocket ss;

			ss = new ServerSocket(4444);

			ArrayList<Thread> clientThreads = new ArrayList<Thread>();

			while(true){
				Socket newConnection = ss.accept();
				Thread newClientThread = new Thread(new ThreadForClient(newConnection, dbinstance.getConnection()));
				newClientThread.start();
				clientThreads.add(newClientThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
