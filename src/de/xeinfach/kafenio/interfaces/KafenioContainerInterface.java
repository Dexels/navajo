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
