package server;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
/**The view for the server (the text area on which logs are shown)
 * 
 * @author Mark
 *
 */
public class ServerView extends JTextPane implements Observer{
	
	private ServerModel model;
	private StyledDocument document;

	/**
	 * constructor for the view, takes in the model to get text from
	 * @param model
	 */
	public ServerView(ServerModel model){
		this.model = model;
		this.document = this.getStyledDocument();
	}
	/**
	 * getter for the model
	 * @return the model
	 */
	public ServerModel getModel(){
		return this.model;
	}
	/**
	 * getter for the styled document that determines the look and feel
	 * of the text
	 * @return teh stylyed document
	 */
	public StyledDocument getDoc(){
		return this.document;
	}
	/**
	 * setter for the styled document
	 */
	public void setStyledDocument(StyledDocument document){
		this.document = document;
	}

	@Override
	/**when the observable model updates, this method updates the text in the
	 * pane with the updated text
	 * @param o the observable objects
	 * @parm arg no arguments will be supplied to this update method
	 */
	public void update(Observable o, Object arg) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
		
		int len = getDoc().getLength();
        
        try {
			getDoc().remove(0, len);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
        
        try {
			getDoc().insertString(0, getModel().getText(), aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
        
	}

}
