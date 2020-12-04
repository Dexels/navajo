/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package de.xeinfach.kafenio.interfaces;

import javax.swing.JMenuBar;

/**
 * This is an interface for a containter that can contain a KafenioPanel
 * @author Karsten Pawlik
 */
public interface KafenioContainerInterface {
	
	/**
	 * method for popping the containers rootPane into another container and back.
	 */
	public abstract void detachFrame();

	/**
	 * method that sets the menubar for the object that implements this interface.
	 * @param newMenuBar a menubar component.
	 */
	public abstract void setJMenuBar(JMenuBar newMenuBar);
}
