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
import gui.Login;
import gui.MenuPanel;
import gui.Password;
import gui.Profile;
import gui.Registration;
import jBCrypt.BCrypt;
import objectTransferrable.Event;
import objectTransferrable.OTCreateEvent;
import objectTransferrable.OTDeleteEvent;
import objectTransferrable.OTLogin;
import objectTransferrable.OTLoginSuccessful;
import objectTransferrable.OTRegistrationInformation;
import objectTransferrable.OTRequestMeetingsOnDay;
import objectTransferrable.OTUpdateEvent;
import objectTransferrable.OTUpdatePassword;
import objectTransferrable.OTUpdateUserProfile;

public class Model extends Observable {
	private ModelState currentstate;
	private Client client;

	private JScrollPane currentScrollPanel = null;
	private JPanel currentInnerPanel = null;
	private Login loginView = null;
	private Registration registrationView = null;
	private JPanel eventsView = null;
	private ErrorConnectionDown errorView;
	private List listView = null;
	private Profile profileView = null;
	private Edit editView = null;
	private Password passwordView = null;
	private MenuPanel menuPanel = null;
	private Calendar currentCalendarBorrowedFromListPanel;

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
	private String hashedPassword = null;

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
	private ArrayList<Event> meetings = new ArrayList<Event>();

	// --------------------Event editing-------------------//
	// --Data here has passed Validation-----//

	private String changedEventStartTimeHours;
	private String changedEventStartTimeMinutes;
	private String changedEventEndTimeHours;
	private String changedEventTimeMinutes;
	private java.util.Date changedDate;

	// ------------------Event create/update/delete--------------------//
	private boolean meetingCreationSuccessful = false;
	private boolean meetingUpdateSuccessful = false;
	private boolean meetingDeleteSuccessful = false;

	// -----------------------Profile update--------------------------//
	private boolean updateProfileSuccess = false;
	private boolean updatePasswordSuccess = false;
	private String intermediateHashedPwStorage;

	public Model(Client client) {
		this.client = client;
		this.currentstate = ModelState.LOGIN;

		loginView = new Login(this.client, this);
		currentScrollPanel = new JScrollPane(loginView);

	}

	/**
	 * This method is used to change the model's state. This then affects the
	 * view that the user sees.
	 * 
	 * @param state
	 */
	public synchronized void changeCurrentState(ModelState state) {
		this.currentstate = state;

		// !!--Dont forget breaks;--!!//
		switch (state) {

		case REGISTRATION:
			if (this.registrationView == null) {
				this.registrationView = new Registration(client, this);
			} else {
				this.registrationView.refresh();
			}
			setPanel(this.registrationView);
			break;

		case LOGIN:
			if (this.loginView == null) {
				this.loginView = new Login(client, this);
			} else {
				this.loginView.refresh();
			}
			setPanel(this.loginView);
			break;

		case LOGINUNSUCCESSFULWRONGUSERNAME:
			JOptionPane.showMessageDialog(this.getCurrentScrollPanel(), "No such user");
			break;

		case LOGINUNSUCCESSFULWRONGPASSWORD:
			JOptionPane.showMessageDialog(this.getCurrentScrollPanel(), "Password incorrect");
			break;

		case REGISTRATIONUPDATE:
			setPanel(this.registrationView);
			break;

		case ERRORCONNECTIONDOWN:
			if (this.errorView == null) {
				this.errorView = new ErrorConnectionDown(this);
			}
			setPanel(this.errorView);
			break;

		case ERRORCONNECTIONDOWNSTILL:
			this.errorView.connectionStillDown();
			setPanel(this.errorView);
			break;
		
		case ERRORSERVERDOWN:
			this.errorView.serverDown();
			setPanel(this.errorView);
			break; 
			
			
		case PROMPTRELOAD:
			setPanel(this.errorView);
			break;

		case EXIT:
			// by this time, the windows is closed.
			this.client.exitGracefully();
			break;

		case EVENTS:
//			System.out.println("State Change: Events");
			if (this.listView == null) {
				// first time the MenuPanel is made
				this.listView = new List(client, this);
				// set menuPanel for the only time
				this.menuPanel = this.listView.getMenuPanel();
			} else {
				this.listView.refresh();
			}
			this.setPanel(listView);
			break;

		case EVENTSUPDATE:
			this.listView.refresh();
			setPanel(this.listView);
			break;

		case PROFILE:
			if (this.profileView == null) {
				this.profileView = new Profile(client, this, this.menuPanel);
			} else {
				this.profileView.refresh();
			}
			this.setPanel(profileView);
			break;

		case EDIT:
			if (this.editView == null) {
				this.editView = new Edit(client, this, this.menuPanel);
			} else {
				this.editView.refresh();
			}
			this.setPanel(editView);
			break;

		case PASSWORD:
			if (this.passwordView == null) {
				this.passwordView = new Password(client, this, this.menuPanel);
			} else {
				this.passwordView.refresh();
			}
			this.setPanel(passwordView);
			break;
		}

	}

