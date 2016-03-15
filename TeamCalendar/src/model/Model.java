package model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Observable;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.Client;
import gui.Edit;
import gui.ErrorConnectionDown;
import gui.List;
import gui.ListPanel;
import gui.Login;
import gui.Password;
import gui.Profile;
import gui.Registration;
import jBCrypt.BCrypt;
import objectTransferrable.Event;
import objectTransferrable.*;

public class Model extends Observable {
	private ModelState currentstate;
	private Client client;

	private JScrollPane currentPanel = null;
	private Login loginView;
	private Registration registrationView;
	private JPanel meeting;
	private ErrorConnectionDown error;
	private List listView;
	private Profile profileView;
	private Edit editView;
	private Password passwordView;

	// ----------- Regex's and other formatting information-------//
	// http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
	final Pattern emailRegex = Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	final DateFormat yearFormat = new SimpleDateFormat("yyyy");
	final DateFormat monthFormat = new SimpleDateFormat("MM");
	final DateFormat dayFormat = new SimpleDateFormat("dd");
	final DateFormat hourFormat = new SimpleDateFormat("HH");
	final DateFormat minuteFormat = new SimpleDateFormat("mm");
	// ------------Registration information----------------------//
	private String username;
	private String email;
	private String firstName;
	private String lastname;
	private String dob;
	private char[] password;
	private String passwordAsString;

	// --------------Boolean values for registration--------------//
	private boolean usernameUnique = false;
	private boolean username20orLess = false;
	private boolean emailUnique = false;
	private boolean emailMatchesRegex = false;
	private boolean firstNameLessThan30 = false;
	private boolean lastNameNameLessThan30 = false;
	private boolean passwordMatchesConfirm = false;
	private boolean password60orLess = false;
	private boolean passwordatleast8 = false;
	private boolean oldEnough = false;

	// ----------------------Login success-------------------------//

	private boolean successfulLogin = false;
	private boolean successfulRegistration = false;

	// ------------------Event view information--------------------//

	private String displayYear;
	private String displayMonth;
	private String displayDay;
	private ArrayList<Event> meetings;

	public Model(Client client) {
		this.client = client;
		this.currentstate = ModelState.LOGIN;

		loginView = new Login(this.client, this);
		currentPanel = new JScrollPane(loginView);

	}

	// -------------------------Registration Methods---------------//

	// Checks if username is duplicated in the database
	public void checkUsername(String username) {
		this.username = username;
		if (DataValidation.checkLessThanTwenty(username)) {
			this.username20orLess = true;
			this.registrationView.getRegistrationPanel().setUserLabel("User*");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			this.client.checkUsername(username);
		} else {
			this.username20orLess = false;
			this.registrationView.getRegistrationPanel().setUserLabel("User* : too long");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		}

	}

	// Checks if email matches regex, then check if email is in in the database
	public void checkEmail(String email) {
		this.email = email;
		if (emailRegex.matcher(email).matches()) {
			this.emailMatchesRegex = true;
			this.registrationView.getRegistrationPanel().setEmailLabel("Email*");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			this.client.checkEmail(email);
		} else {
			this.emailMatchesRegex = false;
			this.registrationView.getRegistrationPanel().setEmailLabel("Email*: incorrect format*");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		}

	}

	// checks whether firstname <= 30 char
	public void validateFirstName(String name) {
		this.firstName = name;
		if (DataValidation.checkLessThanThirty(name)) {
			this.firstNameLessThan30 = true;
			this.registrationView.getRegistrationPanel().setFirstLabel("First Name");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		} else {
			this.firstNameLessThan30 = false;
			this.registrationView.getRegistrationPanel().setFirstLabel("First Name*: first name too long");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		}
	}

	// checks whether lastname <= 30 char
	public void validateLastName(String name) {
		this.lastname = name;
		if (DataValidation.checkLessThanThirty(name)) {
			this.lastNameNameLessThan30 = true;
			this.registrationView.getRegistrationPanel().setFirstLabel("Last Name");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
		} else {
			this.lastNameNameLessThan30 = false;
			this.registrationView.getRegistrationPanel().setLastLabel("Last Name*: incorrect format");
			this.changeCurrentState(ModelState.REGISTRATIONUPDATE);

		}

	}

