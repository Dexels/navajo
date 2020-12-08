/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
