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

public class ServerView extends JTextPane implements Observer{
	
	private ServerModel model;
	private StyledDocument document;

	public ServerView(ServerModel model){
		this.model = model;
		this.document = this.getStyledDocument();
	}
	
	public ServerModel getModel(){
		return this.model;
	}
	
	public StyledDocument getDoc(){
		return this.document;
	}
	
	public void setStyledDocument(StyledDocument document){
		this.document = document;
	}

	@Override
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
        
//        sc = StyleContext.getDefaultStyleContext();
//		aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
//		
//		len = getDoc().getLength();
		
//        try {
//			getDoc().insertString(len, getModel().getCurrentWord(), aset);
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
	}

}
