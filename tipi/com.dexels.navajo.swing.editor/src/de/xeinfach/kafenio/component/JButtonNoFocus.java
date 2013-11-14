/*
GNU Lesser General Public License

JButtonNoFocus
Copyright (C) 2000-2003 Howard Kistler
changes to JButtonNoFocus
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
package de.xeinfach.kafenio.component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a JButton that does not obtain focus
 * @author Howard Kistler
 */
public class JButtonNoFocus extends JButton {

	private static LeanLogger log = new LeanLogger("JButtonNoFocus.class");
	
	/**
	 * creates a new JButtonNoFocus Object
	 */
	public JButtonNoFocus() {
		super();
		this.setRequestFocusEnabled(false);
	}

	/**
	 * creates a new JButtonNoFocus Object
	 * @param a an Action for this button
	 */
	public JButtonNoFocus(Action a) {
		super(a);
		this.setRequestFocusEnabled(false);
	}
	
	/**
	 * creates a new JButtonNoFocus Object
	 * @param icon an icon for this button
	 */
	public JButtonNoFocus(Icon icon) {
		super(icon);
		this.setRequestFocusEnabled(false);
	}
	
	/**
	 * creates a new JButtonNoFocus Object
	 * @param text tooltip-text for this button
	 */
	public JButtonNoFocus(String text) {
		super(text);
		this.setRequestFocusEnabled(false);
	}
	
	/**
	 * creates a new JButtonNoFocus Object
	 * @param text tooltip-text for this button
	 * @param icon an icon for this button
	 */
	public JButtonNoFocus(String text, Icon icon) {
		super(text, icon);
		this.setRequestFocusEnabled(false);
	}

	/**
	 * @return returns always false. (it's JButtonNOFOCUS) remember?)
	 */
	public boolean isFocusable() {
		return false;
	}
}
