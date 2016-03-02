package JDBCTesting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.UIManager;

/*
 * Created on Aug 6, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author udr
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Main {
    public static Properties settings = new Properties();

    public static void main(String[] args) {
	// Load properties
	try {
	    FileInputStream stream = new FileInputStream("test.properties");
	    settings.load(stream);
	}
	catch (FileNotFoundException e) {
	    System.err.println("Please create a .properties file");
	    System.exit(1);
	}
	catch (IOException e) {
	    System.err.println("Could not read the properties file");
	    System.exit(1);
	}
		
	try {
	    String ui = settings.getProperty("UserInterface");
	    String lookAndFeel;
	    if (ui.equalsIgnoreCase("Windows"))
		lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	    else if (ui.equalsIgnoreCase("Motif"))
		lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookandFeel";
	    else throw (new Exception());

	    UIManager.setLookAndFeel(lookAndFeel);
			
	} catch (Exception e) {
	    // Use the default
	}

	JFrame frame = new JFrame();
	frame.show();
    }
}

