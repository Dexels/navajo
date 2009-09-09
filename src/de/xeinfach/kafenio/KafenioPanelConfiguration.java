package de.xeinfach.kafenio;

import java.awt.Color;
import java.net.URL;
import java.util.Properties;
import java.util.Vector;

import de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: contains the KafenioPanel configuration, encapsulates the configuration of the
 * editor component.
 *  
 * @author Karsten Pawlik
 */
public class KafenioPanelConfiguration implements KafenioPanelConfigurationInterface {
	
	private static LeanLogger log = new LeanLogger("KafenioPanelConfiguration.class");

	public static final int STANDALONE_MODE = 0;
	public static final int APPLET_MODE = 1;
	public static final int JWS_MODE = 2;

	private static String defaultToolbar1Items =
		"NEW,OPEN,SAVE,SEPARATOR,CUT,COPY,PASTE,SEPARATOR,BOLD"
		+ ",ITALIC,UNDERLINE,SEPARATOR,LEFT,CENTER,RIGHT,JUSTIFY"
		+ ",SEPARATOR,STYLESELECT";

	private static String defaultToolbar2Items =	
		"ULIST,OLIST,SEPARATOR,DEINDENT,INDENT,SEPARATOR,ANCHOR"
		+ ",SEPARATOR,IMAGE,SEPARATOR,CLEARFORMATS,SEPARATOR" 
		+ ",VIEWSOURCE,SEPARATOR,STRIKE,SUPERSCRIPT,SUBSCRIPT" 
		+ ",INSERTCHARACTER,SEPARATOR,FIND,COLOR,TABLE,SEPARATOR";
	
	private Object kafenioParent = null;
	private String outputmode = null;
	private String contentParameter = null;
	private String postUrl = null;
	private String servletMode = null;
	private String servletUrl = null;
	private boolean unicode = false;
	private String treePilotSystemID = null;
	private String imageDir = null;
	private String fileDir = null;
	private boolean showMenuBar = true;
	private boolean showToolbar2 = true;
	private boolean showToolbar = true;
	private String document = null; 
	private String styleSheet = null; 
	private String rawDocument = null; 
	private URL urlStyleSheet = null; 
	private boolean showViewSource = false; 
	private boolean showMenuIcons = true;
	private String language = null; 
	private String country = null;
	private boolean base64 = false;
	private boolean debugMode = false; 
	private String[] styleSheetFileList = null; 
	private String codeBase = null;
	private boolean applet = false; 
	private Color bgcolor = null;
	private Vector customMenuItems = null;
	private Vector customToolBar1;
	private Vector customToolBar2;
	private int mode = 0;
	private Properties properties = new Properties();



	/**
	 * default constructor.creates a new instance of this class.
	 * the default toolbars are loaded. (as proposed by yangyu)
	 */
	public KafenioPanelConfiguration() {
		customToolBar1 = parseToolbarItems(defaultToolbar1Items);
		customToolBar2 = parseToolbarItems(defaultToolbar2Items);
	}
	
	/**
	 * @return returns true if Kafenio should be run in applet mode, false otherwise.
	 */
	public boolean isApplet() {
		return (mode == APPLET_MODE);
	}

    /**
     * @return returns true if Kafenio should be run in web start mode, false otherwise.
     */
	public boolean isWebStart(){
		return (mode == JWS_MODE);
	}

	/**
	 * @return returns true if Kafenio should be run in standalones mode, false otherwise.
	 */
	public boolean isStandalone(){
		return (mode == STANDALONE_MODE);
	}

	/**
	 * @return returns true if document is encoded in base64, false otherwise
	 */
	public boolean isBase64() {
		return base64;
	}

	/**
	 * @return returns the background color to be set. if not set, the default system value
	 * is assumed.
	 */
	public Color getBgcolor() {
		return bgcolor;
	}

	/**
	 * @return returns true if applet should be run in debug mode, false otherwise.
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * @return returns the applet's codebase (only applicable for isApplet() == true
	 */
	public String getCodeBase() {
		return codeBase;
	}