	// checks confirm matches password field
	public boolean checkConfirmMatchesPassword(char[] confirm) {
		String confirmPassword = new String(confirm);
		String firstPassword = new String(password);
		if (confirmPassword.equals(firstPassword)) {
			this.passwordMatchesConfirm = true;
			return true;
		} else {
			this.passwordMatchesConfirm = false;
			return false;
		}

	}

	// checks if date is valid and then if so, check >= 18. If it passes both
	// tests, set oldEnough to true.
	public void validateDOB(String dob) {

		if (DataValidation.isThisDateValid(dob)) {

			int day = Integer.parseInt(dob.substring(0, 2));
			int month = Integer.parseInt(dob.substring(3, 5));
			int year = Integer.parseInt(dob.substring(6));

			LocalDate birthdate = LocalDate.of(year, month, day);
			LocalDate now = LocalDate.now();
			Period period = Period.between(birthdate, now);

			if (period.getYears() >= 18) {
				this.oldEnough = true;
				this.registrationView.getRegistrationPanel().setDobLabel("Date of Birth* dd/mm/yyyy");
				this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			} else {
				this.oldEnough = false;
				this.registrationView.getRegistrationPanel().setDobLabel("DOB*: Must be 18 or over");
				this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
			}
		} else {
			JOptionPane.showMessageDialog(this.getCurrentPanel(), "Invalid date");
		}

	}

	public void validatePassword(char[] password) {
		this.password = password;
		if (password.length <= 7) {
			this.passwordatleast8 = false;
			this.registrationView.getRegistrationPanel()
					.setPasswordLabel("Password*: must be between 8 and 60 characters");
			
		} else if (password.length > 60) {
			this.password60orLess = false;
			this.registrationView.getRegistrationPanel().setPasswordLabel("Password*: must be less than 60 characters");
		} else {
			this.password60orLess = true;
			this.passwordatleast8 = true;
			this.registrationView.getRegistrationPanel().setPasswordLabel("Password*");
		}
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
	}
	
	private boolean registrationDataValidated() {
		try{
			return (this.username20orLess && this.emailMatchesRegex && this.emailUnique && this.firstNameLessThan30
					&& this.lastNameNameLessThan30 && this.passwordMatchesConfirm && this.password60orLess
					&& this.passwordatleast8 && this.oldEnough);
		} catch (NullPointerException e){
			return false;
		}
	
	}

	public void checkRegistrationInformation() {
		if (this.usernameUnique && this.emailUnique && registrationDataValidated()) {
			String passwordAsString = new String(password);
			String hashedPassword = BCrypt.hashpw(passwordAsString, BCrypt.gensalt());
			OTRegistrationInformation otri = new OTRegistrationInformation(this.username, this.email, this.firstName,
					this.lastname, hashedPassword);
			this.client.checkRegistration(otri);

		} else {
			JOptionPane.showMessageDialog(this.getCurrentPanel(), "Data is incorrect, check warnings");
		}

	}

	

	// --------------------Registration Ends -----------------------------//

	// --------------------View Events methods/ List view---------------//

	// --------------------View Events methods/ List view Ends ---------------//
	// --------------------- Prompt Reload
	// ------------------------------------//
	public void promptRestart() {
		if (this.error != null) {
			this.error.promptRestart();
		} else {
			this.error = new ErrorConnectionDown(this);
			this.error.promptRestart();
		}
		this.changeCurrentState(ModelState.PROMPTRELOAD);
	}
	// --------------- Prompt Reload Ends---------//
	// -----------------ButtonMethods--------------//

	public void login(String username, char[] password) {
		String passwordAsString = new String(password);
		OTLogin loginObject = new OTLogin(username);

		setUsername(username);
		setPasswordAsString(passwordAsString);

		this.client.checkLoginDetails(loginObject);
	}

