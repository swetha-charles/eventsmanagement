package model;

import java.util.Observable;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.ObjectClientController;

public class Model extends Observable {
	private State currentstate;
	private ObjectClientController controller;

	private String username;
	private String email;
	
	private Pattern emailRegex = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");	
	//from: http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
	private boolean usernameExists = false;
	private boolean emailExists = false;
	private boolean emailMatchesRegex = true;

	public Model(ObjectClientController controller2) {
		this.controller = controller2;

		this.currentstate = State.LOGIN;
	}
	//////////////////Check if username OR email is duplicated in the database///////////////////////////
	public void checkUsername(String username) {
		this.username = username;
		this.controller.checkUsername(username);
	}

	public void checkEmail(String email) {
		this.email = email;
		if(emailRegex.matcher(email).matches()){
			this.emailMatchesRegex = true;
			this.changeCurrentState(State.REGISTRATIONUPDATE);
			this.controller.checkEmail(email);
		} else {
			System.out.println("Email failed Regex");
			
			this.emailMatchesRegex = false;
			this.changeCurrentState(State.REGISTRATIONUPDATE);
		}
		
	}
	
	public void checkRegistrationInformation(
			String firstname, String lastname,
			String dob, String password, String confirm){
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////Save information that comes back from the server/////////////////////////////////// 

	public void setUsernameExists(boolean bool) {
		this.usernameExists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}

	public void setEmailExists(boolean bool) {
		this.emailExists = bool;
		this.changeCurrentState(State.REGISTRATIONUPDATE);

	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean getEmailExists() {
		return emailExists;
	}

	public boolean getUsernameExists() {
		return usernameExists;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////

	public State getCurrentState() {
		return this.currentstate;

	}
	
	public boolean getEmailMatchesRegex(){
		return this.emailMatchesRegex;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void exit(){
		this.controller.setRunning(false);
		this.changeCurrentState(State.EXIT);
	}

	public synchronized void changeCurrentState(State state) {
		//System.out.println("Model: has how many observers?" + this.countObservers());
		this.currentstate = state;
		// System.out.println("Model: Model's state changed to " +
		// this.currentstate);
		this.setChanged();
		// System.out.println("Model: About to notify observers!");
		super.notifyObservers();
		// System.out.println("Model: Notified observers!");

	}
	
	public void setPanel(JFrame frame, JPanel panel){
		JScrollPane scroll = new JScrollPane(panel);
		frame.setContentPane(scroll);
		frame.validate();
	}
	

}
