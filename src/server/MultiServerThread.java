package server;

import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread{

	 private Socket socket = null;

	    public MultiServerThread(Socket socket) {
	        super("MultiServerThread");
	        this.socket = socket;
	    }
	
	    public void run() {

	        try (
	            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                    this.socket.getInputStream()));
	        ) {
	            String inputLine, outputLine;
	            Protocol p = new Protocol();
	            

	            while ((inputLine = in.readLine()) != null) {
	                outputLine = p.processInput(inputLine);
	                out.println(outputLine);
	                if (outputLine.equals("exit"))
	                    break;
	            }
	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	        	try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
}
