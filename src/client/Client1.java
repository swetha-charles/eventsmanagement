package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client1 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		try(
				Socket clientSocket = new Socket("127.0.0.1", 7518);
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				Scanner fromUser = new Scanner(System.in);
				){ 
			System.out.println("Client: Type in a number, or any letter to exit");
			String numberFromUser, numberFromServer;
			while(fromUser.hasNextInt()){
				//System.out.println("Client: fromUser.hasNextInt()?" + fromUser.hasNextInt());
				numberFromUser = fromUser.nextInt()+"";
				out.println(numberFromUser);
				//System.out.println("Client: Sent number to server:" + numberFromUser);
				
				numberFromServer = in.readLine();
				//System.out.println(numberFromServer);
				
				System.out.println("Client: double your number is " + numberFromServer);
				System.out.println("");
				System.out.println("Client: Type in a number, or any letter to exit");
			}
			
			System.out.println("Client: Goodbye");
		}
	}
}