	/**
	 * @return returns the currently set countrycode. if not countrycode is set, the default
	 * locale is assumed.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return returns the currently set document.
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @return returns true if menu icons should be displayed, false otherwise.
	 */
	public boolean isShowMenuIcons() {
		return showMenuIcons;
	}

	/**
	 * @return returns true if html source view should be displayed on startup, false otherwise
	 */
	public boolean isShowViewSource() {
		return showViewSource;
	}

	/**
	 * @return returns the currently set language code
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return returns the currently set raw document.
	 */
	public String getRawDocument() {
		return rawDocument;
	}

	/**
	 * @return returns the currently set CSS Stylesheet used for this document.
	 */
	public String getStyleSheet() {
		return styleSheet;
	}

	/**
	 * @return returns the list of CSS Stylesheet files to be included.
	 */
	public String[] getStyleSheetFileList() {
		return styleSheetFileList;
	}

	/**
	 * @return returns the url to the CSS Stylesheet to use.
	 */
	public URL getUrlStyleSheet() {
		return urlStyleSheet;
	}

	/**
	 * sets the parameter that defines if Kafenio should be run as an applet.
	 * @param newMode application mode-ID as specified by constants
	 */
	public void setMode(int newMode) {
		mode = newMode;
	}

	/**
	 * @param b true if base64 encoding should be used when reading the document into the editor.
	 */
	public void setBase64(boolean b) {
		base64 = b;
	}

	/**
	 * @param color sets the applications background color.
	 */
	public void setBgcolor(Color color) {
		bgcolor = color;
	}

	/**
	 * @param color parses the string into a color object and then sets the applications background color.
	 */
	public void setBgcolor(String color) {
		bgcolor = parseBgColor(color);
	}

	/**
	 * @param b true if debug mode is enabled, false for debug mode disabled.
	 */
	public void setDebugMode(boolean b) {
		debugMode = b;
	}

	/**
	 * @param string sets the applets codebase to the given value.
	 */
	public void setCodeBase(String string) {
		codeBase = string;
	}

	/**
	 * @param string sets the countrycode to use. (i.e.: DE for germany or UK for united kingdom.
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param string sets the document text
	 */
	public void setDocument(String string) {
		document = string;
	}

	/**
	 * @param b true if menu icons should be displayed, false otherwise.
	 */
	public void setShowMenuIcons(boolean b) {
		showMenuIcons = b;
	}

	/**
	 * @param b true if sourceview should be displayed, false otherwise.
	 */
	public void setShowViewSource(boolean b) {
		showViewSource = b;
	}

	/**
	 * @param string sets the language code to use. (i.e.: de for germany or UK for united kingdom)
	 */
	public void setLanguage(String string) {
		language = string;
	}

	/**
	 * @param string sets the raw document text
	 */
	public void setRawDocument(String string) {
		rawDocument = string;
	}

	/**
	 * @param string sets the css stylesheet for the document.
	 */
	public void setStyleSheet(String string) {
		styleSheet = string;
	}

	/**
	 * @param strings sets the css stylesheet files to use.
	 */
	public void setStyleSheetFileList(String[] strings) {
		styleSheetFileList = strings;
	}

	/**
	 * @param url sets the url to the stylesheet to use.
	 */
	public void setUrlStyleSheet(URL url) {
		urlStyleSheet = url;
	}

	/**
 	 * @return returns the current mode of the application as int value.
 	 */
 	public int getMode() {
		return mode;
	}
	
	/**
	 * @return returns vector that contains the keys of the 
	 * menu items to be included in the menu bar.
	 */
	public Vector getCustomMenuItems() {
		return customMenuItems;
	}

	/**
	 * @param vector sets the menu items in a vector that contains the keys of the 
	 * menu items to be included in the menu bar.
	 */
	public void setCustomMenuItems(Vector vector) {
		customMenuItems = vector;
	}

