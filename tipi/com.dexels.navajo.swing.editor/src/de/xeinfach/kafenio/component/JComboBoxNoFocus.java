/*
GNU Lesser General Public License

JComboBoxNoFocus
Copyright (C) 2000-2003 Howard Kistler
changes to JComboBoxNoFocus
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

import javax.swing.JComboBox;

import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a JComboBox that does not obtain focus
 * @author Howard Kistler
 */
public class JComboBoxNoFocus extends JComboBox {
	
	private static LeanLogger log = new LeanLogger("JComboBoxNoFocus.class");
	
	/**
	 * constructs a new JComboboxNoFocus
	 */
	public JComboBoxNoFocus() {
		super();
		this.setRequestFocusEnabled(false);
		log.debug("new JComboBoxNoFocus created.");
	}

	/**
	 * @return always returns false.
	 */
	public boolean isFocusable() {
		return false;
	}
}
