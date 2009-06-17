/*
GNU Lesser General Public License

JToggleButtonNoFocus
Copyright (C) 2000-2003 Howard Kistler
changes to JToggleButtonNoFocus
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
import javax.swing.JToggleButton;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a JToggleButton that does not obtain focus
 * @author Howard Kistler
 */
public class JToggleButtonNoFocus extends JToggleButton {

	private static LeanLogger log = new LeanLogger("JToggleButtonNoFocus.class");
	
	/**
	 * creates a new JToggleButtonNoFocus Object.
	 */
	public JToggleButtonNoFocus() {
		super();
		this.setRequestFocusEnabled(false);
	}

	/**
	 * creates a new JToggleButtonNoFocus Object.
	 * @param a button-Action
	 */
	public JToggleButtonNoFocus(Action a) {
		super(a);
		this.setRequestFocusEnabled(false);
	}

	/**
	 * creates a new JToggleButtonNoFocus Object.
	 * @param icon button-icon
	 */
	public JToggleButtonNoFocus(Icon icon) {
		super(icon);
		this.setRequestFocusEnabled(false);
	}
	
	/**
	 * creates a new JToggleButtonNoFocus Object.
	 * @param text tooltip-text
	 */
	public JToggleButtonNoFocus(String text) {
		super(text);
		this.setRequestFocusEnabled(false);
	}
	
	/**
	 * creates a new JToggleButtonNoFocus Object.
	 * @param text tooltip-text
	 * @param icon button-icon
	 */
	public JToggleButtonNoFocus(String text, Icon icon) {
		super(text, icon);
		this.setRequestFocusEnabled(false);
	}

	/**
	 * @return always returns false.
	 */
	public boolean isFocusable() {
		return false;
	}
}
