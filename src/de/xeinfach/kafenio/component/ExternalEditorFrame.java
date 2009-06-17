package de.xeinfach.kafenio.component;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import de.xeinfach.kafenio.interfaces.KafenioContainerInterface;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: This class is an extended JFrame that overrides the processWindowEvent() method.
 * If the WindowEvent WINDOW_CLOSING is caught, the parentApplet's popEditorIn() method is called. 
 * @author Karsten Pawlik
 */
public class ExternalEditorFrame extends JFrame {

	private static LeanLogger log = new LeanLogger("ExternalEditorFrame.class");
	
	private KafenioContainerInterface parentApplet;
	
	/**
	 * creates a new ExternalEditorFrame
	 * @param frameTitle the window title
	 * @param parent the KafenioContainerInterface that instanciates this class.
	 */
	public ExternalEditorFrame(String frameTitle, KafenioContainerInterface parent) {
		super();
		this.setTitle(frameTitle);
		this.setParentApplet(parent);
		log.debug("new ExternalEditorFrame created.");
	}

	/**
	 * @return returns the parent applet that called this frame.
	 */
	public KafenioContainerInterface getParentApplet() {
		return parentApplet;
	}

	/**
	 * sets the parent KafenioContainerInterface that calls this frame.
	 * @param applet an applet
	 */
	public void setParentApplet(KafenioContainerInterface applet) {
		parentApplet = applet;
	}

	/* (non-Javadoc)
	 * @see java.awt.Window#processWindowEvent(java.awt.event.WindowEvent)
	 */
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			getParentApplet().detachFrame();
		}
		super.processWindowEvent(e);
	}

}
