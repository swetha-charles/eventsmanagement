package server;

import java.net.*;
import java.util.*;
import java.io.*;

public class ObjectServer {

	public static void main(String[] args) throws Exception {
		
		ServerSocket ss = new ServerSocket(4444);

		ArrayList<Thread> clientThreads = new ArrayList<Thread>();
		
		while(true){
			clientThreads.add(new Thread(new ThreadForClient(ss.accept())));
		}
	}
}