	/**
	 * @param menuItems parses the top-level menu items to be shown from the given string.
	 */
	public void setCustomMenuItems(String menuItems) {
		if (menuItems != null) {
			customMenuItems = parseMenuItems(menuItems);
		}
	}

	/**
	 * @param b true if toolbar 1 is to be displayed.
	 */
	public void setShowToolbar(boolean b) {
		showToolbar = b;
	}

	/**
	 * @param b true if toolbar 1 is to be displayed.
	 */
	public void setShowToolbar2(boolean b) {
		showToolbar2 = b;
	}

	/**
	 * @param b true if menubar should be displayed.
	 */
	public void setShowMenuBar(boolean b) {
		showMenuBar = b;
	}

	/**
	 * @return returns true if menubar should be displayed.
	 */
	public boolean isShowMenuBar() {
		return showMenuBar;
	}

	/**
	 * @return returns true if toolbar 1 should be displayed.
	 */
	public boolean isShowToolbar() {
		return showToolbar;
	}

	/**
	 * @return returns true if toolbar 2 should be displayed.
	 */
	public boolean isShowToolbar2() {
		return showToolbar2;
	}

	/**
	 * @param string path to image folder
	 */
	public void setImageDir(String string) {
		imageDir = string;
	}

	/**
	 * @param string path to file folder
	 */
	public void setFileDir(String string) {
		fileDir = string;
	}

	/**
	 * @return returns the currently set file folder path
	 */
	public String getFileDir() {
		return fileDir;
	}

	/**
	 * @return returns the currently set image folder path
	 */
	public String getImageDir() {
		return imageDir;
	}

	/**
	 * @param string sets the treepilot system id.
	 */
	public void setTreePilotSystemID(String string) {
		treePilotSystemID = string;
	}

	/**
	 * @return returns the treepilot system id.
	 */
	public String getTreePilotSystemID() {
		return treePilotSystemID;
	}

	/**
	 * @param string sets the servlet URL that is called when fetching image- or file information
	 * for the insert anchor or insert image dialogs.
	 */
	public void setServletUrl(String string) {
		servletUrl = string;
	}

	/**
	 * @return returns the servlet URL.
	 */
	public String getServletUrl() {
		return servletUrl;
	}

	/**
	 * servlet mode can be either "java" or "cgi". default is "java".<BR>
	 * if the standard tagging-servlet is used, servlet mode should be "java".<BR>
	 * if you're using a php- or perl-script for file-input, the servlet mode should be "cgi".<BR>
	 * <BR>interface description for mode <b>cgi:</b><BR>
	 * the script can take the following parameters:<BR>
	 * <UL>
	 * <LI><B>GetImages</B> i.e.: .../lister.cgi?GetImages=true</LI>
	 * <LI><B>GetFiles:</B> i.e.: .../lister.cgi?GetFiles=true</LI>
	 * <LI><B>FileExtensions (optional)</B> i.e.: .../lister.cgi?GetFiles=true&FileExtensions=.gif:.jpg:.jpeg:.png</LI>
	 * </UL>
	 * the script must output a document of MIME-type text/plain.<BR>
	 * the first line of the output defines the http-path to the root directory of your images and files.
	 * each further line contains one relative file-path. 
	 * the path is relative to the root specified in the first line.<BR>
	 * <BR>i.e.:<BR>
	 * physical path to images root directory: /usr/local/httpd/htdocs/images<BR>
	 * http path to images root directory: http://www.mydomain.com/images<BR>
	 * files in the images root directory: car.gif<BR>
	 * files in the images root directory: holiday2002/me.gif<BR>
	 * files in the images root directory: holiday2002/myself.gif<BR>
	 * files in the images root directory: holiday2002/i.gif<BR>
	 * files in the images root directory: holiday2002/mygirlfriend.gif<BR>
	 * Sample output of the script:<BR>
	 * <tt>
	 * http://www.mydomain.com/images<BR>
	 * car.gif<BR>
	 * holiday2002/me.gif<BR>
	 * holiday2002/myself.gif<BR>
	 * holiday2002/i.gif<BR>
	 * holiday2002/mygirlfriend.gif<BR>
	 * </tt>
	 * @param newServletMode the current ServletMode (can be "cgi" or "java", case insensitive)
	 */
	public void setServletMode(String newServletMode) {
		if (newServletMode.equalsIgnoreCase("cgi") || newServletMode.equalsIgnoreCase("java")) {
			servletMode = newServletMode;
		} else if(newServletMode == null || "".equals(newServletMode) || "none".equalsIgnoreCase(newServletMode)) {
			servletMode = "cgi";
		} else {
			throw new IllegalArgumentException("Valid arguments: \"cgi\", \"java\", \"none\"");
		}
	}

