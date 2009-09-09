package de.xeinfach.kafenio.interfaces;

/**
 * Description: This interface describes the
 * methods to save content by posting content to a URL
 * 
 * @author Karsten Pawlik
 */
public interface KafenioControllerSaveContentInterface {
	/**
	 * method that handles the saving of the applet contents.
	 * @return returns true if save was ok, false otherwise.
	 */
	public boolean saveAppletContents();
	
	/**
	 * method that calls the saveAppletContents method on all registered
	 * applets in the observer.
	 */
	public void saveAllAppletsContent();
}
