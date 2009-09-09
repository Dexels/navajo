package de.xeinfach.kafenio;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

import de.xeinfach.kafenio.component.JButtonNoFocus;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: This class provides the possibility to call the save-content
 * method in all currently running KafenioApplets.
 * 
 * @author Karsten Pawlik
 */
public class KafenioControllerApplet extends JApplet implements ActionListener {

	private LeanLogger log = new LeanLogger("KafenioControllerApplet.class");
	private JButtonNoFocus jbtnSaveContent;
	private KafenioAppletObserver applets = null;

	/**
	 * constructs a new KafenioControllerApplet.
	 */
	public KafenioControllerApplet() {
		super();
		applets = KafenioAppletObserver.getInstance();
		log.info("controller applet created.");
	}

	/**
	 * @return returns an information string about this applet.
	 */
	public String getAppletInfo() {
		return "This is a Controller Applet for KafenioApplets";
	}

	/**
	 * method called during applet initialization.
	 */
	public void init() {
		log.debug("initializing controller applet...");
		URL imageURL = getClass().getResource("/ControllerSaveContentHK.gif");
		ImageIcon buttonIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(imageURL));
		jbtnSaveContent = new JButtonNoFocus(buttonIcon); 
		jbtnSaveContent.setActionCommand("savecontent");   
		jbtnSaveContent.addActionListener(this);
		jbtnSaveContent.setBackground(new Color(255,255,255));
		jbtnSaveContent.setBorderPainted(false);

		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().setBackground(new Color(255,255,255));
		this.getContentPane().add(jbtnSaveContent);
		log.debug("...done");
		validate();
	}

	/**
	 * is called while during applet startup.
	 * registers this applet in the global appletRegister.
	 */
	public void start() {
	}
	
	
	/**
	 * is called during applet shutdown.
	 * de-registers this applet from the global appletRegister.
	 */
	public void stop() {
	}
	
	
	/**
	 * is called during applet destruction.
	 * de-registers this applet from the global appletRegister.
	 */
	public void destroy() {
		jbtnSaveContent = null;
		applets = null;
		System.gc();
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if ("savecontent".equalsIgnoreCase(e.getActionCommand())) {
			applets.saveAllAppletContents();		
			log.debug("saved all applet contents.");
		}
	}
}