	/**
	 * @param string set url to post content to for saving documents on the web.
	 */
	public void setPostUrl(String string) {
		postUrl = string;
	}
	/**
	 * @return returns the post url.
	 */
	public String getPostUrl() {
		return postUrl;
	}

	/**
	 * @return returns servlet mode as string.
	 */
	public String getServletMode() {
		return servletMode;
	}

	/**
	 * @param string sets the content parameter. (the document to edit as string value)
	 */
	public void setContentParameter(String string) {
		contentParameter = string;
	}

	/**
	 * @return returns the content as string.
	 */
	public String getContentParameter() {
		return contentParameter;
	}

	/**
	 * sets the outputmode of the editor.<BR>
	 * normal = output is standard plain-ascii html code
	 * off = no content is posted at all
	 * base64 = content is posted as base64 encoded ascii-string
	 * 
	 * @param string outputmode to set (can be "normal", "off" or "base64"
	 */
	public void setOutputmode(String string) {
		if ("normal".equalsIgnoreCase(string)) {
			outputmode = string.toUpperCase();
		} else if ("off".equalsIgnoreCase(string)) {
			outputmode = string.toUpperCase();
		} else if ("base64".equalsIgnoreCase(string)) {
			outputmode = string.toUpperCase();
		}
	}
	

	/**
	 * @return returns the outputmode as string.
	 */
	public String getOutputmode() {
		return outputmode;
	}

	/**
	 * @param parentApplet Parent of KafenioPanel
	 */
	public void setKafenioParent(Object parentApplet) {
		kafenioParent = parentApplet;
	}