	public void setPanel(JPanel panel) {
		setCurrentInnerPanel(panel);
		setCurrentScrollPanel(new JScrollPane(panel));
		setChanged();
		notifyObservers();

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
	public void checkEmailReg(String email) {
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

	public boolean checkEmail(String email) {
		if (emailRegex.matcher(email).matches()) {
			return true;
		} else {
			return false;
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
	public boolean checkConfirmMatchesPassword(char[] firstEntry, char[] secondEntry) {
		String confirmPassword = new String(firstEntry);
		String firstPassword = new String(secondEntry);
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
			JOptionPane.showMessageDialog(this.getCurrentScrollPanel(), "Invalid date");
		}

	}

	/**
	 * This method is used to validate password. Note the user's password is
	 * never stored as plain text. A hashed version is stored.
	 * 
	 * @param password
	 */
	public void validatePasswordReg(char[] password) {
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
			String hashedPassword = BCrypt.hashpw(new String(password), BCrypt.gensalt());
			this.hashedPassword = hashedPassword;
			this.registrationView.getRegistrationPanel().setPasswordLabel("Password*");
		}
		this.changeCurrentState(ModelState.REGISTRATIONUPDATE);
	}

	public boolean validatePassword(char[] password) {
		if (password.length <= 7) {
			return false;

		} else if (password.length > 60) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * This method used to check whether user's data has been validated
	 * 
	 * @return
	 */
	private boolean registrationDataValidated() {
		try {
			return (this.username20orLess && this.emailMatchesRegex && this.emailUnique && this.firstNameLessThan30
					&& this.lastNameNameLessThan30 && this.passwordMatchesConfirm && this.password60orLess
					&& this.passwordatleast8 && this.oldEnough);
		} catch (NullPointerException e) {
			return false;
		}

	}

	/**
	 * This method sends of registration information to the server.
	 */
	public void checkRegistrationInformation() {
		if (this.usernameUnique && this.emailUnique && registrationDataValidated()) {
			OTRegistrationInformation otri = new OTRegistrationInformation(this.username, this.email, this.firstName,
					this.lastname, this.hashedPassword);
			this.client.checkRegistration(otri);

		} else {
			JOptionPane.showMessageDialog(this.getCurrentScrollPanel(), "Data is incorrect, check warnings");
		}

	}
	// -----------------Events --------------//

	public void addEvents(Event event) {
		OTCreateEvent newEvent = new OTCreateEvent(event);
		this.client.createEvent(newEvent);
	}

	public void updateEvent(Event oldEvent, Event newEvent) {
		OTUpdateEvent changeEvent = new OTUpdateEvent(oldEvent, newEvent);
		this.client.updateEvent(changeEvent);
	}

	public void deleteEvent(Event event) {
		OTDeleteEvent deleteEvent = new OTDeleteEvent(event);
		this.client.deleteEvent(deleteEvent);
	}

	public void updateMeetings(Date date) {
//		System.out.println("Model asked to update meetings");
		OTRequestMeetingsOnDay meetingsRequest = new OTRequestMeetingsOnDay(date);
		this.client.getMeetingsForDay(meetingsRequest);
	}

	public void updateMeetings() {
//		System.out.println("Model asked to update meetings");
		OTRequestMeetingsOnDay meetingsRequest = new OTRequestMeetingsOnDay(
				new Date(this.getCalendar().getTimeInMillis()));
		this.client.getMeetingsForDay(meetingsRequest);
	}

	public void promptUserToRestart() {
		this.errorView.addRestartButton();
		this.changeCurrentState(ModelState.PROMPTRELOAD);
	}

	public void userRequestedRestart() {
		this.changeCurrentState(ModelState.PROMPTRELOAD);
		this.client.restart();
	}

	public boolean validateChangedStartTime(String hours, String minutes) {
		if (DataValidation.isThisTimeValid(hours, minutes)) {
			this.changedEventStartTimeHours = hours;
			this.changedEventStartTimeMinutes = minutes;
			return true;
		} else {
			return false;
		}
	}

	// left in this format, incase we want to store the event's details.
	public boolean validateNewStartTime(String hours, String minutes) {
		if (DataValidation.isThisTimeValid(hours, minutes)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateChangedEndTime(String hours, String minutes) {
		if (DataValidation.isThisTimeValid(hours, minutes)) {
			this.changedEventEndTimeHours = hours;
			this.changedEventTimeMinutes = minutes;
			return true;
		} else {
			return false;
		}
	}

	// left in this format, incase we want to store the event's details.
	public boolean validateNewEndTime(String hours, String minutes) {
		if (DataValidation.isThisTimeValid(hours, minutes)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateChangedDate(String day, String month, String year) {
		if (DataValidation.isThisDateValid(day, month, year)) {
			java.util.Date sanitizedDate = (java.util.Date) DataValidation.sanitizeDate(day, month, year);
			this.changedDate = sanitizedDate;
			return true;
		}
		return false;
	}

	// left in this format, incase we want to store the event's details.
	public boolean validateNewDate(String day, String month, String year) {
		if (DataValidation.isThisDateValid(day, month, year)) {
			return true;
		}
		return false;
	}

	/**
	 * returns null if date is not valid
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public Date sanitizeDateAndMakeSQLDate(String day, String month, String year) {
		java.util.Date utilDate = DataValidation.sanitizeDate(day, month, year);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	// ------------------Profile editing------------//
	public void updateProfile(String firstName, String lastName, String email) {
		OTUpdateUserProfile updatedUserInfo = new OTUpdateUserProfile(firstName, lastName, email);
		this.client.updateProfile(updatedUserInfo);

	}

	public void updatePassword(String newPassword) {
		String pwHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		setIntermediatePwStorage(pwHash);
		OTUpdatePassword updatePassword = new OTUpdatePassword(pwHash);
		client.updatePassword(updatePassword);

	}

	// ------------Login----------------------------//
	public void login(String username, char[] password) {
		OTLogin loginObject = new OTLogin(username);
		setUsername(username);
		this.client.checkLoginDetails(loginObject);// this will set model's
													// hashed password
		OTLoginSuccessful returnObject;
		if (this.hashedPassword == null) {
			this.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGUSERNAME);
			return;
		} else if (checkPassword(new String(password))) {

			returnObject = new OTLoginSuccessful(this.username);
			this.client.informServerLoginSuccess(returnObject);
		} else if (!checkPassword(new String(password))) {
			this.changeCurrentState(ModelState.LOGINUNSUCCESSFULWRONGPASSWORD);
		}

	}

	// ----------Password checking--------------//
	public boolean checkPassword(String password) {
		return BCrypt.checkpw(password, this.hashedPassword);
	}

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

	public void setHashedPassword(String hash) {
		this.hashedPassword = hash;

	}
	// --------Getters and setter------------//

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
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public boolean isSuccessfulRegistration() {
		return successfulRegistration;
	}

	public void setSuccessfulRegistration(boolean successfulRegistration) {
		this.successfulRegistration = successfulRegistration;
	}

	public ArrayList<Event> getMeetings() {
		return meetings;
	}

	public void setMeetings(ArrayList<Event> meetings) {
		this.meetings = meetings;
//		System.out.println("Model's meetings set");
	}

	public boolean getMeetingCreationSuccessful() {
		return meetingCreationSuccessful;
	}

	public void setMeetingCreationSuccessful(boolean meetingCreationSuccessful) {
		this.meetingCreationSuccessful = meetingCreationSuccessful;
	}

	public boolean getMeetingUpdateSuccessful() {
		return meetingUpdateSuccessful;
	}

	public void setMeetingUpdateSuccessful(boolean meetingUpdateSuccessful) {
		this.meetingUpdateSuccessful = meetingUpdateSuccessful;
	}

	public boolean getMeetingDeleteSuccessful() {
		return meetingDeleteSuccessful;
	}

	public void setMeetingDeleteSuccessful(boolean meetingDeleteSuccessful) {
		this.meetingDeleteSuccessful = meetingDeleteSuccessful;
		this.changeCurrentState(ModelState.EVENTSUPDATE);
	}

	public boolean getUpdateProfileSuccess() {
		return updateProfileSuccess;
	}

	public void setUpdateProfileSuccess(boolean updateProfileSuccess) {
		this.updateProfileSuccess = updateProfileSuccess;

	}

	public boolean getUpdatePasswordSuccess() {
		return updatePasswordSuccess;
	}

	public void setUpdatePasswordSuccess(boolean updatePasswordSuccess) {
		this.updatePasswordSuccess = updatePasswordSuccess;
		JOptionPane.showMessageDialog(this.currentScrollPanel, "Password successfully changed!");
		this.changeCurrentState(ModelState.PROFILE);
	}

	public void setIntermediatePwStorage(String intermediatePwStorage) {
		this.intermediateHashedPwStorage = intermediatePwStorage;
	}

	public void setCalendar(Calendar calendar) {
		this.currentCalendarBorrowedFromListPanel = calendar;
	}

	public Calendar getCalendar() {
		return this.currentCalendarBorrowedFromListPanel;
	}

	public void setCurrentScrollPanel(JScrollPane jsp) {
		this.currentScrollPanel = jsp;
	}

	public JScrollPane getCurrentScrollPanel() {
		return this.currentScrollPanel;
	}

	public void setCurrentInnerPanel(JPanel panel) {
		this.currentInnerPanel = panel;
	}

	public JPanel getCurrentInnerPanel() {
		return this.currentInnerPanel;
	}

}
