package de.xeinfach.kafenio;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.lang.reflect.Constructor;
import javax.swing.JPanel;
import javax.swing.JFrame;

import de.xeinfach.kafenio.interfaces.KafenioContainerInterface;
import de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface;
import de.xeinfach.kafenio.interfaces.KafenioPanelInterface;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Demo Implementation to run KafenioPanel as standalone application.
 * @author Karsten Pawlik
 */
public class Kafenio extends JFrame implements WindowListener, KafenioContainerInterface {
	
	private KafenioPanelConfigurationInterface config;
	private KafenioPanelInterface kafenioPanel;
	private File currentFile = (File)null;
	private SplashScreen splash;
	private static LeanLogger log = new LeanLogger("Kafenio.class");	
	/**
	 * creates a new Kafenio Editor Window object using the given parameters.
	 * @param conf the configuration for this object.
	 */
	public Kafenio(KafenioPanelConfigurationInterface iConfiguration) {
          super();
		showSplash();
		config = iConfiguration;
		config.setKafenioParent(this);

		try {
			//What follows is more complicated than necessary in order to demonstrate
			//plugin-like employment of Kafenio. Easy, non plugin way, would be: kafenioPanel = new KafenioPanel(config);
			Class kafenioPanelClass = Class.forName("de.xeinfach.kafenio.KafenioPanel");
			Class[] constructorParameterTyping = { KafenioPanelConfigurationInterface.class };
			Constructor kafenioPanelContructor = kafenioPanelClass.getConstructor( constructorParameterTyping );
			kafenioPanel  =(KafenioPanelInterface)kafenioPanelContructor.newInstance(new Object[]{ config });
		}
		catch (Exception ex) { // Probably class not found exception
			ex.printStackTrace();
			return;
		}

		/* Add the components to the app */
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add((JPanel)kafenioPanel, BorderLayout.CENTER);

		//Example of use - remove bevels around buttons in Java1.4.2:
		//  kafenioPanel.getJToolBar1().setRollover(true);
		//  kafenioPanel.getJToolBar2().setRollover(true);

		this.addWindowListener(this);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.updateTitle();
		this.pack();
		this.show();
		splash.destroy();
	}
	
	/**
	 * main method.
	 * @param args commandline arguments.
	 * <UL>
	 * <LI>-t | -T  = turn on/off toolbars</LI>
	 * <LI>-s | -S  = turn on/off sourceview on startup</LI>
	 * <LI>-m | -M  = turn on/off show menu icons</LI>
	 * <LI>-x | -X  = turn on/off exclusive edit mode</LI>
	 * <LI>-b | -B  = turn on/off base64 decoding of raw-document</LI>
	 * <LI>-8       = turn on unicode export of document text</LI>
	 * <LI>-f       = html document filepath to load</LI>
	 * <LI>-c       = css-file to load</LI>
	 * <LI>-r       = raw document filepath to load</LI>
	 * <LI>-u       = stylesheet URL</LI>
	 * <LI>-l       = language setting (i.e.: de_DE for german/germany)</LI>
	 * <LI>-d | -D  = turn on/off debug mode</LI>
	 * </UL>
	 */
	public static void main(String[] args) {
		KafenioPanelConfigurationInterface newConfig = null;
		try {
			newConfig = (KafenioPanelConfigurationInterface)
			Class.forName("de.xeinfach.kafenio.KafenioPanelConfiguration").newInstance();
		} catch (Exception ex) {}

		newConfig.setImageDir("file://");

		// Example of how to show only selected submenus and tool bar items:
		// boolean restrictMenus = false;
		// if (restrictMenus) {
		//   newConfig.setCustomMenuItems("edit view font format insert table forms search tools help");
		//   newConfig.setCustomToolBar1("save cut copy paste bold italic underline left center right justify");
		// }
             
		if (args.length > 0) {
			
			for(int i = 0; i < args.length; i++) {
				if (args[i].equals("-t")) { 
					newConfig.setShowToolbar(true);
					newConfig.setShowToolbar2(true); 
				} else if(args[i].equals("-T")) {
					newConfig.setShowToolbar(false);
					newConfig.setShowToolbar2(false); 
				} else if(args[i].equals("-s")) { 
					newConfig.setShowViewSource(true); 
				} else if(args[i].equals("-S")) {
					newConfig.setShowViewSource(false);
				} else if(args[i].equals("-m")) {
					newConfig.setShowMenuIcons(true); 
				} else if(args[i].equals("-M")) {
					newConfig.setShowMenuIcons(false); 
				} else if(args[i].equals("-b")) { 
					newConfig.setBase64(true);
				} else if(args[i].equals("-B")) { 
					newConfig.setBase64(false);
				} else if(args[i].equals("-8")) { 
					newConfig.setUnicode(true);
				} else if(args[i].startsWith("-f")) { 
					newConfig.setDocument(args[i].substring(2, args[i].length()));
				} else if(args[i].startsWith("-c")) { 
					newConfig.setStyleSheet(args[i].substring(2, args[i].length()));
				} else if(args[i].startsWith("-r")) { 
					newConfig.setRawDocument(args[i].substring(2, args[i].length()));
				} else if(args[i].startsWith("-u")) {
					try {
						newConfig.setUrlStyleSheet(new URL(args[i].substring(2, args[i].length())));
					} catch(Throwable e) {
						log.warn("Exception caught while trying to setURLStylesheet: " + e.fillInStackTrace());
					}
				} else if(args[i].startsWith("-l")) {
					if(args[i].indexOf('_') > -1) {
						newConfig.setLanguage(args[i].substring(2, args[i].indexOf('_')));
						newConfig.setCountry(args[i].substring(args[i].indexOf('_') + 1, args[i].length()));
					}
				} else if(args[i].equals("-d")) { 
					newConfig.setDebugMode(true); 
				} else if(args[i].equals("-D")) { 
					newConfig.setDebugMode(false); 
				}
			}
		}
		new Kafenio(newConfig);
	}

	/** 
	 * Convenience method for updating the application title bar
	 */
	private void updateTitle() {
		this.setTitle(kafenioPanel.getAppName() + (currentFile == null ? "" : " - " + currentFile.getName()));
	}
	
	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent e) {}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent e) {}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
		kafenioPanel.quitApp();
	}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent e) {}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent e) {}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent e) {}

	/** (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent e) {}

	/**
	 * not an applet - no need to use pop-out functionality. do nothing.
	 */
	public void detachFrame() {}

	private void showSplash() {
		splash = new SplashScreen(30);
		log.info("Info: Displaying splash image for max. 30 seconds.");
		new Thread(splash).start();
	}


}