	/**
	 * @return returns the kafenioParent Object.
	 */
	public Object getKafenioParent() {
		return kafenioParent;
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @return returns vector that contains all items as string for toolbar1
	 */
	public Vector getCustomToolBar1() {
		return customToolBar1;
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @return returns vector that contains all items as string for toolbar 2
	 */
	public Vector getCustomToolBar2() {
		return customToolBar2;
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param vector vector that contains all items as string for toolbar 1
	 */
	public void setCustomToolBar1(Vector vector) {
		customToolBar1 = vector;
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param toolbarItems string that contains all items as space separated list for toolbar 1
	 */
	public void setCustomToolBar1(String toolbarItems) {
		if (toolbarItems != null) {
			customToolBar1 = parseToolbarItems(toolbarItems);
		}
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param vector vector that contains all items as string for toolbar 2
	 */
	public void setCustomToolBar2(Vector vector) {
		customToolBar2 = vector;
	}

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param toolbarItems string that contains all items as space separated list for toolbar 2
	 */
	public void setCustomToolBar2(String toolbarItems) {
		if (toolbarItems != null) {
			customToolBar2 = parseToolbarItems(toolbarItems);
		}
	}

	/**
	 * @return returns a string representation of this object.
	 */
	public String toString() {
		StringBuffer out = new StringBuffer();
		out.append("codebase: "+getCodeBase());
		out.append("\ncontent parameter: "+getContentParameter());
		out.append("\ncountry: "+getCountry());
		out.append("\ncustom menuitems: "+getCustomMenuItems());
		out.append("\ntoolbar1: "+getCustomToolBar1());
		out.append("\ntoolbar2: "+getCustomToolBar2());
		out.append("\ndocument: "+getDocument());
		out.append("\nfiledir: "+getFileDir());
		out.append("\nimagedir: "+getImageDir());
		out.append("\nparent: "+getKafenioParent());
		out.append("\nlanguage: "+getLanguage());
		out.append("\noutputmode: "+getOutputmode());
		out.append("\npost url: "+getPostUrl());
		out.append("\nraw document: "+getRawDocument());
		out.append("\nservlet mode: "+getServletMode());
		out.append("\nservlet url: "+getServletUrl());
		out.append("\nstylesheet: "+getStyleSheet());
		out.append("\nstylesheet file list: "+getStyleSheetFileList());
		out.append("\ntreepilot sys id: "+getTreePilotSystemID());
		out.append("\nstylesheet url: "+getUrlStyleSheet());
		out.append("\nisApplet: "+isApplet());
		out.append("\nisBase64: "+isBase64());
		out.append("\nisDebugMode: "+isDebugMode());
		out.append("\nisShowMenuBar: "+isShowMenuBar());
		out.append("\nisShowMenuIcons: "+isShowMenuIcons());
		out.append("\nisShowToolbar: "+isShowToolbar());
		out.append("\nisShowToolbar2: "+isShowToolbar2());
		out.append("\nisShowViewSource: "+isShowViewSource());
		return out.toString();
	}
	
	/**
	 * method to parse a space separated string into a vector.
	 * @param aString the string to parse.
	 * @return returns a vector containing the parsed substrings.
	 */
	public Vector parseToolbarItems(String aString) {
		Vector tools = new Vector();
		String[] buttons = null;
		if (aString != null) {
			buttons = KafenioPanel.tokenize(aString);
			for (int i=0; i < buttons.length; i++) {
				tools.add(buttons[i].toLowerCase());
			}
		}
		if (tools.size() > 0) return tools;
		return null;
	}

	/**
	 * parses the given string into a vector with sorted key-order.
	 * @param menuString menu keys as string
	 * @return returns a vector containing the menu keys.
	 */
	private Vector parseMenuItems(String menuString) {
		Vector menuItemList = null;
		if (isShowMenuBar()) {
			menuItemList = new Vector();
			String[] menuItemsArray = null;
			if (menuString != null) {
				menuItemsArray = KafenioPanel.tokenize(menuString.toLowerCase());
				for (int i=0; i < menuItemsArray.length; i++) {
					if (KafenioMenuBar.getOrderedMenuKeys().contains(menuItemsArray[i])) {
						menuItemList.add(menuItemsArray[i]);
					}
				}
			}
		}
		return menuItemList;
	}

	/**
	 * @param bgColorString string that represents the color in RGB (i.e.: #FFFFFF for white.)
	 * @return returns a Color object containing the background color. if the background-parameter was not set,
	 * the system default (Color.gray) is returned.
	 */
	public Color parseBgColor(String bgColorString) {
		try {
			return Color.decode(bgColorString);
		} catch (Exception e) {
			log.error("bgcolor-parameter not specified. setting background color to system default.");
			return null;
		}
	}

	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface#setProperty(java.lang.String, java.lang.String)
	 */
	public void setProperty(String name, String value) {
	    properties.setProperty(name, value); 
	}
	
	/**
	 * @see de.xeinfach.kafenio.interfaces.KafenioPanelConfigurationInterface#getProperty(java.lang.String)
	 */
	public String getProperty(String name) {               
	    if(properties.getProperty(name) != null) {
	        return properties.getProperty(name);
	    } else {
	        return "";
	    }
	}

	/**
	 * @return Returns true if unicode support for exported text is turned on. default is false.
	 */
	public boolean isUnicode() {
		return unicode;
	}
	
	/**
	 * @param unicode if set to true, exported document text is in unicode format (no html-entities). default is false.
	 */
	public void setUnicode(boolean unicode) {
		this.unicode = unicode;
	}
}
