package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server1 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		int portNumber = 7518;
		
		try(
				ServerSocket serverListeningSocket = new ServerSocket(portNumber);
				
				Socket serverSocket = serverListeningSocket.accept();
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				
				){
			//System.out.println("Server: Setup successful");
			String inputNumber, outputNumber;
			
			Protocol np = new Protocol();
			//System.out.println("Server: how is out?"+out.checkError());
			//System.out.println("Server: how is in?" + in.ready());
			while((inputNumber = in.readLine()) != null){
				//System.out.println("Server: Within while loop and input was" + inputNumber);
			//outputNumber = ""+np.processInput(Integer.parseInt(inputNumber));
				//System.out.println("Server: Protocol finished, output was" + outputNumber);
				//out.println(outputNumber);
				
			}
			
		}
		
		
		
	}
}
