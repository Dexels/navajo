/*
GNU Lesser General Public License

EkitApplet - Java Swing HTML Editor & Viewer Applet
Copyright (C) 2000-2003 Howard Kistler
KafenioApplet - Java Swing HTML Editor & Viewer Applet based on Ekit
Copyright (C) 2003-2004 Karsten Pawlik

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.xeinfach.kafenio;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.xeinfach.kafenio.component.ExternalEditorFrame;
import de.xeinfach.kafenio.interfaces.KafenioContainerInterface;
import de.xeinfach.kafenio.interfaces.KafenioControllerSaveContentInterface;
import de.xeinfach.kafenio.util.HTMLTranslate;
import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.util.Utils;
/**
 * Description: A WYSIWYG Editor Applet that allows to create and edit HTML content 
 * and store it to a URL.<BR>
 * <BR>
 * the following values for the parameter "BUTTONS" are supported:<BR>
 * NEW, SEPARATOR, CUT, COPY, PASTE, BOLD, ITALIC, UNDERLINE, STRIKE, SUPER, SUB, ULIST, 
 * OLIST, CLEAR, ANCHOR, SOURCE, STYLES, DETACHFRAME<BR>
 * 
 * @author Karsten Pawlik
 */
public class KafenioApplet extends JApplet 
	implements KafenioControllerSaveContentInterface, KafenioContainerInterface 
{
	private static LeanLogger log = new LeanLogger("KafenioApplet.class");

	private KafenioToolBar kafenioToolBar1;
	private KafenioToolBar kafenioToolBar2;
	private JPanel toolbarPanel;
	private KafenioPanel kafenioPanel;
	private KafenioAppletObserver appletRegister;
	private JLabel jlblStatus;
	private Vector vcTools;
	private KafenioPanelConfiguration config;
	private ExternalEditorFrame externalEditorFrame;
	private SplashScreen splash;

	/**
	 * Constructs a new Kafenio Applet.
	 */
	public KafenioApplet() {
		getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
		appletRegister = KafenioAppletObserver.getInstance();
	}

	/**
	 * initializes the applet.
	 */
	public void init() {
		showSplash();
		getContentPane().removeAll();
		appletRegister.registerNewApplet(this);
		config = readConfig();
		if (config.isDebugMode()) LeanLogger.setCurrentLogLevel(LeanLogger.DEBUG);
		kafenioPanel = new KafenioPanel(config);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(config.getBgcolor());
		getContentPane().add(kafenioPanel, BorderLayout.CENTER);
		validate();
		splash.destroy();
	}

	/**
	 * reads and checks the parameter-input from the applet configuration
	 * and writes it to the global KafenioPanelConfiguration
	 */
	private synchronized KafenioPanelConfiguration readConfig() {
		KafenioPanelConfiguration newConfig = new KafenioPanelConfiguration();
		newConfig.setKafenioParent(this);
		newConfig.setMode(KafenioPanelConfiguration.APPLET_MODE);
		newConfig.setCodeBase(getCodeBase().toString());
		newConfig.setBgcolor(Utils.checkNullOrEmpty(getParameter("BGCOLOR")));
		newConfig.setRawDocument(Utils.checkNullOrEmpty(getParameter("DOCUMENT")));
		newConfig.setShowViewSource(checkBoolean(getParameter("SOURCEVIEW"))); 
		newConfig.setBase64(checkBoolean(getParameter("BASE64")));
		newConfig.setUnicode(checkBoolean(getParameter("UNICODE")));
		if (Utils.checkNullOrEmpty(getParameter("STYLESHEET")) != null) {
			newConfig.setStyleSheetFileList(KafenioPanel.tokenize(getParameter("STYLESHEET")));
		}
		newConfig.setShowToolbar(checkBoolean(getParameter("TOOLBAR")));
		newConfig.setShowToolbar2(checkBoolean(getParameter("TOOLBAR2")));
		newConfig.setShowMenuBar(checkBoolean(getParameter("MENUBAR")));
		newConfig.setLanguage(Utils.checkNullOrEmpty(getParameter("LANGCODE")));
		newConfig.setCountry(Utils.checkNullOrEmpty(getParameter("LANGCOUNTRY")));
		newConfig.setShowMenuIcons(checkBoolean(getParameter("MENUICONS")));
		newConfig.setImageDir(Utils.checkNullOrEmpty(getParameter("IMAGEDIR"), "")); 
		if (Utils.checkNullOrEmpty(getParameter("FILEDIR")) != null) {
			newConfig.setFileDir(Utils.checkNullOrEmpty(getParameter("FILEDIR"), ""));
		} else {
			newConfig.setFileDir(Utils.checkNullOrEmpty(getParameter("IMAGEDIR"), ""));
		}
		newConfig.setTreePilotSystemID(Utils.checkNullOrEmpty(getParameter("SYSTEMID"), ""));
		newConfig.setServletUrl(Utils.checkNullOrEmpty(getParameter("SERVLETURL"),""));
		newConfig.setServletMode(Utils.checkNullOrEmpty(getParameter("SERVLETMODE"), "")); 
		newConfig.setPostUrl(Utils.checkNullOrEmpty(getParameter("POSTCONTENTURL"), ""));
		newConfig.setContentParameter(Utils.checkNullOrEmpty(getParameter("CONTENTPARAMETER"), ""));
		newConfig.setOutputmode(Utils.checkNullOrEmpty(getParameter("OUTPUTMODE"), ""));
		newConfig.setDebugMode(checkBoolean(getParameter("DEBUG")));

		String toolbarItems = Utils.checkNullOrEmpty(getParameter("BUTTONS"));
		String toolbar2Items = Utils.checkNullOrEmpty(getParameter("BUTTONS2"));
		String menuItems = Utils.checkNullOrEmpty(getParameter("MENUITEMS"));
		newConfig.setCustomToolBar1(toolbarItems);
		newConfig.setCustomToolBar2(toolbar2Items);
		newConfig.setCustomMenuItems(menuItems);

		return newConfig;
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
		appletRegister.unregisterApplet(this);
		kafenioToolBar1 = null;
		kafenioToolBar2 = null;
		toolbarPanel = null;
		kafenioPanel = null;
		appletRegister = null;
		jlblStatus = null;
		vcTools = null;
		config = null;
		externalEditorFrame = null;
		System.gc();
	}

	/**
	 * @param condition a condition to check.
	 * @return returns true if the given String is "true" ignoring the case or false if not.
	 */
	public boolean checkBoolean(String condition) {
		return "true".equalsIgnoreCase(condition);
	}

	/** 
	 * Method for passing back the document text to the applet's container.
	 * This is the entire document, including the top-level HTML tags.<BR>
	 * <BR>
	 * This method is a bridge-function to the KafenioPanel's getDocumentText() method.
	 * @return returns the document text as string.
	 */
	public String getDocumentText()	{
		return kafenioPanel.getDocumentText();
	}

	/** 
	 * Method for passing back the document body to the applet's container.
	 * This is only the text contained within the BODY tags.
	 * @return returns the document body as string.
	 */
	public String getDocumentBody() {
		return kafenioPanel.getDocumentBody();
	}

	/** 
	 * Method for setting the document manually.
	 * Will need code in the web page to call this.
	 * @param text string to set the document text to.
	 */
	public void setDocumentText(String text) {
		kafenioPanel.setDocumentText(text);
	}

	/**
	 * Convenience method for getting the document body from dedi (http://www.der-dirigent.de)
	 * @return returns the document body (same as getDocumentBody())
	 */
	public String getContents() {
		return getDocumentBody();
	}
	
	/**
	 * the method calls the postContentBody method in KafenioPanel.
	 * it is part of the implementation of the KafenioControllerSaveContentInterface.
	 * @return returns true, if content post was successful, false otherwise.
	 */
	public boolean saveAppletContents() {
		return kafenioPanel.postContentBody();
	}
	

	/**
	 * the method calls the saveAllAppletContents method in KafenioAppletObserver.
	 * it is part of the implementation of the KafenioControllerSaveContentInterface.
	 */
	public void saveAllAppletsContent() {
		appletRegister.saveAllAppletContents();
	}
	
	/**
	 * method for popping the applet into a separate window and back.
	 */
	public void detachFrame() {
		if (externalEditorFrame == null) {
			setVisible(false);
			externalEditorFrame = new ExternalEditorFrame("Kafenio", this);
			externalEditorFrame.setBounds(0,0,getWidth(),getHeight());
			externalEditorFrame.setContentPane(getRootPane());
			externalEditorFrame.setVisible(true);
		} else {
			setRootPane(externalEditorFrame.getRootPane());
			externalEditorFrame.setVisible(true);
			externalEditorFrame.dispose();
			externalEditorFrame = null;
			validate();
			setVisible(true);
		}
	}
	
	private void showSplash() {
		splash = new SplashScreen(30);
		log.info("Info: Displaying splash image for max. 30 seconds.");
		new Thread(splash).start();
	}

	
}