	// When the user presses "home" (MenuPanel) or right after successful login
	// (LoginPanel), this method is called by the requisite listeners.
	public void showListViewForToday() {
		Calendar cal = Calendar.getInstance();
		this.displayYear = yearFormat.format(cal.getTime());
		this.displayMonth = monthFormat.format(cal.getTime());
		this.displayDay = dayFormat.format(cal.getTime());
		// this.client.getMeetings(this.username, cal);
	}

	// method for next day

	// method for previous day

	// method for add event

	// method for

	// method for next day

	// method for previous day

	// method for add event

	// method for

	// --------Save information that returns from server----//

	public void setUsernameExists(boolean usernameExists) {
		if (usernameExists) {
			this.usernameUnique = false;
			this.registrationView.getRegistrationPanel().setUserLabel("Username*: already exists!*");
		} else {
			this.usernameUnique = true;
			this.registrationView.getRegistrationPanel().setUserLabel("Username*");
		}
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);

	}

	public void setEmailExists(boolean emailExists) {
		if (emailExists) {
			this.emailUnique = false;
			this.registrationView.getRegistrationPanel().setEmailLabel("Email*: already exists!*");
		} else {
			this.emailUnique = true;
			this.registrationView.getRegistrationPanel().setEmailLabel("Email*");
		}
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);

	}

	// method to get meetings

	public void getMeetingsOnDay(String userName, Calendar dateRequest) {

	}

	// --------End of information from server------------//

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isEmailUnique() {
		return emailUnique;
	}

	public boolean isUsernameUnique() {
		return usernameUnique;
	}

	public boolean isEmailMatchesRegex() {
		return emailMatchesRegex;
	}

	public void setUsernameUnique(boolean usernameUnique) {
		this.usernameUnique = usernameUnique;
	}

	public ModelState getCurrentState() {
		return this.currentstate;

	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public boolean getSuccessfulLogin() {
		return successfulLogin;
	}

	public void setSuccessfulLogin(boolean successfulLogin) {
		this.successfulLogin = successfulLogin;

		if (successfulLogin) {
			this.listView = new List(this.client, this);
		}
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public boolean isSuccessfulRegistration() {
		return successfulRegistration;
	}

	public void setSuccessfulRegistration(boolean successfulRegistration) {
		this.successfulRegistration = successfulRegistration;
	}

	public void updateMeetings(Date date) {
		OTRequestMeetingsOnDay meetingsRequest = new OTRequestMeetingsOnDay(date);
		this.client.getMeetingsForDay(meetingsRequest);
	}

	public ArrayList<Event> getMeetings() {
		return meetings;
	}

	public String getPasswordAsString() {
		return passwordAsString;
	}

	public void setPasswordAsString(String passwordAsString) {
		this.passwordAsString = passwordAsString;
	}

	public void setMeetings(ArrayList<Event> meetings) {
		this.meetings = meetings;
	}

	public synchronized void changeCurrentState(ModelState state) {
		this.currentstate = state;

		// !!--Dont forget breaks;--!!//
		switch (state) {

		case REGISTRATION:
			this.registrationView = new Registration(client, this);
			setPanel(this.registrationView);
			break;

		case LOGIN:
			this.loginView = new Login(client, this);
			setPanel(this.loginView);
			break;

		case REGISTRATIONUPDATE:
			setPanel(this.registrationView);
			break;

		case ERRORCONNECTIONDOWN:
			this.error = new ErrorConnectionDown(this);
			setPanel(this.error);
			break;

		case PROMPTRELOAD:
			setPanel(this.error);
			break;

		case EXIT:
			this.client.exitGracefully();
			break;

		case EVENTS:
			this.listView = new List(client, this);
			this.setPanel(listView);
			// keep this in. Differentiates between List and ListUpdate for the
			// reader.
			// See class Client, method RunOT(), switch/case: 0013 for use.
			// listView is created at class Model, method setSuccesfulLogin()
			break;

		case EVENTSUPDATE:
			setPanel(this.listView);
			break;

		case PROFILE:
			this.profileView = new Profile(client, this);
			this.setPanel(profileView);
			break;

		case EDIT:
			this.editView = new Edit(client, this);
			this.setPanel(editView);
			break;

		case PASSWORD:
			this.passwordView = new Password(client, this);
			this.setPanel(passwordView);
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

	}

}
