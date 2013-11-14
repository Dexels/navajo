package de.xeinfach.kafenio.interfaces;

import java.awt.Color;
import java.net.URL;
import java.util.Vector;

/**
 * Description: contains the KafenioPanel configuration, encapsulates the configuration of the
 * editor component.
 *  
 * @author Karsten Pawlik
 */
public interface KafenioPanelConfigurationInterface {
	
	/**
	 * @return returns true if Kafenio should be run in applet mode, false otherwise.
	 */
	public boolean isApplet();

    /**
     * @return returns true if Kafenio should be run in web start mode, false otherwise.
     */
	public boolean isWebStart();

	/**
	 * @return returns true if Kafenio should be run in standalones mode, false otherwise.
	 */
	public boolean isStandalone();

	/**
	 * @return returns true if document is encoded in base64, false otherwise
	 */
	public boolean isBase64();

	/**
	 * @return returns true if document is exportet in unicode format, false otherwise
	 */
	public boolean isUnicode();

	/**
	 * @return returns the background color to be set. if not set, the default system value
	 * is assumed.
	 */
	public Color getBgcolor();

	/**
	 * @return returns true if applet should be run in debug mode, false otherwise.
	 */
	public boolean isDebugMode();

	/**
	 * @return returns the applet's codebase (only applicable for isApplet() == true
	 */
	public String getCodeBase();

	/**
	 * @return returns the currently set countrycode. if not countrycode is set, the default
	 * locale is assumed.
	 */
	public String getCountry();

	/**
	 * @return returns the currently set document.
	 */
	public String getDocument();

	/**
	 * @return returns true if menu icons should be displayed, false otherwise.
	 */
	public boolean isShowMenuIcons();

	/**
	 * @return returns true if html source view should be displayed on startup, false otherwise
	 */
	public boolean isShowViewSource();

	/**
	 * @return returns the currently set language code
	 */
	public String getLanguage();

	/**
	 * @return returns the currently set raw document.
	 */
	public String getRawDocument();

	/**
	 * @return returns the currently set CSS Stylesheet used for this document.
	 */
	public String getStyleSheet();

	/**
	 * @return returns the list of CSS Stylesheet files to be included.
	 */
	public String[] getStyleSheetFileList();

	/**
	 * @return returns the url to the CSS Stylesheet to use.
	 */
	public URL getUrlStyleSheet();

	/**
	 * sets the parameter that defines if Kafenio should be run as an applet.
	 * @param newMode application mode-ID as specified by constants
	 */
	public void setMode(int newMode);

	/**
	 * @param b true if base64 encoding should be used when reading the document into the editor.
	 */
	public void setBase64(boolean b);

	/**
	 * @param b true if export of document text is in unicode format, false otherwise. 
	 */
	public void setUnicode(boolean b);

	/**
	 * @param color sets the applications background color.
	 */
	public void setBgcolor(Color color);

	/**
	 * @param color parses the string into a color object and then sets the applications background color.
	 */
	public void setBgcolor(String color);

	/**
	 * @param b true if debug mode is enabled, false for debug mode disabled.
	 */
	public void setDebugMode(boolean b);

	/**
	 * @param string sets the applets codebase to the given value.
	 */
	public void setCodeBase(String string);

	/**
	 * @param string sets the countrycode to use. (i.e.: DE for germany or UK for united kingdom.
	 */
	public void setCountry(String string);

	/**
	 * @param string sets the document text
	 */
	public void setDocument(String string);

	/**
	 * @param b true if menu icons should be displayed, false otherwise.
	 */
	public void setShowMenuIcons(boolean b);

	/**
	 * @param b true if sourceview should be displayed, false otherwise.
	 */
	public void setShowViewSource(boolean b);

	/**
	 * @param string sets the language code to use. (i.e.: de for germany or UK for united kingdom)
	 */
	public void setLanguage(String string);

	/**
	 * @param string sets the raw document text
	 */
	public void setRawDocument(String string);

	/**
	 * @param string sets the css stylesheet for the document.
	 */
	public void setStyleSheet(String string);

	/**
	 * @param strings sets the css stylesheet files to use.
	 */
	public void setStyleSheetFileList(String[] strings);

	/**
	 * @param url sets the url to the stylesheet to use.
	 */
	public void setUrlStyleSheet(URL url);

	/**
 	 * @return returns the current mode of the application as int value.
 	 */
 	public int getMode();
	
	/**
	 * @return returns vector that contains the keys of the 
	 * menu items to be included in the menu bar.
	 */
	public Vector getCustomMenuItems();

