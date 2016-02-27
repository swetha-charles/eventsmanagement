package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server0 {
	public static void main(String[] args) {
		int portNumber;
		if(args.length == 0){
			portNumber = 7516;
		}else{
			portNumber = Integer.parseInt(args[0]);
		}
		
		
		try(
				ServerSocket serverListeningSocket = new ServerSocket(portNumber);
				
				Socket serverSocket = serverListeningSocket.accept();
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				
				){
			//System.out.println("Server: Setup successful");
			String inputNumber, outputNumber;
			
			Protocol p = new Protocol();
			//System.out.println("Server: how is out?"+out.checkError());
			//System.out.println("Server: how is in?" + in.ready());
			while((inputNumber = in.readLine()) != null){
				//System.out.println("Server: Within while loop and input was" + inputNumber);
				outputNumber = p.processInput();
				//System.out.println("Server: Protocol finished, output was" + outputNumber);
				out.println(outputNumber);
				
			}
			
		} catch (IOException e) {
			portNumber++;
			String[] newPort = {portNumber+""};
			System.out.println("Server is now listening to port: " + portNumber);
			Server0.main(newPort);
		}
		
	}
}
