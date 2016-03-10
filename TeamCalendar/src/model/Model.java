package model;

import java.util.Observable;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.Login;
import gui.Registration;
import server.ObjectClientController;

public class Model extends Observable {
	private ModelState currentstate;
	private ObjectClientController controller;

	private String username;
	private String email;

	private JScrollPane currentPanel = null;
	private Login login;
	private Registration registration;
	private JPanel meeting;

	private Pattern emailRegex = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	// from:
	// http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
	private boolean usernameExists = false;
	private boolean emailExists = false;
	private boolean emailMatchesRegex = true;

	public Model(ObjectClientController controller2) {
		this.controller = controller2;
		this.currentstate = ModelState.LOGIN;

		login = new Login(this.controller);
		currentPanel = new JScrollPane(login);

	}

	////////////////// Check if username OR email is duplicated in the
	////////////////// database///////////////////////////
	public void checkUsername(String username) {
		this.username = username;
		this.controller.checkUsername(username);
	}

	public void checkEmail(String email) {
		this.email = email;
		if (emailRegex.matcher(email).matches()) {
			this.emailMatchesRegex = true;
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			this.controller.checkEmail(email);
		} else {
			System.out.println("Email failed Regex");

			this.emailMatchesRegex = false;
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		}

	}

	public void checkRegistrationInformation(String firstname, String lastname, String dob, String password,
			String confirm) {
		String[] regInfo = { firstname, lastname, password, confirm };
		if (DataValidation.checkLessThanFifty(regInfo)) {

		} else {
			System.out.println("User input has failed data validation, atleast one entry is more than 50 characters");

		}

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////// Save information that comes back from the
	//////////////// server///////////////////////////////////

	public void setUsernameExists(boolean bool) {
		this.usernameExists = bool;
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);

	}

	public void setEmailExists(boolean bool) {
		this.emailExists = bool;
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);

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

	public ModelState getCurrentState() {
		return this.currentstate;

	}

	public boolean getEmailMatchesRegex() {
		return this.emailMatchesRegex;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public synchronized void changeCurrentState(ModelState state) {
		this.currentstate = state;

		switch (state) {
		case REGISTRATION:

			this.registration = new Registration(controller, this);

			setPanel(this.registration);
			break;

		case LOGIN:
			this.login = new Login(controller);
			setPanel(this.login);
			break;

		case REGISTRATIONUPDATE:
			if (!this.getEmailMatchesRegex()) {
				this.registration.getRegistrationPanel().setEmailLabel("Email incorrect format*");

			} else {
				if (this.getEmailExists()) {
					this.registration.getRegistrationPanel().setEmailLabel("Email already exists!*");
				} else {
					this.registration.getRegistrationPanel().setEmailLabel("Email*");
				}
			}
			if (this.getUsernameExists()) {
				this.registration.getRegistrationPanel().setUserLabel("Username already exists!*");
			} else {
				this.registration.getRegistrationPanel().setUserLabel("Username*");
			}

			setPanel(this.registration);
			break;
		case ERRORCONNECTIONDOWN:
			// create an error message??

			break;
		case EXIT:
			this.controller.exitGracefully();
			break;
		}

	}

	public JScrollPane getCurrentPanel() {
		return this.currentPanel;
	}

	public void setPanel(JPanel panel) {
		currentPanel = new JScrollPane(panel);
		setChanged();
		notifyObservers();
		/*
		 * frame.setContentPane(scroll); frame.validate();
		 */
	}

}