	/**
	 * @param vector sets the menu items in a vector that contains the keys of the 
	 * menu items to be included in the menu bar.
	 */
	public void setCustomMenuItems(Vector vector);

	/**
	 * @param menuItems parses the top-level menu items to be shown from the given string.
	 */
	public void setCustomMenuItems(String menuItems);

	/**
	 * @param b true if toolbar 1 is to be displayed.
	 */
	public void setShowToolbar(boolean b);

	/**
	 * @param b true if toolbar 1 is to be displayed.
	 */
	public void setShowToolbar2(boolean b);

	/**
	 * @param b true if menubar should be displayed.
	 */
	public void setShowMenuBar(boolean b);

	/**
	 * @return returns true if menubar should be displayed.
	 */
	public boolean isShowMenuBar();

	/**
	 * @return returns true if toolbar 1 should be displayed.
	 */
	public boolean isShowToolbar();

	/**
	 * @return returns true if toolbar 2 should be displayed.
	 */
	public boolean isShowToolbar2();

	/**
	 * @param string path to image folder
	 */
	public void setImageDir(String string);

	/**
	 * @param string path to file folder
	 */
	public void setFileDir(String string);

	/**
	 * @return returns the currently set file folder path
	 */
	public String getFileDir();

	/**
	 * @return returns the currently set image folder path
	 */
	public String getImageDir();

	/**
	 * @param string sets the treepilot system id.
	 */
	public void setTreePilotSystemID(String string);

	/**
	 * @return returns the treepilot system id.
	 */
	public String getTreePilotSystemID();

	/**
	 * @param string sets the servlet URL that is called when fetching image- or file information
	 * for the insert anchor or insert image dialogs.
	 */
	public void setServletUrl(String string);

	/**
	 * @return returns the servlet URL.
	 */
	public String getServletUrl();

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
	public void setServletMode(String newServletMode);

	/**
	 * @param string set url to post content to for saving documents on the web.
	 */
	public void setPostUrl(String string);

	/**
	 * @return returns the post url.
	 */
	public String getPostUrl();

	/**
	 * @return returns servlet mode as string.
	 */
	public String getServletMode();

	/**
	 * @param string sets the content parameter. (the document to edit as string value)
	 */
	public void setContentParameter(String string);


	/**
	 * @return returns the content as string.
	 */
	public String getContentParameter();

	/**
	 * sets the outputmode of the editor.<BR>
	 * normal = output is standard plain-ascii html code
	 * off = no content is posted at all
	 * base64 = content is posted as base64 encoded ascii-string
	 * 
	 * @param string outputmode to set (can be "normal", "off" or "base64"
	 */
	public void setOutputmode(String string);


	/**
	 * @return returns the outputmode as string.
	 */
	public String getOutputmode();

	/**
	 * @param parentApplet Parent of KafenioPanel
	 */
	public void setKafenioParent(Object parentApplet);

	/**
	 * @return returns the kafenioParent Object.
	 */
	public Object getKafenioParent();

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @return returns vector that contains all items as string for toolbar1
	 */
	public Vector getCustomToolBar1();

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @return returns vector that contains all items as string for toolbar 2
	 */
	public Vector getCustomToolBar2();

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param vector vector that contains all items as string for toolbar 1
	 */
	public void setCustomToolBar1(Vector vector);

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param toolbarItems string that contains all items as space separated list for toolbar 1
	 */
	public void setCustomToolBar1(String toolbarItems);

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param vector vector that contains all items as string for toolbar 2
	 */
	public void setCustomToolBar2(Vector vector);

	/**
	 * keys for the items are the constants from KafenioToolBar class
	 * @param toolbarItems string that contains all items as space separated list for toolbar 2
	 */
	public void setCustomToolBar2(String toolbarItems);

	/**
	 * @return returns a string representation of this object.
	 */
	public String toString();
	
	/**
	 * method to parse a space separated string into a vector.
	 * @param aString the string to parse.
	 * @return returns a vector containing the parsed substrings.
	 */
	public Vector parseToolbarItems(String aString);

	/**
	 * @param bgColorString string that represents the color in RGB (i.e.: #FFFFFF for white.)
	 * @return returns a Color object containing the background color. if the background-parameter was not set,
	 * the system default (Color.gray) is returned.
	 */
	public Color parseBgColor(String bgColorString);

	/**
	 * sets or overwrites a property with the given information.
	 * @param name name of the property (id)
	 * @param value value of the property.
	 */
	public void setProperty(String name, String value); 
	
	/**
	 * returns the value of a property with the given name
	 * @param name name of the property.
	 * @return returns the value of the property.
	 */
	public String getProperty(String name);

}
